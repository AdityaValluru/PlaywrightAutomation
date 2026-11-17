package com.framework.utils;

import com.framework.constants.FrameworkConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for saving Playwright screenshots to the filesystem.
 * <p>
 * Screenshots are stored under {@link FrameworkConstants#SCREENSHOTS_DIR} with a
 * timestamp-based file name to prevent overwrites across test runs.
 * </p>
 */
public final class ScreenshotUtils {

    private static final Logger log = LogManager.getLogger(ScreenshotUtils.class);

    /** Prevent instantiation. */
    private ScreenshotUtils() {}

    /**
     * Writes the given screenshot bytes to a PNG file on disk.
     * The directory is created if it does not already exist.
     *
     * @param screenshotBytes raw PNG bytes returned by Playwright
     * @param testName        name of the test (used as part of the file name)
     * @return absolute path of the saved screenshot, or {@code ""} on failure
     */
    public static String saveScreenshot(byte[] screenshotBytes, String testName) {
        if (screenshotBytes == null || screenshotBytes.length == 0) {
            log.warn("Screenshot byte array is empty for test: {}", testName);
            return "";
        }

        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern(FrameworkConstants.SCREENSHOT_DATETIME_PATTERN));

        // Sanitise test name to produce a valid file-system name
        String safeName = testName.replaceAll("[^a-zA-Z0-9_\\-]", "_");
        String fileName  = safeName + "_" + timestamp + ".png";
        String filePath  = FrameworkConstants.SCREENSHOTS_DIR + fileName;

        File screenshotsDir = new File(FrameworkConstants.SCREENSHOTS_DIR);
        if (!screenshotsDir.exists() && !screenshotsDir.mkdirs()) {
            log.error("Failed to create screenshots directory: {}", FrameworkConstants.SCREENSHOTS_DIR);
            return "";
        }

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(screenshotBytes);
            log.info("Screenshot saved: {}", filePath);
        } catch (IOException e) {
            log.error("Failed to save screenshot '{}': {}", filePath, e.getMessage(), e);
            return "";
        }

        return filePath;
    }
}
