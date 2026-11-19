---
feature: skills
stage: run-report
status: Draft            # Draft | Reviewed | Approved (only the human changes this)
source: docs/features/skills/test-cases.xlsx
---

# 06. Test Run Report: Employee Management – Skills Module (Smoke Suite)

## 1. Execution Overview

| Attribute | Details |
|---|---|
| Feature | Employee Management – Skills Module |
| Target Test Class | `com.framework.tests.skillspage.SkillsTest` |
| Test Group | Smoke Suite (`FrameworkConstants.GROUP_SMOKE`) |
| Environment | Dev (`http://localhost:5173/login`) |
| Execution Browser | Chromium (Headless mode) |
| Git Branch | `qa/skills-automation` |
| Git Commit Hash | `4bae65b` |
| Execution Date | 2026-11-18 |
| Scope | Targeted rerun for `TC-006` and `TC-010` (Full suite: 10 tests) |
| Total Suite Passing | 9 (90%) |
| Total Suite Failing | 1 (10%) |
| Flaky | 0 (0%) |
| Weak Assertions | 0 (0%) |

---

## 2. Test Execution Summary Table

| Test Case ID | Scenario ID | Automated Test Method | Single Run | Repeated (5x) | Group Run | Assertion Check | Final Result | Notes |
|---|---|---|---|---|---|---|---|---|
| `TC-001` | `SCN-001` | `SkillsTest#verifySkillsMenuItemUnderEmployeeDetails` | Pass | 5/5 | Pass | Failed as expected | **Pass** | Prior run verified |
| `TC-002` | `SCN-003` | `SkillsTest#verifyNavigationToSkillsPage` | Pass | 5/5 | Pass | Failed as expected | **Pass** | Prior run verified |
| `TC-003` | `SCN-006` | `SkillsTest#verifySkillDropdownPopulatesWithSkills` | Pass | 5/5 | Pass | Failed as expected | **Pass** | Prior run verified |
| `TC-004` | `SCN-009` | `SkillsTest#verifyUserCanSelectSkill` | Pass | 5/5 | Pass | Failed as expected | **Pass** | Prior run verified |
| `TC-005` | `SCN-010` | `SkillsTest#verifySelectingSkillDisplaysMatchingEmployees` | Pass | 5/5 | Pass | Failed as expected | **Pass** | Prior run verified |
| `TC-006` | `SCN-014` | `SkillsTest#verifyMatchingEmployeesTableColumns` | Pass | 5/5 | Pass | Failed as expected | **Pass** | Verified with locator exact match |
| `TC-007` | `SCN-017` | `SkillsTest#verifyClickingEmployeeNameNavigatesToDetails` | Pass | 5/5 | Pass | Failed as expected | **Pass** | Prior run verified |
| `TC-008` | `SCN-018` | `SkillsTest#verifyAddSkillInterfaceCanBeOpened` | Pass | 5/5 | Pass | Failed as expected | **Pass** | Prior run verified |
| `TC-009` | `SCN-019` | `SkillsTest#verifySearchAndSelectEmployeeInModal` | Pass | 5/5 | Pass | Failed as expected | **Pass** | Prior run verified |
| `TC-010` | `SCN-021` | `SkillsTest#verifyAssigningNewSkillWithIntegerExperience` | Fail | 0/5 | Fail | N/A (Failed) | **Fail** | Timeout waiting for modal to close |

---

## 3. Failure Details & Evidence

### Active Failure: TC-010 (`SCN-021`)

- **Test Case**: `TC-010` — Verify assigning a new skill with valid integer experience to an employee
- **Method**: `SkillsTest#verifyAssigningNewSkillWithIntegerExperience`
- **Error Message**:
  ```text
  com.microsoft.playwright.TimeoutError: Error {
    message='Timeout 30000ms exceeded.
    name='TimeoutError
  }
  Call log:
  - waiting for getByRole(AriaRole.DIALOG, new Page.GetByRoleOptions().setName("Add or Manage Employee Skills")) to be hidden
  ```
- **Description / Behavior**:
  After selecting an employee, entering a new skill name (`Selenium`) and integer experience (`3`), and clicking the `"Save Skills"` button, the application modal did not dismiss, and `PlaywrightUtils.waitForHidden(SkillsObjects.getAddManageSkillsDialog())` timed out after 30,000ms.
- **Evidence / Trace**:
  - `target/traces/verifyAssigningNewSkillWithIntegerExperience-trace.zip`
  - `reports/ExtentReport_2026-11-18_15-49-42.html`

---

## 4. Next Steps

- `TC-006` is resolved and confirmed stable across single, repeated (5x), group, and assertion mutation checks.
- `TC-010` failure is confirmed and documented. In accordance with pipeline rules, no test code was altered to force a pass; this failure remains with **Stage 7 (Failure Triage)** for defect / action handling.
