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

package by.salin.apps.doer.base;

import by.salin.apps.doer.utils.callbacks.LifeCycleCallback;

import java.util.concurrent.ExecutionException;

/**
 * Created by Alexandr.Salin on 21.12.13.
 */
public abstract class CycleManager implements LifeCycleCallback, Runnable
{
	private boolean isFinish = false;

	public CycleManager()
	{
		onCreate();
	}

	@Override
	public void run()
	{
		onStart();
		try
		{
			while (!isFinish)
			{
				onRunIteration();
			}
		}
		catch (Exception e)
		{
			//maybe alert event
		}
		finally
		{
			onStop();
			onDestroy();
		}
	}

	@Override
	public void onCreate()
	{
		//
	}

	@Override
	public void onStart()
	{

	}

	@Override
	public void onRunIteration() throws InterruptedException, ExecutionException
	{

	}

	@Override
	public void onStop()
	{

	}

	@Override
	public void onDestroy()
	{

	}

	@Override
	public void finish()
	{
		isFinish = true;
	}
}
