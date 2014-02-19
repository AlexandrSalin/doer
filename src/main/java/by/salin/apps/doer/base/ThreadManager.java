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

import android.util.Log;
import by.salin.apps.doer.DoerConfig;
import by.salin.apps.doer.utils.callbacks.IterationCallBack;
import by.salin.apps.doer.utils.tasks.ITaskResult;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by root on 21.12.13.
 */
public abstract class ThreadManager extends CycleManager implements IterationCallBack<ITaskResult>
{

	public static final String TAG = ThreadManager.class.getSimpleName();
	private static final int EXECUTER_SHUTDOWN_TIMOUT = 60;
	private static DoerConfig config;
	private Map<Object, Future<ITaskResult>> runnablesMap;
	private LinkedBlockingQueue<Runnable> workingQueue;
	private ExecutorService executor;
	private ExecutorCompletionService<ITaskResult> executorCompletionService;

	@Override
	public void onCreate()
	{
		super.onCreate();

		if (config == null)
		{
			config = new DoerConfig.Builder().setThreadPoolSize(5).build();
		}
		//FIXME what???
		runnablesMap = Collections.synchronizedMap(new HashMap<Object, Future<ITaskResult>>());

		workingQueue = new LinkedBlockingQueue<Runnable>();
		executor = new ThreadPoolExecutor(config.threadPoolSize, 128, 0, TimeUnit.SECONDS, workingQueue);
		executorCompletionService = new ExecutorCompletionService<ITaskResult>(executor);
	}

	@Override
	final public void onRunIteration() throws InterruptedException, ExecutionException
	{
		super.onRunIteration();
		final ITaskResult result = executeIteration();
		handleResult(result);
	}

	@Override
	final public ITaskResult executeIteration() throws InterruptedException, ExecutionException
	{
		Future<ITaskResult> future = executorCompletionService.take();
		synchronized (runnablesMap)
		{
			for (Map.Entry<Object, Future<ITaskResult>> entry : runnablesMap.entrySet())
			{
				if (entry.getValue().equals(future))
				{
					runnablesMap.remove(entry.getKey());
					break;
				}
			}
		}
		return future.get();
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		Log.i(TAG, "Destroying ThreadManager");
		// interrupt any running tasks
		workingQueue.clear();
		runnablesMap.clear();
		shutdownAndAwaitTermination(executor);
	}

	/**
	 * http://docs.oracle.com/javase/7/docs/api/java/util/concurrent/ExecutorService.html
	 *
	 * @param executor
	 */
	private void shutdownAndAwaitTermination(ExecutorService executor)
	{
		executor.shutdown(); // Disable new tasks from being submitted
		try
		{
			// Wait a while for existing tasks to terminate
			if (!executor.awaitTermination(EXECUTER_SHUTDOWN_TIMOUT, TimeUnit.SECONDS))
			{
				executor.shutdownNow(); // Cancel currently executing tasks
				// Wait a while for tasks to respond to being cancelled
				if (!executor.awaitTermination(EXECUTER_SHUTDOWN_TIMOUT, TimeUnit.SECONDS))
				{
					Log.e(TAG, "Pool did not terminate");
				}
			}
		}
		catch (InterruptedException ie)
		{
			// (Re-)Cancel if current thread also interrupted
			executor.shutdownNow();
			// Preserve interrupt status
			Thread.currentThread().interrupt();
		}
	}

	protected Map<Object, Future<ITaskResult>> getQueue()
	{
		return runnablesMap;
	}

	protected ExecutorCompletionService<ITaskResult> getExecuterCompletionService()
	{
		return executorCompletionService;
	}

}
