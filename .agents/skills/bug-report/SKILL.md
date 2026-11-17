---
name: bug-report
description: Writes a clear, factual bug report file from a triage item classified as an application bug, with steps to reproduce, expected versus actual result, and evidence, and adds an index row to the Defects sheet of test-cases.xlsx. Use whenever the user asks to raise a bug, write a defect report, or document a failed test as a bug.
---

# Bug report

## Steps

1. Only proceed for triage items in `07-triage.md` marked **Application bug** and approved by the human.
2. Create `docs/features/<feature>/bugs/BUG-NNN.md` (next unused number) with:
   - **Title**: what is wrong, in one line
   - **Severity** and **priority**: a suggestion marked `[ai]`, for the human to confirm
   - **Environment**: application URL from config, browser, date
   - **Preconditions**
   - **Steps to reproduce**: numbered, one action each
   - **Expected result**: with the `REQ-` and `TC-` references
   - **Actual result**
   - **Evidence**: screenshot and trace paths
   - **Related test**: class and method
   - **Notes**: workaround if known
3. Add one row to the **Defects** sheet of `test-cases.xlsx`: Defect ID, title, linked test case, requirement ID, severity and priority (only if the human confirmed them, otherwise leave blank), Status `New`, the report file path, and the raised date. Close-the-file and lock-file rules apply.
4. Fill the **Defect ID** column on the matching failed row in the Execution Log. This is the only edit allowed to an old log row.
5. Stop and tell the human to review.

## Rules

- Stick to what was observed. Do not guess at root causes in the application code.
- Do not fix the application code. Reporting is the job here.
- Do not file the bug in Jira from this skill. After the human approves the report, filing is a separate step handled by the `jira-defects` skill.
