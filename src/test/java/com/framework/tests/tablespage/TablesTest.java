package com.framework.tests.tablespage;

import com.framework.base.BaseTest;
import com.framework.constants.FrameworkConstants;
import com.framework.tests.loginpage.LoginPageActions;

import java.util.Random;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test class for Tables Page.
 * <p>
 * Contains test cases that validate the Tables page functionality.
 * Business logic and verifications are delegated to {@link TablesActions};
 * locators are owned by {@link TablesLocators}.
 * </p>
 */
public class TablesTest extends BaseTest {

    private TablesActions tablesActions;
    static int rn = 0;

    @BeforeMethod(alwaysRun = true)
    public void setupTablesTest() {
        // Log in before the test runs
        LoginPageActions loginActions = new LoginPageActions();
        loginActions.loginWithConfigCredentials();
        tablesActions = new TablesActions();

        Random random = new Random();
        rn = random.nextInt(100);

    }

    /**
     * Validates the complete flow of adding, searching, and deleting a user on the
     * Tables page.
     */
    @Test(groups = { FrameworkConstants.GROUP_SMOKE,
            FrameworkConstants.GROUP_REGRESSION }, description = "Validate Add/Search/Delete User on the Tables page", priority = 1)
    public void validateAddAndDeleteUserFlow() {
        String testUserName = "Tester" + rn;
        String testUserEmail = "testauto" + rn + " @example.com";

        tablesActions
                // Step 2: Navigate to Tables page under Data menu
                .navigateToTables()

                // Step 3: Look for User Table section and click ADD button
                .clickAddButton()

                // Step 4: Check Add Row popup/window open
                .verifyAddRowPopupDisplayed()

                // Step 5: Enter Name and Email in the input fields and click Save button.
                .fillUserDetails(testUserName, testUserEmail)
                .clickSaveButton()

                // Step 6: Check Add Row popup/window is closed
                .verifyAddRowPopupClosed()

                // Step 7: Enter the name in the Search box and validate the Name and Email are
                // matching from the table.
                .searchUser(testUserName)
                .verifyUserInTable(testUserName, testUserEmail)

                // Step 8: Click the Delete icon under Actions column for the same User.
                .deleteUser(testUserName)

                // Step 9: Check Delete Row popup/window is displayed.
                .verifyDeleteRowPopupDisplayed()

                // Step 10: Click on the Delete button and check the user row is deleted from
                // the table
                .confirmDelete()

                // Clear search or just verify it's deleted
                .verifyUserDeleted(testUserName);
    }

}
