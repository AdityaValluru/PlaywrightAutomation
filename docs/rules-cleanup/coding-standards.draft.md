---
trigger: always_on
---

# Coding Standards

> These rules describe what the code in this repository actually does. Every class and method named here exists and was verified in the source tree.

---

## Locators

- Prefer `PlaywrightUtils.locateByRole()`, `PlaywrightUtils.locateByLabel()`, `PlaywrightUtils.locateByPlaceholder()`, `PlaywrightUtils.locateByText()`, `PlaywrightUtils.locateByTestId()`, `PlaywrightUtils.locateByAltText()`, or `PlaywrightUtils.locateByTitle()` in that priority order.
- Use CSS or XPath selectors only when no `locateBy*` method in `PlaywrightUtils` can uniquely identify the element.
- Never use absolute XPath (starting from `//html` or the document root without a stable anchor).
- Never use positional XPath such as `//tr[3]` or `//div[2]`.
- All Playwright locator calls must go through `PlaywrightUtils`. Never call `BrowserManager.getPage().locator()` directly in test classes, action classes, or `*Objects` classes (direct calls such as in `LoginPageObjects.getErrorMessage()` are violations to be fixed). Only `PlaywrightUtils` methods may interact with Playwright's `Page` or `Locator` APIs directly.

## Interactions & Action Methods

- Every click, fill, wait, state query, and scroll must go through a method in `PlaywrightUtils`, not through direct Playwright `Page` or `Locator` API calls in page objects or test classes.
- Action methods that navigate, submit forms, or alter state must validate that the expected outcome occurred (e.g. via `PlaywrightUtils.waitForNavigation()` or `PlaywrightUtils.waitForVisible()`) before returning.
- Action methods that perform steps or state transitions must return `this` (or the next page's action object) to allow fluent method chaining. Verification methods that inspect state or retrieve data must return the value or `this`.
- Never call `Thread.sleep()`. Use `PlaywrightUtils.waitForVisible(String|Locator)`, `PlaywrightUtils.waitForHidden(String|Locator)`, `PlaywrightUtils.waitForNavigation()`, or `PlaywrightUtils.waitForURL(String)`.
- Never hardcode timeout values in page objects or tests; they come from `FrameworkConstants.EXPLICIT_WAIT_MS` and `FrameworkConstants.NAVIGATION_TIMEOUT_MS`.

## Configuration and Test Data

- Read all URLs from `ConfigReader.get("base.url")` (and other keys). Never hardcode a URL string in test or action code.
- Read all credentials from `ConfigReader.getCredential(key)` backed by `src/test/resources/config/credentials.properties`. Never hardcode a username or password.
- Read all other test data (search terms, page titles, expected URL fragments, error messages) from `ConfigReader.get(key)` or `ConfigReader.get(key, defaultValue)`.
- Any value that must be constant across the codebase (timeout, group name, file path, date pattern) goes into `FrameworkConstants` and is referenced by name.

## Logging

- Every action method must log its start with `log.info(...)`. The `log` field is inherited from `BasePage` as `LogManager.getLogger(getClass())` — do not create a new logger in action classes.
- Use `log.info()` for normal actions, `log.warn()` for recoverable issues, and `log.error()` for failures.
- Never log a password value. Log only `"Entering password"` (no value), as shown in `LoginPageActions.enterPassword()`.

## Extent Reporting

- Log each action to the Extent report via a null-safe wrapper:
  ```java
  if (ExtentReportManager.getTest() != null) {
      ExtentReportManager.getTest().log(status, message);
  }
  ```
  This guard is required because `@BeforeMethod` runs before `TestNGListener.onTestStart()` creates the `ExtentTest` node.
- Use `Status.INFO` for action steps, `Status.PASS` for verification pass, `Status.FAIL` for logged failures.

## Exception Handling

- Use `try/catch` only when the exception can be handled meaningfully (for example, `BrowserManager.closeBrowser()` catches and logs cleanup errors).
- Never swallow exceptions. If catching an exception, log the context (test name, element, parameters) and rethrow with the original cause.
- Do not invent custom exception classes; let native Playwright exceptions propagate or rethrow as standard `RuntimeException` with descriptive context and the root cause attached.

## Screenshots

- Failure screenshots are captured strictly at the framework level by `BaseTest.tearDown()` and `TestNGListener.onTestFailure()` (using `PlaywrightUtils.takeScreenshot()` and `ScreenshotUtils.saveScreenshot(byte[], String)`).
- Action methods must never contain try/catch blocks merely to capture screenshots; keeping capture at the framework level avoids duplicate screenshots and bloated action methods.

## Code Duplication

- Never copy a Playwright interaction pattern into a second place. Extract it into a method in `PlaywrightUtils`, `ConfigReader`, or `FrameworkConstants` as appropriate.
- Shared `@BeforeMethod` setup (e.g. login) may be extracted into a private helper method in the test class (see `HomePageTest.loginToApplication()`).

## Test Groups

- Always assign tests to at least one group using `FrameworkConstants.GROUP_SMOKE`, `FrameworkConstants.GROUP_REGRESSION`, or `FrameworkConstants.GROUP_SANITY` — never raw strings like `"smoke"`.
- Smoke tests cover critical happy-path flows. Sanity tests cover quick element-visibility checks. Regression tests cover all scenarios.
