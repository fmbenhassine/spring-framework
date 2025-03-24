package org.springframework.core.retry.support.backoff;

import org.springframework.core.retry.BackOffPolicy;

import java.time.Duration;

public class ExponentialBackOffPolicy implements BackOffPolicy {

	private Duration duration = Duration.ofSeconds(1);

	@Override
	public Duration backOff() {
		this.duration = this.duration.multipliedBy(2);
		return this.duration;
	}

}