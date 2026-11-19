---
feature: skills
stage: test-plan
status: Approved            # Draft | Reviewed | Approved (only the human changes this)
source: docs/features/skills/02-scenarios.md
---

# 03. Test Plan: Employee Management – Skills Module

## 1. Scope

### In Scope
- Verification of navigation to the Skills module under **Employee Details**.
- Validation of dynamic skill dropdown population, unique entries, in-control search, and selection.
- Verification of matching employee display in tabular format with all 7 mandatory columns and drill-down links.
- Single and multi-skill assignment and updates for existing employees, including decimal experience and special characters.
- Bulk skill upload via CSV and Excel (.xlsx) files, preview extraction, cancellation, and confirmation.
- Business rule enforcement: experience independence, employee-specific deduplication, update overwrite, and data isolation.
- Input validation, boundary checks, and client-side error handling for form fields and file uploads.
- Automated regression suite using Java Playwright and TestNG following repository coding standards.

### Out of Scope
- Performance, load, and stress testing of high-volume bulk uploads.
- Security and penetration testing (e.g., SQL injection, XSS payload testing in file contents).
- Modification of existing core Employee Details pages beyond regression verification of entry points.
- Localization and multi-language translation testing.

---

## 2. Approach

Testing follows a risk-based, automated-first strategy:
- **UI & Navigation**: Automated end-to-end tests verify menu visibility, ordering, URL routing, and page load states using `PlaywrightUtils`.
- **Functional Happy Path**: Automated tests verify skill selection, employee listing rendering, modal interactions, single/multi-skill saving, and bulk upload preview/commit workflows.
- **Data Validation & Boundaries**: Automated tests execute negative and boundary scenarios (e.g., empty fields, negative/decimal experience, malformed files, unsupported extensions) to confirm robust error messaging and state preservation.
- **Business Rule Integrity**: Automated tests verify deduplication, overwrite behavior, and employee isolation by asserting state before and after save operations.
- **Manual / Exploratory**: Visual layout subtleties (exact submenu ordering) and transient animation/loading indicators are verified manually to avoid test flakiness.

---

## 3. Risk and Priority

| Risk Category | Impact | Likelihood | Mitigation Strategy |
|---|---|---|---|
| **Data Corruption / Cross-Employee Leakage** | High | Low | High-priority automation covering `SCN-025`, `SCN-026`, and `SCN-042` to verify updates remain isolated to the selected employee. |
| **Bulk Upload Ingestion Failures** | High | Medium | Comprehensive test data coverage (`SCN-037`–`SCN-049`) verifying valid CSV/XLSX parsing, malformed row rejection, and transaction commit. |
| **Dynamic Dropdown / Listing Sync Failure** | Medium | Medium | Automated checks verifying reactive refresh of dropdown and employee grid immediately after save/upload (`SCN-027`, `SCN-028`, `SCN-044`, `SCN-045`). |
| **UI Timing / Asynchronous Latency** | Medium | High | Rely on explicit `PlaywrightUtils` auto-waiting (`waitForVisible`, `waitForNavigation`) rather than arbitrary sleep calls. |

---

## 4. Automation Decision

