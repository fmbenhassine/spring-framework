package org.springframework.core.retry.support;

import org.springframework.core.retry.RetryPolicy;

public class AlwaysRetryPolicy implements RetryPolicy {

	@Override
	public int getMaxAttempts() {
		return Integer.MAX_VALUE;
	}

}