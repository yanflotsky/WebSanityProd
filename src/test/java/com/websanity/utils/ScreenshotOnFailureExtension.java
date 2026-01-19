package com.websanity.utils;

import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * JUnit 5 Extension to capture screenshots only on test failure
 * Located in test/java because it depends on JUnit test dependencies
 */
public class ScreenshotOnFailureExtension implements TestWatcher {

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        // Get Page from test instance
        Object testInstance = context.getTestInstance().orElse(null);
        if (testInstance == null) return;

        try {
            // Use reflection to get the 'page' field from BaseTest
            // Need to traverse up the class hierarchy to find the field
            Page page = null;
            Class<?> currentClass = testInstance.getClass();

            while (currentClass != null && page == null) {
                try {
                    java.lang.reflect.Field pageField = currentClass.getDeclaredField("page");
                    pageField.setAccessible(true);
                    page = (Page) pageField.get(testInstance);
                    break;
                } catch (NoSuchFieldException e) {
                    // Field not found in this class, try parent
                    currentClass = currentClass.getSuperclass();
                }
            }

            if (page != null && !page.isClosed()) {
                String testName = context.getTestMethod()
                        .map(java.lang.reflect.Method::getName)
                        .orElse("unknown");

                String className = context.getTestClass()
                        .map(Class::getSimpleName)
                        .orElse("UnknownClass");

                String timestamp = LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));

                String screenshotName = String.format("%s_%s_%s.png",
                        className, testName, timestamp);

                // Create screenshots directory
                Path screenshotsDir = Paths.get("target/screenshots");
                Files.createDirectories(screenshotsDir);

                Path screenshotPath = screenshotsDir.resolve(screenshotName);

                // Take screenshot
                byte[] screenshot = page.screenshot(new Page.ScreenshotOptions()
                        .setPath(screenshotPath)
                        .setFullPage(true));

                System.out.println("📸 Screenshot saved (test failed): " + screenshotPath);

                // Attach to Allure report
                Allure.addAttachment("Screenshot: " + testName + " (FAILED)", "image/png",
                        new ByteArrayInputStream(screenshot), "png");
            } else {
                System.err.println("⚠️ Page is null or closed, cannot take screenshot");
            }
        } catch (Exception e) {
            System.err.println("⚠️ Failed to take screenshot: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        // Do nothing on success - no screenshot needed
        String testName = context.getDisplayName();
        System.out.println("✅ Test passed (no screenshot): " + testName);
    }
}