| Scenario ID | Title | Priority | Automate? | Reason |
|---|---|---|---|---|
| `SCN-001` | Skills menu item under Employee Details | High | **Yes** | Critical navigation entry point; stable DOM locator. |
| `SCN-002` | Skills menu item positioned as last submenu item | Low | **No** | Visual layout check; low regression risk; best verified manually. |
| `SCN-003` | Selecting Skills menu item navigates to Skills page | High | **Yes** | Core route activation and page load verification. |
| `SCN-004` | Existing Employee Details submenus remain functional | Medium | **Yes** | Regression check for sibling navigation routes. |
| `SCN-005` | Initial page state prompts user to select a skill | Medium | **Yes** | Stable prompt/placeholder visibility check. |
| `SCN-006` | Skill dropdown dynamically populates with employee skills | High | **Yes** | High-value functional check for data retrieval. |
| `SCN-007` | Skill dropdown contains only unique skill names | High | **Yes** | Validates core deduplication logic. |
| `SCN-008` | Searching by skill name filters dropdown options | High | **Yes** | Key user interaction control; high reusability. |
| `SCN-009` | User can select a skill from dropdown | High | **Yes** | Standard user selection interaction. |
| `SCN-010` | Selecting skill displays all matching employees | High | **Yes** | Primary query and grid rendering happy path. |
| `SCN-011` | Skill search matches case-insensitively | Medium | **Yes** | Verifies search normalization logic. |
| `SCN-012` | Empty state message when no employees match skill | Medium | **Yes** | Stable UI state assertion. |
| `SCN-013` | Changing selected skill updates employee listing | High | **Yes** | Reactive filter update verification. |
| `SCN-014` | Matching employees display in tabular format (7 columns) | High | **Yes** | Structural table layout and column header assertion. |
| `SCN-015` | Loading indicator displayed during data retrieval | Low | **No** | Transient spinner; high risk of timing flakiness in fast local runs. |
| `SCN-016` | Employee name is rendered as clickable link | High | **Yes** | DOM attribute and role check (`href`/link). |
| `SCN-017` | Clicking employee name navigates to Employee Details | High | **Yes** | Critical drill-down link and route verification. |
| `SCN-018` | Add Skill interface can be opened | High | **Yes** | Modal/drawer toggle verification. |
| `SCN-019` | User can search and select existing employee | High | **Yes** | Prerequisite workflow step for skill assignment. |
| `SCN-020` | Validation error when saving without employee selection | Medium | **Yes** | Form validation check; prevents orphan records. |
| `SCN-021` | Assigning new skill with valid integer experience | High | **Yes** | Core end-to-end skill creation workflow. |
| `SCN-022` | Assigning skill with valid decimal experience | Medium | **Yes** | Boundary input validation (fractional years). |
| `SCN-023` | Assigning skill experience exceeding total experience | Medium | **Yes** | Business rule verification (attribute independence). |
| `SCN-024` | Assigning multiple skills in a single session | High | **Yes** | Core multi-item form workflow. |
| `SCN-025` | Updating skill experience overwrites previous experience | High | **Yes** | Deduplication and update-in-place integrity check. |
| `SCN-026` | Skill update on one employee does not alter others | High | **Yes** | Data isolation check across records. |
| `SCN-027` | Employee listing refreshes immediately after save | High | **Yes** | Reactive table refresh verification. |
| `SCN-028` | Skill dropdown refreshes immediately after save | High | **Yes** | Reactive filter synchronization check. |
| `SCN-029` | Success notification displayed upon saving skills | Medium | **Yes** | Toast/alert notification assertion. |
| `SCN-030` | Validation error when skill name is empty | High | **Yes** | Mandatory field validation check. |
| `SCN-031` | Assigning skill name with special characters | Medium | **Yes** | Character set compatibility check (`C++`, `.NET`). |
| `SCN-032` | Validation error when skill experience is non-numeric | High | **Yes** | Input format validation check. |
| `SCN-033` | Validation error when skill experience is negative | High | **Yes** | Negative number rejection check. |
| `SCN-034` | Validation error when skill experience is empty | Medium | **Yes** | Mandatory experience field validation check. |
| `SCN-035` | Bulk Upload interface can be opened | High | **Yes** | Upload modal trigger and visibility check. |
| `SCN-036` | Employee selection required before file upload | High | **Yes** | Mandatory prerequisite validation check. |
| `SCN-037` | Uploading valid CSV displays data in preview | High | **Yes** | Core CSV file ingestion happy path. |
| `SCN-038` | Uploading valid Excel (.xlsx) displays data in preview | High | **Yes** | Core Excel file ingestion happy path. |
| `SCN-039` | Preview displays Skill and Experience values accurately | High | **Yes** | Preview data extraction grid validation. |
| `SCN-040` | Canceling upload from preview dismisses without saving | Medium | **Yes** | Workflow cancellation and rollback check. |
| `SCN-041` | Confirming upload saves skills against selected employee | High | **Yes** | Core upload commit and persistence workflow. |
| `SCN-042` | Bulk upload overwrites experience without duplicates | High | **Yes** | Bulk deduplication and update verification. |
| `SCN-043` | Success notification displayed after bulk upload | Medium | **Yes** | Feedback banner assertion post-upload. |
| `SCN-044` | Employee listing refreshes after bulk upload | High | **Yes** | Reactive table refresh verification. |
| `SCN-045` | Skill dropdown refreshes with newly uploaded skills | High | **Yes** | Reactive dropdown refresh verification. |
| `SCN-046` | Error message when uploading unsupported file format | High | **Yes** | File extension validation check. |
| `SCN-047` | Validation error when uploading empty file | Medium | **Yes** | 0-byte file boundary validation check. |
| `SCN-048` | Validation error when file is missing required columns | High | **Yes** | File header schema validation check. |
| `SCN-049` | Validation error when file contains malformed data rows | High | **Yes** | Row-level data validation check. |
| `SCN-050` | Error notification when skill dropdown retrieval fails | Medium | **No** | Requires backend failure injection / network fault mock; manual or mock-dependent. |
| `SCN-051` | Error notification when employee listing retrieval fails | Medium | **No** | Requires backend failure injection / network fault mock; manual or mock-dependent. |
| `SCN-052` | Error notification when skill save operation fails | Medium | **No** | Requires backend persistence failure injection; manual or mock-dependent. |

**Summary of Decisions:**
- **Automated**: 47 scenarios (90.4%)
- **Manual / Mock-dependent**: 5 scenarios (9.6% — `SCN-002`, `SCN-015`, `SCN-050`, `SCN-051`, `SCN-052`)

---

## 5. Test Suites

