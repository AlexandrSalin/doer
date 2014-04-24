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

import by.salin.apps.doer.TaskHandler;
import by.salin.apps.doer.utils.callbacks.ProgressCallback;
import by.salin.apps.doer.utils.tasks.ITask;
import by.salin.apps.doer.utils.tasks.ITaskResult;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * Created by Alexandr.Salin on 21.12.13.
 */
public abstract class TaskManager extends ThreadManager implements TaskHandler<ITask>, ProgressCallback
{
	@Override
	public void addTask(ITask task)
	{
		if (task.getId() == null)
		{
			throw new IllegalArgumentException("ComplexTask should have non null id");
		}
		final Map<Object, Future<ITaskResult>> queue = getQueue();
		synchronized (queue)
		{
			if (queue.containsKey(task.getId()))
			{
				onFailedAddTask(task);
				return;
			}

			task.setProgressCallback(this);
			Future<ITaskResult> taskResult = getExecuterCompletionService().submit(task);
			queue.put(task.getId(), taskResult);
		}
	}

	@Override
	public void removeTask(Object taskId)
	{
		final Map<Object, Future<ITaskResult>> queue = getQueue();
		synchronized (queue)
		{
			queue.remove(taskId);
		}
	}

	@Override
	public boolean isTaskExists(Object taskId)
	{
		final Map<Object, Future<ITaskResult>> queue = getQueue();
		synchronized (queue)
		{
			return queue.containsKey(taskId);
		}
	}
}
