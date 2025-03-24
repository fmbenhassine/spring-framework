package org.springframework.core.retry.support.backoff;

import org.springframework.core.retry.BackOffPolicy;

import java.time.Duration;

public class ImmediateBackOffPolicy implements BackOffPolicy {

	@Override
	public Duration backOff() {
		return Duration.ZERO;
	}

}