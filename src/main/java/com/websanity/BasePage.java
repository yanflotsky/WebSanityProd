package com.websanity;

import com.microsoft.playwright.Page;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * Base Page Object class with common page methods
 */
@Slf4j
public abstract class BasePage {
    protected Page page;
    private static final Map<Page, Consumer<com.microsoft.playwright.Dialog>> dialogHandlers = new ConcurrentHashMap<>();

    public BasePage(Page page) {
        this.page = page;
    }

    public void navigate(String url) {
        page.navigate(url);
    }

    public String getTitle() {
        return page.title();
    }

    public String getUrl() {
        return page.url();
    }

    public void refreshPage() {
        log.info("Refreshing page");
        page.reload();
        page.waitForTimeout(2000);
    }

    public void waitForPageLoad() {
        page.waitForLoadState();
    }

    public void takeScreenshot(String path) {
        page.screenshot(new Page.ScreenshotOptions().setPath(java.nio.file.Paths.get(path)));
    }

    /**
     * Sets up alert handler that will automatically handle any alerts that appear
     * This should be called BEFORE an action that might trigger alerts
     * The handler will:
     * - Accept simple alerts/confirms
     * - Enter text and accept prompt dialogs
     * - Do nothing if no alerts appear
     *
     * @param reason The reason text to enter if prompt appears
     */
    public void setupAlertHandler(String reason) {
        // Get existing handler for this page
        Consumer<com.microsoft.playwright.Dialog> existingHandler = dialogHandlers.get(page);

        // Remove old handler if exists
        if (existingHandler != null) {
            page.offDialog(existingHandler);
        }

        // Create new handler
        Consumer<com.microsoft.playwright.Dialog> newHandler = dialog -> {
            String dialogType = dialog.type();
            String message = dialog.message();

            log.info("Alert detected - Type: {}, Message: '{}'", dialogType, message);

            if (dialogType.equals("prompt")) {
                log.info("Entering reason: '{}' and accepting prompt", reason);
                dialog.accept(reason);
            } else {
                log.info("Accepting alert");
                dialog.accept();
            }
        };

        // Store new handler for this page
        dialogHandlers.put(page, newHandler);

        // Add new handler
        page.onDialog(newHandler);
    }

    /**
     * Sets up alert handler with default reason "autoqa"
     */
    public void setupAlertHandler() {
        setupAlertHandler("autoqa");
    }

    /**
     * Clean up dialog handler for this page
     * Should be called when page is closed
     */
    public static void cleanupDialogHandler(Page page) {
        Consumer<com.microsoft.playwright.Dialog> handler = dialogHandlers.remove(page);
        if (handler != null) {
            page.offDialog(handler);
        }
    }
}

