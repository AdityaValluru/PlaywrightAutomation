---
name: jira-defects
description: Raises approved pipeline defects in Jira through the Atlassian CLI (acli) and tracks them by adding retest comments after later runs. Use whenever the user asks to raise a defect in Jira, log a bug in Jira, add the Jira key to a bug, or add a retest result to a Jira bug. Only for defects. Test cases stay in test-cases.xlsx. Every write needs the human's approval of the exact commands first.
---

# Jira defects (Atlassian CLI)

Jira holds defects only. Test cases and run history stay in `test-cases.xlsx`. The CLI calls issues "work items".

## Before any write

1. Confirm the CLI is logged in with a harmless read, for example `acli jira workitem search --jql "project = <KEY>" --limit 1 --json`. If it fails, stop and tell the human. Never print, log, or store API tokens.
2. Use the Jira project key from `.agents/rules/pipeline.md`. Never guess it. Work only in that project.
3. Check unfamiliar commands or flags with `--help` before using them. Do not invent flags.
4. Every write follows this loop: show a **preview** with the **exact commands** and the text they will send, **wait for the human's approval**, run them, then report the work item keys.
5. Allowed: `workitem create`, `edit`, `comment create`, `transition`, and read commands (`view`, `search`, `comment list`). **Forbidden:** any `delete` or `archive` command, the `--yes` flag, and any write that selects work items with `--jql` or `--filter`. Name each work item with `--key`.
6. Raise at most 5 defects per approval. If an error appears, stop and report where it stopped.
7. Never put credentials, tokens, or personal data into issue text. Comment bodies are plain text, with no markdown.

## Raise a defect

Input: a `bugs/BUG-NNN.md` that the human has marked `Approved`, with a matching row in the **Defects** sheet.

1. Search for likely duplicates: `acli jira workitem search --jql 'project = <KEY> AND issuetype = Bug AND text ~ "<keywords>"' --json --limit 10`. Show matches in the preview. If one looks like the same defect, stop and ask whether to comment on it instead.
2. Create the work item with type `Bug`. If Jira rejects the type, stop and ask. Use a summary like `[BUG-001] <title>`, labels for the pipeline, the feature name (kebab-case), and the test case ID, and a description built from the report file: environment, preconditions, steps to reproduce, expected result (with `REQ-` and `TC-` references), actual result, related test (`Class#method`), and evidence file paths. For multi-line text, run `acli jira workitem create --generate-json`, fill the template, and use `--from-json`.
3. Set priority only if the human confirmed severity in the report file, and check the valid values with `--help` first. Otherwise leave it for the human.
4. Do not upload attachments unless the human asks and `acli jira workitem attachment --help` confirms the usage. By default, list the evidence files and tell the human to attach them after checking for sensitive data.
5. After creation, write the Jira key into the **Jira Key** column of the Defects sheet and as a `Jira: PROJ-123` line in `BUG-NNN.md`. Follow the workbook rules (close-the-file, lock file, commit first).

## Track a defect: retest comment

When a later run in the Execution Log shows the linked test case passing, or failing again, after the developer reports a fix:

1. Draft a plain-text comment: date, branch, short commit hash, test (`Class#method`), result (`RETEST PASS` or `RETEST FAIL`), the run counts, and evidence paths.
2. Preview and, once approved, add it with `acli jira workitem comment create --key <KEY> --body-file <file>`.
3. Change the Jira status only when the human asks, using `acli jira workitem transition --key <KEY> --status "<name>"` with a status name that exists in the project. Never invent statuses.
4. Update the Status column in the Defects sheet only when the human tells you the new status.

## Rules

- Report what was observed. Do not guess at root causes.
- If Jira and the files disagree, report it and let the human decide. Do not overwrite either side silently.
