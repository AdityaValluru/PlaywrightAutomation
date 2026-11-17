package com.framework.tests.loginpage;

import com.framework.utils.PlaywrightUtils;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.util.regex.Pattern;

/**
 * Locators for the Login UI Page.
 * Provides static methods to retrieve Playwright Locators using framework standards.
 */
public class LoginPageObjects {

    /** Prevent instantiation of this object-repository class. */
    private LoginPageObjects() {}

    // =========================================================================
    // FORM FIELDS
    // =========================================================================

    /** Username / email input field. */
    public static Locator getUsernameInput() {
        return PlaywrightUtils.locateByLabel("Username");
    }

    /** Password input field. */
    public static Locator getPasswordInput() {
        // getByLabel("Password") matches both the input label and the "toggle password visibility" button.
        // We use .first() to strictly target the input field and avoid Playwright's strict mode violation.
        return PlaywrightUtils.locateByLabel("Password").first();
    }

    // =========================================================================
    // BUTTONS
    // =========================================================================

    /** Primary login / sign-in submit button. */
    public static Locator getLoginButton() {
        return PlaywrightUtils.locateByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(Pattern.compile("login|sign in", Pattern.CASE_INSENSITIVE)));
    }

    // =========================================================================
    // LINKS
    // =========================================================================

    /** Forgot password link. */
    public static Locator getForgotPasswordLink() {
        return PlaywrightUtils.locateByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(Pattern.compile("forgot password", Pattern.CASE_INSENSITIVE)));
    }

    /** Register / Sign Up link. */
    public static Locator getRegisterLink() {
        return PlaywrightUtils.locateByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(Pattern.compile("register|sign up", Pattern.CASE_INSENSITIVE)));
    }

    // =========================================================================
    // CHECKBOXES
    // =========================================================================

    /** Remember Me checkbox. */
    public static Locator getRememberMeCheckbox() {
        return PlaywrightUtils.locateByLabel("Remember Me");
    }

    // =========================================================================
    // MESSAGES
    // =========================================================================

    /** Error / validation message element. */
    public static Locator getErrorMessage() {
        // Material UI inputs usually use .Mui-error for validation messages
        return com.framework.drivers.BrowserManager.getPage().locator("[role='alert'], .Mui-error");
    }
}
