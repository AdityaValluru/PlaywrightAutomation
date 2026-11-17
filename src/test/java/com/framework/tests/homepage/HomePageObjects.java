package com.framework.tests.homepage;

/**
 * Object Repository for the Home / Dashboard Page.
 * <p>
 * All XPath and CSS locators for Home Page elements are defined here as
 * {@code public static final} constants. The Action class references these
 * constants instead of embedding raw locator strings.
 * </p>
 *
 * <p>Update the selectors below to match your target application's DOM structure.</p>
 */
public final class HomePageObjects {

    /** Prevent instantiation of this object-repository class. */
    private HomePageObjects() {}

    // =========================================================================
    // HEADER / BRAND
    // =========================================================================

    /** Application brand logo in the header. */
    public static final String HEADER_LOGO =
            "//header//img[@alt='Logo'] | //a[contains(@class,'navbar-brand')]";

    /** Welcome message containing the logged-in user's name. */
    public static final String WELCOME_MESSAGE =
            "//span[contains(@class,'welcome')] | //div[@id='welcomeMessage'] | //p[contains(@class,'welcome')]";

    // =========================================================================
    // NAVIGATION BAR
    // =========================================================================

    /** Top-level navigation bar container. */
    public static final String NAVIGATION_BAR =
            "[data-testid='appbar']";

    /** All clickable navigation menu items / links within the navbar. */
    public static final String NAV_MENU_ITEMS =
            "[data-testid^='nav-']";

    /** Home navigation link inside the nav bar. */
    public static final String HOME_NAV_LINK =
            "//nav//a[contains(@href,'home') or contains(text(),'Home')]";

    /** Logout button or link. */
    public static final String LOGOUT_BUTTON =
            "[data-testid='menu-logout']";

    /** User-profile dropdown toggle button. */
    public static final String USER_PROFILE_DROPDOWN =
            "[data-testid='btn-user-menu']";

    /** User-profile menu container (visible after toggle). */
    public static final String USER_PROFILE_MENU =
            "//div[contains(@class,'user-menu')] | //ul[contains(@class,'dropdown-menu') and ancestor::*[@id='userMenu']]";

    // =========================================================================
    // DASHBOARD
    // =========================================================================

    /** Main dashboard / content container. */
    public static final String DASHBOARD_CONTAINER =
            "[data-testid='dashboard-page']";

    /** Page title heading. */
    public static final String PAGE_TITLE =
            "//h1[@id='pageTitle'] | //h1[contains(@class,'page-title')] | //h1[contains(@class,'dashboard-title')]";

    /** Dashboard statistic / info cards or tiles. */
    public static final String DASHBOARD_CARDS =
            "[data-testid='kpi-1'], [data-testid='kpi-2'], [data-testid='kpi-3'], [data-testid='kpi-4']";

    // =========================================================================
    // SEARCH
    // =========================================================================

    /** Search input field. */
    public static final String SEARCH_INPUT =
            "[data-testid='global-search'] input";

    /** Search submit button. */
    public static final String SEARCH_BUTTON =
            "//button[contains(@class,'search-btn')] | //button[@id='searchBtn'] | //button[@aria-label='Search']";

    /** The container or list wrapper that appears displaying search results. */
    public static final String SEARCH_RESULTS =
            "[data-testid='global-search']";

    // =========================================================================
    // NOTIFICATIONS
    // =========================================================================

    /** Notification bell icon / button. */
    public static final String NOTIFICATION_BELL =
            "//button[contains(@class,'notification')] | //a[@id='notifications'] | //button[@aria-label='Notifications']";

    /** Badge showing the unread notification count. */
    public static final String NOTIFICATION_BADGE =
            "//span[contains(@class,'badge') and ancestor::*[contains(@class,'notification')]]";

    // =========================================================================
    // FOOTER
    // =========================================================================

    /** Page footer element. */
    public static final String FOOTER =
            "[data-testid='main-layout']"; // Temporary fallback if footer doesn't exist

    /** Copyright text within the footer. */
    public static final String FOOTER_COPYRIGHT =
            "//footer//span[contains(@class,'copyright')] | //div[contains(@class,'copyright')]";
}
