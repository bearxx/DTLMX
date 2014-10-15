package lmx.phone.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadUtil {
	private static BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(128);

	private static RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();

	private static ThreadPoolExecutor executorService = new ThreadPoolExecutor(10, 32, 0L,
            TimeUnit.MILLISECONDS, blockingQueue, rejectedExecutionHandler);
	
	public ThreadPoolExecutor getExecutor() {
		return executorService;
	}
}
