---
name: checkpoint-commit
description: Makes a local Git checkpoint commit of pipeline work (documents, the test case workbook, page objects, tests) after a stage, using an explicit file list and a message the human approves. Local commits only, it never pushes. Use whenever the user asks to commit the stage output, save a checkpoint, commit the AI draft, or commit the reviewed edits, and offer it at the end of every pipeline stage.
---

# Checkpoint commit (local only)

Checkpoint commits are the backup and the audit trail between stages. They use the local `git` command in the terminal, not the GitHub MCP server. Commits stay on the local branch.

## Steps

1. Run `git branch --show-current`. If the branch is `main`, `master`, or detached, stop and ask the human to switch to the feature branch.
2. Run `git status --short` and show the result. Group the changed files into: pipeline documents (`docs/features/<feature>/`), the workbook, page objects and tests, and anything else.
3. Stop and ask if anything else appears: application source code, CI configuration, `pom.xml` or other dependency files, or files outside this feature. Do not commit those.
4. Scan the changes for secrets (tokens, passwords, keys, real personal data) and report anything found. Do not commit until it is resolved.
5. Decide the label with the human if it is not clear:
   - `ai-draft`: the AI's output exactly as written, before the human edits it
   - `reviewed`: the human's edits and approvals after review
6. If the workbook changed, ask the human to save and close it first, so unsaved edits are not missed.
7. Show the proposed commit:
   - The explicit file list for `git add`. Never use `git add .`, `git add -A`, or `git commit -a`.
   - A message in the form `<type>(<feature>): <summary> [<stage>, <label>]`. Use `docs` for documents and the workbook, `test` for page objects and tests, and `chore` for anything else.
   - For workbook changes, a body listing the test case IDs added or changed, since Excel diffs are not readable.
8. **Wait for the human's approval.** Then run `git add <files>` and `git commit` (use `-F` with a temporary message file for multi-line messages).
9. Show `git log -1 --stat` and `git status --short`, and report what was committed and what is still uncommitted.

## Forbidden

- Any push, in any form, and any GitHub tool that writes to the remote (`push_files`, `create_or_update_file`, `delete_file`).
- `--force`, `--no-verify`, `--amend` (unless the human asks), `reset`, `rebase`, `clean`, `restore` or `checkout --` that discards changes, `stash drop`, deleting branches, and tags.
- Committing on main or master.

## Rules

- One commit per request. Do not combine an `ai-draft` commit and a `reviewed` commit.
- Never commit without the human's approval of the file list and message.
