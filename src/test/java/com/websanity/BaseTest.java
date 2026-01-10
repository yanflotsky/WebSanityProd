package com.websanity;

import com.microsoft.playwright.*;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInfo;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @AfterEach
    void takeScreenshotOnFailure(TestInfo testInfo) {
        // Check if the test failed by checking the execution exception
        // We capture screenshot for any test that might have issues
        if (testInfo.getTestMethod().isPresent()) {
            String testName = testInfo.getTestMethod().get().getName();
            String className = testInfo.getTestClass()
                    .map(Class::getSimpleName)
                    .orElse("UnknownClass");

            try {
                // Generate timestamp for unique filename
                String timestamp = LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));

                String screenshotName = String.format("%s_%s_%s.png",
                        className, testName, timestamp);

                // Create screenshots directory
                Path screenshotsDir = Paths.get("target/screenshots");
                Files.createDirectories(screenshotsDir);

                Path screenshotPath = screenshotsDir.resolve(screenshotName);

                // Take screenshot (works in headless mode too!)
                byte[] screenshot = page.screenshot(new Page.ScreenshotOptions()
                        .setPath(screenshotPath)
                        .setFullPage(true));

                System.out.println("📸 Screenshot saved: " + screenshotPath);

                // Attach to Allure report
                Allure.addAttachment("Screenshot: " + testName, "image/png",
                        new ByteArrayInputStream(screenshot), "png");

            } catch (Exception e) {
                System.err.println("⚠️ Failed to take screenshot: " + e.getMessage());
            }
        }
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

