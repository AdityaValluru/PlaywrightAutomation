package com.framework.tests.skillspage;

import com.aventstack.extentreports.Status;
import com.framework.base.BasePage;
import com.framework.reports.ExtentReportManager;
import com.framework.utils.PlaywrightUtils;
import com.microsoft.playwright.Locator;

/**
 * Action class for the Skills UI Page and associated workflows.
 * Implements business steps, interactions, and verifications following framework coding standards.
 */
public class SkillsActions extends BasePage {

    /**
     * Constructs a {@code SkillsActions} instance for the current test thread.
     */
    public SkillsActions() {
        super();
    }

    // =========================================================================
    // HELPER / LOGGING
    // =========================================================================

    private void logExtent(Status status, String message) {
        if (ExtentReportManager.getTest() != null) {
            ExtentReportManager.getTest().log(status, message);
        }
    }

    // =========================================================================
    // NAVIGATION
    // =========================================================================

    /**
     * Expands the Employee Details dropdown menu in the sidebar navigation.
     *
     * @return this instance for method chaining
     */
    public SkillsActions expandEmployeeDetailsMenu() {
        log.info("Expanding Employee Details menu in sidebar");
        PlaywrightUtils.click(SkillsObjects.getEmployeeDetailsMenu());
        PlaywrightUtils.waitForVisible(SkillsObjects.getSkillsNavLink());
        logExtent(Status.INFO, "Expanded Employee Details navigation menu");
        return this;
    }

    /**
     * Verifies if the Skills navigation link is visible in the sidebar.
     *
     * @return true if visible, false otherwise
     */
    public boolean isSkillsMenuItemVisible() {
        log.info("Checking visibility of Skills navigation link");
        return PlaywrightUtils.isVisible(SkillsObjects.getSkillsNavLink());
    }

    /**
     * Navigates directly to the Skills page by clicking the Skills menu link.
     *
     * @return this instance for method chaining
     */
    public SkillsActions navigateToSkillsPage() {
        log.info("Navigating to Skills page via sidebar link");
        if (!isSkillsMenuItemVisible()) {
            expandEmployeeDetailsMenu();
        }
        PlaywrightUtils.click(SkillsObjects.getSkillsNavLink());
        PlaywrightUtils.waitForVisible(SkillsObjects.getSkillsHeading());
        logExtent(Status.INFO, "Navigated to Skills page");
        return this;
    }

    /**
     * Checks if the Skills landing page heading is visible.
     *
     * @return true if visible, false otherwise
     */
    public boolean isSkillsHeadingVisible() {
        log.info("Checking visibility of Skills heading");
        return PlaywrightUtils.isVisible(SkillsObjects.getSkillsHeading());
    }

    // =========================================================================
    // SKILL FILTER & SELECTION
    // =========================================================================

    /**
     * Opens the skill selection dropdown.
     *
     * @return this instance for method chaining
     */
    public SkillsActions openSkillDropdown() {
        log.info("Opening skill selection dropdown");
        PlaywrightUtils.click(SkillsObjects.getSkillDropdownOpenButton());
        PlaywrightUtils.waitForVisible(SkillsObjects.getSkillOptions().first());
        logExtent(Status.INFO, "Opened skill selection dropdown");
        return this;
    }

    /**
     * Retrieves the count of available skill options in the dropdown.
     *
     * @return count of skill options
     */
    public int getSkillOptionsCount() {
        log.info("Counting populated skill options");
        return SkillsObjects.getSkillOptions().count();
    }

    /**
     * Selects a skill by name from the dropdown.
     *
     * @param skillName name of the skill to select (e.g. "Java")
     * @return this instance for method chaining
     */
    public SkillsActions selectSkill(String skillName) {
        log.info("Selecting skill: {}", skillName);
        openSkillDropdown();
        Locator option = SkillsObjects.getSkillOption(skillName);
        PlaywrightUtils.click(option);
        PlaywrightUtils.waitForVisible(SkillsObjects.getMatchingEmployeesTable());
        logExtent(Status.INFO, "Selected skill: " + skillName);
        return this;
    }

    /**
     * Returns the currently selected skill text from the combobox.
     *
     * @return string value of the selected skill
     */
    public String getSelectedSkillValue() {
        log.info("Retrieving selected skill value from combobox");
        return SkillsObjects.getSkillCombobox().inputValue();
    }

    // =========================================================================
    // EMPLOYEE TABLE
    // =========================================================================

