---
feature: skills
stage: srs-review
status: Approved            # Draft | Reviewed | Approved (only the human changes this)
source: docs/features/skills/SRS.md (SRS-EMP-SKILLS-001, Version 1.0)
---

# 01. SRS Review: Employee Management – Skills Module

## 1. Summary

The Skills module introduces skill management capabilities under the existing Employee Details navigation menu of the Employee Management application. It enables users to dynamically search and filter employee records by skill, view matching employees in a structured table, navigate to individual employee details, add or update skills and experience years for specific employees, and bulk upload skills via CSV or Excel files with preview and duplicate resolution.

---

## 2. Requirements List

| Requirement ID | Category | Summary / Statement |
|---|---|---|
| `REQ-NAV-001` (FR-NAV-001) | Navigation | Provide "Skills" menu item under Employee Details. |
| `REQ-NAV-002` (FR-NAV-002) | Navigation | Skills menu item placed as the last submenu item under Employee Details. |
| `REQ-NAV-003` (FR-NAV-003) | Navigation | Selecting Skills menu item navigates user to the Skills page. |
| `REQ-NAV-004` (FR-NAV-004) | Navigation | Existing Employee Details submenu items remain unaffected. |
| `REQ-SKL-001` (FR-SKL-001) | Search & Filter | Dynamically populate skill dropdown from existing employee records without hardcoded values. |
| `REQ-SKL-002` (FR-SKL-002) | Search & Filter | Display unique skill names without duplicate entries in the dropdown. |
| `REQ-SKL-003` (FR-SKL-003) | Search & Filter | Support text-based search/filter within the skill selection control. |
| `REQ-SKL-004` (FR-SKL-004) | Search & Filter | User can select a skill from the dropdown. |
| `REQ-SKL-005` (FR-SKL-005) | Search & Filter | Selecting a skill retrieves and displays all matching employees. |
| `REQ-SKL-006` (FR-SKL-006) | Search & Filter | Skill matching is case-insensitive (e.g. Java, JAVA, java). |
| `REQ-SKL-007` (FR-SKL-007) | Search & Filter | Display all matching employees when multiple employees possess the skill. |
| `REQ-SKL-008` (FR-SKL-008) | Search & Filter | Display an appropriate message when no employees match the selected skill. |
| `REQ-SKL-009` (FR-SKL-009) | Search & Filter | Changing selected skill refreshes employee listing with new results. |
| `REQ-SKL-010` (FR-SKL-010) | Search & Filter | Display prompt to select or search for a skill on initial page load. |
| `REQ-EMP-001` (FR-EMP-001) | Employee Listing | Display matching employee records in a tabular format. |
| `REQ-EMP-002` (FR-EMP-002) | Employee Listing | Listing includes: Employee ID, Employee Name, Email, Location, Total Experience, Skill, Skill Experience. |
| `REQ-EMP-003` (FR-EMP-003) | Employee Listing | Employee name is clickable. |
| `REQ-EMP-004` (FR-EMP-004) | Employee Listing | Clicking employee name navigates to the employee's details page/view. |
| `REQ-EMP-005` (FR-EMP-005) | Employee Listing | Display details for the correct Employee ID matching the clicked record. |
| `REQ-EMP-006` (FR-EMP-006) | Employee Listing | Show loading indicator during asynchronous data retrieval. |
| `REQ-ADD-001` (FR-ADD-001) | Add/Update Skill | Provide option to add skills to existing employee records. |
| `REQ-ADD-002` (FR-ADD-002) | Add/Update Skill | User can search for and select an existing employee before assigning skills. |
| `REQ-ADD-003` (FR-ADD-003) | Add/Update Skill | Allow assigning multiple skills to an employee. |
| `REQ-ADD-004` (FR-ADD-004) | Add/Update Skill | Each assigned skill must have experience value expressed in years. |
| `REQ-ADD-005` (FR-ADD-005) | Add/Update Skill | Allow associating a new skill with an existing employee. |
| `REQ-ADD-006` (FR-ADD-006) | Add/Update Skill | Update existing skill experience if skill already exists for the employee. |
| `REQ-ADD-007` (FR-ADD-007) | Add/Update Skill | Skill updates are employee-specific; do not alter other employees. |
| `REQ-ADD-008` (FR-ADD-008) | Add/Update Skill | Persist added/updated skills via backend storage. |
| `REQ-ADD-009` (FR-ADD-009) | Add/Update Skill | Refresh employee listing and available skills list after save. |
| `REQ-ADD-010` (FR-ADD-010) | Add/Update Skill | Display success or error notification following add/update. |
| `REQ-UPL-001` (FR-UPL-001) | Bulk Upload | Provide option to upload multiple skills via file. |
| `REQ-UPL-002` (FR-UPL-002) | Bulk Upload | Support CSV (.csv) and Excel (.xlsx) file formats. |
| `REQ-UPL-003` (FR-UPL-003) | Bulk Upload | Require selecting an existing employee before file upload. |
| `REQ-UPL-004` (FR-UPL-004) | Bulk Upload | Allow file selection from local system. |
| `REQ-UPL-005` (FR-UPL-005) | Bulk Upload | Parse uploaded file and extract skill names and experience values. |
| `REQ-UPL-006` (FR-UPL-006) | Bulk Upload | Support structured tabular format with `Skill` and `Experience` columns. |
| `REQ-UPL-007` (FR-UPL-007) | Bulk Upload | Display preview of extracted data before saving. |
| `REQ-UPL-008` (FR-UPL-008) | Bulk Upload | User can confirm or cancel upload after reviewing preview. |
| `REQ-UPL-009` (FR-UPL-009) | Bulk Upload | Save valid skill records against selected employee upon confirmation. |
| `REQ-UPL-010` (FR-UPL-010) | Bulk Upload | Update existing skill experience instead of creating duplicates on upload. |
| `REQ-UPL-011` (FR-UPL-011) | Bulk Upload | Validate uploaded file and contents before saving. |
| `REQ-UPL-012` (FR-UPL-012) | Bulk Upload | Display success or failure notification after upload operation. |
| `REQ-UPL-013` (FR-UPL-013) | Bulk Upload | Refresh employee skill information and available skills list post-upload. |
| `REQ-BR-001` to `REQ-BR-010` | Business Rules | Business rules BR-001 to BR-010 governing multi-skill association, experience independence, deduplication, and non-regression. |
| `REQ-VAL-001` to `REQ-VAL-014` | Data Validation | Input validation rules covering employee selection, non-empty skills, case insensitivity, numeric non-negative experience, and file format/column checks. |
| `REQ-ERR-001` to `REQ-ERR-008` | Error Handling | Graceful error handling for API/network failures, persistence errors, invalid file formats, and user-friendly error messages without technical leaks. |

