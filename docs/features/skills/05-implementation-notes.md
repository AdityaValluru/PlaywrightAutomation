---
feature: skills
stage: implementation
status: Draft
source: docs/features/skills/test-cases.xlsx
---

# 05. Test Implementation Notes: Skills Module (Smoke Suite)

## 1. Summary of Work Done

Automated the 10 approved smoke test case rows from `docs/features/skills/test-cases.xlsx` (TC-001 through TC-010):
- **TC-001** (`SCN-001`): `SkillsTest#verifySkillsMenuItemUnderEmployeeDetails`
- **TC-002** (`SCN-003`): `SkillsTest#verifyNavigationToSkillsPage`
- **TC-003** (`SCN-006`): `SkillsTest#verifySkillDropdownPopulatesWithSkills`
- **TC-004** (`SCN-009`): `SkillsTest#verifyUserCanSelectSkill`
- **TC-005** (`SCN-010`): `SkillsTest#verifySelectingSkillDisplaysMatchingEmployees`
- **TC-006** (`SCN-014`): `SkillsTest#verifyMatchingEmployeesTableColumns`
- **TC-007** (`SCN-017`): `SkillsTest#verifyClickingEmployeeNameNavigatesToDetails`
- **TC-008** (`SCN-018`): `SkillsTest#verifyAddSkillInterfaceCanBeOpened`
- **TC-009** (`SCN-019`): `SkillsTest#verifySearchAndSelectEmployeeInModal`
- **TC-010** (`SCN-021`): `SkillsTest#verifyAssigningNewSkillWithIntegerExperience`

All 10 test case rows in `test-cases.xlsx` were updated in the `Automated Test` column with their respective `SkillsTest#methodName`.

---

## 2. Reuse Notes

- **Authentication & Setup**: Reused `LoginPageActions.loginWithConfigCredentials()` within `@BeforeMethod` to authenticate using configured credentials in `credentials.properties`.
- **Framework Base Classes**:
  - `BaseTest` for TestNG suite/method lifecycle, Extent Report initialization, failure screenshots, and Playwright tracing.
  - `BasePage` for logger inheritance and thread-local page management.
- **Utilities**:
  - All DOM interactions and locators strictly routed through `PlaywrightUtils` (`locateByRole`, `click`, `type`, `waitForVisible`, `waitForHidden`, `isVisible`, `getText`, `getCurrentUrl`).
  - Reporting safely logged via `ExtentReportManager` using null-guards.
- **Constants**: Test methods assigned to `FrameworkConstants.GROUP_SMOKE` and `FrameworkConstants.GROUP_REGRESSION`.
- **New Classes**:
  - `com.framework.tests.skillspage.SkillsObjects`: Object repository with static semantic locator methods.
  - `com.framework.tests.skillspage.SkillsActions`: Fluent action class extending `BasePage`.
  - `com.framework.tests.skillspage.SkillsTest`: Smoke test class extending `BaseTest`.

---

## 3. Locator Notes

- All locators adhere strictly to the prioritized accessibility role standards in `coding-standards.md`:
  - Main navigation: `AriaRole.BUTTON` ("Employee Details"), `AriaRole.LINK` ("Skills")
  - Page headings: `AriaRole.HEADING` ("Skills", "Employee Details")
  - Dropdown controls: `AriaRole.COMBOBOX` ("Search or select a skill"), `AriaRole.OPTION` (skill names)
  - Table & Grid: `AriaRole.TABLE` ("Employees matching selected skill"), `AriaRole.COLUMNHEADER` (all 7 required column headers), `AriaRole.ROW`, `AriaRole.BUTTON` ("View [Name] details")
  - Add / Manage Modal: `AriaRole.DIALOG` ("Add or Manage Employee Skills"), `AriaRole.COMBOBOX` ("Search and select employee", "Skill"), `AriaRole.SPINBUTTON` ("Skill Experience (years)"), `AriaRole.BUTTON` ("Add / Manage Skills", "Add another skill", "Save Skills", "Cancel")
  - Feedback: `AriaRole.ALERT`
- Zero raw CSS or XPath selectors were introduced.

---

## 4. Open Points & Skipped Rows

- **UNSPECIFIED Rows**: None. All 10 approved rows (TC-001 through TC-010) contained specific, observable expected results derived from the SRS. No rows were skipped due to `UNSPECIFIED` results.
- **Bulk Upload Smoke Scenarios**: Scenarios `SCN-035`, `SCN-036`, `SCN-037`, and `SCN-041` (Bulk Upload CSV smoke flow) are described in `03-test-plan.md` but do not yet have corresponding rows in `test-cases.xlsx`. They will be automated once test case rows for bulk upload are generated and approved in stage 4.
