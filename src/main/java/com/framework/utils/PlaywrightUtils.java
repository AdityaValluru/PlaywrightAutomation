package com.framework.utils;

import com.framework.constants.FrameworkConstants;
import com.framework.drivers.BrowserManager;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Paths;

/**
 * Central utility class containing reusable Playwright browser interactions.
 * <p>
 * All common UI operations (click, type, navigate, wait, assert visibility, etc.)
 * are encapsulated here. Action classes should call these helpers instead of
 * accessing the {@link Page} API directly, ensuring a single point of change
 * for timeout settings and logging.
 * </p>
 *
 * <p>This class is stateless — every method fetches the current-thread {@link Page}
 * from {@link BrowserManager} and is therefore safe for parallel execution.</p>
 */
public final class PlaywrightUtils {

    private static final Logger log = LogManager.getLogger(PlaywrightUtils.class);

    /** Prevent instantiation. */
    private PlaywrightUtils() {}

    // =========================================================================
    // LOCATORS — getBy* Semantic Wrappers
    // =========================================================================

    /**
     * Locates an element by its ARIA role.
     * <p>
     * Prefer this strategy over XPath/CSS when a stable ARIA role uniquely
     * identifies the element. Returns a {@link Locator} that can be passed
     * directly into any of the {@code Locator}-accepting overloads in this class.
     * </p>
     *
     * @param role the ARIA role (e.g. {@link AriaRole#BUTTON}, {@link AriaRole#TEXTBOX})
     * @return a {@link Locator} scoped to the matching element(s)
     */
    public static Locator locateByRole(AriaRole role) {
        log.debug("Locating element by role: {}", role);
        return BrowserManager.getPage().getByRole(role);
    }

    /**
     * Locates an element by its ARIA role with additional filtering options.
     * <p>
     * Use {@link Page.GetByRoleOptions} to further narrow the match by accessible
     * name, exact flag, pressed/expanded state, and more.
     * </p>
     *
     * @param role    the ARIA role (e.g. {@link AriaRole#BUTTON}, {@link AriaRole#LINK})
     * @param options {@link Page.GetByRoleOptions} for additional filtering
     *                (e.g. {@code new Page.GetByRoleOptions().setName("Submit")})
     * @return a {@link Locator} scoped to the matching element(s)
     */
    public static Locator locateByRole(AriaRole role, Page.GetByRoleOptions options) {
        log.debug("Locating element by role: {} with options", role);
        return BrowserManager.getPage().getByRole(role, options);
    }

    /**
     * Locates an element by its ARIA role within a parent locator.
     *
     * @param parentLocator the parent {@link Locator} to search within
     * @param role          the ARIA role (e.g. {@link AriaRole#BUTTON}, {@link AriaRole#TEXTBOX})
     * @return a {@link Locator} scoped to the matching element(s) within the parent
     */
    public static Locator locateByRole(Locator parentLocator, AriaRole role) {
        log.debug("Locating element by role: {} within a parent locator", role);
        return parentLocator.getByRole(role);
    }

    /**
     * Locates an element by its ARIA role with additional filtering options, within a parent locator.
     *
     * @param parentLocator the parent {@link Locator} to search within
     * @param role          the ARIA role (e.g. {@link AriaRole#BUTTON}, {@link AriaRole#LINK})
     * @param options       {@link Locator.GetByRoleOptions} for additional filtering
     * @return a {@link Locator} scoped to the matching element(s) within the parent
     */
    public static Locator locateByRole(Locator parentLocator, AriaRole role, Locator.GetByRoleOptions options) {
        log.debug("Locating element by role: {} with options within a parent locator", role);
        return parentLocator.getByRole(role, options);
    }

    /**
     * Locates a form element by the visible text of its associated {@code <label>}.
     * <p>
     * This is the preferred strategy for inputs that have a visible label, as it
     * mirrors how assistive technologies identify fields.
     * </p>
     *
     * @param labelText visible text of the {@code <label>} element linked to the input
     * @return a {@link Locator} for the labelled form element
     */
    public static Locator locateByLabel(String labelText) {
        log.debug("Locating element by label: '{}'", labelText);
        return BrowserManager.getPage().getByLabel(labelText);
    }

    /**
     * Locates a form element by its associated {@code <label>} text with exact-match control.
     *
     * @param labelText visible text of the {@code <label>} element
     * @param options   {@link Page.GetByLabelOptions} (e.g. {@code .setExact(true)} to
     *                  require the full label text rather than a substring)
     * @return a {@link Locator} for the labelled form element
     */
    public static Locator locateByLabel(String labelText, Page.GetByLabelOptions options) {
        log.debug("Locating element by label: '{}' with options", labelText);
        return BrowserManager.getPage().getByLabel(labelText, options);
    }

