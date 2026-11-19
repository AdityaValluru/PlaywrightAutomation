---
feature: skills
stage: pr
status: Draft            # Draft | Reviewed | Approved (only the human changes this)
source: docs/features/skills/
---

# 08. Pull Request Description: Employee Management – Skills Module Automation

## 1. Summary & Scope

This PR introduces end-to-end UI automation and full QA pipeline artifacts for the **Employee Management – Skills Module** (`docs/features/skills`).

### Scope of Changes
- **Page Objects Repository**: `com.framework.tests.skillspage.SkillsObjects` containing semantic, accessible locators (`AriaRole`).
- **Actions Class**: `com.framework.tests.skillspage.SkillsActions` (extends `BasePage`) with fluent method chaining, logging, and Extent Report hooks.
- **Test Class**: `com.framework.tests.skillspage.SkillsTest` (extends `BaseTest`) implementing 10 smoke test cases (TC-001 through TC-010).
- **QA Pipeline Documentation**:
  - `01-srs-review.md` (Approved)
  - `02-scenarios.md` (Approved)
  - `03-test-plan.md` (Approved)
  - `05-implementation-notes.md` (Draft)
  - `06-run-report.md` (Draft)
  - `07-triage.md` (Approved)
  - `test-cases.xlsx` (Execution Log & Test Cases sheets updated)

---

## 2. Test Coverage

### Requirements & Test Cases Covered
10 smoke test cases covering core navigation, dropdown population, filtering, tabular employee presentation, and Add/Manage modal interactions:

| Test Case ID | Scenario ID | Requirement Traceability | Title | Status | Automated Test Method |
|---|---|---|---|---|---|
| `TC-001` | `SCN-001` | `REQ-NAV-001` | Skills navigation item visible under Employee Details | Pass | `SkillsTest#verifySkillsMenuItemUnderEmployeeDetails` |
| `TC-002` | `SCN-003` | `REQ-NAV-002` | Navigation to Skills landing page | Pass | `SkillsTest#verifyNavigationToSkillsPage` |
| `TC-003` | `SCN-006` | `REQ-EMP-003` | Skill dropdown dynamically populates with skills | Pass | `SkillsTest#verifySkillDropdownPopulatesWithSkills` |
| `TC-004` | `SCN-009` | `REQ-EMP-004` | Selection of a skill from dropdown | Pass | `SkillsTest#verifyUserCanSelectSkill` |
| `TC-005` | `SCN-010` | `REQ-EMP-001` | Selecting a skill displays matching employees | Pass | `SkillsTest#verifySelectingSkillDisplaysMatchingEmployees` |
| `TC-006` | `SCN-014` | `REQ-EMP-001`, `REQ-EMP-002` | Matching employees table contains all 7 required columns | Pass | `SkillsTest#verifyMatchingEmployeesTableColumns` |
| `TC-007` | `SCN-017` | `REQ-EMP-005` | Clicking employee name navigates to Employee Details view | Pass | `SkillsTest#verifyClickingEmployeeNameNavigatesToDetails` |
| `TC-008` | `SCN-018` | `REQ-ADD-001`, `REQ-ADD-002` | Add / Manage Skills modal opens with required controls | Pass | `SkillsTest#verifyAddSkillInterfaceCanBeOpened` |
| `TC-009` | `SCN-019` | `REQ-ADD-003` | Search and select employee in Add Skill modal | Pass | `SkillsTest#verifySearchAndSelectEmployeeInModal` |
| `TC-010` | `SCN-021` | `REQ-ADD-004`, `REQ-ADD-005`, `REQ-ADD-008`, `REQ-ADD-010` | Assign new skill with integer experience | Fail | `SkillsTest#verifyAssigningNewSkillWithIntegerExperience` |

### Workbook Changes (`test-cases.xlsx`)
- **Test Cases Sheet**: Rows `TC-001` through `TC-010` updated with `Automated Test` methods, `Last Result` (`Pass` for TC-001..TC-009, `Fail` for TC-010), and `Last Run Date` (`2026-11-18`).
- **Execution Log Sheet**: Appended run records `RUN-001` through `RUN-012` capturing single, repeated (5x), group, and assertion check results with commit hashes and evidence paths.

