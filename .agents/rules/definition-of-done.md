---
trigger: always_on
---

# Definition of done for tests

Before you say any test work is finished, run the checks below that apply and show the result as a table with one line of evidence each. If you did not check something, write "not checked" and say why. This is a first pass. The human still reviews the full diff.

| Check | Question | Evidence to show |
|---|---|---|
| Right | Does each assertion check the expected result from the test case row, not just that the page loaded? | Test case ID mapped to its assertion lines |
| Right | Does the test fail when the expected value is wrong? | The assertion check result from `test-run` |
| Stable | No hard waits, no hard-coded data, URLs, or credentials? | Search result of the diff |
| Stable | Did it pass on its own, 5 times in a row, and in the group? | Run counts, for example 5/5 |
| Fits | Locators live in page objects, semantic locators first, existing code reused, framework conventions followed? | Files touched and what was reused |
| Real | Does it compile, and does every method and class used exist and is it current? | Compile command output |
| Clean | Only files related to the task changed, no secrets, no weakened or removed assertions? | `git status` and a diff summary |

Rules:
- Never report a test as passing when the assertion check failed to show the test can fail. Report it as weak.
- Never mark work finished while any check above is failing. Say what failed and stop.