    /**
     * Verifies if the matching employees table is displayed.
     *
     * @return true if visible, false otherwise
     */
    public boolean isMatchingEmployeesTableVisible() {
        log.info("Checking visibility of matching employees table");
        return PlaywrightUtils.isVisible(SkillsObjects.getMatchingEmployeesTable());
    }

    /**
     * Verifies if a specific column header is visible in the employee table.
     *
     * @param headerName accessible column header name
     * @return true if visible, false otherwise
     */
    public boolean isColumnHeaderVisible(String headerName) {
        log.info("Checking visibility of column header: {}", headerName);
        return PlaywrightUtils.isVisible(SkillsObjects.getTableColumnHeader(headerName));
    }

    /**
     * Clicks the employee details button/link for the specified employee name.
     *
     * @param employeeName employee name
     * @return this instance for method chaining
     */
    public SkillsActions clickEmployeeName(String employeeName) {
        log.info("Clicking employee link for: {}", employeeName);
        PlaywrightUtils.click(SkillsObjects.getEmployeeNameButton(employeeName));
        PlaywrightUtils.waitForVisible(SkillsObjects.getEmployeeDetailsHeading());
        logExtent(Status.INFO, "Clicked employee link for: " + employeeName);
        return this;
    }

    /**
     * Clicks the first available employee name link in the table.
     *
     * @return this instance for method chaining
     */
    public SkillsActions clickFirstEmployeeName() {
        log.info("Clicking first available employee link in table");
        PlaywrightUtils.click(SkillsObjects.getFirstEmployeeNameButton());
        PlaywrightUtils.waitForVisible(SkillsObjects.getEmployeeDetailsHeading());
        logExtent(Status.INFO, "Clicked first employee link");
        return this;
    }

    /**
     * Checks if the Employee Details page heading is displayed.
     *
     * @return true if visible, false otherwise
     */
    public boolean isEmployeeDetailsHeadingVisible() {
        log.info("Checking visibility of Employee Details heading");
        return PlaywrightUtils.isVisible(SkillsObjects.getEmployeeDetailsHeading());
    }

    // =========================================================================
    // ADD / MANAGE SKILLS MODAL
    // =========================================================================

    /**
     * Clicks the "Add / Manage Skills" button to open the modal.
     *
     * @return this instance for method chaining
     */
    public SkillsActions openAddManageSkillsModal() {
        log.info("Opening Add / Manage Skills modal");
        PlaywrightUtils.click(SkillsObjects.getAddManageSkillsButton());
        PlaywrightUtils.waitForVisible(SkillsObjects.getAddManageSkillsDialog());
        logExtent(Status.INFO, "Opened Add / Manage Skills modal");
        return this;
    }

    /**
     * Verifies if the Add / Manage Skills modal dialog is visible.
     *
     * @return true if visible, false otherwise
     */
    public boolean isAddManageSkillsModalVisible() {
        log.info("Checking visibility of Add / Manage Skills modal");
        return PlaywrightUtils.isVisible(SkillsObjects.getAddManageSkillsDialog());
    }

    /**
     * Verifies if the modal employee selection combobox is visible.
     *
     * @return true if visible, false otherwise
     */
    public boolean isModalEmployeeComboboxVisible() {
        log.info("Checking visibility of modal employee combobox");
        return PlaywrightUtils.isVisible(SkillsObjects.getModalEmployeeCombobox());
    }

    /**
     * Verifies if the modal skill input field is visible.
     *
     * @return true if visible, false otherwise
     */
    public boolean isModalSkillInputVisible() {
        log.info("Checking visibility of modal skill input");
        return PlaywrightUtils.isVisible(SkillsObjects.getModalSkillInput());
    }

    /**
     * Verifies if the modal experience input field is visible.
     *
     * @return true if visible, false otherwise
     */
    public boolean isModalExperienceInputVisible() {
        log.info("Checking visibility of modal experience input");
        return PlaywrightUtils.isVisible(SkillsObjects.getModalExperienceInput());
    }

    /**
     * Verifies if the modal Save Skills button is visible.
     *
     * @return true if visible, false otherwise
     */
    public boolean isModalSaveButtonVisible() {
        log.info("Checking visibility of modal Save Skills button");
        return PlaywrightUtils.isVisible(SkillsObjects.getModalSaveButton());
    }

    /**
     * Verifies if the modal Cancel button is visible.
     *
     * @return true if visible, false otherwise
     */
    public boolean isModalCancelButtonVisible() {
        log.info("Checking visibility of modal Cancel button");
        return PlaywrightUtils.isVisible(SkillsObjects.getModalCancelButton());
    }

