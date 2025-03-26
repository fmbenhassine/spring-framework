package org.springframework.core.retry;

import java.util.concurrent.Callable;

/**
 * Callback interface for an operation that can be retried using a
 * {@link RetryOperations}.
 */
public interface RetryCallback<R> extends Callable<R> {

}