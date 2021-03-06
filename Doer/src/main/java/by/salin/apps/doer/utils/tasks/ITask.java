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

package by.salin.apps.doer.utils.tasks;

import android.os.Bundle;
import by.salin.apps.doer.utils.callbacks.PartsProgressCallback;

import java.util.concurrent.Callable;

/**
 * Created by alexander.salin on 03.12.13.
 */
public interface ITask extends Callable<ITaskResult>, PartsProgressCallback
{
	void setInputParams(Bundle params);

	Bundle getOutputParams();

	Object getId();

	void rollback();
}
