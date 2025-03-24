package org.springframework.core.retry.support.backoff;

import org.springframework.core.retry.BackOffPolicy;

import java.time.Duration;

public class FixedBackOffPolicy implements BackOffPolicy {

	private Duration duration = Duration.ofSeconds(1);

	public FixedBackOffPolicy(Duration duration) {
		this.duration = duration;
	}

	@Override
	public Duration backOff() {
		return this.duration;
	}

}