---

## 3. Gaps and Ambiguities

1. **Skill Experience vs. Total Experience Relationship**
   - *SRS Section*: Section 4 (`BR-003: Total Experience and Skill Experience shall be maintained as separate attributes`), Section 5.3 (`VAL-008: Negative experience values shall not be accepted`).
   - *Issue*: Can an employee have a Skill Experience greater than their Total Experience (e.g. Total Experience = 3 years, Java Experience = 7 years)? The SRS does not state whether `Skill Experience <= Total Experience` is a validation constraint or if skill experience can exceed total experience.

2. **Numeric Experience Format and Precision**
   - *SRS Section*: Section 5.3 (`VAL-007: Skill experience shall be provided as a valid numeric value`, `VAL-010: Experience values shall be interpreted in years`).
   - *Issue*: Are fractional/decimal years permitted (e.g., `2.5` or `0.5` years), or must experience strictly be a whole integer? What is the upper bound (e.g., maximum 50 or 70 years)?

3. **Allowed Characters and Length Constraints for Skill Names**
   - *SRS Section*: Section 5.2 (`VAL-003: Skill names shall not be empty`, `VAL-004: Skill names shall be treated consistently regardless of letter casing`).
   - *Issue*: What are the min/max character lengths for skill names? Are special characters permitted (e.g. `C++`, `C#`, `.NET`, `Node.js`, `CI/CD`)? How are leading, trailing, and repeated whitespace handled (e.g. `"  Java  "` vs `"Java"`)?

