package com.framework.tests.homepage;

import com.aventstack.extentreports.Status;
import com.framework.base.BasePage;
import com.framework.reports.ExtentReportManager;
import com.framework.utils.PlaywrightUtils;
import org.testng.Assert;

/**
 * Action class for the Home / Dashboard Page.
 * <p>
 * Contains all user interactions and verifications related to the Home Page.
 * Methods in this class are called from {@link HomePageTest}.
 * Locators are sourced exclusively from {@link HomePageObjects}.
 * </p>
 */
public class HomePageActions extends BasePage {

    /**
     * Constructs a {@code HomePageActions} instance for the current test thread.
     */
    public HomePageActions() {
        super();
    }

    /**
     * Safely logs to Extent reports. During @BeforeMethod, ExtentTest is null.
     */
    private void logExtent(Status status, String message) {
        if (ExtentReportManager.getTest() != null) {
            ExtentReportManager.getTest().log(status, message);
        }
    }

    // =========================================================================
    // INTERACTIONS
    // =========================================================================

    /**
     * Clicks the Logout button and waits for the resulting navigation to complete.
     */
    public void logout() {
        log.info("Logging out");
        try {
            clickUserProfileDropdown();
        } catch (Exception e) {
            log.info("Profile dropdown not found or could not be clicked, proceeding to click logout directly");
        }
        PlaywrightUtils.click(HomePageObjects.LOGOUT_BUTTON);
        PlaywrightUtils.waitForNavigation();
        logExtent(Status.INFO, "Logged out successfully");
    }

    /**
     * Opens the user profile dropdown menu.
     */
    public void clickUserProfileDropdown() {
        log.info("Opening user profile dropdown");
        PlaywrightUtils.click(HomePageObjects.USER_PROFILE_DROPDOWN);
        PlaywrightUtils.waitForVisible(HomePageObjects.USER_PROFILE_MENU);
        logExtent(Status.INFO, "User profile dropdown opened");
    }

    /**
     * Clicks a navigation link by its visible text.
     *
     * @param linkText visible text of the navigation link to click
     */
    public void clickNavLink(String linkText) {
        log.info("Clicking navigation link: '{}'", linkText);
        String locator = "//nav//a[contains(text(),'" + linkText + "')]";
        PlaywrightUtils.click(locator);
        PlaywrightUtils.waitForNavigation();
        logExtent(Status.INFO, "Clicked navigation link: " + linkText);
    }

    /**
     * Types a search term into the search field and submits it.
     *
     * @param searchTerm the term to search for
     */
    public void performSearch(String searchTerm) {
        log.info("Performing search for: '{}'", searchTerm);
        PlaywrightUtils.type(HomePageObjects.SEARCH_INPUT, searchTerm);
        PlaywrightUtils.pressKey("Enter");
        logExtent(Status.INFO, "Search performed for: " + searchTerm);
    }

    /**
     * Clicks the notification bell to open the notifications panel.
     */
    public void clickNotificationBell() {
        log.info("Clicking notification bell");
        PlaywrightUtils.click(HomePageObjects.NOTIFICATION_BELL);
        logExtent(Status.INFO, "Notification bell clicked");
    }

    // =========================================================================
    // VERIFICATIONS / ASSERTIONS
    // =========================================================================

    /**
     * Asserts that the Home page is fully loaded by verifying the navigation bar
     * and dashboard container are visible.
     */
    public void verifyHomePageLoaded() {
        log.info("Verifying Home page is loaded");
        PlaywrightUtils.waitForVisible(HomePageObjects.NAVIGATION_BAR);
        Assert.assertTrue(PlaywrightUtils.isVisible(HomePageObjects.NAVIGATION_BAR),
                "Navigation bar should be visible on Home page");
        Assert.assertTrue(PlaywrightUtils.isVisible(HomePageObjects.DASHBOARD_CONTAINER),
                "Dashboard container should be visible on Home page");
        logExtent(Status.PASS, "Home page loaded with all required elements");
    }

    /**
     * Asserts that the page title contains the expected text.
     *
     * @param expectedTitle substring that the browser {@code <title>} should contain
     */
    public void verifyPageTitle(String expectedTitle) {
        log.info("Verifying page title contains: '{}'", expectedTitle);
        String actualTitle = PlaywrightUtils.getPageTitle();
        Assert.assertTrue(actualTitle.contains(expectedTitle),
                "Expected page title to contain '" + expectedTitle + "' but was: '" + actualTitle + "'");
        logExtent(Status.PASS, "Page title verified: " + actualTitle);
    }

    /**
     * Asserts that the navigation bar is visible and contains at least one menu item.
     */
    public void verifyNavigationBar() {
        log.info("Verifying navigation bar");
        Assert.assertTrue(PlaywrightUtils.isVisible(HomePageObjects.NAVIGATION_BAR),
                "Navigation bar should be visible");
        int itemCount = PlaywrightUtils.getElementCount(HomePageObjects.NAV_MENU_ITEMS);
        Assert.assertTrue(itemCount > 0,
                "Navigation bar should contain at least one menu item but found " + itemCount);
        logExtent(Status.PASS, "Navigation bar verified with " + itemCount + " item(s)");
    }

    /**
     * Asserts that at least one dashboard card / widget is visible.
     */
    public void verifyDashboardCardsDisplayed() {
        log.info("Verifying dashboard cards are displayed");
        int count = PlaywrightUtils.getElementCount(HomePageObjects.DASHBOARD_CARDS);
        Assert.assertTrue(count > 0,
                "At least one dashboard card should be visible but found " + count);
        logExtent(Status.PASS, "Dashboard cards visible: " + count);
    }

    /**
     * Asserts that the footer is visible on the Home page.
     */
    public void verifyFooterDisplayed() {
        log.info("Verifying footer is displayed");
        Assert.assertTrue(PlaywrightUtils.isVisible(HomePageObjects.FOOTER),
                "Footer should be visible on Home page");
        logExtent(Status.PASS, "Footer is visible");
    }

    /**
     * Asserts that search results are displayed after a search.
     */
    public void verifySearchResultsDisplayed() {
        log.info("Verifying search results are displayed");
        PlaywrightUtils.waitForVisible(HomePageObjects.SEARCH_RESULTS);
        Assert.assertTrue(PlaywrightUtils.isVisible(HomePageObjects.SEARCH_RESULTS),
                "Search results container should be visible");
        logExtent(Status.PASS, "Search results are displayed");
    }

    /**
     * Asserts that the welcome message is visible and contains the expected username.
     *
     * @param expectedUsername username fragment expected in the welcome message
     */
    public void verifyWelcomeMessage(String expectedUsername) {
        log.info("Verifying welcome message for user: '{}'", expectedUsername);
        PlaywrightUtils.waitForVisible(HomePageObjects.WELCOME_MESSAGE);
        String actual = PlaywrightUtils.getText(HomePageObjects.WELCOME_MESSAGE);
        Assert.assertTrue(actual.contains(expectedUsername),
                "Expected welcome message to contain '" + expectedUsername + "' but got: '" + actual + "'");
        logExtent(Status.PASS, "Welcome message verified: " + actual);
    }

    /**
     * Asserts that the current URL contains the expected fragment.
     *
     * @param expectedFragment URL fragment that should be present
     */
    public void verifyCurrentUrlContains(String expectedFragment) {
        log.info("Verifying current URL contains: '{}'", expectedFragment);
        String currentUrl = PlaywrightUtils.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains(expectedFragment),
                "Expected URL to contain '" + expectedFragment + "' but URL is: " + currentUrl);
        logExtent(Status.PASS, "URL verified: " + currentUrl);
    }
}
