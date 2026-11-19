# Rules Audit

> Read in a few minutes. Evidence is cited by file; all class and method names were verified in the source tree.

---

## 1. Rules That Match the Code

Each rule is followed by an example file that demonstrates it.

| # | Rule (paraphrased) | Evidence |
|---|---|---|
| 1 | Action methods that navigate or change state call `PlaywrightUtils.waitForNavigation()` before returning | `LoginPageActions.clickLoginButton()` |
| 2 | Public methods have JavaDoc (`@param`, `@return`, `@throws`) | `PlaywrightUtils.click(Locator)`, `ConfigReader.get(String)` |
| 3 | Action methods log every action via `log.info()` inherited from `BasePage` | `LoginPageActions.enterUsername()`, `HomePageActions.logout()` |
| 4 | Failure screenshots are captured automatically — `BaseTest.tearDown()` calls `PlaywrightUtils.takeScreenshot()` / `ScreenshotUtils.saveScreenshot()` on `ITestResult.FAILURE` | `BaseTest.captureAndAttachScreenshot()` |
| 5 | Action methods return `this` (the same Actions class) to allow fluent chaining | `TablesActions.navigateToTables()` → `clickAddButton()` → … |
| 6 | `PlaywrightUtils` is used for clicks, type, waits, state queries | `HomePageActions`, `LoginPageActions`, `TablesActions` |
| 7 | `Thread.sleep()` is never used | Grep across all Java files: none found |
| 8 | Wait timings are not hardcoded; timeouts come from `FrameworkConstants.EXPLICIT_WAIT_MS` | `PlaywrightUtils.waitForVisible(Locator)` |
| 9 | Credentials are not hardcoded; they come from `ConfigReader.getCredential()` | `LoginPageActions.loginWithConfigCredentials()` |
| 10 | URLs are not hardcoded; they come from `ConfigReader.get("base.url")` | `BasePage.goToBaseUrl()`, `BrowserManager.initBrowser()` |
| 11 | No duplicate code — shared Playwright operations are centralised in `PlaywrightUtils` | All action classes |
| 12 | Reusable logic is in framework methods, not copied into page objects | `PlaywrightUtils.type()`, `PlaywrightUtils.click()`, etc. |
| 13 | Exceptions are not swallowed silently — `ConfigReader` rethrows as `RuntimeException` with context | `ConfigReader.loadProperties()` |

---

## 2. Rules the Code Does Not Follow

| # | Rule | Extent of violation | Resolution / Decision |
|---|---|---|---|
| A | "Throw framework exceptions" — the rule implies a custom exception hierarchy | **All code** — the framework has **no custom exception class**; it throws `RuntimeException` and lets `com.microsoft.playwright.TimeoutError` propagate natively | **Resolved (D1):** Reworded to "never swallow exceptions; if you catch one, log the context and rethrow with the original cause." Skip custom exception class for now. |
| B | "Never directly use Playwright `page.locator()` outside page objects" | **Some code** — `LoginPageObjects.getErrorMessage()` calls `BrowserManager.getPage().locator(...)` directly instead of going via `PlaywrightUtils`. | **Resolved (D2):** Strict rule kept: all locator queries must go through `PlaywrightUtils`. `LoginPageObjects.getErrorMessage()` is flagged to be fixed. |
| C | "Capture screenshots on failure" in action methods | **Most code** — screenshots on failure are captured by `BaseTest.tearDown()` and `TestNGListener.onTestFailure()` **at the framework level**, not inside individual action methods. | **Resolved (D3):** Rule reworded to reflect framework-level capture only. Action methods stay clean; skill to be aligned. |
| D | "Action methods must return Page Objects" | **Some code** — `LoginPageActions` methods return `void`, not `LoginPageActions`. Only `TablesActions` uses the fluent/chaining pattern. `HomePageActions` also returns `void`. | **Resolved (D4):** Standardised: actions must return `this` or the next page object; getters return the queried value. |
| E | `TablesLocators` has no JavaDoc on most methods | **Some code** — `TablesLocators.getDataMenu()`, `getTablesMenu()`, etc. are missing method-level JavaDoc | **Fix required:** To be added during the rename to `TablesObjects`. |

---

## 3. Conventions the Code Follows (No Rule Mentions)

