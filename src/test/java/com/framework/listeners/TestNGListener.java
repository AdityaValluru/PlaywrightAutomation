package com.framework.listeners;

import com.aventstack.extentreports.Status;
import com.framework.reports.ExtentReportManager;
import com.framework.utils.PlaywrightUtils;
import com.framework.utils.ScreenshotUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Arrays;
import java.util.Base64;

/**
 * TestNG listener that integrates the test lifecycle with Extent Reports and logging.
 * <p>
 * Register this listener in each {@code testng*.xml} file:
 * <pre>
 *   &lt;listeners&gt;
 *     &lt;listener class-name="com.framework.listeners.TestNGListener"/&gt;
 *   &lt;/listeners&gt;
 * </pre>
 * </p>
 *
 * <p>Responsibilities:</p>
 * <ul>
 *   <li>Creates an {@link com.aventstack.extentreports.ExtentTest} node per test.</li>
 *   <li>Captures a screenshot and attaches it to the report on test failure.</li>
 *   <li>Logs suite and test lifecycle events via Log4j2.</li>
 * </ul>
 */
public class TestNGListener implements ITestListener, ISuiteListener {

    private static final Logger log = LogManager.getLogger(TestNGListener.class);

    // =========================================================================
    // ISuiteListener
    // =========================================================================

    @Override
    public void onStart(ISuite suite) {
        log.info("======== Suite Started: '{}' ========", suite.getName());
    }

    @Override
    public void onFinish(ISuite suite) {
        log.info("======== Suite Finished: '{}' ========", suite.getName());
    }

    // =========================================================================
    // ITestListener
    // =========================================================================

    /**
     * Creates a new Extent test node when a test method starts.
     * Associates it with the test's declared groups (categories).
     */
    @Override
    public void onTestStart(ITestResult result) {
        String   testName   = result.getMethod().getMethodName();
        String   desc       = result.getMethod().getDescription();
        String[] groups     = result.getMethod().getGroups();

        log.info(">>> Test Started: [{}] | Groups: {}", testName, Arrays.toString(groups));

        ExtentReportManager.createTest(
                testName,
                (desc != null && !desc.isEmpty()) ? desc : testName,
                groups);

        ExtentReportManager.getTest()
                .assignAuthor(result.getTestClass().getRealClass().getSimpleName())
                .log(Status.INFO, "Test started | Groups: " + Arrays.toString(groups));
    }

    /**
     * Marks the Extent test as passed.
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("<<< Test PASSED: [{}]", result.getMethod().getMethodName());
        if (ExtentReportManager.getTest() != null) {
            ExtentReportManager.getTest().log(Status.PASS, "Test passed successfully");
        }
    }

    /**
     * Captures a failure screenshot, attaches it to the report, and marks the
     * Extent test as failed.
     */
    @Override
    public void onTestFailure(ITestResult result) {
        log.error("<<< Test FAILED: [{}] | Cause: {}",
                result.getMethod().getMethodName(),
                result.getThrowable() != null ? result.getThrowable().getMessage() : "unknown");

        if (ExtentReportManager.getTest() != null) {
            try {
                byte[] screenshot = PlaywrightUtils.takeScreenshot();
                ScreenshotUtils.saveScreenshot(screenshot, result.getName());
                ExtentReportManager.getTest()
                        .addScreenCaptureFromBase64String(
                                Base64.getEncoder().encodeToString(screenshot),
                                "Failure Screenshot")
                        .log(Status.FAIL, result.getThrowable());
            } catch (Exception e) {
                log.warn("Could not capture screenshot for '{}': {}", result.getName(), e.getMessage());
                ExtentReportManager.getTest().log(Status.FAIL, result.getThrowable());
            }
        }
    }

    /**
     * Marks the Extent test as skipped.
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("<<< Test SKIPPED: [{}]", result.getMethod().getMethodName());
        if (ExtentReportManager.getTest() != null) {
            Throwable cause = result.getThrowable();
            if (cause != null) {
                ExtentReportManager.getTest().log(Status.SKIP, cause);
            } else {
                ExtentReportManager.getTest().log(Status.SKIP, "Test was skipped");
            }
        }
    }

    /**
     * Handles tests that failed but within the defined success-percentage threshold.
     */
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        log.warn("Test FAILED within success percentage: [{}]", result.getMethod().getMethodName());
    }
}
