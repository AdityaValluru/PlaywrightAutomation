package com.framework.tests.loginpage;

import com.framework.base.BaseTest;
import com.framework.constants.FrameworkConstants;
import com.framework.listeners.RetryAnalyzer;
import com.framework.utils.ConfigReader;
import org.testng.annotations.Test;

/**
 * Test class for Login Page.
 * <p>
 * Contains all test cases that validate the Login Page functionality.
 * Business logic and verifications are delegated to {@link LoginPageActions};
 * locators are owned by {@link LoginPageObjects}.
 * </p>
 */
public class LoginPageTest extends BaseTest {

    // =========================================================================
    // TC_LOGIN_001 — Page load
    // =========================================================================

    /**
     * TC_LOGIN_001: Verifies that the Login page loads with all required elements.
     */
    @Test(
        groups       = { FrameworkConstants.GROUP_SMOKE, FrameworkConstants.GROUP_REGRESSION, FrameworkConstants.GROUP_SANITY },
        description  = "Verify Login page loads with username field, password field, and login button visible",
        priority     = 1
    )
    public void verifyLoginPageLoads() {
        new LoginPageActions().verifyLoginPageLoaded();
    }

    // =========================================================================
    // TC_LOGIN_002 — Valid credentials
    // =========================================================================

    /**
     * TC_LOGIN_002: Verifies that a user can log in with valid credentials.
     */
    @Test(
        groups        = { FrameworkConstants.GROUP_SMOKE, FrameworkConstants.GROUP_REGRESSION },
        description   = "Verify successful login with valid credentials redirects to dashboard",
        priority      = 2,
        retryAnalyzer = RetryAnalyzer.class
    )
    public void verifyLoginWithValidCredentials() {
        LoginPageActions actions = new LoginPageActions();
        actions.loginWithConfigCredentials();
        actions.verifySuccessfulLogin(ConfigReader.get("post.login.url.fragment", "dashboard"));
    }

    // =========================================================================
    // TC_LOGIN_003 — Invalid credentials
    // =========================================================================

    /**
     * TC_LOGIN_003: Verifies that an error message is shown for invalid credentials.
     */
    @Test(
        groups       = { FrameworkConstants.GROUP_REGRESSION },
        description  = "Verify error message is displayed for incorrect username/password",
        priority     = 3
    )
    public void verifyLoginWithInvalidCredentials() {
        LoginPageActions actions = new LoginPageActions();
        actions.login(
            ConfigReader.getCredential("invalid.username"),
            ConfigReader.getCredential("invalid.password"));
        actions.verifyErrorMessage(ConfigReader.get("login.error.message", "Invalid credentials"));
    }

    // =========================================================================
    // TC_LOGIN_004 — Empty username
    // =========================================================================

    /**
     * TC_LOGIN_004: Verifies that a validation message appears when the username
     * field is left blank.
     */
    @Test(
        groups       = { FrameworkConstants.GROUP_REGRESSION },
        description  = "Verify validation error is shown when username is empty",
        priority     = 4
    )
    public void verifyLoginWithEmptyUsername() {
        LoginPageActions actions = new LoginPageActions();
        actions.enterPassword(ConfigReader.getCredential("valid.password"));
        actions.clickLoginButton();
        actions.verifyValidationMessage(LoginPageObjects.getUsernameInput(), "Please fill in this field");
    }

    // =========================================================================
    // TC_LOGIN_005 — Empty password
    // =========================================================================

    /**
     * TC_LOGIN_005: Verifies that a validation message appears when the password
     * field is left blank.
     */
    @Test(
        groups       = { FrameworkConstants.GROUP_REGRESSION },
        description  = "Verify validation error is shown when password is empty",
        priority     = 5
    )
    public void verifyLoginWithEmptyPassword() {
        LoginPageActions actions = new LoginPageActions();
        actions.enterUsername(ConfigReader.getCredential("valid.username"));
        actions.clickLoginButton();
        actions.verifyValidationMessage(LoginPageObjects.getPasswordInput(), "Please fill in this field");
    }

    // =========================================================================
    // TC_LOGIN_006 — Login button state
    // =========================================================================

    /**
     * TC_LOGIN_006: Verifies that the Login button is enabled on page load.
     */
    @Test(
        groups       = { FrameworkConstants.GROUP_SANITY },
        description  = "Verify the Login button is in an enabled state on page load",
        priority     = 6
    )
    public void verifyLoginButtonIsEnabled() {
        new LoginPageActions().verifyLoginButtonEnabled();
    }

    // =========================================================================
    // TC_LOGIN_007 — Forgot Password link
    // =========================================================================

    /**
     * TC_LOGIN_007: Verifies that the Forgot Password link is clickable and
     * navigates away from the Login page.
     */
    @Test(
        groups       = { FrameworkConstants.GROUP_REGRESSION },
        description  = "Verify Forgot Password link navigates to the password-reset page",
        priority     = 7
    )
    public void verifyForgotPasswordLink() {
        LoginPageActions actions = new LoginPageActions();
        actions.clickForgotPassword();
        // TODO: add verifyForgotPasswordPageLoaded() once page is known
    }

    // =========================================================================
    // TC_LOGIN_008 — SQL injection safety
    // =========================================================================

    /**
     * TC_LOGIN_008: Verifies that submitting SQL injection payloads as credentials
     * results in an error message (not a bypass).
     */
    @Test(
        groups       = { FrameworkConstants.GROUP_REGRESSION },
        description  = "Verify SQL injection attempt in login form is rejected",
        priority     = 8
    )
    public void verifyLoginWithSqlInjection() {
        LoginPageActions actions = new LoginPageActions();
        actions.login("' OR '1'='1' --", "' OR '1'='1' --");
        actions.verifyErrorMessage(ConfigReader.get("login.error.message", "Invalid credentials"));
    }

    // =========================================================================
    // TC_LOGIN_009 — XSS safety
    // =========================================================================

    /**
     * TC_LOGIN_009: Verifies that submitting a script tag as a username results in
     * an error message (not script execution).
     */
    @Test(
        groups       = { FrameworkConstants.GROUP_REGRESSION },
        description  = "Verify XSS payload in username field is handled safely",
        priority     = 9
    )
    public void verifyLoginWithXssPayload() {
        LoginPageActions actions = new LoginPageActions();
        actions.login("<script>alert('xss')</script>", "password");
        actions.verifyErrorMessage(ConfigReader.get("login.error.message", "Invalid credentials"));
    }
}
