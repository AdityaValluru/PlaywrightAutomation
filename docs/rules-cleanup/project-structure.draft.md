---
trigger: always_on
---

# Project Structure

> Verified against `pom.xml` and the source tree as of November 2026.

---

## Stack

| Concern | Library / Version |
|---|---|
| Language | Java 11 |
| Browser automation | Playwright for Java `1.44.0` |
| Test runner | TestNG `7.10.2` |
| Reporting | Extent Reports (Spark) `5.1.2` |
| Logging | Log4j2 `2.23.1` |
| Build tool | Maven (maven-surefire-plugin `3.3.0`, maven-compiler-plugin `3.13.0`) |

---

## Folder Structure

```
PlayWrightCopilot/
├── pom.xml
└── src/
    ├── main/java/com/framework/
    │   ├── base/
    │   │   └── BasePage.java            # Abstract base for all action classes
    │   ├── constants/
    │   │   └── FrameworkConstants.java  # Timeouts, paths, group names, patterns
    │   ├── drivers/
    │   │   └── BrowserManager.java      # ThreadLocal Playwright/Browser/Page lifecycle
    │   ├── reports/
    │   │   └── ExtentReportManager.java # ThreadLocal ExtentTest; suite-level HTML report
    │   └── utils/
    │       ├── ConfigReader.java        # Reads config.properties and credentials.properties
    │       ├── PlaywrightUtils.java     # All reusable browser interactions (locators, clicks, waits, …)
    │       └── ScreenshotUtils.java     # Saves PNG bytes to reports/screenshots/
    └── test/
        ├── java/com/framework/
        │   ├── base/
        │   │   └── BaseTest.java        # @BeforeSuite/@AfterSuite/@BeforeMethod/@AfterMethod
        │   ├── listeners/
        │   │   ├── RetryAnalyzer.java   # IRetryAnalyzer — retries up to FrameworkConstants.MAX_RETRY_COUNT
        │   │   └── TestNGListener.java  # ITestListener + ISuiteListener — Extent + screenshots
        │   └── tests/
        │       ├── homepage/
        │       │   ├── HomePageObjects.java   # String locator constants (XPath/CSS)
        │       │   ├── HomePageActions.java   # Extends BasePage; interactions + verifications
        │       │   └── HomePageTest.java      # Extends BaseTest; @Test methods
        │       ├── loginpage/
        │       │   ├── LoginPageObjects.java  # Static Locator-returning methods via PlaywrightUtils
        │       │   ├── LoginPageActions.java  # Extends BasePage; interactions + verifications
        │       │   └── LoginPageTest.java     # Extends BaseTest; @Test methods
        │       └── tablespage/
        │           ├── TablesLocators.java    # Static Locator-returning methods (scheduled rename: TablesObjects)
        │           ├── TablesActions.java     # Extends BasePage; fluent chaining, return this
        │           └── TablesTest.java        # Extends BaseTest; @Test methods
        └── resources/
            ├── config/
            │   ├── config.properties          # base.url, browser, timeouts, report settings, test data
            │   └── credentials.properties     # valid.username, valid.password, invalid.* — never committed
            ├── log4j2.xml                     # Console (INFO+) + RollingFile (INFO+, daily + 10 MB)
            └── testng/
                ├── testng-serial.xml          # All groups, sequential (default for mvn test)
                ├── testng-all.xml             # All groups, parallel (thread-count=2)
                ├── testng-smoke.xml           # smoke group only, parallel (thread-count=2)
                └── testng-regression.xml      # regression group only, parallel (thread-count=4)
```

Generated output (not committed):
```
reports/
├── ExtentReport_<timestamp>.html
└── screenshots/<testName>_<timestamp>.png
logs/
└── automation.log  (+ dated .log.gz archives)
target/
└── traces/<testName>-trace.zip  (when trace.enabled=true and test fails)
```

---

## Naming Conventions

| Artefact | Convention | Example |
|---|---|---|
| Package | `com.framework.tests.[pagename]` (all lowercase) | `com.framework.tests.loginpage` |
| Locator class | `[PageName]Objects` (static methods/constants, private constructor) | `LoginPageObjects`, `HomePageObjects` *(Note: `TablesLocators` to be renamed to `TablesObjects`)* |
| Actions class | `[PageName]Actions` extends `BasePage` | `LoginPageActions`, `TablesActions` |
| Test class | `[PageName]Test` extends `BaseTest` | `LoginPageTest`, `TablesTest` |
| Test method | `verify[Scenario]` or `validate[Flow]`, camelCase | `verifyLoginPageLoads` |
| Test case ID | Comment `// TC_<PAGE>_<NNN>` above each `@Test` block | `// TC_LOGIN_001` |
| Group constants | `FrameworkConstants.GROUP_SMOKE` / `GROUP_REGRESSION` / `GROUP_SANITY` | — |

---

## Base Classes

| Class | Role |
|---|---|
| `com.framework.base.BasePage` | Abstract. Provides `protected final Logger log` and `protected final Page page` (from `BrowserManager.getPage()`). All action classes must extend it. |
| `com.framework.base.BaseTest` | Concrete. `@BeforeSuite` → `ExtentReportManager.initReports()`. `@BeforeMethod` → `BrowserManager.initBrowser()` + navigate to `base.url`. `@AfterMethod` → capture screenshot on failure, `BrowserManager.closeBrowser()`. `@AfterSuite` → `ExtentReportManager.flushReports()`. All test classes must extend it. |

---

## Commands

**Compile only (no tests):**
```bash
cd PlayWrightCopilot
mvn compile test-compile -q
```

**Run the full suite (sequential, default profile):**
```bash
mvn test
# Uses testng-serial.xml (all groups, single thread)
```

**Run smoke tests only:**
```bash
mvn test -P smoke
# Uses testng-smoke.xml (smoke group, parallel, thread-count=2)
```

**Run regression tests only:**
```bash
mvn test -P regression
# Uses testng-regression.xml (regression group, parallel, thread-count=4)
```

**Run all tests in parallel:**
```bash
mvn test -DsuiteXmlFile=src/test/resources/testng/testng-all.xml
# smoke + regression + sanity, parallel, thread-count=2
```

**Run a single test class:**
```bash
mvn test -Dtest=LoginPageTest
```

**Override browser or headless at runtime:**
```bash
mvn test -Dbrowser=firefox -Dheadless=true
```

---

## Key Configuration Keys (`config.properties`)

| Key | Default | Purpose |
|---|---|---|
| `base.url` | `http://localhost:5173/login` | Application entry point |
| `browser` | `chromium` | `chromium` \| `firefox` \| `webkit` |
| `headless` | `false` | `true` for CI |
| `slowmo` | `0` | ms delay between Playwright actions |
| `viewport.width` / `viewport.height` | `1920` / `1080` | Browser viewport |
| `navigation.timeout.ms` | `60000` | `BrowserContext` default navigation timeout |
| `trace.enabled` | `true` | Enable Playwright tracing |
| `trace.retain.on.failure` | `true` | Keep trace zip only on failure |
| `post.login.url.fragment` | `/` | URL fragment asserted after login |

---

## Notes

- `BrowserManager` and `ExtentReportManager` both use `ThreadLocal` — the framework is safe for parallel test execution.
- `RetryAnalyzer` retries a failed test up to `FrameworkConstants.MAX_RETRY_COUNT` (= 1) extra time. Apply per-test with `@Test(retryAnalyzer = RetryAnalyzer.class)`.
- `TestNGListener` must be registered in every `testng*.xml` under `<listeners>`.
- Git and pipeline rules are in `pipeline.md` (not duplicated here).
