/*
 *    Copyright 2013 Alexandr Salin
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package by.salin.apps.doer.broker;

import android.util.Log;
import by.salin.apps.doer.Doer;
import by.salin.apps.doer.utils.callbacks.BindCallback;
import by.salin.apps.doer.utils.events.AddNewTaskEvent;
import by.salin.apps.doer.utils.tasks.ITask;
import by.salin.apps.jems.EventHandlerCallback;
import by.salin.apps.jems.JEMS;
import by.salin.apps.jems.impl.Event;

/**
 * Created by Alexandr.Salin on 15.12.13.
 */
public class TaskBroker implements BindCallback, EventHandlerCallback
{
	private static final String TAG = TaskBroker.class.getSimpleName();
	private static TaskBroker instance;
	private Thread thread;
	private Doer executor;
	private boolean isBind = false;

	//add intermediate listener
	private TaskBroker()
	{

	}

	public static void defInit()
	{
		getInstance();
	}

	public static TaskBroker getInstance()
	{
		if (instance == null)
		{
			instance = new TaskBroker();
			instance.init();
			while (!instance.isBind)
			{

			}
		}
		return instance;
	}

	private void init()
	{
		executor = new Doer();
		thread = new Thread(executor);
		executor.setBindListener(this);
		thread.start();
	}

	@Override
	public void onBind()
	{
		Log.i(TAG, "Executor  bind");
		JEMS.dispatcher().addListenerOnEvent(AddNewTaskEvent.class, this);
		isBind = true;
	}

	@Override
	public boolean isBind()
	{
		return isBind;
	}

	@Override
	public void onUnBind()
	{
		Log.i(TAG, "Executor  unbind");
		JEMS.dispatcher().removeListenerOnEvent(AddNewTaskEvent.class, this);
		isBind = false;
	}

	@Override
	public void onEvent(Event event)
	{
		if (event instanceof AddNewTaskEvent)
		{
			ITask task = ((AddNewTaskEvent) event).getTask();
			executor.addTask(task);
		}
	}
}
