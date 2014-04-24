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
 * Created by alexander.salin on 06.12.13.
 */
public class DoerConfig
{
	public final int threadPoolSize;
	public final int threadPriority;
	public final int timeAwaitTerminate;

	public DoerConfig(final Builder builder){
		threadPoolSize = builder.threadPoolSize;
		threadPriority = builder.threadPriority;
		timeAwaitTerminate = builder.timeAwaitTerminate;
	}
	public static DoerConfig createDefault() {
		return new Builder().build();
	}
	public static class Builder
	{
		public static final int DEFAULT_THREAD_POOL_SIZE = 3;

		public static final int DEFAULT_THREAD_PRIORITY = Thread.NORM_PRIORITY - 1;

		public static final int DEFAULT_TIME_AWAIT_TERMINATE = 60;

		private int threadPoolSize = DEFAULT_THREAD_POOL_SIZE;
		private int threadPriority = DEFAULT_THREAD_PRIORITY;
		private int timeAwaitTerminate = DEFAULT_TIME_AWAIT_TERMINATE;

		public Builder(){

		}

		public Builder setThreadPoolSize(int threadPoolSize){
			this.threadPoolSize = threadPoolSize;
			return this;
		}

		public Builder setThreadPriority(int threadPriority){
			this.threadPriority = threadPriority;
			return this;
		}

		public Builder setTimeAwaitTerminate(int timeAwaitTerminate){
			this.timeAwaitTerminate = timeAwaitTerminate;
			return this;
		}

		public DoerConfig build(){
			return new DoerConfig(this);
		}
	}
}
