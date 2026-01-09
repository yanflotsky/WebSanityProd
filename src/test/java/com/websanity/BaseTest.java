package com.websanity;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseTest {
    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext context;
    protected static Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();

        // Check if running in Docker mode (isLocalRun=false means Docker)
        String isLocalRun = System.getProperty("isLocalRun", "true");
        boolean headless = isLocalRun.equals("false"); // Docker = headless, Local = headed

        System.out.println("Browser headless mode: " + headless + " (isLocalRun=" + isLocalRun + ")");

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(headless)
                .setSlowMo(headless ? 0 : 50); // No slowMo in Docker

        // Add extra args for Docker stability (prevent SIGSEGV crashes)
        if (headless) {
            launchOptions.setArgs(java.util.Arrays.asList(
                    "--disable-dev-shm-usage",      // Overcome limited resource problems
                    "--disable-gpu",                 // Disable GPU hardware acceleration
                    "--no-sandbox",                  // Required for Docker
                    "--disable-software-rasterizer", // Prevent software rendering crashes
                    "--disable-features=VizDisplayCompositor" // Stability fix
            ));
            // Use regular chromium channel (not headless_shell which is unstable)
            launchOptions.setChannel("chromium");
        }

        browser = playwright.chromium().launch(launchOptions);

        // Create context and page once for all tests
        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(1920, 1080));
        page = context.newPage();
    }

    @AfterAll
    static void closeBrowser() {
        if (context != null) {
            context.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}

