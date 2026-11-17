package com.framework.tests.skillspage;

import com.framework.base.BaseTest;
import com.framework.constants.FrameworkConstants;
import com.framework.tests.loginpage.LoginPageActions;
import com.framework.utils.PlaywrightUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test class for the Skills feature smoke suite.
 * Maps to approved smoke test cases (TC-001 through TC-010) in test-cases.xlsx.
 */
public class SkillsTest extends BaseTest {

    private SkillsActions skillsActions;

    @BeforeMethod(alwaysRun = true)
    public void setupSkillsTest() {
        LoginPageActions loginActions = new LoginPageActions();
        loginActions.loginWithConfigCredentials();
        skillsActions = new SkillsActions();
    }

    // TC-001
    @Test(groups = {FrameworkConstants.GROUP_SMOKE, FrameworkConstants.GROUP_REGRESSION},
          description = "Verify Skills menu item appears under Employee Details navigation")
    public void verifySkillsMenuItemUnderEmployeeDetails() {
        skillsActions.expandEmployeeDetailsMenu();
        Assert.assertTrue(skillsActions.isSkillsMenuItemVisible(),
                "Skills menu item should be visible and interactable under Employee Details submenu");
    }

    // TC-002
    @Test(groups = {FrameworkConstants.GROUP_SMOKE, FrameworkConstants.GROUP_REGRESSION},
          description = "Verify clicking Skills menu item navigates to the Skills landing page")
    public void verifyNavigationToSkillsPage() {
        skillsActions.navigateToSkillsPage();
        Assert.assertTrue(PlaywrightUtils.getCurrentUrl().contains("/skills"),
                "Browser URL should reflect the /skills route");
        Assert.assertTrue(skillsActions.isSkillsHeadingVisible(),
                "Skills landing page heading should be visible");
    }

    // TC-003
    @Test(groups = {FrameworkConstants.GROUP_SMOKE, FrameworkConstants.GROUP_REGRESSION},
          description = "Verify skill dropdown dynamically populates with existing employee skills")
    public void verifySkillDropdownPopulatesWithSkills() {
        skillsActions.navigateToSkillsPage()
                     .openSkillDropdown();
        Assert.assertTrue(skillsActions.getSkillOptionsCount() > 0,
                "Skill dropdown list should be populated dynamically with existing skills");
    }

    // TC-004
    @Test(groups = {FrameworkConstants.GROUP_SMOKE, FrameworkConstants.GROUP_REGRESSION},
          description = "Verify user can select a skill from the skill dropdown")
    public void verifyUserCanSelectSkill() {
        skillsActions.navigateToSkillsPage()
                     .selectSkill("Java");
        Assert.assertEquals(skillsActions.getSelectedSkillValue().toLowerCase(), "java",
                "The selected skill name should be displayed as active value in the dropdown");
    }

    // TC-005
    @Test(groups = {FrameworkConstants.GROUP_SMOKE, FrameworkConstants.GROUP_REGRESSION},
          description = "Verify selecting a skill displays all matching employee records")
    public void verifySelectingSkillDisplaysMatchingEmployees() {
        skillsActions.navigateToSkillsPage()
                     .selectSkill("Java");
        Assert.assertTrue(skillsActions.isMatchingEmployeesTableVisible(),
                "Table should populate with records for employees possessing the selected skill");
    }

    // TC-006
    @Test(groups = {FrameworkConstants.GROUP_SMOKE, FrameworkConstants.GROUP_REGRESSION},
          description = "Verify matching employees display in tabular format with all 7 required columns")
    public void verifyMatchingEmployeesTableColumns() {
        skillsActions.navigateToSkillsPage()
                     .selectSkill("Java");
        Assert.assertTrue(skillsActions.isColumnHeaderVisible("Employee ID"),
                "Employee ID column header must be present");
        Assert.assertTrue(skillsActions.isColumnHeaderVisible("Employee Name"),
                "Employee Name column header must be present");
        Assert.assertTrue(skillsActions.isColumnHeaderVisible("Email"),
                "Email column header must be present");
        Assert.assertTrue(skillsActions.isColumnHeaderVisible("Location"),
                "Location column header must be present");
        Assert.assertTrue(skillsActions.isColumnHeaderVisible("Total Experience"),
                "Total Experience column header must be present");
        Assert.assertTrue(skillsActions.isColumnHeaderVisible("Skill"),
                "Skill column header must be present");
        Assert.assertTrue(skillsActions.isColumnHeaderVisible("Skill Experience"),
                "Skill Experience column header must be present");
    }

    // TC-007
    @Test(groups = {FrameworkConstants.GROUP_SMOKE, FrameworkConstants.GROUP_REGRESSION},
          description = "Verify clicking employee name navigates to corresponding Employee Details view")
    public void verifyClickingEmployeeNameNavigatesToDetails() {
        skillsActions.navigateToSkillsPage()
                     .selectSkill("Java")
                     .clickFirstEmployeeName();
        Assert.assertTrue(PlaywrightUtils.getCurrentUrl().contains("/forms"),
                "Browser should navigate to Employee Details view (/forms)");
        Assert.assertTrue(skillsActions.isEmployeeDetailsHeadingVisible(),
                "Employee Details view heading should be visible");
    }

    // TC-008
    @Test(groups = {FrameworkConstants.GROUP_SMOKE, FrameworkConstants.GROUP_REGRESSION},
          description = "Verify Add Skill interface can be opened from the Skills page")
    public void verifyAddSkillInterfaceCanBeOpened() {
        skillsActions.navigateToSkillsPage()
                     .openAddManageSkillsModal();
        Assert.assertTrue(skillsActions.isAddManageSkillsModalVisible(),
                "Add or Manage Employee Skills modal dialog should be visible");
        Assert.assertTrue(skillsActions.isModalEmployeeComboboxVisible(),
                "Employee selection control should be visible");
        Assert.assertTrue(skillsActions.isModalSkillInputVisible(),
                "Skill name input should be visible");
        Assert.assertTrue(skillsActions.isModalExperienceInputVisible(),
                "Skill experience input should be visible");
        Assert.assertTrue(skillsActions.isModalSaveButtonVisible(),
                "Save Skills button should be visible");
        Assert.assertTrue(skillsActions.isModalCancelButtonVisible(),
                "Cancel button should be visible");
        skillsActions.closeModal();
    }

    // TC-009
    @Test(groups = {FrameworkConstants.GROUP_SMOKE, FrameworkConstants.GROUP_REGRESSION},
          description = "Verify user can search and select an existing employee in Add Skill modal")
    public void verifySearchAndSelectEmployeeInModal() {
        skillsActions.navigateToSkillsPage()
                     .openAddManageSkillsModal()
                     .selectFirstEmployeeInModal();
        Assert.assertFalse(skillsActions.getSelectedEmployeeModalValue().trim().isEmpty(),
                "Selected employee name and ID should be populated in the selection field");
        skillsActions.closeModal();
    }

    // TC-010
    @Test(groups = {FrameworkConstants.GROUP_SMOKE, FrameworkConstants.GROUP_REGRESSION},
          description = "Verify assigning a new skill with valid integer experience to an employee")
    public void verifyAssigningNewSkillWithIntegerExperience() {
        skillsActions.navigateToSkillsPage()
                     .openAddManageSkillsModal()
                     .selectFirstEmployeeInModal()
                     .clickAddAnotherSkill()
                     .fillSkillRow(1, "Selenium", "3")
                     .clickSaveSkills();
        Assert.assertTrue(skillsActions.isAlertBannerVisible(),
                "Success notification should confirm the skill was saved");
    }
}
