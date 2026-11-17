---
name: test-implementation
description: Stage 5 of the QA pipeline. Automates approved test case rows from test-cases.xlsx in the Java Playwright framework by exploring the app with Playwright MCP, reusing existing page objects, and writing tests that carry the TC ID. Records the test in the workbook's Automated Test column and writes 05-implementation-notes.md. Use whenever the user asks to automate test cases, implement tests, write the Playwright code for approved cases, or add page objects for a feature.
---

# Test implementation (stage 5)

## Steps

1. Read the `Test Cases` sheet of `docs/features/<feature>/test-cases.xlsx`. Automate only rows with Status `Approved` and Automate `Yes`. Skip Retired rows and example rows. Stop and list any row whose expected result is `UNSPECIFIED`; those need a human decision first.
2. Search the existing page objects, utilities, and tests before writing anything. Reuse what exists. Add a method only when nothing fits.
3. Explore the feature with Playwright MCP, using the application URL from config. Use it to understand navigation and to see the accessibility roles, labels, and test ids available.
4. Write page objects and tests following `coding-standards.md`, `project-structure.md`, and the `playwright-page-object` skill. Do not restate those rules here, just follow them.
5. Name each test so it carries its case ID (for example `TC001_...`) and put the ID in the test annotation or description.
6. Make every assertion map to a line in the row's Expected Result. A test with no assertion on the expected result is not finished.
7. Compile the project using the compile command in `project-structure.md` and fix compile errors.
8. After it compiles, fill the **Automated Test** column for each implemented row with `ClassName#methodName`. This is the only cell you may change on an Approved row. Close-the-file and lock-file rules from `test-case-generation` apply.
9. Write `05-implementation-notes.md` (stage `implementation`, status `Draft`):
   - **Reuse notes**: what existing code was reused, and why any new method was needed.
   - **Locator notes**: any locator that is not role, label, or test id, with the reason.
   - **Open points**: anything you were unsure about.
10. Stop and tell the human to review the code. Do not run the full suite; that is the next stage.

## Rules

- Do not change application source code. If something in the app blocks automation (no test id, no label), list it under open points.
- Do not use hard waits or hard-coded data, URLs, or credentials.
- Work in small batches, such as one feature area at a time, so the review stays manageable.
