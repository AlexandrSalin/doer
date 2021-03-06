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

package by.salin.apps.doer.utils.events;

import by.salin.apps.doer.utils.tasks.ITask;
import by.salin.apps.jems.impl.Event;

/**
 * Created by Alexandr.Salin on 22.12.13.
 */
public class TaskAddFailedEvent extends Event implements IDHandlerEvent
{
	private ITask task;

	public TaskAddFailedEvent(ITask task)
	{
		this.task = task;
	}

	public ITask getTask()
	{
		return task;
	}

	@Override
	public Object getId()
	{
		return task.getId();
	}
}
