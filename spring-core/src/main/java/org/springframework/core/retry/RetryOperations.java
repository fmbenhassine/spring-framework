package org.springframework.core.retry;

import java.util.concurrent.Callable;

public interface RetryOperations {

	/**
	 * Retry the given callback (according to the retry policy configured at the implementation level)
	 * until it succeeds or eventually throw an exception if the retry policy is exhausted.
	 * @param retryCallback the callback to call and retry
	 * @return the callback's result
	 * @param <R> the type of the callback's result
	 * @throws Exception if the retry policy is exhausted
	 */
    <R> R execute(Callable<R> retryCallback) throws Exception;

}
