---
feature: skills
stage: scenarios
status: Approved            # Draft | Reviewed | Approved (only the human changes this)
source: docs/features/skills/01-srs-review.md
---

# 02. Test Scenarios: Employee Management – Skills Module

## 1. Summary

This document defines the functional and non-functional test scenarios for the Skills module in the Employee Management application. The scenarios cover navigation, dynamic skill population, search and filtering, employee listing display, single and multi-skill addition and updates, bulk upload via CSV and Excel with preview and deduplication, data validation, and graceful error handling.

All scenarios are derived directly from the approved SRS review (`01-srs-review.md`) and the underlying specifications (`SRS.md`), incorporating the approved assumptions and human clarifications.

---

## 2. Test Scenarios

| ID | Requirement Link | Title | Type | Priority | Reason |
|---|---|---|---|---|---|
| `SCN-001` | `REQ-NAV-001` | Verify Skills menu item appears under Employee Details | navigation | High | Verifies the module entry point is rendered in the main navigation menu. |
| `SCN-002` | `REQ-NAV-002` | Verify Skills menu item is positioned as the last submenu item | navigation | Low | Ensures menu ordering complies with UI layout specifications. |
| `SCN-003` | `REQ-NAV-003` | Verify selecting Skills menu item navigates to the Skills page | navigation | High | Confirms successful navigation and route activation for the feature landing page. |
| `SCN-004` | `REQ-NAV-004`, `REQ-BR-010` | Verify existing Employee Details submenus remain functional | navigation | Medium | Ensures adding the Skills module causes no regression in existing sibling navigation. |
| `SCN-005` | `REQ-SKL-010` | Verify initial page state prompts user to select a skill | UI state | Medium | Confirms clear user guidance is displayed prior to applying any skill filter. |
| `SCN-006` | `REQ-SKL-001`, `REQ-BR-007` | Verify skill dropdown populates dynamically with existing employee skills | positive | High | Ensures dropdown values reflect real data from employee records without hardcoded values. |
| `SCN-007` | `REQ-SKL-002`, `REQ-BR-004` | Verify skill dropdown contains only unique skill names | positive | High | Validates deduplication so identical skills across employees appear only once. |
| `SCN-008` | `REQ-SKL-003` | Verify searching by skill name filters the dropdown options | positive | High | Ensures in-control search allows users to find specific skills quickly. |
| `SCN-009` | `REQ-SKL-004` | Verify user can select a skill from the dropdown | positive | High | Validates standard user interaction with the skill selection dropdown. |
| `SCN-010` | `REQ-SKL-005`, `REQ-SKL-007` | Verify selecting a skill displays all matching employees | positive | High | Validates core query functionality when multiple employees share a skill. |
| `SCN-011` | `REQ-SKL-006`, `REQ-BR-005`, `REQ-VAL-004` | Verify skill search matches case-insensitively | positive | Medium | Confirms case variations (e.g., `java`, `Java`, `JAVA`) resolve to the same skill. |
| `SCN-012` | `REQ-SKL-008` | Verify empty state message displayed when no employees match selected skill | UI state | Medium | Ensures proper feedback message appears when query yields zero employee records. |
| `SCN-013` | `REQ-SKL-009` | Verify changing selected skill updates the employee listing | positive | High | Confirms table contents refresh reactively upon choosing a different skill. |
| `SCN-014` | `REQ-EMP-001`, `REQ-EMP-002` | Verify matching employees display in tabular format with all required columns | positive | High | Verifies presence and ordering of all 7 mandatory columns in the employee table. |
| `SCN-015` | `REQ-EMP-006` | Verify loading indicator is displayed during employee data retrieval | UI state | Low | Confirms visual loading feedback during asynchronous data fetching operations. |
| `SCN-016` | `REQ-EMP-003` | Verify employee name is rendered as a clickable link | UI state | High | Ensures visual and interactive navigation affordance is present on employee names. |
| `SCN-017` | `REQ-EMP-004`, `REQ-EMP-005` | Verify clicking employee name navigates to the corresponding Employee Details page | navigation | High | Validates drill-down navigation and ensures details view matches the selected Employee ID. |
| `SCN-018` | `REQ-ADD-001` | Verify Add Skill interface can be opened | UI state | High | Confirms user can trigger and access the skill assignment form or modal. |
| `SCN-019` | `REQ-ADD-002`, `REQ-VAL-001`, `REQ-VAL-002` | Verify user can search and select an existing employee | positive | High | Confirms target employee selection functions properly before assigning skills. |
| `SCN-020` | `REQ-VAL-001` | Verify validation error when saving skill without selecting an employee | validation | Medium | Prevents orphan skill records and enforces employee selection prerequisite. |
| `SCN-021` | `REQ-ADD-004`, `REQ-ADD-005`, `REQ-ADD-008`, `REQ-ADD-010`, `REQ-BR-001`, `REQ-BR-002` | Verify assigning a new skill with valid integer experience to an employee | positive | High | Validates standard single-skill assignment, backend persistence, and feedback. |
| `SCN-022` | `REQ-ADD-004`, `REQ-VAL-010` | Verify assigning a skill with valid decimal experience value | boundary | Medium | Confirms fractional years (e.g. 1.5) are accepted per requirement clarifications. |
| `SCN-023` | `REQ-ADD-004`, `REQ-BR-003` | Verify assigning a skill with experience exceeding employee total experience | positive | Medium | Validates independent experience attributes per approved business rules. |
| `SCN-024` | `REQ-ADD-003`, `REQ-BR-001` | Verify assigning multiple skills to an employee in a single session | positive | High | Validates multi-skill assignment capability for an individual employee record. |
| `SCN-025` | `REQ-ADD-006`, `REQ-BR-004`, `REQ-VAL-005` | Verify updating experience for an existing employee skill overwrites previous experience | positive | High | Ensures existing skill experience updates in place without creating duplicate records. |
| `SCN-026` | `REQ-ADD-007`, `REQ-BR-006`, `REQ-BR-009` | Verify skill update on one employee does not alter other employees | positive | High | Verifies isolation of skill updates and prevents cross-employee data mutation. |
| `SCN-027` | `REQ-ADD-009` | Verify employee listing refreshes immediately after saving skills | positive | High | Ensures the employee table immediately reflects newly added or updated skills. |
| `SCN-028` | `REQ-ADD-009`, `REQ-SKL-001` | Verify skill dropdown refreshes with new skill immediately after saving | positive | High | Confirms newly assigned skills are dynamically appended to the dropdown filter. |
| `SCN-029` | `REQ-ADD-010` | Verify success notification displayed upon saving skills | UI state | Medium | Ensures affirmative user feedback is presented upon successful persistence. |
| `SCN-030` | `REQ-VAL-003` | Verify validation error when skill name is empty | validation | High | Enforces mandatory skill name constraint and prevents blank entries. |
| `SCN-031` | `REQ-VAL-003`, `REQ-ADD-005` | Verify assigning skill name containing special characters | positive | Medium | Confirms skills with characters like `C++`, `.NET`, `Node.js` are accepted. |
| `SCN-032` | `REQ-VAL-007`, `REQ-VAL-009` | Verify validation error when skill experience is non-numeric | negative | High | Rejects alphabetic or symbol inputs in experience field. |
| `SCN-033` | `REQ-VAL-008` | Verify validation error when skill experience is negative | negative | High | Rejects negative numeric values for skill experience. |
| `SCN-034` | `REQ-VAL-009` | Verify validation error when skill experience is empty | validation | Medium | Enforces mandatory experience field requirement. |
| `SCN-035` | `REQ-UPL-001` | Verify Bulk Upload interface can be opened | UI state | High | Confirms user can access the file upload interface or modal. |
| `SCN-036` | `REQ-UPL-003`, `REQ-BR-008`, `REQ-VAL-001` | Verify employee selection is required before uploading a file | validation | High | Enforces mandatory employee pre-selection prior to initiating bulk upload. |
| `SCN-037` | `REQ-UPL-002`, `REQ-UPL-004`, `REQ-UPL-005`, `REQ-UPL-006` | Verify uploading a valid CSV file displays extracted data in preview | positive | High | Validates CSV parsing happy path with Skill and Experience column extraction. |
| `SCN-038` | `REQ-UPL-002`, `REQ-UPL-004`, `REQ-UPL-005`, `REQ-UPL-006` | Verify uploading a valid Excel file displays extracted data in preview | positive | High | Validates Excel (.xlsx) parsing happy path with column extraction. |
| `SCN-039` | `REQ-UPL-007` | Verify preview displays Skill and Experience column values accurately | UI state | High | Validates preview data accuracy before user commits records to storage. |
| `SCN-040` | `REQ-UPL-008` | Verify canceling upload from preview dismisses interface without saving | positive | Medium | Confirms user can abort file upload without any database modification. |
| `SCN-041` | `REQ-UPL-008`, `REQ-UPL-009`, `REQ-BR-008` | Verify confirming upload saves skill records against the selected employee | positive | High | Validates persistence of previewed records to selected employee upon confirmation. |
| `SCN-042` | `REQ-UPL-010`, `REQ-BR-004` | Verify bulk upload overwrites existing skill experience without duplicate records | positive | High | Ensures file upload updates existing skills rather than appending duplicates. |
| `SCN-043` | `REQ-UPL-012` | Verify success notification displayed following successful bulk upload | UI state | Medium | Confirms clear user feedback after successful file ingestion and save. |
| `SCN-044` | `REQ-UPL-013` | Verify employee listing refreshes immediately after successful bulk upload | positive | High | Ensures table display updates with newly uploaded skills without manual reload. |
| `SCN-045` | `REQ-UPL-013`, `REQ-SKL-001` | Verify skill dropdown refreshes with newly uploaded skills | positive | High | Confirms dropdown filter is updated dynamically with skills added via file. |
| `SCN-046` | `REQ-VAL-011`, `REQ-ERR-005` | Verify error message when uploading unsupported file format | negative | High | Rejects unsupported extensions (e.g. .txt, .pdf, .json) with descriptive error. |
| `SCN-047` | `REQ-VAL-012`, `REQ-ERR-006` | Verify validation error when uploading an empty file | negative | Medium | Validates graceful rejection of 0-byte or empty files. |
| `SCN-048` | `REQ-VAL-013`, `REQ-VAL-014`, `REQ-ERR-006` | Verify validation error when uploaded file is missing required columns | validation | High | Identifies and rejects files lacking `Skill` or `Experience` column headers. |
| `SCN-049` | `REQ-VAL-006`, `REQ-VAL-008`, `REQ-VAL-013`, `REQ-VAL-014`, `REQ-UPL-011` | Verify validation error when uploaded file contains malformed data rows | validation | High | Detects invalid rows (empty skill name or negative experience) during file validation. |
| `SCN-050` | `REQ-ERR-001`, `REQ-ERR-007` | Verify error notification displayed when skill dropdown retrieval fails | negative | Medium | Ensures graceful non-technical error notification if skills query encounters an error. |
| `SCN-051` | `REQ-ERR-002`, `REQ-ERR-007` | Verify error notification displayed when employee listing retrieval fails | negative | Medium | Ensures graceful error notification if employee listing request fails. |
| `SCN-052` | `REQ-ERR-003`, `REQ-ERR-004`, `REQ-ERR-007`, `REQ-ERR-008` | Verify error notification displayed when skill save operation fails | negative | Medium | Validates persistence failure handling without corrupting UI state or falsely reporting success. |

