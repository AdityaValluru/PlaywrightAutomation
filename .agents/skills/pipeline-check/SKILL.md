---
name: pipeline-check
description: Read-only consistency and impact check across the QA pipeline documents and the test-cases.xlsx workbook. Verifies traceability from requirement to scenario to test case to automated test to run result to defect, finds gaps and stale files, and lists what is affected after an SRS change. Use whenever the user asks for a traceability check, coverage check, pipeline status, impact analysis, or says the SRS or a document changed.
---

# Pipeline check (cross-stage consistency)

This skill only reads files and writes `00-pipeline-check.md`. It changes nothing else, including the workbook.

## Mode 1: Consistency check

1. Read every document in `docs/features/<feature>/`, the sheets of `test-cases.xlsx`, and the test code. Ignore Retired rows and example rows (IDs ending in `000`).
2. Write `00-pipeline-check.md` (status `Draft`) with:
   - **Traceability table**: `REQ-` to `SCN-` to `TC-` to automated test to latest result to defect.
   - **Gaps**: requirements with no scenario, scenarios with no case, cases marked Automate with no Automated Test, tests with no case ID, Automated Test values that do not exist in the code.
   - **Result consistency**: Last Result values that differ from the latest Execution Log row, and Defects rows with no `BUG-` file (or the reverse).
   - **Stale files**: documents older than the files they were generated from, or whose input changed after approval.
   - **Status check**: rows or documents not yet Approved that a later stage already used.
   - **Duplicates**: scenarios or cases that test the same thing.

## Mode 2: Impact analysis after an SRS change

1. Compare the current `srs.md` with the version the downstream documents were based on (use git history if available).
2. List the changed requirements and every scenario, test case row, and test that depends on them.
3. Propose the smallest set of edits. Do not apply any of them. Wait for the human to approve the list.

## Rules

- Report facts only. Do not fix anything while checking.
- Preserve the human's judgment: if something looks wrong but the row has Origin `Human`, report it as a question, never as an error to correct.
