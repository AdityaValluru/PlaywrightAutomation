package com.framework.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.framework.constants.FrameworkConstants;
import com.framework.utils.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Manages the Extent Reports lifecycle for the entire test suite.
 * <p>
 * A single {@link ExtentReports} instance is shared across all threads.
 * Each test thread owns its own {@link ExtentTest} via a {@link ThreadLocal},
 * making this class safe for parallel test execution.
 * </p>
 *
 * <p>Lifecycle (handled by {@code BaseTest} and {@code TestNGListener}):</p>
 * <ol>
 *   <li>{@link #initReports()} — called once in {@code @BeforeSuite}</li>
 *   <li>{@link #createTest(String, String, String...)} — called per test in {@code onTestStart}</li>
 *   <li>{@link #getTest()} — used inside tests and action classes to log steps</li>
 *   <li>{@link #flushReports()} — called once in {@code @AfterSuite}</li>
 * </ol>
 */
public final class ExtentReportManager {

    private static final Logger log = LogManager.getLogger(ExtentReportManager.class);

    private static ExtentReports extentReports;
    private static final ThreadLocal<ExtentTest> testThreadLocal = new ThreadLocal<>();

    /** Prevent instantiation. */
    private ExtentReportManager() {}

    // -------------------------------------------------------------------------
    // Initialisation
    // -------------------------------------------------------------------------

    /**
     * Creates a new Extent HTML report (Spark theme) in the reports directory.
     * Should be called exactly once before any tests run ({@code @BeforeSuite}).
     * <p>
     * The report file name includes a timestamp so that successive runs do not
     * overwrite previous results.
     * </p>
     */
    public static synchronized void initReports() {
        String timestamp  = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String reportPath = FrameworkConstants.REPORTS_DIR + "ExtentReport_" + timestamp + ".html";

        // Ensure the reports directory exists
        new File(FrameworkConstants.REPORTS_DIR).mkdirs();

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setReportName(ConfigReader.get("report.name",  "Playwright Automation Report"));
        sparkReporter.config().setDocumentTitle(ConfigReader.get("report.title", "UI Automation Report"));
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setEncoding("UTF-8");

        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);

        // System information shown in the report header
        extentReports.setSystemInfo("OS",           System.getProperty("os.name"));
        extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
        extentReports.setSystemInfo("Browser",      ConfigReader.get("browser", "chromium"));
        extentReports.setSystemInfo("Environment",  ConfigReader.get("environment", "dev"));
        extentReports.setSystemInfo("Author",       ConfigReader.get("report.author", "Automation Team"));

        log.info("Extent Reports initialised. Report path: {}", reportPath);
    }

    // -------------------------------------------------------------------------
    // Test creation
    // -------------------------------------------------------------------------

    /**
     * Creates a new test node in the report and stores it in thread-local storage.
     *
     * @param testName    name of the test (typically the method name)
     * @param description short description of what the test validates
     * @param groups      optional test group tags (e.g. "smoke", "regression")
     * @return the {@link ExtentTest} instance for the calling thread
     */
    public static ExtentTest createTest(String testName, String description, String... groups) {
        ExtentTest test = extentReports.createTest(testName, description);
        for (String group : groups) {
            if (group != null && !group.isEmpty()) {
                test.assignCategory(group);
            }
        }
        testThreadLocal.set(test);
        return test;
    }

    // -------------------------------------------------------------------------
    // Accessor
    // -------------------------------------------------------------------------

    /**
     * Returns the {@link ExtentTest} bound to the current thread.
     * Returns {@code null} if {@link #createTest} has not been called on this thread.
     *
     * @return current thread's {@link ExtentTest}, or {@code null}
     */
    public static ExtentTest getTest() {
        return testThreadLocal.get();
    }

    // -------------------------------------------------------------------------
    // Cleanup
    // -------------------------------------------------------------------------

    /**
     * Removes the {@link ExtentTest} reference from thread-local storage.
     * Should be called at the end of each test ({@code @AfterMethod}).
     */
    public static void removeTest() {
        testThreadLocal.remove();
    }

    /**
     * Flushes all buffered test results to the HTML report file.
     * Should be called exactly once after all tests complete ({@code @AfterSuite}).
     */
    public static synchronized void flushReports() {
        if (extentReports != null) {
            extentReports.flush();
            log.info("Extent Reports flushed to disk.");
        }
    }
}