---

## 3. Requirement Coverage Matrix

| Requirement ID | Requirement Description | Covered By Scenarios | Status |
|---|---|---|---|
| `REQ-NAV-001` | Provide "Skills" menu item under Employee Details | `SCN-001` | Covered |
| `REQ-NAV-002` | Skills menu item placed as the last submenu item under Employee Details | `SCN-002` | Covered |
| `REQ-NAV-003` | Selecting Skills menu item navigates user to the Skills page | `SCN-003` | Covered |
| `REQ-NAV-004` | Existing Employee Details submenu items remain unaffected | `SCN-004` | Covered |
| `REQ-SKL-001` | Dynamically populate skill dropdown from existing employee records | `SCN-006`, `SCN-028`, `SCN-045` | Covered |
| `REQ-SKL-002` | Display unique skill names without duplicate entries | `SCN-007` | Covered |
| `REQ-SKL-003` | Support text-based search/filter within skill selection control | `SCN-008` | Covered |
| `REQ-SKL-004` | User can select a skill from dropdown | `SCN-009` | Covered |
| `REQ-SKL-005` | Selecting a skill retrieves and displays all matching employees | `SCN-010` | Covered |
| `REQ-SKL-006` | Skill matching is case-insensitive (e.g. Java, JAVA, java) | `SCN-011` | Covered |
| `REQ-SKL-007` | Display all matching employees when multiple employees possess skill | `SCN-010` | Covered |
| `REQ-SKL-008` | Display message when no employees match selected skill | `SCN-012` | Covered |
| `REQ-SKL-009` | Changing selected skill refreshes employee listing with new results | `SCN-013` | Covered |
| `REQ-SKL-010` | Display prompt to select or search for skill on initial page load | `SCN-005` | Covered |
| `REQ-EMP-001` | Display matching employee records in tabular format | `SCN-014` | Covered |
| `REQ-EMP-002` | Listing includes 7 required columns (ID, Name, Email, Location, Total Exp, Skill, Skill Exp) | `SCN-014` | Covered |
| `REQ-EMP-003` | Employee name is clickable | `SCN-016` | Covered |
| `REQ-EMP-004` | Clicking employee name navigates to employee details view | `SCN-017` | Covered |
| `REQ-EMP-005` | Display details for correct Employee ID matching clicked record | `SCN-017` | Covered |
| `REQ-EMP-006` | Show loading indicator during asynchronous data retrieval | `SCN-015` | Covered |
| `REQ-ADD-001` | Provide option to add skills to existing employee records | `SCN-018` | Covered |
| `REQ-ADD-002` | User can search for and select existing employee before assigning skills | `SCN-019` | Covered |
| `REQ-ADD-003` | Allow assigning multiple skills to an employee | `SCN-024` | Covered |
| `REQ-ADD-004` | Each assigned skill must have experience value expressed in years | `SCN-021`, `SCN-022`, `SCN-023` | Covered |
| `REQ-ADD-005` | Allow associating a new skill with an existing employee | `SCN-021`, `SCN-031` | Covered |
| `REQ-ADD-006` | Update existing skill experience if skill already exists for employee | `SCN-025` | Covered |
| `REQ-ADD-007` | Skill updates are employee-specific; do not alter other employees | `SCN-026` | Covered |
| `REQ-ADD-008` | Persist added/updated skills via backend storage | `SCN-021` | Covered |
| `REQ-ADD-009` | Refresh employee listing and available skills list after save | `SCN-027`, `SCN-028` | Covered |
| `REQ-ADD-010` | Display success or error notification following add/update | `SCN-021`, `SCN-029` | Covered |
| `REQ-UPL-001` | Provide option to upload multiple skills via file | `SCN-035` | Covered |
| `REQ-UPL-002` | Support CSV (.csv) and Excel (.xlsx) file formats | `SCN-037`, `SCN-038` | Covered |
| `REQ-UPL-003` | Require selecting an existing employee before file upload | `SCN-036` | Covered |
| `REQ-UPL-004` | Allow file selection from local system | `SCN-037`, `SCN-038` | Covered |
| `REQ-UPL-005` | Parse uploaded file and extract skill names and experience values | `SCN-037`, `SCN-038` | Covered |
| `REQ-UPL-006` | Support structured tabular format with Skill and Experience columns | `SCN-037`, `SCN-038` | Covered |
| `REQ-UPL-007` | Display preview of extracted data before saving | `SCN-039` | Covered |
| `REQ-UPL-008` | User can confirm or cancel upload after reviewing preview | `SCN-040`, `SCN-041` | Covered |
| `REQ-UPL-009` | Save valid skill records against selected employee upon confirmation | `SCN-041` | Covered |
| `REQ-UPL-010` | Update existing skill experience instead of creating duplicates on upload | `SCN-042` | Covered |
| `REQ-UPL-011` | Validate uploaded file and contents before saving | `SCN-049` | Covered |
| `REQ-UPL-012` | Display success or failure notification after upload operation | `SCN-043` | Covered |
| `REQ-UPL-013` | Refresh employee skill information and available skills list post-upload | `SCN-044`, `SCN-045` | Covered |
| `REQ-BR-001` | Multi-skill association per employee | `SCN-021`, `SCN-024` | Covered |
| `REQ-BR-002` | Individual skill experience per employee-skill association | `SCN-021` | Covered |
| `REQ-BR-003` | Experience independence (Skill Experience maintained separately from Total Experience) | `SCN-023` | Covered |
| `REQ-BR-004` | Skill deduplication for same employee | `SCN-007`, `SCN-025`, `SCN-042` | Covered |
| `REQ-BR-005` | Case-insensitive skill matching | `SCN-011` | Covered |
| `REQ-BR-006` | Skill experience updates apply only to target employee | `SCN-026` | Covered |
| `REQ-BR-007` | Dynamic retrieval of skills from employee records | `SCN-006` | Covered |
| `REQ-BR-008` | Uploaded skills associated with pre-selected employee | `SCN-036`, `SCN-041` | Covered |
| `REQ-BR-009` | Employee records not duplicated during operations | `SCN-026` | Covered |
| `REQ-BR-010` | Existing employee functionality unaffected | `SCN-004` | Covered |
| `REQ-VAL-001` | Employee selection required before assigning or uploading skills | `SCN-019`, `SCN-020`, `SCN-036` | Covered |
| `REQ-VAL-002` | Selected employee corresponds to existing record | `SCN-019` | Covered |
| `REQ-VAL-003` | Skill names shall not be empty | `SCN-030`, `SCN-031` | Covered |
| `REQ-VAL-004` | Skill names treated consistently regardless of casing | `SCN-011` | Covered |
| `REQ-VAL-005` | Prevent duplicate skill entries for same employee | `SCN-025` | Covered |
| `REQ-VAL-006` | Validate skill names extracted from uploaded files | `SCN-049` | Covered |
| `REQ-VAL-007` | Skill experience must be valid numeric value | `SCN-032` | Covered |
| `REQ-VAL-008` | Negative experience values shall not be accepted | `SCN-033`, `SCN-049` | Covered |
| `REQ-VAL-009` | Missing or invalid experience values handled appropriately | `SCN-032`, `SCN-034` | Covered |
| `REQ-VAL-010` | Experience values interpreted in years (decimals allowed) | `SCN-022` | Covered |
| `REQ-VAL-011` | Only supported file formats accepted | `SCN-046` | Covered |
| `REQ-VAL-012` | Empty or invalid files handled appropriately | `SCN-047` | Covered |
| `REQ-VAL-013` | Files with missing columns or malformed data validated | `SCN-048`, `SCN-049` | Covered |
| `REQ-VAL-014` | Meaningful validation messages for invalid upload data | `SCN-048`, `SCN-049` | Covered |
| `REQ-ERR-001` | Handle failures while retrieving available skills | `SCN-050` | Covered |
| `REQ-ERR-002` | Handle failures while retrieving employees associated with a skill | `SCN-051` | Covered |
| `REQ-ERR-003` | Handle failures while saving newly assigned skills | `SCN-052` | Covered |
| `REQ-ERR-004` | Handle failures while updating existing skill experience | `SCN-052` | Covered |
| `REQ-ERR-005` | Handle invalid or unsupported upload files | `SCN-046` | Covered |
| `REQ-ERR-006` | Handle file parsing and data validation errors | `SCN-047`, `SCN-048`, `SCN-049` | Covered |
| `REQ-ERR-007` | Meaningful error messages without exposing internal technical details | `SCN-050`, `SCN-051`, `SCN-052` | Covered |
| `REQ-ERR-008` | Unsuccessful persistence maintains data integrity | `SCN-052` | Covered |