4. **Exact Notification and Feedback UI Messages**
   - *SRS Section*: Section 3.2 (`FR-SKL-008: appropriate message indicating that no matching employees were found`), Section 3.4 (`FR-ADD-010: appropriate success or error notifications`), Section 5.4 (`VAL-014: meaningful validation messages`), Section 7 (`ERR-007: meaningful error messages`).
   - *Issue*: None of the user-facing text strings, notification delivery mechanisms (toast, inline alert, modal), or auto-dismiss timeouts are specified. Automated testing requires deterministic locator and assertion targets.

5. **Bulk Upload File and Row Constraints**
   - *SRS Section*: Section 3.5 (`FR-UPL-002: CSV and Excel`), Section 5.4 (`VAL-011`, `VAL-012`, `VAL-013`).
   - *Issue*: What is the maximum file size limit (e.g., 5 MB, 10 MB)? What is the maximum number of rows allowed per file? Are column headers case-sensitive (e.g. `skill` vs `Skill`, `experience` vs `Experience`)? Can columns appear in any order? What happens if extra unrecognized columns exist?

6. **Bulk Upload Transaction Atomicity (Partial vs. All-or-Nothing)**
   - *SRS Section*: Section 3.5 (`FR-UPL-007: preview`, `FR-UPL-009: valid skill records shall be saved`, `FR-UPL-011`).
   - *Issue*: If a file contains 10 rows where 8 are valid and 2 are invalid (e.g. negative experience or blank skill name), does the entire upload fail, or can the user save the valid rows while viewing errors on the invalid rows?

7. **Duplicate Skills within the Same Upload File**
   - *SRS Section*: Section 3.5 (`FR-UPL-010: If an uploaded skill already exists for the selected employee, the existing skill experience shall be updated`).
   - *Issue*: What happens if the uploaded file contains multiple rows for the same skill with differing experience values (e.g., Row 1: `Java - 3 yrs`, Row 5: `Java - 6 yrs`)? Does the last row win, does the first row win, or is the file rejected as containing duplicates?

8. **Skill Removal / Deletion Capability**
   - *SRS Section*: Section 1.2 (Scope), Section 3.4 (Add and Update).
   - *Issue*: There is no requirement or scope defined for removing or deleting a skill from an employee if it was assigned erroneously. Does a delete/archive capability exist, or is setting experience to 0 used, or is deletion out of scope?

9. **Employee Listing Pagination and Sorting**
   - *SRS Section*: Section 3.3 (`FR-EMP-001: tabular format`, `FR-EMP-002`).
   - *Issue*: If a popular skill (e.g. "Java") matches hundreds of employees, how is the list handled? Is there client-side or server-side pagination? Are table columns sortable (e.g., sort by Name or Skill Experience)?

10. **Navigation Details on Employee Click**
    - *SRS Section*: Section 3.3 (`FR-EMP-003`, `FR-EMP-004: Selecting an employee name shall navigate to the existing Employee Details page or details view`).
    - *Issue*: Does clicking the employee name navigate within the same tab, open a new browser tab, or open a slide-out drawer/modal? If navigating away in the same tab, does navigating back preserve the previously selected skill and search filter?

---

## 4. Untestable Statements

The following requirements lack quantifiable, observable criteria for black-box test automation:

