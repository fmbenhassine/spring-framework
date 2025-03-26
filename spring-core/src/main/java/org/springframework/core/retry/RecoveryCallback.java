package org.springframework.core.retry;

/**
 * Callback for recovery code to execute after all retry attempts are exhausted.
 * @param <R> the type that is returned from the recovery
 */
public interface RecoveryCallback<R> {

	/**
	 * @param exception the last exception thrown from the retry callback
	 * @return an object that can be used to replace the callback result that failed
	 */
	R recover(Exception exception);

}