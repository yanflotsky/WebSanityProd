package com.websanity;

import com.websanity.adminPortalPages.AdminPortalLogInPage;
import com.websanity.adminPortalPages.AdminPortalMenuPage;
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

    protected static AdminPortalMenuPage menuPage;

    /**
     * Perform login once before all tests in the class
     * Browser context and page are created by parent BaseTest
     */
    @BeforeAll
    void setupAndLoginOnce() {
        log.info("🔧 Setting up Admin Portal session - performing login once for all tests...");

        // Create context and page (normally done in @BeforeEach)
        createContextAndPage();

        // Initialize login page
        AdminPortalLogInPage loginPage = new AdminPortalLogInPage(page);

        // Perform automatic login with MFA
        menuPage = loginPage.loginToAdminPortalWithAutoUser()
                .waitForUserManagementBtnToAppear()
                .closePopUpsAfterLogin();

        log.info("✅ Login completed successfully, session will be kept open for all tests");
    }

    /**
     * Close context after all tests are completed
     * Override parent's @AfterEach to prevent closing context after each test
     */
    @AfterAll
    void closeContextAfterAllTests() {
        log.info("🧹 Closing Admin Portal session after all tests...");
        if (context != null) {
            context.close();
        }
    }

    /**
     * Override parent's createContextAndPage to do nothing
     * We create context once in setupAndLoginOnce
     */
    @Override
    void createContextAndPage() {
        if (context == null) {
            // Only create if not yet created
            super.createContextAndPage();
        }
    }

    /**
     * Override parent's closeContext to do nothing
     * We keep context open between tests
     */
    @Override
    void closeContext() {
        // Do nothing - keep context open between tests
        log.debug("Keeping context open between tests");
    }
}