| Requirement ID | Statement | Why It Is Untestable as Written | Testable Clarification Required |
|---|---|---|---|
| `FR-SKL-001` | "The skill list shall not depend on hardcoded values." | Internal implementation detail. Black-box UI automation cannot inspect backend code architecture directly. | Verify dynamically by adding a new skill to an employee and asserting that the new skill appears in the dropdown list. |
| `NFR-USR-001` | "The Skills page shall maintain consistency with the existing application's user interface." | Subjective visual design aesthetic without objective measurable criteria. | Validate specific shared UI design tokens (fonts, colors, layout components) or mark manual review only. |
| `NFR-USR-002` | "The skill search and employee selection controls shall be intuitive and easy to use." | Usability opinion, cannot be verified or failed by an automated test assertion. | Convert to specific functional verification (e.g., search filters dropdown list within 300ms, dropdown keyboard navigation works). |
| `NFR-PER-001` | "retrieve and display skill information within an acceptable response time under normal operating conditions." | "Acceptable response time" and "normal operating conditions" are vague and unquantified. | Specify threshold: e.g. "Page and filter results render within 2.0 seconds under test environment conditions." |
| `NFR-PER-002` | "Skill filtering shall not cause unnecessary delays or repeated requests." | "Unnecessary delays" is subjective and unquantified. | Define network call criteria: e.g. exactly one API call per selection or debounce of 300ms on search input. |
| `NFR-PER-003` | "File processing shall handle supported uploads without application instability." | "Application instability" lacks technical thresholds (memory leak, crash, timeout). | Define explicit maximum processing duration (e.g. <= 5s for 500 rows) and HTTP status expectation. |
| `NFR-MNT-001` / `NFR-MNT-002` | "follow existing application architecture and coding conventions" / "reused wherever appropriate" | Code-level architectural requirement, not observable via UI/E2E test automation. | Exclude from functional test suite; govern via code review and static analysis. |

---

## 5. Assumptions [ai]

- `[ai]` **Decimal Experience**: Skill experience allows decimal numbers up to 1 decimal place (e.g., `1.5`, `2.0`), with a minimum of `0` and a reasonable maximum of `50`.
- `[ai]` **Skill Experience vs Total Experience**: The application permits `Skill Experience` to be entered independently of `Total Experience` without enforcing `Skill Experience <= Total Experience` validation at the UI layer.
- `[ai]` **Skill Name Normalization**: Skill names are trimmed of leading/trailing whitespace. Standard technical symbols (e.g. `C++`, `C#`, `.NET`, `Node.js`) are accepted as valid skill names.
- `[ai]` **Case-Insensitive Display**: When matching or storing case-insensitively, the UI displays the canonical name or the first-entered casing, and does not create duplicate options in the dropdown.
- `[ai]` **Bulk Upload Atomicity**: Bulk upload operates atomically; if validation fails on any row during file upload, the user is notified of the errors in the preview, and invalid records cannot be committed.
- `[ai]` **Bulk Upload Skill Update**: When an uploaded skill already exists for an employee, the uploaded experience value replaces (overwrites) the existing skill experience value, rather than adding to it.
- `[ai]` **Feedback Notifications**: Feedback notifications appear as standard application toast banners at the top or top-right of the screen and contain descriptive status text.
- `[ai]` **Employee Details Navigation**: Clicking an employee name navigates the current page to the Employee Details view in the same browser tab.
- `[ai]` **Skill Deletion Out of Scope**: Deletion of skills is not supported in this version of the Skills module.

---

## 6. Risk Areas

- **High Risk**:
  - **Bulk Upload Data Integrity & Parsing (FR-UPL-005, FR-UPL-010, VAL-013)**: Parsing CSV/XLSX files, handling malformed structures, empty cells, special characters, and ensuring existing skills are correctly updated without corrupting employee records.
  - **Cross-Employee Data Contamination (FR-ADD-007, BR-006, NFR-REL-003)**: Ensuring that adding or uploading skills for Employee X never accidentally mutates or associates records with Employee Y.
  - **Case-Insensitive Deduplication Integrity (FR-SKL-002, FR-SKL-006, BR-004, BR-005)**: Ensuring that variations like `java`, `Java`, and `JAVA` do not create duplicate records in the database or duplicate options in the dropdown.
- **Medium Risk**:
  - **Dropdown Retrieval Performance at Scale (FR-SKL-001, FR-SKL-005, NFR-PER-001)**: If the dropdown queries all distinct skills across thousands of employee records on the fly, load times or filtering latency could degrade without caching or indexing.
  - **Preview & Confirmation Synchronization (FR-UPL-007, FR-UPL-008, FR-UPL-009)**: State divergence between what is previewed from a file and what actually gets written to the database if the user cancels or re-uploads.
  - **Navigation State Loss (FR-EMP-003, FR-EMP-004)**: Clicking through to Employee Details and using the browser back button might lose the selected skill, requiring the user to re-filter.
