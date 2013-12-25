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

import by.salin.apps.doer.utils.callbacks.BindCallback;
import by.salin.apps.doer.utils.callbacks.ProgressCallback;

/**
 * Created by Alexandr.Salin on 14.12.13.
 */
public abstract class BaseManager extends TaskManager
{
	private BindCallback bindListener;

	@Override
	public void onStart()
	{
		super.onStart();
		if (bindListener != null)
		{
			bindListener.onBind();
		}
	}

	@Override
	public void onStop()
	{
		if (bindListener != null)
		{
			bindListener.onUnBind();
		}
		super.onStop();
	}

	public BindCallback getBindListener()
	{
		return bindListener;
	}

	public void setBindListener(BindCallback bindListener)
	{
		this.bindListener = bindListener;
	}

	@Override
	final public void setProgressCallback(ProgressCallback progressCallback)
	{
		//do nothing
	}
}
