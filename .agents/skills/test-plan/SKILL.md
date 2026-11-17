---
name: test-plan
description: Stage 3 of the QA pipeline. Writes a risk-based test plan (scope, approach, automation candidates, data, environment, entry and exit criteria) from approved scenarios into 03-test-plan.md. Use whenever the user asks for a test plan, test strategy for a feature, or what to automate versus test manually.
---

# Test plan (stage 3)

## Steps

1. Check that `02-scenarios.md` has status `Approved`. If not, stop and say so.
2. Write `03-test-plan.md` (stage `test-plan`, status `Draft`) with these sections:
   - **Scope**: in scope and out of scope.
   - **Approach**: how each scenario type will be tested.
   - **Risk and priority**: which scenarios matter most and why.
   - **Automation decision**: a table with each `SCN-` ID, Automate (Yes or No), and the reason. Automate stable, repeatable, high-value checks. Keep visual judgment and one-off checks manual.
   - **Suites**: grouping into smoke and regression.
   - **Test data**: what data is needed and where it will come from. No hard-coded data in tests.
   - **Environment**: application URL and prerequisites, referenced from config, never typed in as a secret.
   - **Entry and exit criteria**: when testing can start and when it is done.
   - **Risks and assumptions**: marked `[ai]`.
3. Stop and tell the human to review the automation decisions first, since they drive the work in later stages.

## Rules

- Keep it short enough to read in five minutes. A plan nobody reads is not a plan.
- Do not add scenarios here. If something is missing, list it under risks and let the human decide.
