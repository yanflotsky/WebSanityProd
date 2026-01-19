package com.websanity;

import com.websanity.adminPortalPages.LogInPage;
import com.websanity.adminPortalPages.MenuPage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

/**
 * Base test class for Admin Portal tests
 * Keeps browser and session open between tests
 * Login is performed once before all tests
 */
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AdminPortalBaseTest extends BaseTest {

    protected static MenuPage menuPage;

    /**
     * Perform login once before all tests in the class
     * Browser context and page are created by parent BaseTest in @BeforeAll
     */
    @BeforeAll
    static void setupAndLoginOnce() {
        log.info("🔧 Setting up Admin Portal session - performing login once for all tests...");

        // page and context are already created in BaseTest.launchBrowser()
        // Initialize login page
        LogInPage loginPage = new LogInPage(page);

        // Perform automatic login with MFA
        menuPage = loginPage.loginToAdminPortalWithAutoUser()
                .closePopUpsAfterLogin();  // This method already waits for User Management button

        log.info("✅ Login completed successfully, session will be kept open for all tests");
    }

    /**
     * Cleanup after all tests are completed
     */
    @AfterAll
    static void tearDownAll() {
        log.info("🧹 Closing Admin Portal session after all tests...");
    }
}

