package org.springframework.core.retry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.retry.support.MaxAttemptsRetryPolicy;
import org.springframework.core.retry.support.backoff.LinearBackOffPolicy;

import java.util.concurrent.Callable;

class RetryTemplateTests {

	@Test
	void testRetry() {
		// given some unreliable code
		Callable<String> callable = new Callable<>() {
			int failure;
			@Override
			public String call() throws Exception {
				if (failure++ < 3) {
					throw new Exception("Error while invoking service");
				}
				return "hello world";
			}
		};
		// and a configured retry template
		RetryTemplate template = new RetryTemplate();
		template.setRetryPolicy(new MaxAttemptsRetryPolicy(5));
		template.setBackOffPolicy(new LinearBackOffPolicy());

		// when
		try {
			String result = template.execute(callable);
			// then
			Assertions.assertEquals("hello world", result);
			System.out.println("result = " + result);
		} catch (Exception e) {
			System.err.println("Retry exhausted due to: " + e.getMessage());
		}
	}

	@Test
	void testRecovery() {
		// given some unreliable code
		Callable<String> callable = new Callable<>() {
			int failure;
			@Override
			public String call() throws Exception {
				if (failure++ < 3) {
					throw new Exception("Error while invoking service");
				}
				return "hello world";
			}
		};
		// and a recovery callback
		RecoveryCallback<String> recoveryCallback = exception -> "default result";
		// and a configured retry template
		RetryTemplate template = new RetryTemplate();
		template.setRetryPolicy(new MaxAttemptsRetryPolicy(2));
		template.setBackOffPolicy(new LinearBackOffPolicy());

		// when
		try {
			String result = template.execute(callable, recoveryCallback);
			// then
			Assertions.assertEquals("default result", result);
			System.out.println("result = " + result);
		} catch (Exception e) {
			System.err.println("Retry exhausted due to: " + e.getMessage());
		}
	}

}