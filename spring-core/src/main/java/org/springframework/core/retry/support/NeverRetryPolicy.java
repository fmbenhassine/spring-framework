package org.springframework.core.retry.support;

import org.springframework.core.retry.RetryPolicy;

public class NeverRetryPolicy implements RetryPolicy {

	@Override
	public int getMaxAttempts() {
		return 0;
	}

}