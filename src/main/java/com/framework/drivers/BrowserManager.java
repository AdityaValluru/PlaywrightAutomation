package com.framework.drivers;

import com.framework.utils.ConfigReader;
import com.microsoft.playwright.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Manages the lifecycle of Playwright {@link Browser}, {@link BrowserContext},
 * and {@link Page} instances.
 * <p>
 * {@link ThreadLocal} storage is used so that every test thread in a parallel
 * execution run gets its own completely independent browser session, preventing
 * cross-thread contamination.
 * </p>
 *
 * <p>Usage (called by {@code BaseTest}):</p>
 * <pre>
 *   BrowserManager.initBrowser();          // @BeforeMethod
 *   Page page = BrowserManager.getPage();  // inside tests / actions
 *   BrowserManager.closeBrowser();         // @AfterMethod
 * </pre>
 */
public final class BrowserManager {

    private static final Logger log = LogManager.getLogger(BrowserManager.class);

    private static final ThreadLocal<Playwright>      playwrightLocal = new ThreadLocal<>();
    private static final ThreadLocal<Browser>         browserLocal    = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext>  contextLocal    = new ThreadLocal<>();
    private static final ThreadLocal<Page>            pageLocal       = new ThreadLocal<>();

    /** Prevent instantiation of this utility class. */
    private BrowserManager() {}

    // -------------------------------------------------------------------------
    // Initialisation
    // -------------------------------------------------------------------------

    /**
     * Creates a new Playwright instance, launches the configured browser, opens
     * a new {@link BrowserContext} (with viewport settings) and a new {@link Page}.
     * All instances are stored in thread-local variables.
     *
     * <p>Browser type, headless mode, slow-motion and viewport are read from
     * {@code config.properties}.</p>
     */
    public static void initBrowser() {
        String  browserName = ConfigReader.get("browser", "chromium");
        boolean headless    = Boolean.parseBoolean(ConfigReader.get("headless", "true"));
        double  slowMo      = Double.parseDouble(ConfigReader.get("slowmo", "0"));
        int     vpWidth     = Integer.parseInt(ConfigReader.get("viewport.width", "1920"));
        int     vpHeight    = Integer.parseInt(ConfigReader.get("viewport.height", "1080"));

        Playwright playwright = Playwright.create();
        playwrightLocal.set(playwright);

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(headless)
                .setSlowMo(slowMo);

        Browser browser;
        switch (browserName.toLowerCase()) {
            case "firefox":
                browser = playwright.firefox().launch(launchOptions);
                break;
            case "webkit":
                browser = playwright.webkit().launch(launchOptions);
                break;
            case "chromium":
            case "chrome":
            default:
                browser = playwright.chromium().launch(launchOptions);
                break;
        }
        browserLocal.set(browser);

        BrowserContext context = browser.newContext(
                new Browser.NewContextOptions()
                        .setViewportSize(vpWidth, vpHeight)
                        .setIgnoreHTTPSErrors(true));
        contextLocal.set(context);

        // Attach default navigation timeout to the context
        context.setDefaultNavigationTimeout((int) Double.parseDouble(
                ConfigReader.get("navigation.timeout.ms", "60000")));

        Page page = context.newPage();
        pageLocal.set(page);

        log.info("Browser '{}' launched | headless={} | viewport={}x{}", browserName, headless, vpWidth, vpHeight);
    }

    // -------------------------------------------------------------------------
    // Accessors
    // -------------------------------------------------------------------------

    /**
     * Returns the {@link Page} for the current thread.
     *
     * @return active {@link Page} instance
     * @throws IllegalStateException if {@link #initBrowser()} has not been called
     */
    public static Page getPage() {
        Page page = pageLocal.get();
        if (page == null) {
            throw new IllegalStateException(
                    "Browser has not been initialised for this thread. Call BrowserManager.initBrowser() first.");
        }
        return page;
    }

    /**
     * Returns the {@link BrowserContext} for the current thread.
     *
     * @return active {@link BrowserContext} instance
     */
    public static BrowserContext getContext() {
        return contextLocal.get();
    }

    // -------------------------------------------------------------------------
    // Cleanup
    // -------------------------------------------------------------------------

    /**
     * Closes the {@link Page}, {@link BrowserContext}, {@link Browser} and
     * {@link Playwright} instances for the current thread, then removes them
     * from thread-local storage to prevent memory leaks.
     */
    public static void closeBrowser() {
        try {
            Page page = pageLocal.get();
            if (page != null && !page.isClosed()) {
                page.close();
            }
            pageLocal.remove();

            BrowserContext context = contextLocal.get();
            if (context != null) {
                context.close();
            }
            contextLocal.remove();

            Browser browser = browserLocal.get();
            if (browser != null && browser.isConnected()) {
                browser.close();
            }
            browserLocal.remove();

            Playwright playwright = playwrightLocal.get();
            if (playwright != null) {
                playwright.close();
            }
            playwrightLocal.remove();

            log.info("Browser closed and resources cleaned up for thread: {}", Thread.currentThread().getName());

        } catch (Exception e) {
            log.error("Error while closing browser resources: {}", e.getMessage(), e);
        }
    }
}
