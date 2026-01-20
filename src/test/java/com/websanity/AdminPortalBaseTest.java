package com.websanity;

import com.websanity.adminPortalPages.LogInPage;
import com.websanity.adminPortalPages.MenuPage;
import com.websanity.adminPortalPages.UserManagementPage;
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
     * Cleanup after all tests are completed
     */
    @AfterAll
    static void tearDownAll() {
        log.info("🧹 Closing Admin Portal session after all tests...");
    }
}

