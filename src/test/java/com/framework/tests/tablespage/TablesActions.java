package com.framework.tests.tablespage;

import com.framework.base.BasePage;
import com.framework.utils.PlaywrightUtils;
import org.testng.Assert;

/**
 * Actions for the Tables UI Page.
 * Implements the reusable steps and interactions for the tables functionality.
 * Contains all business logic and validations.
 */
public class TablesActions extends BasePage {

    public TablesActions() {
        super();
    }

    /**
     * Navigates to the Tables page by clicking through the sidebar menu.
     * 
     * @return the current instance for method chaining
     */
    public TablesActions navigateToTables() {
        log.info("Navigating to Tables page via sidebar");
        PlaywrightUtils.click(TablesLocators.getDataMenu());
        PlaywrightUtils.click(TablesLocators.getTablesMenu());
        PlaywrightUtils.waitForNavigation();
        return this;
    }


    /**
     * Clicks the ADD button to open the Add Row modal.
     * 
     * @return the current instance
     */
    public TablesActions clickAddButton() {
        log.info("Clicking the ADD button");
        PlaywrightUtils.click(TablesLocators.getAddButton());
        return this;
    }

    /**
     * Verifies that the Add Row popup is currently displayed.
     * 
     * @return the current instance
     */
    public TablesActions verifyAddRowPopupDisplayed() {
        log.info("Verifying if Add Row popup is displayed");
        PlaywrightUtils.waitForVisible(TablesLocators.getNameInput());
        Assert.assertTrue(PlaywrightUtils.isVisible(TablesLocators.getNameInput()),
                "Add Row popup should be displayed");
        return this;
    }

    /**
     * Verifies that the Add Row popup is NOT currently displayed.
     * 
     * @return the current instance
     */
    public TablesActions verifyAddRowPopupClosed() {
        log.info("Verifying if Add Row popup is closed");
        PlaywrightUtils.waitForHidden(TablesLocators.getNameInput());
        Assert.assertFalse(PlaywrightUtils.isVisible(TablesLocators.getNameInput()),
                "Add Row popup should be closed after saving");
        return this;
    }

    /**
     * Fills the Name and Email fields in the Add Row modal.
     *
     * @param name  The name of the user
     * @param email The email of the user
     * @return the current instance
     */
    public TablesActions fillUserDetails(String name, String email) {
        log.info("Filling user details - Name: {}, Email: {}", name, email);
        PlaywrightUtils.type(TablesLocators.getNameInput(), name);
        PlaywrightUtils.type(TablesLocators.getEmailInput(), email);
        return this;
    }

    /**
     * Clicks the Save button in the Add Row modal.
     * 
     * @return the current instance
     */
    public TablesActions clickSaveButton() {
        log.info("Clicking the Save button in Add Row modal");
        PlaywrightUtils.click(TablesLocators.getSaveButton());
        return this;
    }

    /**
     * Enters the given name into the global search box.
     *
     * @param name The name to search for
     * @return the current instance
     */
    public TablesActions searchUser(String name) {
        log.info("Searching for user: {}", name);
        PlaywrightUtils.type(TablesLocators.getSearchBox(), name);
        // Playwright auto-waits for assertions, no static wait needed.
        // If needed, wait for a specific element state.
        return this;
    }

    /**
     * Validates if the user with given name and email is present in the table.
     *
     * @param name  The expected name
     * @param email The expected email
     * @return the current instance
     */
    public TablesActions verifyUserInTable(String name, String email) {
        log.info("Validating user '{}' with email '{}' is in the table", name, email);
        PlaywrightUtils.waitForVisible(TablesLocators.getUserRow(name));
        boolean isRowVisible = PlaywrightUtils.isVisible(TablesLocators.getUserRow(name));
        Assert.assertTrue(isRowVisible, "User row should be visible in the table");

        String rowText = PlaywrightUtils.getText(TablesLocators.getUserRow(name));
        Assert.assertTrue(rowText.contains(name) && rowText.contains(email),
                "User details should match the search results in the table");
        return this;
    }

    /**
     * Clicks the delete icon for the specific user.
     *
     * @param name The name of the user to delete
     * @return the current instance
     */
    public TablesActions deleteUser(String name) {
        log.info("Clicking delete icon for user: {}", name);
        PlaywrightUtils.click(TablesLocators.getDeleteIcon(name));
        return this;
    }

    /**
     * Verifies that the Delete Row popup is currently displayed.
     * 
     * @return the current instance
     */
    public TablesActions verifyDeleteRowPopupDisplayed() {
        log.info("Verifying if Delete Row confirmation popup is displayed");
        PlaywrightUtils.waitForVisible(TablesLocators.getConfirmDeleteButton());
        Assert.assertTrue(PlaywrightUtils.isVisible(TablesLocators.getConfirmDeleteButton()),
                "Delete Row popup should be displayed");
        return this;
    }

    /**
     * Confirms the deletion by clicking the Delete button in the modal.
     * 
     * @return the current instance
     */
    public TablesActions confirmDelete() {
        log.info("Confirming deletion");
        PlaywrightUtils.click(TablesLocators.getConfirmDeleteButton());
        return this;
    }

    /**
     * Verifies that the specific user row is deleted (hidden/detached).
     *
     * @param name The name of the user to check
     * @return the current instance
     */
    public TablesActions verifyUserDeleted(String name) {
        log.info("Verifying if user '{}' is deleted from table", name);
        PlaywrightUtils.waitForHidden(TablesLocators.getUserRow(name));
        Assert.assertFalse(PlaywrightUtils.isVisible(TablesLocators.getUserRow(name)),
                "User row should be deleted from the table");
        return this;
    }
}
