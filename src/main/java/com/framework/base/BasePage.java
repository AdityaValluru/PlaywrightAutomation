package com.framework.base;

import com.framework.drivers.BrowserManager;
import com.framework.utils.ConfigReader;
import com.framework.utils.PlaywrightUtils;
import com.microsoft.playwright.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Base class for all page-specific Action classes.
 * <p>
 * Every page action class (e.g. {@code LoginPageActions}, {@code HomePageActions})
 * must extend {@code BasePage} to gain access to:
 * <ul>
 *   <li>The current-thread Playwright {@link Page} instance.</li>
 *   <li>Common navigation helpers.</li>
 *   <li>A pre-configured {@link Logger} scoped to the concrete subclass.</li>
 * </ul>
 * </p>
 */
public abstract class BasePage {

    /** Logger scoped to the concrete sub-class for clear log attribution. */
    protected final Logger log = LogManager.getLogger(getClass());

    /** The Playwright {@link Page} for the current test thread. */
    protected final Page page;

    /**
     * Constructs a {@code BasePage} and caches the current thread's {@link Page}.
     * The {@link com.framework.drivers.BrowserManager} must have been initialised
     * ({@code BrowserManager.initBrowser()}) before this constructor is called.
     */
    protected BasePage() {
        this.page = BrowserManager.getPage();
    }

    // -------------------------------------------------------------------------
    // Navigation helpers
    // -------------------------------------------------------------------------

    /**
     * Navigates to the base URL defined in {@code config.properties}.
     */
    protected void goToBaseUrl() {
        PlaywrightUtils.navigateTo(ConfigReader.get("base.url"));
    }

    /**
     * Returns the current page URL.
     *
     * @return full URL of the currently loaded page
     */
    protected String getCurrentUrl() {
        return PlaywrightUtils.getCurrentUrl();
    }

    /**
     * Returns the {@code <title>} of the currently loaded page.
     *
     * @return page title string
     */
    protected String getPageTitle() {
        return PlaywrightUtils.getPageTitle();
    }
}