- **Low Risk**:
  - **Menu Positioning & Visual Alignment (FR-NAV-001, FR-NAV-002)**: Visual check that "Skills" is the last item in the Employee Details submenu.
  - **Loading Indicator Display (FR-EMP-006)**: Visibility of spinner/skeleton loader during fast local network responses.

---

## 7. Confirm Intended Behavior (Reverse-Engineered SRS Check)

The SRS was prepared from an implemented feature. The following behaviors in the specification read like implementation artifacts or contain ambiguities that require human confirmation before test design:

1. **Pre-selecting Employee Before Bulk Upload (FR-UPL-003, BR-008)**:
   - *Observation*: The SRS requires selecting a single employee *before* uploading a file. A standard bulk upload typically allows a file with an `Employee ID` column to update skills across multiple employees at once.
   - *Question for Human*: Is bulk upload intentionally restricted to uploading skills for only **one employee at a time**, or should the file format support multi-employee uploads via an `Employee ID` column?

2. **Skill Experience Exceeding Total Experience (BR-003, VAL-008)**:
   - *Observation*: Total Experience and Skill Experience are kept separate, and VAL-008 only rejects negative numbers.
   - *Question for Human*: Is it intended that an employee can have more years of experience in a skill than their overall total career experience (e.g. Total Experience = 2 years, Skill Experience = 5 years)?

3. **No Skill Deletion / Disassociation Capability**:
   - *Observation*: There are requirements for Add and Update (FR-ADD-001, FR-ADD-006), but no requirement for deleting or detaching a skill assigned by mistake.
   - *Question for Human*: Is the absence of a "Delete Skill" or "Remove Skill" feature intended for this release?

4. **Bulk Upload Existing Skill Handling (FR-UPL-010)**:
   - *Observation*: FR-UPL-010 states "the existing skill experience shall be updated instead of creating a duplicate entry".
   - *Question for Human*: Does "updated" mean the uploaded experience **overwrites** the existing value, or does it increment/add to the existing value?

5. **Display Casing of Deduplicated Skills (FR-SKL-002, FR-SKL-006)**:
   - *Observation*: Matching is case-insensitive, and duplicate entries are prevented in the dropdown.
   - *Question for Human*: When different employees have `java`, `Java`, and `JAVA`, how should the dropdown format the skill name (e.g., preserve the first-added casing, convert to Title Case, or uppercase)?

---

## 8. Open Questions for the Human

Please review and provide a one-line answer for each of the following questions:

1. What is the expected behavior if an entered `Skill Experience` exceeds the employee's `Total Experience` (allow, warn, or reject with validation error)?
[human] allow
2. Are decimal/fractional years allowed for skill experience (e.g. `1.5`), and is there an upper bound limit?
[human] decimals allowed, no limit for upper bound
3. What are the allowed characters and length constraints for skill names (e.g., are special characters like `C++`, `C#`, `.NET` allowed)?
[human] special characters allowed, no limit for length
4. What are the exact message strings and notification types (e.g. toast banner) for:
   - Empty search results (`FR-SKL-008`)
   - Successful skill add/update (`FR-ADD-010`)
   - Successful file upload (`FR-UPL-012`)
   - Validation failure errors (`VAL-014`, `ERR-007`)?
   [human] Accept assumption
5. What are the file constraints for bulk upload (maximum file size in MB and maximum row count)?
[human] Accept assumption
6. If an uploaded file contains a mix of valid and invalid rows, should the upload fail completely (atomic), or allow partial import of valid rows?
[human] Accept assumption
7. In bulk upload, if a skill already exists for an employee, does the file value overwrite the existing experience or add to it?
[human] Accept assumption
8. Is bulk upload strictly intended for one pre-selected employee at a time, or should a multi-employee upload format be supported?
[human] Accept assumption
9. Does the system support deleting or removing an assigned skill from an employee?
[human] Accept assumption
10. Does the employee listing table support pagination and sorting when matching records exceed the view threshold?
[human] Accept assumption
11. When clicking an employee name from the listing (`FR-EMP-003`), does it navigate in the same tab, open a new tab, or open a modal view?
[human] Accept assumption
