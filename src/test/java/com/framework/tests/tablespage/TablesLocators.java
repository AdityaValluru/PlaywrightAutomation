package com.framework.tests.tablespage;

import com.framework.utils.PlaywrightUtils;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.util.regex.Pattern;

/**
 * Locators for the Tables UI Page.
 * Provides static methods to retrieve Playwright Locators using framework
 * standards.
 */
public class TablesLocators {

    public static Locator getDataMenu() {
        return PlaywrightUtils.locateByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Data"));
    }

    public static Locator getTablesMenu() {
        return PlaywrightUtils.locateByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Tables"));
    }

    public static Locator getAddButton() {
        return PlaywrightUtils.locateByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add"));
    }

    public static Locator getNameInput() {
        return PlaywrightUtils.locateByLabel("Name", new Page.GetByLabelOptions().setExact(true));
    }

    public static Locator getEmailInput() {
        return PlaywrightUtils.locateByLabel("Email", new Page.GetByLabelOptions().setExact(true));
    }

    public static Locator getSaveButton() {
        return PlaywrightUtils.locateByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save"));
    }

    public static Locator getSearchBox() {
        // return PlaywrightUtils.locateByTestId("tbl-search-input");
        Locator userTable = PlaywrightUtils.locateByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setName("User Table")).locator("xpath=..");
        return userTable.getByRole(AriaRole.TEXTBOX, new Locator.GetByRoleOptions().setName("Search"));
    }

    public static Locator getUserRow(String name) {
        return PlaywrightUtils.locateByRole(AriaRole.ROW, new Page.GetByRoleOptions().setName(Pattern.compile(name)));
    }

    public static Locator getDeleteIcon(String name) {
        // The delete button has a dynamic aria-label containing the user name, e.g.
        // "delete-Test User"
        return PlaywrightUtils.locateByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("delete-" + name));
    }

    public static Locator getConfirmDeleteButton() {
        return PlaywrightUtils.locateByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Delete"));
    }
}