    /**
     * Locates an input element by its {@code placeholder} attribute text.
     * <p>
     * Performs a case-insensitive substring match by default.
     * Use the options overload to require an exact match.
     * </p>
     *
     * @param placeholderText the placeholder string shown inside the input
     * @return a {@link Locator} for the matching input element
     */
    public static Locator locateByPlaceholder(String placeholderText) {
        log.debug("Locating element by placeholder: '{}'", placeholderText);
        return BrowserManager.getPage().getByPlaceholder(placeholderText);
    }

    /**
     * Locates an input element by its {@code placeholder} attribute text with exact-match control.
     *
     * @param placeholderText the placeholder string shown inside the input
     * @param options         {@link Page.GetByPlaceholderOptions}
     *                        (e.g. {@code .setExact(true)})
     * @return a {@link Locator} for the matching input element
     */
    public static Locator locateByPlaceholder(String placeholderText, Page.GetByPlaceholderOptions options) {
        log.debug("Locating element by placeholder: '{}' with options", placeholderText);
        return BrowserManager.getPage().getByPlaceholder(placeholderText, options);
    }

    /**
     * Locates an element by its visible text content.
     * <p>
     * Performs a substring match by default. Useful for buttons, headings, and
     * links whose text content is stable.
     * </p>
     *
     * @param text visible text to search for
     * @return a {@link Locator} for the matching element(s)
     */
    public static Locator locateByText(String text) {
        log.debug("Locating element by text: '{}'", text);
        return BrowserManager.getPage().getByText(text);
    }

    /**
     * Locates an element by its visible text content with exact-match control.
     *
     * @param text    visible text to search for
     * @param options {@link Page.GetByTextOptions} (e.g. {@code .setExact(true)} to
     *                match the full string instead of a substring)
     * @return a {@link Locator} for the matching element(s)
     */
    public static Locator locateByText(String text, Page.GetByTextOptions options) {
        log.debug("Locating element by text: '{}' with options", text);
        return BrowserManager.getPage().getByText(text, options);
    }

    /**
     * Locates an element by its {@code data-testid} attribute.
     * <p>
     * The attribute name used for test IDs is configurable at the Playwright
     * {@code Selectors} level (default: {@code data-testid}). This is the most
     * resilient strategy when the application exposes dedicated test hooks.
     * </p>
     *
     * @param testId value of the {@code data-testid} attribute
     * @return a {@link Locator} for the matching element
     */
    public static Locator locateByTestId(String testId) {
        log.debug("Locating element by test-id: '{}'", testId);
        return BrowserManager.getPage().getByTestId(testId);
    }

    /**
     * Locates an image (or any element) by its {@code alt} attribute text.
     * <p>
     * Performs a case-insensitive substring match by default.
     * </p>
     *
     * @param altText value of the {@code alt} attribute (e.g. image description)
     * @return a {@link Locator} for the matching element
     */
    public static Locator locateByAltText(String altText) {
        log.debug("Locating element by alt-text: '{}'", altText);
        return BrowserManager.getPage().getByAltText(altText);
    }

    /**
     * Locates an image (or any element) by its {@code alt} attribute text with
     * exact-match control.
     *
     * @param altText value of the {@code alt} attribute
     * @param options {@link Page.GetByAltTextOptions} (e.g. {@code .setExact(true)})
     * @return a {@link Locator} for the matching element
     */
    public static Locator locateByAltText(String altText, Page.GetByAltTextOptions options) {
        log.debug("Locating element by alt-text: '{}' with options", altText);
        return BrowserManager.getPage().getByAltText(altText, options);
    }

    /**
     * Locates an element by its {@code title} attribute text.
     * <p>
     * Performs a case-insensitive substring match by default.
     * </p>
     *
     * @param titleText value of the {@code title} attribute
     * @return a {@link Locator} for the matching element
     */
    public static Locator locateByTitle(String titleText) {
        log.debug("Locating element by title: '{}'", titleText);
        return BrowserManager.getPage().getByTitle(titleText);
    }

