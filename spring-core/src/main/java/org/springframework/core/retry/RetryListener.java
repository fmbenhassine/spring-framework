package org.springframework.core.retry;

public interface RetryListener {

	/**
	 * Called before every retry attempt
	 */
    default void beforeRetry() {
    }

	/**
	 * Called after the callback has successfully been called
	 * @param result the result for the callback
	 * @param <T> the type of the result
	 */
    default <T> void onSuccess(T result) {
    }

	/**
	 * Called every time a retry attempt has failed
	 * @param exception the exception thrown by the callback
	 */
    default void onFailure(Exception exception) {
    }

	/**
	 * Called once the retry policy is exhausted
	 * @param exception the final exception thrown by the callback
	 */
    default void onMaxAttempts(Exception exception) {
    }

}