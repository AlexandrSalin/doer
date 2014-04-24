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

import by.salin.apps.doer.utils.tasks.ITaskResult;
import by.salin.apps.doer.utils.tasks.ITaskStatus;

/**
 * base implementation for TaskResult
 * Created by alexandr.salin on 03.12.13.
 */
public final class TaskResult implements ITaskResult
{
	private final Object _id;
	ITaskStatus status;

	public TaskResult(Object id)
	{
		this._id = id;
	}

	public TaskResult(Object id, ITaskStatus status)
	{
		this(id);
		this.status = status;
	}

	@Override
	public ITaskStatus getStatus()
	{
		return status;
	}

	@Override
	public void setStatus(ITaskStatus status)
	{
		this.status = status;
	}

	@Override
	public Object getId()
	{
		return _id;
	}

}