*Flagged Requirements without Scenarios*: None. All 63 requirements (functional, business rules, validations, and error handling) have direct scenario coverage.

---

## 4. Assumptions [ai]

- `[ai]` **Skill Experience vs Total Experience**: As approved by human clarification, entered `Skill Experience` is allowed to exceed `Total Experience` without triggering validation blocking (`SCN-023`).
- `[ai]` **Decimal Experience Values**: As approved by human clarification, decimal numbers (e.g. `1.5`, `2.5`) are accepted for skill experience without an artificial upper bound limit (`SCN-022`).
- `[ai]` **Skill Name Characters**: As approved by human clarification, skill names accept technical punctuation and special characters (e.g. `C++`, `C#`, `.NET`, `Node.js`) without an artificial length restriction (`SCN-031`).
- `[ai]` **Single Employee Bulk Upload**: As approved by human clarification, bulk upload strictly applies to the single employee selected in the UI prior to upload (`SCN-036`, `SCN-041`).
- `[ai]` **Bulk Upload Skill Overwrite**: When an uploaded file contains a skill already possessed by the selected employee, the uploaded experience value overwrites the existing record upon confirmation (`SCN-042`).
- `[ai]` **Bulk Upload Atomicity**: If validation fails for malformed rows in an uploaded file, the user is notified during preview and invalid records cannot be committed (`SCN-049`).
- `[ai]` **Employee Details Navigation Target**: Clicking an employee name in the listing navigates to the Employee Details page within the same browser tab (`SCN-017`).
- `[ai]` **Feedback Notifications**: Feedback for success, warning, and error events is displayed via user-friendly UI toast alerts or inline notification banners (`SCN-029`, `SCN-043`, `SCN-050`, `SCN-051`, `SCN-052`).
