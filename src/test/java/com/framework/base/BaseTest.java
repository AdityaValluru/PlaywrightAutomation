package com.framework.base;

import com.aventstack.extentreports.Status;
import com.framework.drivers.BrowserManager;
import com.framework.reports.ExtentReportManager;
import com.framework.utils.ConfigReader;
import com.framework.utils.PlaywrightUtils;
import com.framework.utils.ScreenshotUtils;
import com.microsoft.playwright.Tracing;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * Base class for all test classes.
 * <p>
 * Provides {@code @BeforeSuite / @AfterSuite} hooks for Extent Reports
 * and {@code @BeforeMethod / @AfterMethod} hooks for browser lifecycle
 * management. Every test class must extend {@code BaseTest}.
 * </p>
 *
 * <p>Execution order per test:</p>
 * <ol>
 *   <li>{@link #setUpSuite()} — once per suite (init reports)</li>
 *   <li>{@link #setUp(Method)} — once per test method (launch browser, navigate to base URL)</li>
 *   <li><em>test method runs</em></li>
 *   <li>{@link #tearDown(ITestResult)} — once per test method (screenshot on failure, close browser)</li>
 *   <li>{@link #tearDownSuite()} — once per suite (flush reports)</li>
 * </ol>
 */
public class BaseTest {

    private static final Logger log = LogManager.getLogger(BaseTest.class);

    // =========================================================================
    // Suite-level lifecycle
    // =========================================================================

    /**
     * Initialises the Extent Report once before any test in the suite runs.
     */
    @BeforeSuite(alwaysRun = true)
    public void setUpSuite() {
        ExtentReportManager.initReports();
        log.info("==================== TEST SUITE STARTED ====================");
    }

    /**
     * Flushes the Extent Report to disk after all tests in the suite have run.
     */
    @AfterSuite(alwaysRun = true)
    public void tearDownSuite() {
        ExtentReportManager.flushReports();
        log.info("==================== TEST SUITE COMPLETED ====================");
    }

    // =========================================================================
    // Test-level lifecycle
    // =========================================================================

    /**
     * Launches the browser and navigates to the application base URL before
     * each test method.
     *
     * @param method the test method about to run (injected by TestNG)
     */
    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method) {
        BrowserManager.initBrowser();
        
        if (Boolean.parseBoolean(ConfigReader.get("trace.enabled", "false"))) {
            BrowserManager.getContext().tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(true));
        }

        PlaywrightUtils.navigateTo(ConfigReader.get("base.url"));
        log.info(">>> Test started: [{}]", method.getName());
    }

    /**
     * Handles post-test clean-up after each test method:
     * <ul>
     *   <li>Captures and attaches a failure screenshot to the report.</li>
     *   <li>Marks the Extent test node as passed / failed / skipped.</li>
     *   <li>Closes the browser and removes thread-local resources.</li>
     * </ul>
     *
     * @param result TestNG result object for the just-completed test method
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        try {
            if (ExtentReportManager.getTest() != null) {
                switch (result.getStatus()) {
                    case ITestResult.FAILURE:
                        captureAndAttachScreenshot(result.getName());
                        ExtentReportManager.getTest().log(Status.FAIL, result.getThrowable());
                        log.error("<<< Test FAILED: [{}]", result.getName());
                        break;

                    case ITestResult.SKIP:
                        String skipReason = (result.getThrowable() != null)
                                ? result.getThrowable().getMessage() : "No reason provided";
                        ExtentReportManager.getTest().log(Status.SKIP, "Test skipped: " + skipReason);
                        log.warn("<<< Test SKIPPED: [{}]", result.getName());
                        break;

                    default: // PASS
                        ExtentReportManager.getTest().log(Status.PASS, "Test passed successfully");
                        log.info("<<< Test PASSED: [{}]", result.getName());
                        break;
                }
            }
        } finally {
            if (BrowserManager.getContext() != null && Boolean.parseBoolean(ConfigReader.get("trace.enabled", "false"))) {
                boolean retainOnFailure = Boolean.parseBoolean(ConfigReader.get("trace.retain.on.failure", "true"));
                boolean isFailure = (result.getStatus() == ITestResult.FAILURE);
                if (!retainOnFailure || isFailure) {
                    BrowserManager.getContext().tracing().stop(new Tracing.StopOptions()
                            .setPath(Paths.get("target/traces/" + result.getName() + "-trace.zip")));
                } else {
                    BrowserManager.getContext().tracing().stop();
                }
            }

            ExtentReportManager.removeTest();
            BrowserManager.closeBrowser();
            log.info("--- Browser closed for test: [{}] ---", result.getName());
        }
    }

    // =========================================================================
    // Private helpers
    // =========================================================================

    /**
     * Captures a viewport screenshot and attaches it to the current Extent test node
     * both as a Base64 inline image and saves a physical file to disk.
     *
     * @param testName name of the failed test (used in the file name)
     */
    private void captureAndAttachScreenshot(String testName) {
        try {
            byte[] screenshotBytes = PlaywrightUtils.takeScreenshot();
            ScreenshotUtils.saveScreenshot(screenshotBytes, testName);
            ExtentReportManager.getTest().addScreenCaptureFromBase64String(
                    Base64.getEncoder().encodeToString(screenshotBytes),
                    "Failure Screenshot — " + testName);
        } catch (Exception e) {
            log.warn("Could not capture screenshot for test '{}': {}", testName, e.getMessage());
        }
    }
}
