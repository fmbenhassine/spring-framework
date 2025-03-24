package org.springframework.core.retry;

import java.time.Duration;

public interface BackOffPolicy {

    /**
     * Signal how long to backoff before the next retry attempt.
     *
     * @return the duration to wait for before the next retry attempt
     */
    Duration backOff();

}