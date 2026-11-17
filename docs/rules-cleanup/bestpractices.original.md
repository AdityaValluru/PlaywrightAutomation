---
trigger: always_on
---

Action methods that navigate, submit forms, or change application state must validate that the expected outcome occurred before returning.

Public methods must include JavaDoc describing:

- purpose
- parameters
- return value
- exceptions

Action methods must:

- Log every action.
- Capture screenshots on failure.
- Return Page Objects.
- Use framework wait methods.
- Throw framework exceptions.
- Never directly use Playwright page.locator() outside page objects.


Use try/catch only when the exception can be handled meaningfully.

Never swallow exceptions.

If catching an exception, log sufficient information and rethrow when appropriate.

## Coding Standards

- Never use absolute XPath.
- Never use indexed XPath like //tr[3] or //div[2].
- Always prefer unique locators (id, data-testid, aria-label, name).
- Never use Thread.sleep().
- Never hardcode wait timings.
- Never hardcode URLs.
- Never hardcode credentials.
- Never duplicate code.
- Never use static waits where explicit waits are possible.
- All reusable logic must be moved into framework methods.
- Always prefer Playwright's built-in locator methods (getByRole, getByLabel, getByText, getByTestId, etc.). Use CSS/XPath selectors only when no reliable getBy* locator is possible.
- All Playwright interactions (locators, waits, clicks, fills, assertions, etc.) must be performed through reusable framework methods. Direct Playwright API usage in page objects or tests is not allowed unless no framework method exists.