### Smoke Suite (`FrameworkConstants.GROUP_SMOKE`)
Focuses on critical end-to-end happy paths to quickly establish build stability:
- `SCN-001`, `SCN-003`: Navigate to Skills page from main menu.
- `SCN-006`, `SCN-009`, `SCN-010`, `SCN-014`: Populate dropdown, select skill, and view matching employee listing table with 7 columns.
- `SCN-017`: Drill down to Employee Details from the listing.
- `SCN-018`, `SCN-019`, `SCN-021`: Add single skill with valid integer experience for an employee.
- `SCN-035`, `SCN-036`, `SCN-037`, `SCN-041`: Bulk upload valid CSV file, preview, and confirm save.

### Regression Suite (`FrameworkConstants.GROUP_REGRESSION`)
Includes all 47 automated scenarios:
- All Smoke tests.
- Multi-skill addition (`SCN-024`), decimal experience (`SCN-022`), special characters (`SCN-031`), and experience independence (`SCN-023`).
- Overwrite and isolation checks (`SCN-025`, `SCN-026`, `SCN-042`).
- Excel (.xlsx) bulk upload flow (`SCN-038`, `SCN-039`) and cancellation flow (`SCN-040`).
- Input validations and error messaging (`SCN-020`, `SCN-030`, `SCN-032`, `SCN-033`, `SCN-034`, `SCN-046`, `SCN-047`, `SCN-048`, `SCN-049`).
- Empty state display (`SCN-012`) and sibling submenu preservation (`SCN-004`).

---

## 6. Test Data

All test data is externalized and loaded dynamically or via `ConfigReader`; no test data or credentials are hardcoded:

1. **Application URLs and Credentials**:
   - URL: `ConfigReader.get("base.url")`
   - Credentials: `ConfigReader.getCredential("valid.username")`, `ConfigReader.getCredential("valid.password")`
2. **Pre-existing Employees & Skills**:
   - Seeded/existing employee identifiers and names configured via `config.properties` or test data JSON (e.g. `EMP-001` / Alice Smith, `EMP-002` / Bob Jones).
3. **Upload Test Artifacts** (located under `src/test/resources/data/skills/`):
   - `valid_skills.csv`: CSV containing valid `Skill` and `Experience` rows.
   - `valid_skills.xlsx`: Excel file containing valid `Skill` and `Experience` rows.
   - `unsupported_format.txt`: Non-CSV/Excel file to verify extension rejection.
   - `empty_file.csv`: 0-byte file to test empty upload validation.
   - `missing_headers.csv`: CSV lacking the mandatory `Skill` or `Experience` column header.
   - `malformed_rows.csv`: File containing negative experience and blank skill rows.

---

## 7. Environment

- **Target Application**: Employee Management Web Application running at `base.url` (default: `http://localhost:5173/login`).
- **Execution Browser**: Chromium (configured in `config.properties`), with multi-browser support for Firefox and WebKit.
- **Prerequisites**:
  - Application server and database running with seeded employee records.
  - Active network connectivity to local/remote test instance.
  - Test runner: Java 11+, Maven 3.8+, Playwright for Java 1.44.0, TestNG 7.10.2.

---

## 8. Entry and Exit Criteria

### Entry Criteria
1. Feature code deployed to test environment and accessible at configured `base.url`.
2. `02-scenarios.md` approved by human reviewer.
3. Test environment seeded with prerequisite employee and skill data.
4. Execution branch `qa/skills-automation` ready and clean.

### Exit Criteria
1. 100% of approved automated test cases implemented and executed.
2. 100% pass rate on Smoke test suite.
3. 95%+ pass rate on Regression test suite (any failures triaged and logged as defects).
4. Zero open Critical or High severity defects.
5. All automated tests verified for stability (passed 5 consecutive runs with no flakiness).
6. Test run results recorded in `test-cases.xlsx` and `06-run-report.md`.

---

## 9. Risks and Assumptions [ai]

- `[ai]` **Backend Error Simulation**: Scenarios `SCN-050`, `SCN-051`, and `SCN-052` (handling backend retrieval/save failures) require network request interception/aborting or server-side failure injection. They are assigned to manual exploratory testing unless mock-intercept fixtures are explicitly requested.
- `[ai]` **Upload File Storage**: Upload test files (`.csv`, `.xlsx`, `.txt`) will be managed under `src/test/resources/data/skills/` so tests can run self-contained across any developer workstation or CI runner.
- `[ai]` **Timing of Reactive Updates**: Scenarios `SCN-027`, `SCN-028`, `SCN-044`, and `SCN-045` verify immediate reactive refresh of UI controls; tests will use Playwright's web-first locator assertions (`toHaveText`, `toBeVisible`) with standard framework timeouts rather than hardcoded sleep delays.
- `[ai]` **Employee Seed Data**: It is assumed that at least two active employee records exist in the application database to allow testing of data isolation (`SCN-026`).
