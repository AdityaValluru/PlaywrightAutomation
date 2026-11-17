---
name: scenario-generation
description: Stage 2 of the QA pipeline. Turns an approved SRS review into test scenarios with a requirement coverage matrix, written to 02-scenarios.md. Use whenever the user asks to generate scenarios, derive test scenarios from the SRS, or continue the pipeline after the SRS review is approved.
---

# Scenario generation (stage 2)

Goal: a reviewable list of what to test, with no steps yet.

## Steps

1. Check that `01-srs-review.md` has status `Approved`. If not, stop and say so.
2. Read `srs.md` and `01-srs-review.md`, including the human's answers and `[human]` items.
3. Write `02-scenarios.md` (stage `scenarios`, status `Draft`) containing:
   - A table with columns: ID (`SCN-001`), requirement link (`REQ-xxx`), title, type (positive, negative, boundary, validation, UI state, navigation), priority (High, Medium, Low), and a one-line reason.
   - A **coverage matrix**: every requirement and the scenarios that cover it. Flag any requirement with no scenario.
   - An **assumptions** list, marked `[ai]`.
4. Stop and tell the human to add missing scenarios, remove weak or duplicate ones, and adjust priorities.

## Rules

- Prefer realistic, commonly occurring scenarios. Do not pad the list with far-fetched corner cases.
- UI scenarios only, unless the SRS defines non-UI behavior that needs to be tested.
- Each scenario tests one thing. If a title contains "and", consider splitting it.
- Every scenario links to at least one requirement. Do not create scenarios without a requirement.
