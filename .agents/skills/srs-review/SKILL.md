---
name: srs-review
description: Stage 1 of the QA pipeline. Reviews a feature's SRS from a tester's point of view and writes 01-srs-review.md with requirement IDs, gaps, ambiguities, untestable statements, risks, and open questions. Use whenever the user says review the SRS, analyze requirements, start the pipeline for a feature, or asks what is unclear in a requirement document, even if they do not name this skill.
---

# SRS review (stage 1)

Goal: find what a tester cannot test or cannot be sure about, before any scenario is written.

## Steps

1. Ask which feature if it is not clear. Read `docs/features/<feature>/srs.md`.
2. Write `docs/features/<feature>/01-srs-review.md` using the header block from `docs/features/_template/header.md` (stage `srs-review`, status `Draft`).
3. Include these sections:
   - **Summary**: what the feature does in 3 to 5 lines.
   - **Requirements list**: one row per requirement with an ID (`REQ-001`, ...). If the SRS has no IDs, propose them here and do not edit `srs.md`.
   - **Gaps and ambiguities**: missing rules, vague words ("quickly", "valid"), undefined limits, missing error messages.
   - **Untestable statements**: requirements with no observable result.
   - **Assumptions**: anything you had to assume. Mark each `[ai]`.
   - **Risk areas**: where a defect would hurt most (high, medium, low with one-line reasons).
   - **Open questions for the human**: numbered, answerable in one line each.
4. Stop and tell the human to answer the questions and update `srs.md`.

## Reverse-engineered SRS check

If the SRS was written from an already-built feature, it can describe the app's current behavior even where that behavior is wrong. Add a section **"Confirm intended behavior"** listing statements that look odd: inconsistent validation, surprising limits, unclear messages, behavior that reads like an implementation detail. For each, ask the human: is this intended?

## Rules

- Do not invent requirements. If it is not in the SRS, it is a question, not a requirement.
- Quote the SRS section for every gap you raise so the human can find it.
