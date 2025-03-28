package org.springframework.core.retry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.retry.support.NeverRetryPolicy;
import org.springframework.core.retry.support.backoff.ImmediateBackOffPolicy;

import java.time.Duration;
import java.util.concurrent.Callable;

public class RetryTemplate implements RetryOperations {

	private static final Log log = LogFactory.getLog(RetryTemplate.class);

    private RetryPolicy retryPolicy = new NeverRetryPolicy();

    private BackOffPolicy backOffPolicy = new ImmediateBackOffPolicy();

    private RetryListener retryListener = new RetryListener() {};

    @Override
    public <R> R execute(Callable<R> retryCallback) throws Exception {
        int attempts = 0;
        int maxAttempts = retryPolicy.getMaxAttempts();
        while(attempts++ <= maxAttempts) {
			if (log.isDebugEnabled()){
				log.debug("Retry attempt #" + attempts);
			}
			try {
                retryListener.beforeRetry();
                R result = retryCallback.call();
                retryListener.onSuccess(result);
				if (log.isDebugEnabled()){
					log.debug("Retry attempt #" + attempts + " succeeded");
				}
				return result;
            } catch (Exception e) {
                retryListener.onFailure(e);
                Duration duration = backOffPolicy.backOff();
                Thread.sleep(duration.toMillis());
				if (log.isDebugEnabled()){
					log.debug("Attempt #" + attempts + " failed, backing off for " + duration.toMillis() + "ms");
				}
				if (attempts >= maxAttempts) {
					if (log.isDebugEnabled()){
						log.debug("Maximum retry attempts " + attempts + " exhausted, aborting execution");
					}
					retryListener.onMaxAttempts(e);
                    throw e;
                }
            }
        }
        return null;
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