### Uncovered Scenarios
- **Bulk Upload Scenarios** (`SCN-035` to `SCN-037`, `SCN-041`) and non-smoke scenarios (`SCN-002`, `SCN-004`, `SCN-005`, `SCN-007`, `SCN-008`, `SCN-011`..`SCN-013`, `SCN-015`, `SCN-016`, `SCN-020`, `SCN-022`..`SCN-034`, `SCN-038`..`SCN-040`) are cataloged for future sprint automation in [docs/pipeline-backlog.md](file:///Users/adityavalluru/eclipse-workspace-PlayWright-Copilot/PlayWrightCopilot/docs/pipeline-backlog.md).

---

## 3. How to Run the Tests

```bash
# 1. Compile test and framework code
mvn compile test-compile -q

# 2. Run all Skills smoke tests
mvn test -Dtest=SkillsTest

# 3. Run a single test method
mvn test -Dtest=SkillsTest#verifyMatchingEmployeesTableColumns

# 4. Run the full smoke suite via profile
mvn test -P smoke

# 5. Run in headless mode (default is headed in config.properties)
mvn test -Dtest=SkillsTest -Dheadless=true
```

---

## 4. Test Execution Results

- **Total Automated Tests**: 10
- **Passed**: 9 (90.0%)
- **Failed**: 1 (10.0%)
- **Flaky**: 0 (0.0%)
- **Assertion Mutation Checks**: Passed (tested and verified failure on negative mutation)
- **Detailed Run Report**: See [06-run-report.md](file:///Users/adityavalluru/eclipse-workspace-PlayWright-Copilot/PlayWrightCopilot/docs/features/skills/06-run-report.md)

---

## 5. Known Failures

### `TC-010`: `verifyAssigningNewSkillWithIntegerExperience`
- **Symptom**: `TimeoutError: 30000ms exceeded waiting for dialog to be hidden`.
- **Root Cause & Triage**: Analyzed in [07-triage.md](file:///Users/adityavalluru/eclipse-workspace-PlayWright-Copilot/PlayWrightCopilot/docs/features/skills/07-triage.md) as a **Test Logic / Test Data issue** (not an application bug). The test selected an employee already possessing `"Selenium"`, and clicking `"Add another skill"` appended an empty row which triggered application validation (`REQ-VAL-003`, `REQ-VAL-004`), preventing modal closure.
- **Resolution Path**: Deferred for test refinement and precondition alignment as tracked in [docs/pipeline-backlog.md](file:///Users/adityavalluru/eclipse-workspace-PlayWright-Copilot/PlayWrightCopilot/docs/pipeline-backlog.md). No application bug was raised.

---

## 6. AI Self-Review (First Pass)

> [!NOTE]
> This is a first-pass AI automated check against the framework's coding standards and definition of done. It does not replace human code review.

| Check | Status | Evidence / Notes |
|---|---|---|
| **Right** | Passed | Each test method asserts observable outcomes specified in `test-cases.xlsx`. Mutation assertion checks demonstrated that assertions fail when expectations are modified. |
| **Stable** | Passed | No `Thread.sleep()` used anywhere. All synchronization uses `PlaywrightUtils` dynamic waits (`waitForVisible`, `waitForHidden`). Passed 5/5 repeated runs for all passing tests. |
| **Fits** | Passed | Follows Page Object Model pattern. Page objects extend `BasePage` and tests extend `BaseTest`. Method chaining (`return this`) implemented in `SkillsActions`. Semantic role locators prioritized via `PlaywrightUtils` with zero direct locator creation in tests. |
| **Real** | Passed | Code compiles cleanly with Maven (`mvn compile test-compile -q`). Verified against application running on `http://localhost:5173/login`. |
| **Clean** | Passed | No credentials, API tokens, or secrets committed. Credentials fetched via `ConfigReader.getCredential()`. No unrelated application or build files modified. |

---

## 7. Reviewer Checklist

- [ ] Locators strictly use accessible semantic roles (`AriaRole`) via `PlaywrightUtils`.
- [ ] No hard-coded timeouts, credentials, or URLs exist in test or action code.
- [ ] Action methods log appropriately using inherited `log` and `ExtentReportManager`.
- [ ] All test methods are tagged with appropriate TestNG groups (`GROUP_SMOKE`, `GROUP_REGRESSION`).
- [ ] Workbook `test-cases.xlsx` reflects current execution state without corrupted formulas or deleted sheets.
