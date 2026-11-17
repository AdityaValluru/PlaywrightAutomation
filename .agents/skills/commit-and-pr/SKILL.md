---
name: commit-and-pr
description: Final stage of the QA pipeline. Prepares local commits for approved test work and writes 08-pr-description.md with coverage, results, known failures, and a reviewer checklist, including a first-pass AI self-review. Use whenever the user asks to commit the tests, prepare a PR, write a PR description, or finish the pipeline. Never pushes or creates a PR without explicit instruction.
---

# Commit and PR preparation (final stage)

## Steps

1. Confirm the current branch is a feature branch (for example `qa/<feature>-automation`). If it is main or master, stop and ask.
2. Show `git status` and a summary of the diff. Check that only files in scope changed: test code, page objects, and `docs/features/<feature>/`.
3. Check the diff for secrets, hard-coded URLs or data, hard waits, removed or weakened assertions, and changes to CI config or dependencies. Report anything found.
4. Propose commits in logical groups (for example docs, page objects, tests). Use messages like `test(<feature>): add TC-001..TC-005 login validation tests`. **Wait for the human's approval before each commit.**
5. Write `08-pr-description.md` (stage `pr`, status `Draft`):
   - **Summary** and scope
   - **Coverage**: requirements and test cases covered, and any left uncovered, taken from `test-cases.xlsx`. Excel files do not show readable diffs, so list the test case IDs that were added or changed and what changed
   - **How to run** the new tests
   - **Results**: from `06-run-report.md`
   - **Known failures**: linked to `BUG-` files
   - **AI self-review (first pass)**: findings from the diff check, clearly labeled as a first pass
   - **Reviewer checklist**: right (checks the right thing), stable, fits the framework, real (compiles, APIs exist), clean (small diff, no secrets)
6. Stop and tell the human to review the full diff themselves.

## Rules

- Never push. Never create a PR unless the human explicitly asks in the current session. If asked to raise a PR through the GitHub tool, confirm branch, base branch, and title first, and create it as a draft.
- The AI self-review is a first pass only. It does not replace the human's review.
