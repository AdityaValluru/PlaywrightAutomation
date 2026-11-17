package com.framework.tests.skillspage;

import com.framework.utils.PlaywrightUtils;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.util.regex.Pattern;

/**
 * Object Repository for the Skills UI Page and related modals.
 * Provides static methods returning Playwright Locators using framework standards.
 */
public final class SkillsObjects {

    /** Prevent instantiation of this object-repository class. */
    private SkillsObjects() {}

    // =========================================================================
    // NAVIGATION
    // =========================================================================

    /** Employee Details menu expander button in the sidebar. */
    public static Locator getEmployeeDetailsMenu() {
        return PlaywrightUtils.locateByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Employee Details"));
    }

    /** Skills navigation link under Employee Details submenu. */
    public static Locator getSkillsNavLink() {
        return PlaywrightUtils.locateByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Skills"));
    }

    // =========================================================================
    // SKILLS PAGE HEADER & FILTER
    // =========================================================================

    /** Main heading on the Skills landing page. */
    public static Locator getSkillsHeading() {
        return PlaywrightUtils.locateByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Skills"));
    }

    /** Skill selection combobox filter. */
    public static Locator getSkillCombobox() {
        return PlaywrightUtils.locateByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Search or select a skill"));
    }

    /** Open button for the skill selection dropdown. */
    public static Locator getSkillDropdownOpenButton() {
        return PlaywrightUtils.locateByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Open")).first();
    }

    /** Clear button for the skill selection dropdown. */
    public static Locator getSkillDropdownClearButton() {
        return PlaywrightUtils.locateByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Clear"));
    }

    /** A specific skill option in the dropdown listbox. */
    public static Locator getSkillOption(String skillName) {
        return PlaywrightUtils.locateByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName(skillName).setExact(true));
    }

    /** All option elements in the active listbox dropdown. */
    public static Locator getSkillOptions() {
        return PlaywrightUtils.locateByRole(AriaRole.OPTION);
    }

    // =========================================================================
    // EMPLOYEE TABLE
    // =========================================================================

    /** Matching employees table container. */
    public static Locator getMatchingEmployeesTable() {
        return PlaywrightUtils.locateByRole(AriaRole.TABLE, new Page.GetByRoleOptions().setName("Employees matching selected skill"));
    }

    /** Table column header by accessible name. */
    public static Locator getTableColumnHeader(String headerName) {
        return PlaywrightUtils.locateByRole(AriaRole.COLUMNHEADER, new Page.GetByRoleOptions().setName(headerName));
    }

    /** Data rows inside the employee table. */
    public static Locator getTableRows() {
        return PlaywrightUtils.locateByRole(AriaRole.ROW);
    }

    /** Clickable employee name button/link within the table rows. */
    public static Locator getEmployeeNameButton(String employeeName) {
        return PlaywrightUtils.locateByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName(Pattern.compile("View " + employeeName + " details", Pattern.CASE_INSENSITIVE)));
    }

    /** First available clickable employee name button in the table. */
    public static Locator getFirstEmployeeNameButton() {
        return PlaywrightUtils.locateByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName(Pattern.compile("^View .* details$", Pattern.CASE_INSENSITIVE))).first();
    }

    /** Heading on the Employee Details page after navigation. */
    public static Locator getEmployeeDetailsHeading() {
        return PlaywrightUtils.locateByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Employee Details"));
    }

    // =========================================================================
    // ADD / MANAGE SKILLS MODAL
    // =========================================================================

    /** Action button to open Add / Manage Skills dialog. */
    public static Locator getAddManageSkillsButton() {
        return PlaywrightUtils.locateByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add / Manage Skills"));
    }

    /** Add or Manage Employee Skills dialog modal container. */
    public static Locator getAddManageSkillsDialog() {
        return PlaywrightUtils.locateByRole(AriaRole.DIALOG, new Page.GetByRoleOptions().setName("Add or Manage Employee Skills"));
    }

    /** Employee search/select combobox in the modal. */
    public static Locator getModalEmployeeCombobox() {
        return PlaywrightUtils.locateByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Search and select employee"));
    }

    /** Open button for employee selection in modal. */
    public static Locator getModalEmployeeOpenButton() {
        return PlaywrightUtils.locateByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Open")).first();
    }

    /** Specific employee option in modal dropdown. */
    public static Locator getModalEmployeeOption(String employeeIdentifier) {
        return PlaywrightUtils.locateByRole(AriaRole.OPTION,
                new Page.GetByRoleOptions().setName(Pattern.compile(Pattern.quote(employeeIdentifier), Pattern.CASE_INSENSITIVE)));
    }

    /** First employee option in modal dropdown. */
    public static Locator getModalFirstEmployeeOption() {
        return PlaywrightUtils.locateByRole(AriaRole.OPTION).first();
    }

    /** Skill combobox input in the modal (default first row). */
    public static Locator getModalSkillInput() {
        return PlaywrightUtils.locateByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Skill")).first();
    }

    /** Skill combobox input in the modal by row index. */
    public static Locator getModalSkillInput(int index) {
        return PlaywrightUtils.locateByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Skill")).nth(index);
    }

    /** Skill Experience spinbutton input in the modal (default first row). */
    public static Locator getModalExperienceInput() {
        return PlaywrightUtils.locateByRole(AriaRole.SPINBUTTON, new Page.GetByRoleOptions().setName("Skill Experience (years)")).first();
    }

    /** Skill Experience spinbutton input in the modal by row index. */
    public static Locator getModalExperienceInput(int index) {
        return PlaywrightUtils.locateByRole(AriaRole.SPINBUTTON, new Page.GetByRoleOptions().setName("Skill Experience (years)")).nth(index);
    }

    /** Add another skill button in modal. */
    public static Locator getModalAddAnotherSkillButton() {
        return PlaywrightUtils.locateByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add another skill"));
    }

    /** Save Skills button in modal. */
    public static Locator getModalSaveButton() {
        return PlaywrightUtils.locateByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save Skills"));
    }

    /** Cancel button in modal. */
    public static Locator getModalCancelButton() {
        return PlaywrightUtils.locateByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Cancel"));
    }

    // =========================================================================
    // NOTIFICATIONS & ALERTS
    // =========================================================================

    /** Alert or feedback notification banner. */
    public static Locator getAlertBanner() {
        return PlaywrightUtils.locateByRole(AriaRole.ALERT);
    }
}
