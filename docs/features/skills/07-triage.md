---
feature: skills
stage: triage
status: Approved            # Draft | Reviewed | Approved (only the human changes this)
source: docs/features/skills/06-run-report.md
---

# 07. Failure Triage: Employee Management – Skills Module (Smoke Suite) [ai]

## 1. Overview [ai]

During the execution of the Skills Module Smoke Suite (documented in `06-run-report.md` and the `Execution Log` of `test-cases.xlsx`), **10 tests** were executed:
- **Passed**: 8 (80%)
- **Failed**: 2 (20%) — `TC-006` and `TC-010`
- **Flaky**: 0 (0%)
- **Weak Assertions**: 0 (0%)

This document analyzes the root cause, classification, and recommended resolution for both test failures in accordance with QA Pipeline Stage 7 rules.

---

## 2. Failure Triage Summary Table [ai]

| Test Case ID | Test Method | Category | Confidence | Recommended Action |
|---|---|---|---|---|
| `TC-006` | `SkillsTest#verifyMatchingEmployeesTableColumns` | **Test issue (locator)** | High | Fix test locator: add `.setExact(true)` to `SkillsObjects.getTableColumnHeader()` to resolve Playwright strict mode violation. |
| `TC-010` | `SkillsTest#verifyAssigningNewSkillWithIntegerExperience` | **Test issue (test logic)** / **Test data or state** | High | Fix test logic & data: correct row indexing so an empty row is not submitted, and isolate employee test data to satisfy TC preconditions. |

---

## 3. Detailed Failure Analysis [ai]

### Failure 1: TC-006 (`SCN-014`) [ai]

- **Test Case**: `TC-006` — Verify matching employees display in tabular format with all 7 required columns
- **Requirement Traceability**: `REQ-EMP-001` (Tabular display), `REQ-EMP-002` (Mandatory columns: `Employee ID`, `Employee Name`, `Email`, `Location`, `Total Experience`, `Skill`, `Skill Experience`)
- **Test Method**: `com.framework.tests.skillspage.SkillsTest#verifyMatchingEmployeesTableColumns`
- **Reported Error**:
  ```text
  java.lang.AssertionError: Skill column header must be present expected [true] but found [false]
      at com.framework.tests.skillspage.SkillsTest.verifyMatchingEmployeesTableColumns(SkillsTest.java:92)
  ```
- **Evidence Examined**:
  - `target/traces/verifyMatchingEmployeesTableColumns-trace.zip`
  - `reports/ExtentReport_2026-11-18_12-31-35.html`
  - Playwright action log in trace `call@41`:
    ```text
    Error: strict mode violation: getByRole(AriaRole.COLUMNHEADER, new Page.GetByRoleOptions().setName("Skill")) resolved to 2 elements:
        1) <th scope="col" class="MuiTableCell-root MuiTableCell-head MuiTableCell-sizeSmall css-g6bqb5-MuiTableCell-root">Skill</th>
        2) <th scope="col" class="MuiTableCell-root MuiTableCell-head MuiTableCell-alignRight MuiTableCell-sizeSmall css-1ais0ar-MuiTableCell-root">Skill Experience</th>
    ```
- **Root Cause Analysis**:
  - The application DOM actually contains all 7 column headers as specified in the SRS:
    `Employee ID`, `Employee Name`, `Email`, `Location`, `Total Experience`, `Skill`, and `Skill Experience`.
  - In `SkillsObjects.java`, the locator is defined as:
    ```java
    public static Locator getTableColumnHeader(String headerName) {
        return PlaywrightUtils.locateByRole(AriaRole.COLUMNHEADER, new Page.GetByRoleOptions().setName(headerName));
    }
    ```
  - Because `setExact(true)` is not specified on the `GetByRoleOptions`, Playwright performs substring matching by default. Searching for `"Skill"` matches both `"Skill"` and `"Skill Experience"`.
  - In Playwright, `isVisible()` on a locator matching multiple elements violates strict mode and throws a `PlaywrightException`.
  - `PlaywrightUtils.isVisible(Locator)` catches this exception, logs a warning, and safely returns `false`.
  - The assertion `Assert.assertTrue(skillsActions.isColumnHeaderVisible("Skill"))` therefore failed even though the column header exists in the UI.
