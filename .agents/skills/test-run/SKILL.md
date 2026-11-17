---
name: test-run
description: Stage 6 of the QA pipeline. Runs newly automated tests singly, repeatedly, and as a group, performs an assertion check to prove each test can fail, records every run in the Execution Log sheet of test-cases.xlsx, updates Last Result in the Test Cases sheet, and writes 06-run-report.md with evidence. Use whenever the user asks to run the new tests, check test stability, verify tests are not flaky, record test results, or continue the pipeline after implementation.
---

# Test run (stage 6)

## Steps

1. Read the rows in `test-cases.xlsx` that have a value in **Automated Test**. Use the run commands in `project-structure.md`. If none are defined, stop and ask.
2. Run each new test **on its own**. Record pass or fail.
3. Run each new test **5 times in a row**. Record how many passed (for example 5/5).
4. Run the new tests **as a group**, then in parallel if the framework supports it. Record the results.
5. **Assertion check**: for each passing test, temporarily change one expected value in the test code, run the test, and confirm it fails. Then restore the original value and confirm with `git diff` that nothing was left changed. Only edit test files for this check, never application code.
6. Decide each result. **Pass** only when the single run passed, all repeated runs passed, the group run passed, and the assertion check failed as expected. Otherwise use **Fail** (a run failed), **Flaky** (mixed results), or **Weak** (passes even with a wrong expected value).
7. Record in the workbook (close-the-file and lock-file rules apply, and remind the human to commit first):
   - **Execution Log**: append one row per test case with Run ID, date, test case ID, `Class#method`, branch, short commit hash, single, repeated, group, assertion check, result, and evidence paths. Never edit old rows.
   - **Test Cases**: update only Last Result and Last Run Date for each row.
8. Write `06-run-report.md` (stage `run-report`, status `Draft`): one summary table, then each failure with the error message and paths to the screenshot or trace.
9. Stop and tell the human the result. Do not fix failures here. Failures go to `failure-triage`.

## Rules

- Never edit a test to make it pass during this stage.
- Report what happened, including flaky results, even if they pass most of the time. "Passed 4/5" is a finding, not a pass.
- Never delete or rewrite Execution Log rows.
