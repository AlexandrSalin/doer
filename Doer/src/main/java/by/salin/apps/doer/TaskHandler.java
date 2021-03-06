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

package by.salin.apps.doer;

/**
 * Created by Alexandr.Salin on 21.12.13.
 */
public interface TaskHandler<T>
{
	/**
	 * Add new task in queue for execution
	 *
	 * @param task , in task required id field for identification,
	 *             if id will be null throw IllegalArgumentException
	 */
	void addTask(T task);

	void onFailedAddTask(T task);

	/**
	 * Check task on exists in queue
	 *
	 * @param taskId
	 * @return true if task with the Id already exists in queue, false otherwise
	 */
	boolean isTaskExists(Object taskId);

	void removeTask(Object taskId);
}
