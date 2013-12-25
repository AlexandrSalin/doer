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
import android.util.Log;
import by.salin.apps.doer.utils.callbacks.ProgressCallback;
import by.salin.apps.doer.utils.tasks.ITask;

/**
 * Created by alexander.salin on 05.12.13.
 */
public abstract class BaseTask implements ITask
{
	private static final String TAG = BaseTask.class.getSimpleName();
	protected ProgressCallback progressCallback;
	private Object id;
	private Bundle params;
	private float progressPart = 0;

	public BaseTask(Object id)
	{
		this.id = id;
	}

	@Override
	public void setInputParams(Bundle params)
	{
		this.params = params;
	}

	@Override
	public Bundle getOutputParams()
	{
		return params;
	}

	@Override
	public Object getId()
	{
		return id;
	}

	@Override
	public void onProgressUpdate(Object id, float progress)
	{
		if (progressCallback != null)
		{
			Log.i(TAG, "progress:" + getId().toString() + " ------ " + progress);
			progressCallback.onProgressUpdate(id, progress);
		}
	}

	@Override
	public float getProgressPart()
	{
		return progressPart;
	}

	@Override
	public void setProgressPart(float progressMax)
	{
		this.progressPart = progressMax;
	}

	public ProgressCallback getProgressCallback()
	{
		return progressCallback;
	}

	public void setProgressCallback(ProgressCallback progressCallback)
	{
		this.progressCallback = progressCallback;
	}
}