1. **Three-class split per page**: Objects class (locators), Actions class (interactions + assertions), Test class (TestNG `@Test` methods). Objects class has a private constructor to prevent instantiation.
2. **Two locator styles coexist**: `LoginPageObjects` and `TablesLocators` return `Locator` objects via `PlaywrightUtils.locateBy*()`. `HomePageObjects` stores raw XPath/CSS `String` constants passed directly to `PlaywrightUtils.click(String)` / `waitForVisible(String)`. Both are supported by `PlaywrightUtils`.
3. **Thread-local browser session**: `BrowserManager` stores `Playwright`, `Browser`, `BrowserContext`, and `Page` in `ThreadLocal` — one per test thread, safe for parallel execution.
4. **Thread-local Extent Report node**: `ExtentReportManager` stores `ExtentTest` in `ThreadLocal`; every action class uses a null-safe `logExtent()` wrapper (`if (ExtentReportManager.getTest() != null)`).
5. **Test groups use `FrameworkConstants` constants**, not raw strings: `FrameworkConstants.GROUP_SMOKE`, `GROUP_REGRESSION`, `GROUP_SANITY`.
6. **Retry**: `RetryAnalyzer` is applied per-test via `@Test(retryAnalyzer = RetryAnalyzer.class)`; the max count is `FrameworkConstants.MAX_RETRY_COUNT` (= 1).
7. **Tracing**: `BrowserContext` tracing is started/stopped in `BaseTest.setUp()` / `tearDown()`, controlled by `trace.enabled` in `config.properties`. Traces go to `target/traces/`.
8. **Config override via `-D` flags**: `ConfigReader.loadProperties()` merges `System.getProperties()` after loading the file — any `-D` flag wins.
9. **Report lifecycle is split**: `ExtentReportManager.initReports()` is called in `BaseTest.setUpSuite()` (`@BeforeSuite`); flush is in `tearDownSuite()` (`@AfterSuite`). `TestNGListener` creates one `ExtentTest` node per test in `onTestStart`.
10. **Logger is scoped to the concrete subclass**: `BasePage` uses `LogManager.getLogger(getClass())` so each Actions class appears under its own name in the log.
11. **Test data via `config.properties`** (`search.term`, `home.page.title`, `post.login.url.fragment`, etc.); credentials in a separate `credentials.properties` file.
12. **Four Maven profiles/suites**: default (`testng-serial.xml`), `smoke` (`-P smoke`), `regression` (`-P regression`), and `all` (via `-DsuiteXmlFile=…`).
13. **Fluent/builder chaining with `return this`** is used in `TablesActions` but NOT in `LoginPageActions` or `HomePageActions` (standardising on `return this` per D4).
14. **Naming convention discrepancy**: `HomePageObjects` and `LoginPageObjects` use `Objects`; `TablesLocators` uses `Locators` (standardising on `Objects` per D5).

---

## 4. Rules That Conflict With Each Other, or Duplicate the Skill

| # | Conflict / Duplication | Detail | Resolution |
|---|---|---|---|
| C1 | "Action methods must capture screenshots on failure" (rule) vs. "screenshots are captured by `BaseTest.tearDown()` automatically" (actual code) | Double-capture if both apply. | Resolved (D3): Capture is exclusively framework-level (`BaseTest.tearDown()`). |
| C2 | "Action methods must return Page Objects" (rule + skill) vs. "action methods return `void`" (`LoginPageActions`, `HomePageActions`) | Two codebases/classes contradict. | Resolved (D4): Standardised to return `this` or next page object. |
| C3 | "Never directly use `page.locator()` outside page objects" (rule) vs. "Direct `BrowserManager.getPage().locator()` in `LoginPageObjects.getErrorMessage()`" (code) | Direct call bypasses `PlaywrightUtils`. | Resolved (D2): Keep strict rule; fix `LoginPageObjects`. |
| C4 | The rule and the skill both specify the same JavaDoc requirements — exact duplication. | Redundant duplication across files. | Moved to skill (§4). |
| C5 | The rule says "throw framework exceptions" but no custom exception class exists in the codebase. | Rule cannot be followed as written. | Resolved (D1): Reword to not swallow exceptions, rethrow with cause. |

---

## 5. Rules Too Vague to Follow

| # | Rule | Why it is too vague | How it was fixed in `coding-standards.draft.md` |
|---|---|---|---|
| V1 | "Use framework wait methods" | Does not name the class. | Named `PlaywrightUtils.waitForVisible(String\|Locator)`, `waitForHidden(String\|Locator)`, `waitForNavigation()`, `waitForURL(String)`. |
| V2 | "Throw framework exceptions" | No custom exception class exists. | Reworded to never swallow exceptions and rethrow with cause and context (D1). |
| V3 | "Log every action" | Does not say which logger or at what level. | Named `log.info(...)` inherited from `BasePage.log` (`LogManager.getLogger(getClass())`). |
| V4 | "Capture screenshots on failure" | Does not say how. | Specified framework-level capture in `BaseTest.tearDown()` / `TestNGListener` (D3). |
| V5 | "All Playwright interactions … must be performed through reusable framework methods" | Boundary unclear. | Defined boundary: all callers outside `PlaywrightUtils` must invoke `PlaywrightUtils` methods (D2). |

---

## 6. Facts I Still Need to Provide

The 5 key decisions (D1–D5) have now been resolved:

1. **Custom exception classes (D1)**: Reworded to "never swallow exceptions; if you catch one, log the context and rethrow with the original cause." Custom exception class skipped for now.
2. **Direct `page.locator()` in `LoginPageObjects` (D2)**: Strict rule kept — all Playwright interactions must go through `PlaywrightUtils`. The one violation in `LoginPageObjects.getErrorMessage()` will be fixed in code.
3. **Screenshot responsibility (D3)**: Framework-level only (`BaseTest.tearDown()` and `TestNGListener`). Action methods do not catch/screenshot. Skill will be updated to match.
4. **Action return types (D4)**: Standardised on fluent chaining — actions return `this` or the next page object; getters return the queried value.
5. **Locator class naming (D5)**: Standardised on `[PageName]Objects` (e.g. `TablesLocators` will be renamed to `TablesObjects`).

---

## Summary of Decisions

| ID | Decision | Action to Take |
|---|---|---|
| **D1** | Custom exception | Reword rule to require rethrowing with context/cause; do not invent a custom exception class. |
| **D2** | Direct `page.locator()` | Keep strict rule (`PlaywrightUtils` for all calls); fix `LoginPageObjects.getErrorMessage()`. |
| **D3** | Screenshots | Framework-level capture only (`BaseTest.tearDown()`); remove action-level screenshot requirement from skill. |
| **D4** | Return type | Standardise on `return this` / next page object for actions; getters return queried values. |
| **D5** | Naming | Canonical suffix is `Objects` (`LoginPageObjects`, `HomePageObjects`); rename `TablesLocators` to `TablesObjects`. |
