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


import by.salin.apps.jems.impl.Event;

/**
 * Event provide information about progress of task,
 * the task identified by id.
 * Created by Alexandr.Salin on 22.12.13.
 */
public class TaskProgressUpdateEvent extends Event implements IDHandlerEvent
{
	private final Object id;
	private final float progress;

	public TaskProgressUpdateEvent(Object id, float progress)
	{
		this.id = id;
		this.progress = progress;
	}

	public Object getId()
	{
		return id;
	}

	public float getProgress()
	{
		return progress;
	}
}
