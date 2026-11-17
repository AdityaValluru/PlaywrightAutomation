---
name: failure-triage
description: Stage 7 of the QA pipeline. Classifies each failed or flaky test as test issue, test data, environment, SRS mismatch, or application bug, with evidence and a recommended action, in 07-triage.md. Use whenever the user asks why a test failed, to triage failures, to decide whether a failure is a bug, or to analyze a run report.
---

# Failure triage (stage 7)

## Steps

1. Read `06-run-report.md`, the latest rows in the `Execution Log` sheet of `test-cases.xlsx`, and the evidence they link (error messages, screenshots, traces, logs).
2. For each failure or flaky result, write a row in `07-triage.md` (stage `triage`, status `Draft`) with:
   - `TC-` ID and test name
   - **Category**: one of Test issue (locator, wait, assertion), Test data or state, Environment, SRS mismatch, Application bug
   - **Evidence**: what you saw, with file paths
   - **Confidence**: High, Medium, or Low
   - **Recommended action**: fix the test, fix the data, update the SRS (human decides), or raise a bug
3. Mark an item **Application bug** only when the observed behavior contradicts the approved SRS or test case. Quote the requirement. Reproduce it with an isolated re-run first.
4. Stop and tell the human to review each classification. Do not change any code in this stage.

## Why SRS mismatch exists

If the SRS was reverse-engineered from the built feature, a failing test can mean the SRS is wrong, not the app. Always check the SRS wording before calling something a bug.

## Rules

- Never propose weakening an assertion to get a pass. If an assertion looks wrong, say why and flag it for the human.
- After the human approves a classification: test fixes go back through `test-implementation` rules, SRS changes are made by the human, and bugs go to `bug-report`.