    /**
     * Locates an element by its {@code title} attribute text with exact-match control.
     *
     * @param titleText value of the {@code title} attribute
     * @param options   {@link Page.GetByTitleOptions} (e.g. {@code .setExact(true)})
     * @return a {@link Locator} for the matching element
     */
    public static Locator locateByTitle(String titleText, Page.GetByTitleOptions options) {
        log.debug("Locating element by title: '{}' with options", titleText);
        return BrowserManager.getPage().getByTitle(titleText, options);
    }

    // =========================================================================
    // NAVIGATION
    // =========================================================================

    /**
     * Navigates to the given URL and waits until the network is idle.
     *
     * @param url full URL to navigate to
     */
    public static void navigateTo(String url) {
        log.info("Navigating to: {}", url);
        BrowserManager.getPage().navigate(url);
        BrowserManager.getPage().waitForLoadState(LoadState.NETWORKIDLE);
    }

    /**
     * Reloads the current page and waits until the network is idle.
     */
    public static void reloadPage() {
        log.info("Reloading current page");
        BrowserManager.getPage().reload();
        BrowserManager.getPage().waitForLoadState(LoadState.NETWORKIDLE);
    }

    /**
     * Waits until the page network is idle (no ongoing requests for 500 ms).
     */
    public static void waitForNavigation() {
        BrowserManager.getPage().waitForLoadState(LoadState.NETWORKIDLE);
    }

    /**
     * Waits for the page URL to match the given glob pattern or regular expression.
     * 
     * @param url URL string, glob pattern, or regular expression to wait for
     */
    public static void waitForURL(String url) {
        log.info("Waiting for URL: {}", url);
        BrowserManager.getPage().waitForURL(url);
    }

    // =========================================================================
    // CLICK INTERACTIONS
    // =========================================================================

    /**
     * Waits for the element to be visible and performs a left-click.
     *
     * @param locator XPath or CSS selector
     */
    public static void click(String locator) {
        log.info("Clicking element: {}", locator);
        waitForVisible(locator);
        BrowserManager.getPage().locator(locator).click();
    }

    /**
     * Performs a double-click on the element.
     *
     * @param locator XPath or CSS selector
     */
    public static void doubleClick(String locator) {
        log.info("Double-clicking element: {}", locator);
        waitForVisible(locator);
        BrowserManager.getPage().locator(locator).dblclick();
    }

    /**
     * Performs a right-click (context menu) on the element.
     *
     * @param locator XPath or CSS selector
     */
    public static void rightClick(String locator) {
        log.info("Right-clicking element: {}", locator);
        BrowserManager.getPage().locator(locator)
                .click(new Locator.ClickOptions().setButton(MouseButton.RIGHT));
    }

    /**
     * Clicks an element using JavaScript evaluation.
     * Useful for elements that are obscured or not interceptable via standard click.
     *
     * @param locator XPath or CSS selector
     */
    public static void jsClick(String locator) {
        log.info("JS-clicking element: {}", locator);
        BrowserManager.getPage().locator(locator).evaluate("el => el.click()");
    }

