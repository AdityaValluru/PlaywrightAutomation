---
name: test-case-generation
description: Stage 4 of the QA pipeline. Converts approved scenarios and the approved test plan into detailed test case rows (preconditions, data, steps, checkable expected results) in the Test Cases sheet of test-cases.xlsx. Use whenever the user asks to write test cases, expand scenarios into steps, add cases to the workbook, or continue the pipeline after the test plan is approved.
---

# Test case generation (stage 4)

## Steps

1. Check that `02-scenarios.md` and `03-test-plan.md` both have status `Approved`. If not, stop and say so.
2. Open `docs/features/<feature>/test-cases.xlsx`. If it does not exist, copy `docs/features/_template/test-cases.xlsx` there. If a lock file exists next to the workbook (`~$test-cases.xlsx` from Excel, or `.~lock.test-cases.xlsx#` from LibreOffice), the file is open: stop and ask the human to save and close it.
3. Remind the human to commit the current workbook first, so the previous version can be recovered.
4. Read the existing rows before writing. Add new rows only below the last used row and continue IDs from the highest `TC-` number. Do not touch existing rows.
5. Fill these columns for each new row:

   | Column | Content |
   |---|---|
   | Test Case ID, Requirement ID, Scenario ID | `TC-001`, `REQ-xxx`, `SCN-xxx` |
   | Title | What is verified |
   | Type, Priority | From the scenario and plan (use dropdown values only) |
   | Automate | Yes or No, from the plan |
   | Preconditions, Test Data | State before the test and the values or where they come from |
   | Steps | Numbered, one user action per line, inside the one cell |
   | Expected Result | Specific and observable |
   | Expected Result Source | The SRS section it comes from |
   | Cleanup | How to return to a clean state |
   | Origin, Status | `AI`, `Draft` |

   Leave Automated Test, Last Result, and Last Run Date empty.
6. Write cell by cell with an Excel library: Apache POI if the framework already uses it, otherwise Python `openpyxl`. Keep the existing sheets, formatting, and dropdowns. Never recreate the workbook from scratch. Read the new rows back to confirm they were saved.
7. Stop and tell the human to review the rows in Excel: add cases, tighten expected results, resolve `UNSPECIFIED` cells, and set Status to `Approved` row by row.

## Rules

- Expected results come from the SRS, never from guessing how the app behaves. If the SRS does not say, write `UNSPECIFIED: needs human decision` and do not invent a value.
- Each expected result must be something an assertion can check (text, state, visibility, count, URL). Avoid "works correctly" or "displays properly".
- Cover every scenario with at least one test case. List any scenario left without a case.
- Never edit rows with Origin `Human`, never delete rows, and never change Status.
