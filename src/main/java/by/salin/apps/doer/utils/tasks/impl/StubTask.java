/*
 * Copyright 2013 Alexandr Salin
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

package by.salin.apps.doer.utils.tasks.impl;

import android.util.Log;
import by.salin.apps.doer.utils.tasks.ITaskResult;
import by.salin.apps.doer.utils.tasks.status.FaildStatus;
import by.salin.apps.doer.utils.tasks.status.SeccesStatus;

/**
 * Created by Alexander.Salin on 05.12.13.
 */
public class StubTask extends BaseTask
{
	private static final String TAG = StubTask.class.getSimpleName();
	private int duration;

	public StubTask(Object id, int duration)
	{
		super(id);
		this.duration = duration;
		Log.i(TAG, "create stubtask with id:" + id.toString());
	}

	public StubTask()
	{
		this(new Object(), 5000);
	}

	@Override
	public void rollback()
	{
		Log.i(TAG, "rollback:" + getId().toString());
	}

	@Override
	public ITaskResult call()
	{
		Log.i(TAG, "start:" + getId().toString());
		ITaskResult result = new TaskResult(getId());
		result.setStatus(new SeccesStatus());
		for (int i = 0; i < 100; i++)
		{
			try
			{
				Thread.sleep(duration);
			}
			catch (InterruptedException e)
			{
				//TODO add logger
				result.setStatus(new FaildStatus(e));
			}
			onProgressUpdate(getId(), (float) i / 100);
		}
		Log.i(TAG, "finish:" + getId().toString());
		return result;
	}
}