    /**
     * Waits for the element resolved by the given {@link Locator} to be visible,
     * then performs a left-click.
     * <p>
     * Use this overload when the element was located via one of the
     * {@code locateBy*} semantic methods.
     * </p>
     *
     * @param locator a {@link Locator} obtained from one of the {@code locateBy*} methods
     * @throws com.microsoft.playwright.TimeoutError if the element is not visible
     *         within {@link com.framework.constants.FrameworkConstants#EXPLICIT_WAIT_MS}
     */
    public static void click(Locator locator) {
        log.info("Clicking element via Locator: {}", locator);
        locator.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(FrameworkConstants.EXPLICIT_WAIT_MS));
        locator.click();
    }

    /**
     * Performs a double-click on the element resolved by the given {@link Locator}.
     * <p>
     * Waits for the element to be visible before double-clicking.
     * </p>
     *
     * @param locator a {@link Locator} obtained from one of the {@code locateBy*} methods
     * @throws com.microsoft.playwright.TimeoutError if the element is not visible
     *         within {@link com.framework.constants.FrameworkConstants#EXPLICIT_WAIT_MS}
     */
    public static void doubleClick(Locator locator) {
        log.info("Double-clicking element via Locator: {}", locator);
        locator.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(FrameworkConstants.EXPLICIT_WAIT_MS));
        locator.dblclick();
    }

    // =========================================================================
    // TEXT INPUT
    // =========================================================================

    /**
     * Clears the field then fills it with the given text.
     *
     * @param locator XPath or CSS selector
     * @param text    text to enter
     */
    public static void type(String locator, String text) {
        log.info("Typing '{}' into element: {}", text, locator);
        Locator element = BrowserManager.getPage().locator(locator);
        element.waitFor(new Locator.WaitForOptions().setTimeout(FrameworkConstants.EXPLICIT_WAIT_MS));
        element.clear();
        element.fill(text);
    }

    /**
     * Clears the field then fills it with the given text using a {@link Locator}.
     * <p>
     * Use this overload when the element was located via one of the
     * {@code locateBy*} semantic methods.
     * </p>
     *
     * @param locator a {@link Locator} obtained from one of the {@code locateBy*} methods
     * @param text    text to enter into the field
     * @throws com.microsoft.playwright.TimeoutError if the element is not attached
     *         within {@link com.framework.constants.FrameworkConstants#EXPLICIT_WAIT_MS}
     */
    public static void type(Locator locator, String text) {
        log.info("Typing '{}' into element via Locator", text);
        locator.waitFor(new Locator.WaitForOptions()
                .setTimeout(FrameworkConstants.EXPLICIT_WAIT_MS));
        locator.clear();
        locator.fill(text);
    }

    /**
     * Clears the content of an input field.
     *
     * @param locator XPath or CSS selector
     */
    public static void clear(String locator) {
        log.info("Clearing element: {}", locator);
        BrowserManager.getPage().locator(locator).clear();
    }

    /**
     * Presses a keyboard key (e.g. "Enter", "Tab", "Escape").
     *
     * @param key keyboard key name as accepted by Playwright
     */
    public static void pressKey(String key) {
        log.info("Pressing key: {}", key);
        BrowserManager.getPage().keyboard().press(key);
    }

    // =========================================================================
    // SELECT / DROPDOWN
    // =========================================================================

    /**
     * Selects a dropdown option by its {@code value} attribute.
     *
     * @param locator XPath or CSS selector for the {@code <select>} element
     * @param value   option value
     */
    public static void selectByValue(String locator, String value) {
        log.info("Selecting by value '{}' in: {}", value, locator);
        BrowserManager.getPage().locator(locator).selectOption(value);
    }

    /**
     * Selects a dropdown option by its visible label text.
     *
     * @param locator XPath or CSS selector for the {@code <select>} element
     * @param label   visible label of the option to select
     */
    public static void selectByLabel(String locator, String label) {
        log.info("Selecting by label '{}' in: {}", label, locator);
        BrowserManager.getPage().locator(locator)
                .selectOption(new SelectOption().setLabel(label));
    }

    // =========================================================================
    // CHECKBOX / RADIO
    // =========================================================================

    /**
     * Checks a checkbox or radio button.
     *
     * @param locator XPath or CSS selector
     */
    public static void check(String locator) {
        log.info("Checking element: {}", locator);
        BrowserManager.getPage().locator(locator).check();
    }

    /**
     * Unchecks a checkbox.
     *
     * @param locator XPath or CSS selector
     */
    public static void uncheck(String locator) {
        log.info("Unchecking element: {}", locator);
        BrowserManager.getPage().locator(locator).uncheck();
    }

    /**
     * Returns whether a checkbox or radio button is checked.
     *
     * @param locator XPath or CSS selector
     * @return {@code true} if the element is checked
     */
    public static boolean isChecked(String locator) {
        return BrowserManager.getPage().locator(locator).isChecked();
    }

    // =========================================================================
    // MOUSE / HOVER
    // =========================================================================

    /**
     * Hovers the mouse pointer over the element.
     *
     * @param locator XPath or CSS selector
     */
    public static void hover(String locator) {
        log.info("Hovering over element: {}", locator);
        BrowserManager.getPage().locator(locator).hover();
    }

    /**
     * Performs a drag-and-drop from source element to target element.
     *
     * @param sourceLocator source element locator
     * @param targetLocator target element locator
     */
    public static void dragAndDrop(String sourceLocator, String targetLocator) {
        log.info("Dragging '{}' to '{}'", sourceLocator, targetLocator);
        BrowserManager.getPage().dragAndDrop(sourceLocator, targetLocator);
    }

    // =========================================================================
    // WAITS
    // =========================================================================

    /**
     * Waits until the element becomes visible in the DOM.
     *
     * @param locator XPath or CSS selector
     */
    public static void waitForVisible(String locator) {
        log.debug("Waiting for element to be visible: {}", locator);
        BrowserManager.getPage().locator(locator).waitFor(
                new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE)
                        .setTimeout(FrameworkConstants.EXPLICIT_WAIT_MS));
    }

    /**
     * Waits until the element is hidden or detached from the DOM.
     *
     * @param locator XPath or CSS selector
     */
    public static void waitForHidden(String locator) {
        log.debug("Waiting for element to be hidden: {}", locator);
        BrowserManager.getPage().locator(locator).waitFor(
                new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.HIDDEN)
                        .setTimeout(FrameworkConstants.EXPLICIT_WAIT_MS));
    }

    /**
     * Waits until the element resolved by the given {@link Locator} becomes visible
     * in the viewport.
     *
     * @param locator a {@link Locator} obtained from one of the {@code locateBy*} methods
     * @throws com.microsoft.playwright.TimeoutError if the element does not become
     *         visible within {@link com.framework.constants.FrameworkConstants#EXPLICIT_WAIT_MS}
     */
    public static void waitForVisible(Locator locator) {
        log.debug("Waiting for Locator to be visible: {}", locator);
        locator.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(FrameworkConstants.EXPLICIT_WAIT_MS));
    }

    /**
     * Waits until the element resolved by the given {@link Locator} is hidden or
     * detached from the DOM.
     *
     * @param locator a {@link Locator} obtained from one of the {@code locateBy*} methods
     * @throws com.microsoft.playwright.TimeoutError if the element does not become
     *         hidden within {@link com.framework.constants.FrameworkConstants#EXPLICIT_WAIT_MS}
     */
    public static void waitForHidden(Locator locator) {
        log.debug("Waiting for Locator to be hidden: {}", locator);
        locator.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.HIDDEN)
                .setTimeout(FrameworkConstants.EXPLICIT_WAIT_MS));
    }

    // =========================================================================
    // ELEMENT STATE QUERIES
    // =========================================================================

    /**
     * Returns whether the element is currently visible on the page.
     *
     * @param locator XPath or CSS selector
     * @return {@code true} if visible; {@code false} otherwise
     */
    public static boolean isVisible(String locator) {
        try {
            return BrowserManager.getPage().locator(locator).isVisible();
        } catch (Exception e) {
            log.warn("isVisible check failed for '{}': {}", locator, e.getMessage());
            return false;
        }
    }

    /**
     * Returns whether the element resolved by the given {@link Locator} is
     * currently visible on the page.
     *
     * @param locator a {@link Locator} obtained from one of the {@code locateBy*} methods
     * @return {@code true} if the element is visible; {@code false} otherwise
     */
    public static boolean isVisible(Locator locator) {
        try {
            return locator.isVisible();
        } catch (Exception e) {
            log.warn("isVisible check failed for Locator '{}': {}", locator, e.getMessage());
            return false;
        }
    }

    /**
     * Returns whether the element is enabled (not disabled).
     *
     * @param locator XPath or CSS selector
     * @return {@code true} if enabled
     */
    public static boolean isEnabled(String locator) {
        return BrowserManager.getPage().locator(locator).isEnabled();
    }

    /**
     * Returns the number of elements matching the given locator.
     *
     * @param locator XPath or CSS selector
     * @return count of matching elements
     */
    public static int getElementCount(String locator) {
        return BrowserManager.getPage().locator(locator).count();
    }

    // =========================================================================
    // DATA RETRIEVAL
    // =========================================================================

    /**
     * Returns the trimmed visible text content of an element.
     *
     * @param locator XPath or CSS selector
     * @return visible text content
     */
    public static String getText(String locator) {
        log.debug("Getting text from element: {}", locator);
        String text = BrowserManager.getPage().locator(locator).textContent();
        return (text != null) ? text.trim() : "";
    }

    /**
     * Returns the trimmed visible text content of the element resolved by the
     * given {@link Locator}.
     *
     * @param locator a {@link Locator} obtained from one of the {@code locateBy*} methods
     * @return visible text content, never {@code null} (empty string if element has no text)
     */
    public static String getText(Locator locator) {
        log.debug("Getting text via Locator: {}", locator);
        String text = locator.textContent();
        return (text != null) ? text.trim() : "";
    }

    /**
     * Returns the current value of a form input field.
     *
     * @param locator XPath or CSS selector
     * @return input field value
     */
    public static String getInputValue(String locator) {
        return BrowserManager.getPage().locator(locator).inputValue();
    }

    /**
     * Returns the value of the specified attribute for the element.
     *
     * @param locator   XPath or CSS selector
     * @param attribute attribute name (e.g. "href", "class", "data-id")
     * @return attribute value, or {@code null} if not present
     */
    public static String getAttribute(String locator, String attribute) {
        log.debug("Getting attribute '{}' from element: {}", attribute, locator);
        return BrowserManager.getPage().locator(locator).getAttribute(attribute);
    }

    /**
     * Returns the value of the specified attribute for the element resolved by
     * the given {@link Locator}.
     *
     * @param locator   a {@link Locator} obtained from one of the {@code locateBy*} methods
     * @param attribute attribute name (e.g. {@code "href"}, {@code "class"}, {@code "data-id"})
     * @return attribute value, or {@code null} if the attribute is not present
     */
    public static String getAttribute(Locator locator, String attribute) {
        log.debug("Getting attribute '{}' via Locator: {}", attribute, locator);
        return locator.getAttribute(attribute);
    }

    /**
     * Returns the HTML5 validation message for the element resolved by the given {@link Locator}.
     *
     * @param locator a {@link Locator} obtained from one of the {@code locateBy*} methods
     * @return validation message, or empty string if none
     */
    public static String getValidationMessage(Locator locator) {
        log.debug("Getting validation message via Locator: {}", locator);
        Object message = locator.evaluate("el => el.validationMessage");
        return (message != null) ? message.toString() : "";
    }

    /**
     * Returns the current URL of the page.
     *
     * @return current page URL
     */
    public static String getCurrentUrl() {
        return BrowserManager.getPage().url();
    }

    /**
     * Returns the {@code <title>} of the current page.
     *
     * @return page title
     */
    public static String getPageTitle() {
        return BrowserManager.getPage().title();
    }

    // =========================================================================
    // SCROLLING
    // =========================================================================

    /**
     * Scrolls the element into the visible viewport.
     *
     * @param locator XPath or CSS selector
     */
    public static void scrollToElement(String locator) {
        log.info("Scrolling to element: {}", locator);
        BrowserManager.getPage().locator(locator).scrollIntoViewIfNeeded();
    }

    // =========================================================================
    // DIALOGS
    // =========================================================================

    /**
     * Registers a one-time handler to accept the next browser dialog (alert/confirm).
     */
    public static void acceptNextDialog() {
        BrowserManager.getPage().onDialog(dialog -> {
            log.info("Accepting dialog: {}", dialog.message());
            dialog.accept();
        });
    }

    /**
     * Registers a one-time handler to dismiss the next browser dialog.
     */
    public static void dismissNextDialog() {
        BrowserManager.getPage().onDialog(dialog -> {
            log.info("Dismissing dialog: {}", dialog.message());
            dialog.dismiss();
        });
    }

    // =========================================================================
    // FILE UPLOAD
    // =========================================================================

    /**
     * Sets a file for an {@code <input type="file">} element.
     *
     * @param locator  XPath or CSS selector for the file input
     * @param filePath absolute or relative path to the file to upload
     */
    public static void uploadFile(String locator, String filePath) {
        log.info("Uploading file '{}' via element: {}", filePath, locator);
        BrowserManager.getPage().locator(locator).setInputFiles(Paths.get(filePath));
    }

    // =========================================================================
    // JAVASCRIPT
    // =========================================================================

    /**
     * Evaluates arbitrary JavaScript in the page context.
     *
     * @param script JavaScript expression to execute
     * @return result of the expression (may be {@code null})
     */
    public static Object executeScript(String script) {
        log.info("Executing script: {}", script);
        return BrowserManager.getPage().evaluate(script);
    }

    // =========================================================================
    // SCREENSHOTS
    // =========================================================================

    /**
     * Captures a screenshot of the current viewport.
     *
     * @return PNG screenshot as a byte array
     */
    public static byte[] takeScreenshot() {
        return BrowserManager.getPage().screenshot(
                new Page.ScreenshotOptions().setFullPage(false));
    }

    /**
     * Captures a full-page screenshot (scrolls and stitches the entire page).
     *
     * @return PNG screenshot as a byte array
     */
    public static byte[] takeFullPageScreenshot() {
        return BrowserManager.getPage().screenshot(
                new Page.ScreenshotOptions().setFullPage(true));
    }

    // =========================================================================
    // NEW TAB / POPUP
    // =========================================================================

    /**
     * Executes the given action and waits for a new popup/tab to open.
     * Returns the new {@link Page} handle.
     *
     * @param action lambda that triggers the popup (e.g. a click)
     * @return the new popup {@link Page}
     */
    public static Page switchToNewTab(Runnable action) {
        log.info("Waiting for new tab/popup");
        return BrowserManager.getPage().waitForPopup(action);
    }
}
