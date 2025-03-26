package org.springframework.core.retry;

import org.springframework.core.retry.support.NeverRetryPolicy;
import org.springframework.core.retry.support.backoff.ImmediateBackOffPolicy;

import java.time.Duration;
import java.util.concurrent.Callable;

public class RetryTemplate implements RetryOperations {

    private RetryPolicy retryPolicy = new NeverRetryPolicy();

    private BackOffPolicy backOffPolicy = new ImmediateBackOffPolicy();

    private RetryListener retryListener = new RetryListener() {};

    @Override
    public <R> R execute(Callable<R> retryCallback) throws Exception {
        int attempts = 0;
        int maxAttempts = retryPolicy.getMaxAttempts();
        while(attempts++ <= maxAttempts) {
            try {
                retryListener.beforeRetry();
                R result = retryCallback.call();
                retryListener.onSuccess(result);
                return result;
            } catch (Exception e) {
                retryListener.onFailure(e);
                Duration duration = backOffPolicy.backOff();
                Thread.sleep(duration.toMillis());
                if (attempts >= maxAttempts) {
                    retryListener.onMaxAttempts(e);
                    throw e;
                }
            }
        }
        return null;
    }

	@Override
	public <R> R execute(Callable<R> retryCallback, RecoveryCallback<R> recoveryCallback) {
		try {
			return this.execute(retryCallback);
		} catch (Exception exception) {
			return recoveryCallback.recover(exception);
		}
	}

    public void setRetryPolicy(RetryPolicy retryPolicy) {
        this.retryPolicy = retryPolicy;
    }

    public void setBackOffPolicy(BackOffPolicy backOffPolicy) {
        this.backOffPolicy = backOffPolicy;
    }

    public void setRetryListener(RetryListener retryListener) {
        this.retryListener = retryListener;
    }
}
