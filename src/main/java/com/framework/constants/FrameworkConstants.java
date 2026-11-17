package com.framework.constants;

/**
 * Central repository of framework-wide constants.
 * <p>
 * All hard-coded values such as timeouts, directory paths, group names,
 * and date formats are defined here to avoid magic numbers/strings across the codebase.
 * </p>
 */
public final class FrameworkConstants {

    /** Prevent instantiation of this utility class. */
    private FrameworkConstants() {}

    // =====================================================================
    // TIMEOUTS
    // =====================================================================

    /** Default explicit wait timeout in milliseconds (30 seconds). */
    public static final double EXPLICIT_WAIT_MS = 30_000;

    /** Default navigation/page-load timeout in milliseconds (60 seconds). */
    public static final double NAVIGATION_TIMEOUT_MS = 60_000;

    // =====================================================================
    // FILE PATHS
    // =====================================================================

    /** Root directory for generated reports. */
    public static final String REPORTS_DIR = System.getProperty("user.dir") + "/reports/";

    /** Directory for failure screenshots, nested under reports. */
    public static final String SCREENSHOTS_DIR = REPORTS_DIR + "screenshots/";

    /** Directory for log files. */
    public static final String LOGS_DIR = System.getProperty("user.dir") + "/logs/";

    /** Relative path to the main configuration properties file. */
    public static final String CONFIG_FILE = "src/test/resources/config/config.properties";

    /** Relative path to the credentials properties file. */
    public static final String CREDENTIALS_FILE = "src/test/resources/config/credentials.properties";

    // =====================================================================
    // RETRY
    // =====================================================================

    /** Maximum number of retry attempts for a failing test. */
    public static final int MAX_RETRY_COUNT = 1;

    // =====================================================================
    // TEST GROUPS
    // =====================================================================

    /** Group constant for smoke tests (critical path). */
    public static final String GROUP_SMOKE = "smoke";

    /** Group constant for full regression tests. */
    public static final String GROUP_REGRESSION = "regression";

    /** Group constant for sanity / quick-check tests. */
    public static final String GROUP_SANITY = "sanity";

    // =====================================================================
    // SCREENSHOT
    // =====================================================================

    /** Date-time pattern used in screenshot file names. */
    public static final String SCREENSHOT_DATETIME_PATTERN = "yyyy-MM-dd_HH-mm-ss-SSS";
}
