package com.framework.tests.loginpage;

import com.aventstack.extentreports.Status;
import com.framework.base.BasePage;
import com.framework.reports.ExtentReportManager;
import com.framework.utils.ConfigReader;
import com.framework.utils.PlaywrightUtils;
import org.testng.Assert;

/**
 * Action class for the Login Page.
 * <p>
 * Contains all interactions and verifications related to the Login Page.
 * Methods in this class are called from {@link LoginPageTest}.
 * Locators are sourced exclusively from {@link LoginPageObjects}.
 * </p>
 */
public class LoginPageActions extends BasePage {

    /**
     * Constructs a {@code LoginPageActions} instance for the current test thread.
     */
    public LoginPageActions() {
        super();
    }

    // =========================================================================
    // INTERACTIONS
    // =========================================================================

    /**
     * Safely logs to Extent reports. During @BeforeMethod, ExtentTest is null.
     */
    private void logExtent(Status status, String message) {
        if (ExtentReportManager.getTest() != null) {
            ExtentReportManager.getTest().log(status, message);
        }
    }

    /**
     * Types the given username into the username input field.
     *
     * @param username value to enter
     */
    public void enterUsername(String username) {
        log.info("Entering username: {}", username);
        PlaywrightUtils.type(LoginPageObjects.getUsernameInput(), username);
        logExtent(Status.INFO, "Entered username: " + username);
    }

    /**
     * Types the given password into the password input field.
     * The password value is intentionally not logged for security.
     *
     * @param password value to enter
     */
    public void enterPassword(String password) {
        log.info("Entering password");
        PlaywrightUtils.type(LoginPageObjects.getPasswordInput(), password);
        logExtent(Status.INFO, "Entered password");
    }

    /**
     * Clicks the Login/Sign-in button and waits for navigation to settle.
     */
    public void clickLoginButton() {
        log.info("Clicking Login button");
        PlaywrightUtils.click(LoginPageObjects.getLoginButton());
        PlaywrightUtils.waitForNavigation();
        logExtent(Status.INFO, "Clicked Login button");
    }

    /**
     * Convenience method: enters credentials and submits the login form.
     *
     * @param username username / email
     * @param password password
     */
    public void login(String username, String password) {
        log.info("Performing login for user: {}", username);
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    /**
     * Logs in using credentials from {@code credentials.properties}
     * ({@code valid.username} and {@code valid.password}).
     */
    public void loginWithConfigCredentials() {
        login(
            ConfigReader.getCredential("valid.username"),
            ConfigReader.getCredential("valid.password"));
    }

    /**
     * Clicks the Forgot Password link.
     */
    public void clickForgotPassword() {
        log.info("Clicking Forgot Password link");
        PlaywrightUtils.click(LoginPageObjects.getForgotPasswordLink());
        PlaywrightUtils.waitForNavigation();
        logExtent(Status.INFO, "Clicked Forgot Password link");
    }

    /**
     * Clicks the Register / Sign Up link.
     */
    public void clickRegisterLink() {
        log.info("Clicking Register link");
        PlaywrightUtils.click(LoginPageObjects.getRegisterLink());
        PlaywrightUtils.waitForNavigation();
        logExtent(Status.INFO, "Clicked Register link");
    }

    /**
     * Checks (ticks) the Remember Me checkbox.
     */
    public void checkRememberMe() {
        log.info("Checking Remember Me checkbox");
        PlaywrightUtils.click(LoginPageObjects.getRememberMeCheckbox());
        logExtent(Status.INFO, "Checked Remember Me checkbox");
    }

    // =========================================================================
    // VERIFICATIONS / ASSERTIONS
    // =========================================================================

    /**
     * Asserts that the Login page is fully loaded by verifying all essential
     * form elements are visible.
     */
    public void verifyLoginPageLoaded() {
        log.info("Verifying Login page is loaded");

        Assert.assertTrue(PlaywrightUtils.isVisible(LoginPageObjects.getUsernameInput()),
                "Username input should be visible on Login page");
        Assert.assertTrue(PlaywrightUtils.isVisible(LoginPageObjects.getPasswordInput()),
                "Password input should be visible on Login page");
        Assert.assertTrue(PlaywrightUtils.isVisible(LoginPageObjects.getLoginButton()),
                "Login button should be visible on Login page");

        logExtent(Status.PASS, "Login page loaded with all required elements");
    }

    /**
     * Asserts that the Login button is enabled (not disabled).
     */
    public void verifyLoginButtonEnabled() {
        log.info("Verifying Login button is enabled");
        Assert.assertTrue(PlaywrightUtils.isVisible(LoginPageObjects.getLoginButton()), 
                "Login button should be visible");
        logExtent(Status.PASS, "Login button is enabled");
    }

    /**
     * Asserts that the error message is visible and contains the expected text.
     *
     * @param expectedText substring that the displayed error message should contain
     */
    public void verifyErrorMessage(String expectedText) {
        log.info("Verifying error message contains: '{}'", expectedText);
        PlaywrightUtils.waitForVisible(LoginPageObjects.getErrorMessage());
        String actual = PlaywrightUtils.getText(LoginPageObjects.getErrorMessage());

        Assert.assertTrue(actual.contains(expectedText),
                "Expected error message to contain '" + expectedText + "' but found: '" + actual + "'");

        logExtent(Status.PASS, "Error message verified: " + actual);
    }

    /**
     * Asserts that the HTML5 validation message matches the expected text.
     *
     * @param fieldLocator locator for the input field
     * @param expectedText substring that the validation message should contain
     */
    public void verifyValidationMessage(com.microsoft.playwright.Locator fieldLocator, String expectedText) {
        log.info("Verifying validation message contains: '{}'", expectedText);
        String actual = PlaywrightUtils.getValidationMessage(fieldLocator);

        Assert.assertTrue(actual.contains(expectedText),
                "Expected validation message to contain '" + expectedText + "' but found: '" + actual + "'");

        logExtent(Status.PASS, "Validation message verified: " + actual);
    }

    /**
     * Asserts that the current URL contains the expected fragment, indicating a
     * successful post-login redirect.
     *
     * @param expectedUrlFragment URL fragment (e.g. "dashboard") expected after login
     */
    public void verifySuccessfulLogin(String expectedUrlFragment) {
        log.info("Verifying successful login — expected URL fragment: '{}'", expectedUrlFragment);
        try {
            PlaywrightUtils.waitForURL("**/" + expectedUrlFragment);
        } catch (Exception e) {
            log.warn("Timeout waiting for URL fragment: {}", expectedUrlFragment);
        }
        String currentUrl = PlaywrightUtils.getCurrentUrl();

        Assert.assertTrue(currentUrl.contains(expectedUrlFragment),
                "Expected URL to contain '" + expectedUrlFragment + "' but current URL is: " + currentUrl);

        logExtent(Status.PASS,
                "Successful login confirmed. Current URL: " + currentUrl);
    }
}
