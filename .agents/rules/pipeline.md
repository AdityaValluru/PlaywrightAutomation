---
trigger: always_on
---

# QA pipeline rules (SRS to PR)

These rules apply to all work on the feature pipeline under `docs/features/`.
Code conventions (page objects, locators, waits, logging) come from `coding-standards.md`, `project-structure.md`, and the `playwright-page-object` skill. Do not repeat or override them here.

## Where things live

Each feature has a folder `docs/features/<feature>/` with these files:

| File | Stage |
|---|---|
| `srs.md` | Input, written or approved by the human |
| `01-srs-review.md` | SRS review |
| `02-scenarios.md` | Scenarios |
| `03-test-plan.md` | Test plan |
| `test-cases.xlsx` | Test cases, run history, and defect index (Excel workbook) |
| `05-implementation-notes.md` | Automation traceability |
| `06-run-report.md` | Test runs |
| `07-triage.md` | Failure triage |
| `bugs/BUG-NNN.md` | Bug reports |
| `08-pr-description.md` | Commit and PR preparation |
| `00-pipeline-check.md` | Consistency and impact checks |

## Status and gates

- Every pipeline document starts with a front-matter block: `feature`, `stage`, `status`, `source`.
- Status is `Draft`, `Reviewed`, or `Approved`. Only the human sets `Reviewed` or `Approved`. AI-written documents are always `Draft`.
- A stage reads only input documents whose status is `Approved`. If an input is not Approved, stop and name the file that needs approval.
- After writing a document, stop and tell the human what to review. Never start the next stage on your own.
- In `test-cases.xlsx`, the **Status** column on each row plays the same role. A test case row is Approved when its Status says so.

## Protecting human edits

- Items written by AI carry the tag `[ai]`. Items added or edited by the human carry `[human]`.
- Never change, reword, reorder, or delete anything tagged `[human]`.
- In the workbook, never change or delete rows whose Origin is `Human`.
- When re-running a stage after a change, list proposed changes first, then patch the existing file in place. Never regenerate a whole file from scratch.
- IDs (`REQ-`, `SCN-`, `TC-`, `BUG-`) are never renumbered or reused. Retired items are marked `Retired`, not deleted.

## Scope limits

- Only create or change files under `docs/features/`, test code, and page objects.
- Never change application source code, CI configuration, or dependencies unless the human explicitly asks.
- Never weaken, remove, or bend an assertion or test to make it pass. If a change to an assertion is needed, flag it clearly and wait for approval.
- Never put credentials, tokens, or personal data in documents, code, logs, or prompts.

## Git

- Work only on a feature branch (for example `qa/<feature>-automation`). If the current branch is main or master, stop and ask.
- Commit only through the `checkpoint-commit` skill, after the human approves the file list and the message. Never push. Never create a PR unless the human explicitly asks in the current session.
- Checkpoint commits use the local `git` command in the terminal. GitHub MCP tools that write to the remote (`push_files`, `create_or_update_file`, `delete_file`, `merge_pull_request`) are never used. Keep them disabled in the MCP settings.
- After you finish any pipeline stage and tell the human what to review, offer a checkpoint commit labelled `ai-draft`. After the human has reviewed and edited, they ask for one labelled `reviewed`.
- Never run `git push`, `git reset`, `git rebase`, `git clean`, or any force option.

## Running tests

- Use the run commands in `project-structure.md`. If no run commands are defined, ask the human before running anything.

## When the SRS changes

1. The human updates `srs.md` first.
2. Run the `pipeline-check` skill in impact-analysis mode to list affected scenarios, test cases, and tests.
3. Patch only the affected items, after the human approves the list.

## Test case workbook

- Path: `docs/features/<feature>/test-cases.xlsx`, copied from `docs/features/_template/test-cases.xlsx` when missing.
- Sheets: Test Cases (record for test case content), Execution Log (append-only run history), Defects (index of defects with a Jira Key column, details in `bugs/BUG-NNN.md`), Summary (formulas, never type there), Lists (dropdown values).
- Write only with an Excel library (Apache POI if the framework uses it, otherwise Python `openpyxl`), cell by cell. Never recreate the workbook, delete sheets, delete rows, or break the dropdowns. Retire rows by asking the human to set Status to Retired.
- AI adds rows with Origin `AI` and Status `Draft`. Only the human sets Status to Reviewed, Approved, or Retired.
- AI may fill only these columns on existing rows: Automated Test, Last Result, Last Run Date. Execution Log rows are never edited, except the Defect ID column, which may be filled once.
- If a lock file exists next to the workbook (`~$test-cases.xlsx` from Excel, or `.~lock.test-cases.xlsx#` from LibreOffice), the file is open. Stop and ask the human to save and close it.
- Before the first write in a stage, remind the human to commit the workbook. Excel diffs are not readable, so the commit is the backup.
- Rows with IDs ending in `000` are format examples. Ignore them.

## Jira (defects only)

- Jira holds defects only, through the `jira-defects` skill and the Atlassian CLI (`acli`). Test cases and run history stay in `test-cases.xlsx`.
- Show the exact commands for every write and wait for approval before running them.
- Allowed writes: create, edit, comment, transition. Never run `delete` or `archive` commands, never use `--yes`, and never write to work items selected by `--jql` or `--filter`. Name each work item with `--key`.
- Check unfamiliar flags with `--help` first. Never print, log, or commit API tokens.
- Record each Jira key in the Defects sheet and the `BUG-NNN.md` file so re-runs do not create duplicates.
- The old test case sync skill is parked in `parked/jira-sync/` and is not used.
- Jira project key: <set this to your project key>
