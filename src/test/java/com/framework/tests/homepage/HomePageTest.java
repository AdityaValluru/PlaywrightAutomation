package com.framework.tests.homepage;

import com.framework.base.BaseTest;
import com.framework.constants.FrameworkConstants;
import com.framework.listeners.RetryAnalyzer;
import com.framework.tests.loginpage.LoginPageActions;
import com.framework.utils.ConfigReader;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class HomePageTest extends BaseTest {

    private HomePageActions homePageActions;
    private LoginPageActions loginPageActions;

    @BeforeMethod(alwaysRun = true)
    public void setUpActions() {
        homePageActions = new HomePageActions();
        loginPageActions = new LoginPageActions();
    }

    /**
     * Logs in using the credentials configured in {@code credentials.properties}.
     * Called at the start of each test that requires an authenticated session.
     */
    private void loginToApplication() {
        loginPageActions.loginWithConfigCredentials();
        homePageActions.verifyHomePageLoaded();
    }

    // =========================================================================
    // TC_HOME_001 — Page load
    // =========================================================================

    /**
     * TC_HOME_001: Verifies the Home / Dashboard page loads after a successful login.
     */
    @Test(
        groups        = { FrameworkConstants.GROUP_SMOKE, FrameworkConstants.GROUP_REGRESSION, FrameworkConstants.GROUP_SANITY },
        description   = "Verify Home/Dashboard page loads with navigation bar and dashboard container visible after login",
        priority      = 1,
        retryAnalyzer = RetryAnalyzer.class
    )
    public void verifyHomePageLoads() {
        loginToApplication();
        homePageActions.verifyHomePageLoaded();
    }

    // =========================================================================
    // TC_HOME_002 — Page title
    // =========================================================================

    /**
     * TC_HOME_002: Verifies the browser page title is correct on the Home page.
     */
    @Test(
        groups       = { FrameworkConstants.GROUP_SMOKE, FrameworkConstants.GROUP_REGRESSION },
        description  = "Verify browser title contains the expected value on the Home/Dashboard page",
        priority     = 2
    )
    public void verifyHomePageTitle() {
        loginToApplication();
        homePageActions.verifyPageTitle(ConfigReader.get("home.page.title", "Dashboard"));
    }

    // =========================================================================
    // TC_HOME_003 — Navigation bar
    // =========================================================================

    /**
     * TC_HOME_003: Verifies the navigation bar is visible and contains at least one menu item.
     */
    @Test(
        groups       = { FrameworkConstants.GROUP_REGRESSION },
        description  = "Verify navigation bar is visible and contains navigable menu items",
        priority     = 3
    )
    public void verifyNavigationBar() {
        loginToApplication();
        homePageActions.verifyNavigationBar();
    }

    // =========================================================================
    // TC_HOME_004 — Dashboard cards
    // =========================================================================

    /**
     * TC_HOME_004: Verifies that dashboard cards / widgets are rendered.
     */
    @Test(
        groups       = { FrameworkConstants.GROUP_REGRESSION },
        description  = "Verify dashboard cards and widgets are displayed on the Home page",
        priority     = 4
    )
    public void verifyDashboardCards() {
        loginToApplication();
        homePageActions.verifyDashboardCardsDisplayed();
    }

    // =========================================================================
    // TC_HOME_005 — Footer visibility
    // =========================================================================

    /**
     * TC_HOME_005: Verifies the page footer is visible on the Home page.
     */
    @Test(
        groups       = { FrameworkConstants.GROUP_REGRESSION },
        description  = "Verify footer is visible on the Home/Dashboard page",
        priority     = 5
    )
    public void verifyFooter() {
        loginToApplication();
        homePageActions.verifyFooterDisplayed();
    }

    // =========================================================================
    // TC_HOME_006 — Search functionality
    // =========================================================================

    /**
     * TC_HOME_006: Verifies the search functionality returns results.
     */
    @Test(
        groups       = { FrameworkConstants.GROUP_REGRESSION },
        description  = "Verify search functionality is operational on the Home page",
        priority     = 6
    )
    public void verifySearchFunctionality() {
        loginToApplication();
        homePageActions.performSearch(ConfigReader.get("search.term", "test"));
        homePageActions.verifySearchResultsDisplayed();
    }

    // =========================================================================
    // TC_HOME_007 — Logout
    // =========================================================================

    /**
     * TC_HOME_007: Verifies that a logged-in user can log out and is redirected
     * back to the Login page.
     */
    @Test(
        groups        = { FrameworkConstants.GROUP_SMOKE, FrameworkConstants.GROUP_REGRESSION },
        description   = "Verify user can logout from the Home page and is redirected to Login page",
        priority      = 7,
        retryAnalyzer = RetryAnalyzer.class
    )
    public void verifyLogout() {
        loginToApplication();
        homePageActions.logout();
        // After logout the Login page should be displayed again
        loginPageActions.verifyLoginPageLoaded();
    }

    // =========================================================================
    // TC_HOME_008 — URL after login
    // =========================================================================

    /**
     * TC_HOME_008: Verifies the browser URL contains the expected dashboard fragment
     * after login.
     */
    @Test(
        groups       = { FrameworkConstants.GROUP_SMOKE, FrameworkConstants.GROUP_REGRESSION },
        description  = "Verify the URL contains the expected fragment after a successful login",
        priority     = 8
    )
    public void verifyUrlAfterLogin() {
        loginToApplication();
        homePageActions.verifyCurrentUrlContains(
                ConfigReader.get("post.login.url.fragment", "dashboard"));
    }
}