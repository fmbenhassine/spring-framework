package org.springframework.core.retry.support;

import org.springframework.core.retry.RetryPolicy;

public class MaxAttemptsRetryPolicy implements RetryPolicy {

	private final int maxAttempts;

	public MaxAttemptsRetryPolicy(int maxAttempts) {
		this.maxAttempts = maxAttempts;
	}

	@Override
	public int getMaxAttempts() {
		return this.maxAttempts;
	}

}