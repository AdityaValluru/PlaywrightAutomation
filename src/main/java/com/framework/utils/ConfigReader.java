package com.framework.utils;

import com.framework.constants.FrameworkConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Utility class for reading configuration and credential properties files.
 * <p>
 * Properties are loaded once at class-load time and cached.
 * System properties (e.g. {@code -Dbrowser=firefox}) always override file values,
 * making this CI/CD-friendly without code changes.
 * </p>
 *
 * <pre>
 * Usage:
 *   ConfigReader.get("base.url")
 *   ConfigReader.get("headless", "true")
 *   ConfigReader.getCredential("valid.username")
 * </pre>
 */
public final class ConfigReader {

    private static final Logger log = LogManager.getLogger(ConfigReader.class);

    private static final Properties CONFIG      = new Properties();
    private static final Properties CREDENTIALS = new Properties();

    static {
        loadProperties(FrameworkConstants.CONFIG_FILE,      CONFIG);
        loadProperties(FrameworkConstants.CREDENTIALS_FILE, CREDENTIALS);
    }

    /** Prevent instantiation. */
    private ConfigReader() {}

    // -------------------------------------------------------------------------
    // Internal helpers
    // -------------------------------------------------------------------------

    /**
     * Loads a {@code .properties} file into the given {@link Properties} object.
     * System properties are merged afterwards so that JVM {@code -D} flags
     * can override file values.
     *
     * @param filePath   relative path to the properties file
     * @param properties target {@link Properties} instance
     * @throws RuntimeException if the file cannot be found or read
     */
    private static void loadProperties(String filePath, Properties properties) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
            // Allow -D system-property overrides (e.g. -Dbrowser=firefox passed via Maven)
            System.getProperties().forEach((k, v) -> properties.setProperty(k.toString(), v.toString()));
            log.debug("Loaded properties from: {}", filePath);
        } catch (IOException e) {
            log.error("Failed to load properties file '{}': {}", filePath, e.getMessage());
            throw new RuntimeException("Cannot load properties file: " + filePath, e);
        }
    }

    // -------------------------------------------------------------------------
    // Public API — config.properties
    // -------------------------------------------------------------------------

    /**
     * Returns the value for the given key from {@code config.properties}.
     *
     * @param key property key
     * @return trimmed property value
     * @throws RuntimeException if the key is absent
     */
    public static String get(String key) {
        String value = CONFIG.getProperty(key);
        if (value == null) {
            log.error("Property '{}' not found in config.properties", key);
            throw new RuntimeException("Missing required config property: " + key);
        }
        return value.trim();
    }

    /**
     * Returns the value for the given key from {@code config.properties},
     * falling back to {@code defaultValue} when the key is absent.
     *
     * @param key          property key
     * @param defaultValue fallback value
     * @return trimmed property value or the default
     */
    public static String get(String key, String defaultValue) {
        return CONFIG.getProperty(key, defaultValue).trim();
    }

    // -------------------------------------------------------------------------
    // Public API — credentials.properties
    // -------------------------------------------------------------------------

    /**
     * Returns the value for the given key from {@code credentials.properties}.
     *
     * @param key credential key
     * @return trimmed credential value
     * @throws RuntimeException if the key is absent
     */
    public static String getCredential(String key) {
        String value = CREDENTIALS.getProperty(key);
        if (value == null) {
            log.error("Credential '{}' not found in credentials.properties", key);
            throw new RuntimeException("Missing required credential: " + key);
        }
        return value.trim();
    }
}