- **Classification**: **Test issue (locator)**
- **Confidence**: **High**
- **Recommended Action**:
  - Update `SkillsObjects.getTableColumnHeader(String headerName)` to specify exact name matching:
    ```java
    public static Locator getTableColumnHeader(String headerName) {
        return PlaywrightUtils.locateByRole(AriaRole.COLUMNHEADER,
                new Page.GetByRoleOptions().setName(headerName).setExact(true));
    }
    ```
  - No application bug report is needed.

---

### Failure 2: TC-010 (`SCN-021`) [ai]

- **Test Case**: `TC-010` — Verify assigning a new skill with valid integer experience to an employee
- **Requirement Traceability**: `REQ-ADD-004` (Skill Experience in years), `REQ-ADD-005` (New Skill creation), `REQ-ADD-008` (Persistence), `REQ-ADD-010` (Operation Feedback)
- **Test Method**: `com.framework.tests.skillspage.SkillsTest#verifyAssigningNewSkillWithIntegerExperience`
- **Reported Error**:
  ```text
  com.microsoft.playwright.TimeoutError: Timeout 30000ms exceeded.
  Call log:
  - waiting for getByRole(AriaRole.DIALOG, new Page.GetByRoleOptions().setName("Add or Manage Employee Skills")) to be hidden
      at com.framework.tests.skillspage.SkillsActions.clickSaveSkills(SkillsActions.java:353)
      at com.framework.tests.skillspage.SkillsTest.verifyAssigningNewSkillWithIntegerExperience(SkillsTest.java:153)
  ```
- **Evidence Examined**:
  - `target/traces/verifyAssigningNewSkillWithIntegerExperience-trace.zip`
  - DOM snapshot & trace frame screenshot (`resources/page@...1794985331.885365.jpeg`)
  - Active UI validation banner visible in trace:
    ```text
    Skill 3: enter a skill name. Skill 3: experience must be from 0 to 60 years.
    ```
- **Root Cause Analysis**:
  - When the test opened the modal and selected the first employee (`First`), the employee already possessed two existing skills in the UI:
    - Row 0: `Java` (2 years)
    - Row 1: `Selenium` (2 years)
  - The test method executed:
    ```java
    skillsActions.navigateToSkillsPage()
                 .openAddManageSkillsModal()
                 .selectFirstEmployeeInModal()
                 .clickAddAnotherSkill()           // Adds a 3rd row (Row index 2, empty)
                 .fillSkillRow(1, "Selenium", "3") // Overwrites Row index 1, leaving Row index 2 empty
                 .clickSaveSkills();
    ```
  - Because `clickAddAnotherSkill()` appended an empty third row (Row index 2), but `fillSkillRow(1, ...)` populated Row 1, Row 2 remained blank.
  - The application correctly enforced SRS validation rules `REQ-VAL-003` (Skill Name Mandatory) and `REQ-VAL-004` (Experience Value Mandatory), preventing submission and displaying the error notification: `"Skill 3: enter a skill name. Skill 3: experience must be from 0 to 60 years."`
  - Because validation prevented form submission, the modal dialog was never dismissed. `PlaywrightUtils.waitForHidden(...)` subsequently timed out after 30 seconds.
  - In addition, the precondition for `TC-010` in `test-cases.xlsx` requires: `"Add Skill modal is open; target employee is selected; employee does not currently have skill 'Selenium'"`. Selecting an employee that already had `"Selenium"` violated this precondition.
- **Classification**: **Test issue (test logic)** / **Test data or state**
- **Confidence**: **High**
- **Recommended Action**:
  - **Test Logic Fix**:
    - Update `SkillsTest#verifyAssigningNewSkillWithIntegerExperience` so that when `clickAddAnotherSkill()` is clicked, the newly added row index is filled, or dynamically determine the index of the newly added row, or select a clean employee record without unhandled rows.
  - **Test Data Fix**:
    - Ensure the test selects an employee who does not already have the target skill assigned (per `TC-010` Preconditions), or execute cleanup to remove the skill after testing.
  - No application bug report is needed; the application behaves correctly according to SRS validation rules.

---

## 4. Next Steps [ai]

1. **Human Review**: Please review the above triage classifications and evidence.
2. **Implementation Updates**:
   - Once approved, proceed to update the test implementation in `SkillsObjects.java` and `SkillsTest.java` under `test-implementation` guidelines.
   - Re-run the tests via Stage 6 (`test-run`) to verify that both tests pass cleanly and stability is achieved.
3. **Checkpoint Commit**: An `ai-draft` checkpoint commit can be created for `07-triage.md`.
