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

import android.os.Bundle;
import by.salin.apps.doer.utils.callbacks.ProgressCallback;
import by.salin.apps.doer.utils.tasks.IComplexTask;
import by.salin.apps.doer.utils.tasks.ITask;
import by.salin.apps.doer.utils.tasks.ITaskResult;
import by.salin.apps.doer.utils.tasks.status.FaildStatus;
import by.salin.apps.doer.utils.tasks.status.SeccesStatus;


/**
 * Created by Alexander.Salin on 03.12.13.
 */
public class ComplexTask implements IComplexTask
{
	private final Object id;
	protected ProgressCallback progressCallback;
	private ITask[] taskQueue;
	private Bundle params = null;

	public ComplexTask(Object id, ITask[] taskQueue, Bundle params)
	{
		this(id, taskQueue);
		this.params = params;
	}

	public ComplexTask(Object id, ITask[] taskQueue)
	{
		this.id = id;
		if (taskQueue == null || taskQueue.length == 0)
		{
			throw new IllegalArgumentException("Queue of tasks must be not empty");
		}
		if (!checkProgressPartsValid(taskQueue))
		{
			throw new IllegalArgumentException("Progress part for task should be set up, or sum of parts not equals 1. For example for three tasks parts may be set in [0.5,0.2,0.3]");
		}
		this.taskQueue = taskQueue;

	}

	private boolean checkProgressPartsValid(ITask[] taskQueue)
	{
		boolean result = true;
		float sum = 0;
		for (ITask task : taskQueue)
		{
			sum += task.getProgressPart();
		}
		if (sum != 1.0f)
		{
			result = false;
		}
		return result;
	}

	@Override
	public ITaskResult call() throws Exception
	{
		ITaskResult result = new TaskResult();
		result.setStatus(new SeccesStatus());
		try
		{
			Bundle executingParams = params;
			for (ITask task : taskQueue)
			{
				task.setInputParams(executingParams);
				task.setProgressCallback(this);
				ITaskResult intermediateResult = task.call();
				if (intermediateResult.getStatus() instanceof FaildStatus)
				{
					Throwable throwable = (Throwable) intermediateResult.getStatus().getData();
					throw new IllegalStateException("Child task return failed status", throwable);
				}
				//update progress after every task, if task not implemented this use case
				onProgressUpdate(task.getId(), 1);
				executingParams = task.getOutputParams();
			}
			result.getStatus().setData(executingParams);
		}
		catch (Exception e)
		{
			rollback();
			result.setStatus(new FaildStatus(e));
		}
		return result;
	}

	@Override
	public void rollback()
	{
		Bundle executingParams = params;
		for (ITask task : taskQueue)
		{
			task.setInputParams(executingParams);
			task.rollback();
			executingParams = task.getOutputParams();
		}
	}

	@Override
	public void setInputParams(Bundle params)
	{
		//TODO
	}

	@Override
	public Bundle getOutputParams()
	{
		//TODO
		return null;
	}

	@Override
	public Object getId()
	{
		return id;
	}

	@Override
	public void onProgressUpdate(Object id, float progress)
	{
		float curProgress = 0;
		for (ITask task : taskQueue)
		{
			if (task.getId().equals(id))
			{
				curProgress += task.getProgressPart() * progress;
				break;
			}
			else
			{
				curProgress += task.getProgressPart();
			}
		}
		if (progressCallback != null)
		{
			progressCallback.onProgressUpdate(getId(), curProgress);
		}
	}

	@Override
	final public float getProgressPart()
	{
		return 1;
	}

	@Override
	final public void setProgressPart(float progressMax)
	{
		//TODO do not use in this implementation
	}

	public void setProgressCallback(ProgressCallback progressCallback)
	{
		this.progressCallback = progressCallback;
	}
}
