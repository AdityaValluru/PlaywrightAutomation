package com.framework.listeners;

import com.framework.constants.FrameworkConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * TestNG retry analyser that re-runs a failed test up to
 * {@link FrameworkConstants#MAX_RETRY_COUNT} additional times.
 * <p>
 * Apply at the test level:
 * <pre>
 *   {@literal @}Test(retryAnalyzer = RetryAnalyzer.class)
 *   public void myTest() { ... }
 * </pre>
 * </p>
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    private static final Logger log = LogManager.getLogger(RetryAnalyzer.class);

    /** Current attempt count for the test being evaluated. */
    private int retryCount = 0;

    /**
     * Called by TestNG after a test failure to determine whether the test
     * should be retried.
     *
     * @param result the result of the just-failed test
     * @return {@code true} if the test should be retried; {@code false} otherwise
     */
    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < FrameworkConstants.MAX_RETRY_COUNT) {
            retryCount++;
            log.warn("Retrying test '{}' — attempt {}/{}", result.getName(),
                    retryCount, FrameworkConstants.MAX_RETRY_COUNT);
            return true;
        }
        log.error("Test '{}' failed after {} retry attempt(s). Marking as failed.",
                result.getName(), FrameworkConstants.MAX_RETRY_COUNT);
        return false;
    }
}
