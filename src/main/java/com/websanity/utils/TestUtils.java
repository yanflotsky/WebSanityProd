package com.websanity.utils;

import com.microsoft.playwright.Page;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

/**
 * Utility class for common test operations
 */
public class TestUtils {

    private static final DateTimeFormatter TIMESTAMP_FORMAT =
        DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    /**
     * Take a screenshot with automatic timestamp
     */
    public static void takeScreenshot(Page page, String name) {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
        String filename = String.format("target/screenshots/%s_%s.png", name, timestamp);
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get(filename))
                .setFullPage(true));
        System.out.println("Screenshot saved: " + filename);
    }

    /**
     * Wait for a specific amount of time (use sparingly)
     */
    public static void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Generate a unique test identifier
     */
    public static String generateTestId() {
        return "test_" + System.currentTimeMillis();
    }

    /**
     * Check if element exists without throwing exception
     */
    public static boolean elementExists(Page page, String selector) {
        return page.locator(selector).count() > 0;
    }

    /**
     * Wait for page to be fully loaded
     */
    public static void waitForPageLoad(Page page) {
        page.waitForLoadState(com.microsoft.playwright.options.LoadState.NETWORKIDLE);
    }

    /**
     * Decode Base64 encoded string
     */
    public static String decodeBase64(String encoded) {
        byte[] decodedBytes = Base64.getDecoder().decode(encoded);
        return new String(decodedBytes);
    }
}

