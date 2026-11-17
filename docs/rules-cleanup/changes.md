# Changes to bestpractices.md Rules

Every rule from the original file is accounted for here.

| # | Original rule (verbatim) | Disposition | Destination | Reason |
|---|---|---|---|---|
| 1 | Action methods that navigate, submit forms, or change application state must validate that the expected outcome occurred before returning. | **Kept, reworded** | `coding-standards.draft.md` → Interactions section | The rule is correct and the code follows it. Reworded to name `PlaywrightUtils.waitForNavigation()` concretely instead of "validate the expected outcome" (vague). |
| 2 | Public methods must include JavaDoc describing: purpose, parameters, return value, exceptions. | **Moved to skill** | `playwright-page-object` skill (§4) | The requirement is fully covered in the skill under "## 4. Documentation". Keeping it in the rule file duplicates the skill (Conflict C4). |
| 3 | Action methods must — Log every action. | **Kept, reworded** | `coding-standards.draft.md` → Logging section | Correct. Reworded to name the exact field: `log.info()` inherited from `BasePage` via `LogManager.getLogger(getClass())`. |
| 4 | Action methods must — Capture screenshots on failure. | **Reworded (framework-level)** | `coding-standards.draft.md` → Screenshots section | Resolved (D3): Capture is handled strictly at the framework level (`BaseTest.tearDown()`, `TestNGListener.onTestFailure()`). Action methods stay clean and do not duplicate screenshot logic. (Note: skill to be updated to match). |
| 5 | Action methods must — Return Page Objects. | **Kept, reworded** | `coding-standards.draft.md` → Interactions section | Resolved (D4): Standardised so action methods return `this` or the next page's action object for fluent chaining, and getters/verifications return the queried value or `this`. |
| 6 | Action methods must — Use framework wait methods. | **Kept, reworded** | `coding-standards.draft.md` → Interactions section | Correct. Reworded to name `PlaywrightUtils.waitForVisible(String\|Locator)`, `waitForHidden()`, `waitForNavigation()`, `waitForURL()` explicitly. |
| 7 | Action methods must — Throw framework exceptions. | **Reworded** | `coding-standards.draft.md` → Exception Handling section | Resolved (D1): Reworded to "never swallow exceptions; if you catch one, log the context and rethrow with the original cause." Custom exception class skipped for now to avoid inventing non-existent classes. |
| 8 | Action methods must — Never directly use Playwright `page.locator()` outside page objects. | **Kept & tightened** | `coding-standards.draft.md` → Locators section | Resolved (D2): Strict rule maintained. Every Playwright call must go through `PlaywrightUtils`, including inside `*Objects` classes. The direct call in `LoginPageObjects.getErrorMessage()` is flagged as a code fix. |
| 9 | Use try/catch only when the exception can be handled meaningfully. | **Kept, reworded** | `coding-standards.draft.md` → Exception Handling section | Correct. The code follows this. Added example: `BrowserManager.closeBrowser()`. |
| 10 | Never swallow exceptions. | **Kept** | `coding-standards.draft.md` → Exception Handling section | Correct. Code follows this. |
| 11 | If catching an exception, log sufficient information and rethrow when appropriate. | **Merged with rule 10** | `coding-standards.draft.md` → Exception Handling section | Rules 10 and 11 describe one policy. Merged into one paragraph to avoid splitting a single concept across two bullets. |
| 12 | Never use absolute XPath. | **Kept, reworded** | `coding-standards.draft.md` → Locators section | Correct. Specified "starting from `//html` or the document root without a stable anchor". |
| 13 | Never use indexed XPath like `//tr[3]` or `//div[2]`. | **Kept** | `coding-standards.draft.md` → Locators section | Correct. Example preserved verbatim. |
| 14 | Always prefer unique locators (id, data-testid, aria-label, name). | **Kept, reworded** | `coding-standards.draft.md` → Locators section | Correct intent. Reworded to map to actual `PlaywrightUtils.locateBy*()` methods (`locateByRole`, `locateByLabel`, `locateByTestId`, etc.). |
| 15 | Never use Thread.sleep(). | **Kept** | `coding-standards.draft.md` → Interactions section | Correct. No `Thread.sleep()` found anywhere in the codebase. |
| 16 | Never hardcode wait timings. | **Kept, reworded** | `coding-standards.draft.md` → Interactions section | Correct. Reworded to name `FrameworkConstants.EXPLICIT_WAIT_MS` and `NAVIGATION_TIMEOUT_MS`. |
| 17 | Never hardcode URLs. | **Kept, reworded** | `coding-standards.draft.md` → Configuration and Test Data section | Correct. Reworded to name `ConfigReader.get("base.url")`. |
| 18 | Never hardcode credentials. | **Kept, reworded** | `coding-standards.draft.md` → Configuration and Test Data section | Correct. Reworded to name `ConfigReader.getCredential(key)` and `credentials.properties`. |
| 19 | Never duplicate code. | **Kept, reworded** | `coding-standards.draft.md` → Code Duplication section | Correct. Added concrete guidance: extract into `PlaywrightUtils`, `ConfigReader`, or `FrameworkConstants`. |
| 20 | Never use static waits where explicit waits are possible. | **Merged with rule 15** | `coding-standards.draft.md` → Interactions section | Rules 15 and 20 both prohibit non-dynamic waits. Merged into one rule pointing to `PlaywrightUtils.waitForVisible()` etc. |
| 21 | All reusable logic must be moved into framework methods. | **Kept, reworded** | `coding-standards.draft.md` → Code Duplication section | Correct. Reworded to name `PlaywrightUtils`, `ConfigReader`, `FrameworkConstants` as the destinations. |
| 22 | Always prefer Playwright's built-in locator methods (getByRole, getByLabel, getByText, getByTestId, etc.). Use CSS/XPath selectors only when no reliable getBy* locator is possible. | **Kept, reworded** | `coding-standards.draft.md` → Locators section | Correct. Reworded to reference the `PlaywrightUtils.locateBy*()` wrappers (`locateByRole`, `locateByLabel`, `locateByPlaceholder`, `locateByText`, `locateByTestId`, `locateByAltText`, `locateByTitle`). |
| 23 | All Playwright interactions (locators, waits, clicks, fills, assertions, etc.) must be performed through reusable framework methods. Direct Playwright API usage in page objects or tests is not allowed unless no framework method exists. | **Kept, reworded** | `coding-standards.draft.md` → Interactions and Locators sections | Correct intent. Reworded to state that all Playwright interactions must go through `PlaywrightUtils`. Direct Playwright API usage is forbidden across tests, actions, and objects classes. |

---

## Items That Belong in the Skill (not in rules files)

The following belong in the `playwright-page-object` skill:

- **Three-class split template**: `[PageName]Objects` (static methods, private constructor), `[PageName]Actions` (extends `BasePage`), `[PageName]Test` (extends `BaseTest`).
- **`logExtent()` null-safe wrapper pattern**: Helper method inside action classes guarding against `null` during `@BeforeMethod`.
- **Fluent chaining pattern**: Action methods return `this` or next page object; getters return values.
- **Skill alignment updates**: Remove the skill's instruction asking action methods to catch and screenshot failures (aligned with framework-level handling per D3).
