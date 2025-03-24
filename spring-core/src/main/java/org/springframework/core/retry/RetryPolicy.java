package org.springframework.core.retry;

public interface RetryPolicy {

    /**
     * Return the maximum number of attempts to retry
     *
     * @return the maximum number of attempts to retry
     */
    int getMaxAttempts();

}