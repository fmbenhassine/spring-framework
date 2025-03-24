package org.springframework.core.retry.support.backoff;

import org.springframework.core.retry.BackOffPolicy;

import java.time.Duration;

public class LinearBackOffPolicy implements BackOffPolicy {

	private Duration duration = Duration.ZERO;

	@Override
	public Duration backOff() {
		this.duration = this.duration.plusSeconds(1);
		return this.duration;
	}

}