    /**
     * Selects an employee by identifier/name in the Add / Manage Skills modal.
     *
     * @param employeeIdentifier employee name or ID fragment
     * @return this instance for method chaining
     */
    public SkillsActions selectEmployeeInModal(String employeeIdentifier) {
        log.info("Selecting employee in modal: {}", employeeIdentifier);
        PlaywrightUtils.click(SkillsObjects.getModalEmployeeOpenButton());
        PlaywrightUtils.waitForVisible(SkillsObjects.getModalEmployeeOption(employeeIdentifier));
        PlaywrightUtils.click(SkillsObjects.getModalEmployeeOption(employeeIdentifier));
        logExtent(Status.INFO, "Selected employee in modal: " + employeeIdentifier);
        return this;
    }

    /**
     * Selects the first available employee in the Add / Manage Skills modal.
     *
     * @return this instance for method chaining
     */
    public SkillsActions selectFirstEmployeeInModal() {
        log.info("Selecting first available employee in modal");
        PlaywrightUtils.click(SkillsObjects.getModalEmployeeOpenButton());
        PlaywrightUtils.waitForVisible(SkillsObjects.getModalFirstEmployeeOption());
        PlaywrightUtils.click(SkillsObjects.getModalFirstEmployeeOption());
        logExtent(Status.INFO, "Selected first available employee in modal");
        return this;
    }

    /**
     * Returns the selected employee text from the modal combobox.
     *
     * @return string value of the selected employee
     */
    public String getSelectedEmployeeModalValue() {
        log.info("Retrieving selected employee value from modal");
        return SkillsObjects.getModalEmployeeCombobox().inputValue();
    }

    /**
     * Retrieves the count of skill rows currently present in the modal.
     *
     * @return count of skill rows
     */
    public int getModalSkillRowsCount() {
        log.info("Counting skill rows in modal");
        return SkillsObjects.getModalSkillInputs().count();
    }

    /**
     * Clicks "Add another skill" in the modal.
     *
     * @return this instance for method chaining
     */
    public SkillsActions clickAddAnotherSkill() {
        log.info("Clicking Add another skill button in modal");
        PlaywrightUtils.click(SkillsObjects.getModalAddAnotherSkillButton());
        return this;
    }

    /**
     * Fills skill name and experience in the specified skill row of the modal.
     *
     * @param index row index (0-based)
     * @param skillName name of the skill
     * @param experienceYears years of experience
     * @return this instance for method chaining
     */
    public SkillsActions fillSkillRow(int index, String skillName, String experienceYears) {
        log.info("Filling skill row {} with skill: {}, experience: {}", index, skillName, experienceYears);
        Locator skillInput = SkillsObjects.getModalSkillInput(index);
        Locator expInput = SkillsObjects.getModalExperienceInput(index);
        PlaywrightUtils.type(skillInput, skillName);
        PlaywrightUtils.type(expInput, experienceYears);
        logExtent(Status.INFO, String.format("Entered skill '%s' and experience '%s' at row %d", skillName, experienceYears, index));
        return this;
    }

    /**
     * Clicks the Save Skills button in the modal and waits for completion.
     *
     * @return this instance for method chaining
     */
    public SkillsActions clickSaveSkills() {
        log.info("Clicking Save Skills button");
        PlaywrightUtils.click(SkillsObjects.getModalSaveButton());
        PlaywrightUtils.waitForHidden(SkillsObjects.getAddManageSkillsDialog());
        logExtent(Status.INFO, "Saved skills in modal");
        return this;
    }

    /**
     * Clicks the Cancel button to dismiss the modal.
     *
     * @return this instance for method chaining
     */
    public SkillsActions closeModal() {
        log.info("Closing modal via Cancel button");
        PlaywrightUtils.click(SkillsObjects.getModalCancelButton());
        PlaywrightUtils.waitForHidden(SkillsObjects.getAddManageSkillsDialog());
        logExtent(Status.INFO, "Dismissed Add / Manage Skills modal");
        return this;
    }

    /**
     * Checks if the feedback alert banner is visible.
     *
     * @return true if visible, false otherwise
     */
    public boolean isAlertBannerVisible() {
        log.info("Checking visibility of alert banner");
        return PlaywrightUtils.isVisible(SkillsObjects.getAlertBanner());
    }

    /**
     * Returns the text of the alert banner.
     *
     * @return text content of alert banner
     */
    public String getAlertBannerText() {
        log.info("Retrieving alert banner text");
        return PlaywrightUtils.getText(SkillsObjects.getAlertBanner());
    }
}
