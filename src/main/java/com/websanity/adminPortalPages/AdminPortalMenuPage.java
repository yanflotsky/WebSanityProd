package com.websanity.adminPortalPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.websanity.BasePage;
import com.websanity.enums.UserTypes;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AdminPortalMenuPage extends BasePage {

    private final Locator loadingOverlay;
    private final Locator userManagementBtn;
    private final Locator popUpAfterLogin;
    private final Locator closeUserExperiencePopUpBtn;
    private final Locator composeMessageBtn;
    private final Locator inboxBtn;
    private final Locator outboxBtn;
    private final Locator sentItemsBtn;
    private final Locator myContactsBtn;
    private final Locator reportsBtn;
    private final Locator globalContactsBtn;
    private final Locator archiveManagementBtn;
    private final Locator mailingListsBtn;
    private final Locator messageQueryBtn;
    private final Locator whatsAppAPIBtn;

    public AdminPortalMenuPage(Page page) {
        super(page);
        this.userManagementBtn = page.locator("#users");
        this.popUpAfterLogin = page.locator(".aptr-carousel-engagement");
        this.closeUserExperiencePopUpBtn = page.locator("button.close, .aptr-carousel-engagement button[aria-label='Close']");
        this.composeMessageBtn = page.locator("#menu_container");
        this.loadingOverlay = page.locator("#fade");

        // Initialize side menu items
        this.inboxBtn = page.locator("#inbox");
        this.outboxBtn = page.locator("#outbox");
        this.sentItemsBtn = page.locator("#sent_items");
        this.myContactsBtn = page.locator("#personal_contacts");
        this.reportsBtn = page.locator("#reports");
        this.globalContactsBtn = page.locator("#company_contacts");
        this.archiveManagementBtn = page.locator("#archive_management");
        this.mailingListsBtn = page.locator("#mailing_lists");
        this.messageQueryBtn = page.locator("#messages");
        this.whatsAppAPIBtn = page.locator("#wapi_connect");
    }

    /**
     * Check if user management button is visible
     */
    public boolean isUserManagementVisible() {
        try {
            return userManagementBtn.isVisible();
        } catch (Exception e) {
            log.debug("User management button not visible: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Check if popup is displayed
     */
    public boolean isPopUpDisplayed() {
        try {
            return popUpAfterLogin.isVisible();
        } catch (Exception e) {
            log.debug("Popup not visible: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Click close button on user experience popup
     */
    public AdminPortalMenuPage clickCloseUserExperiencePopUpBtn() {
        log.info("Clicking Close button of Experience Pop Up");
        closeUserExperiencePopUpBtn.click();
        return this;
    }

    /**
     * Click on Compose Message button
     */
    public AdminPortalComposeMessagePage clickComposeMessageBtn() {
        log.info("Clicking Compose Message button");
        composeMessageBtn.click();
        waitForLoadingToDisappear();
        return new AdminPortalComposeMessagePage(page);
    }

    /**
     * Wait for loading overlay to disappear
     */
    private void waitForLoadingToDisappear() {
        try {
            // Wait for loading overlay to become hidden (max 10 seconds)
            loadingOverlay.waitFor(new Locator.WaitForOptions()
                    .setState(com.microsoft.playwright.options.WaitForSelectorState.HIDDEN)
                    .setTimeout(10000));
        } catch (Exception e) {
            // If element doesn't appear or already hidden, continue
            log.debug("Loading overlay not found or already hidden");
        }
    }

    /**
     * Click on Inbox menu item
     */
    public AdminPortalMenuPage clickInbox() {
        log.info("Clicking Inbox menu item");
        inboxBtn.click();
        waitForLoadingToDisappear();
        return this;
    }

    /**
     * Click on Outbox menu item
     */
    public AdminPortalMenuPage clickOutbox() {
        log.info("Clicking Outbox menu item");
        outboxBtn.click();
        waitForLoadingToDisappear();
        return this;
    }

    /**
     * Click on Sent Items menu item
     */
    public AdminPortalSentItemsPage clickSentItems() {
        log.info("Clicking Sent Items menu item");
        sentItemsBtn.click();
        waitForLoadingToDisappear();
        return new AdminPortalSentItemsPage(page);
    }

    /**
     * Click on My Contacts menu item
     */
    public AdminPortalMyContactsPage clickMyContacts() {
        log.info("Clicking My Contacts menu item");
        myContactsBtn.click();
        waitForLoadingToDisappear();
        return new AdminPortalMyContactsPage(page);
    }

    /**
     * Click on Reports menu item
     */
    public AdminPortalMenuPage clickReports() {
        log.info("Clicking Reports menu item");
        reportsBtn.click();
        waitForLoadingToDisappear();
        return this;
    }

    /**
     * Click on Global Contacts menu item
     */
    public AdminPortalMenuPage clickGlobalContacts() {
        log.info("Clicking Global Contacts menu item");
        globalContactsBtn.click();
        waitForLoadingToDisappear();
        return this;
    }

    /**
     * Click on User Management menu item
     */
    public AdminPortalUserManagementPage clickUserManagement() {
        log.info("Clicking User Management menu item");
        userManagementBtn.click();
        waitForLoadingToDisappear();
        return new AdminPortalUserManagementPage(page);
    }

    /**
     * Click on Archive Management menu item
     */
    public AdminPortalArchiveManagementPage clickArchiveManagement() {
        log.info("Clicking Archive Management menu item");
        archiveManagementBtn.click();
        waitForLoadingToDisappear();
        page.waitForTimeout(2000);
        return new AdminPortalArchiveManagementPage(page);
    }

    /**
     * Click on Mailing Lists menu item
     */
    public AdminPortalMenuPage clickMailingLists() {
        log.info("Clicking Mailing Lists menu item");
        mailingListsBtn.click();
        waitForLoadingToDisappear();
        return this;
    }

    /**
     * Click on Message Query menu item
     */
    public AdminPortalMenuPage clickMessageQuery() {
        log.info("Clicking Message Query menu item");
        messageQueryBtn.click();
        waitForLoadingToDisappear();
        return this;
    }

    /**
     * Click on WhatsApp API menu item
     */
    public AdminPortalMenuPage clickWhatsAppAPI() {
        log.info("Clicking WhatsApp API menu item");
        whatsAppAPIBtn.click();
        waitForLoadingToDisappear();
        return this;
    }

    /**
     * Wait for user management button to be visible after login
     * Waits for user management button to be visible and verifies we're on the correct page
     */
    public AdminPortalMenuPage waitForUserManagementBtnToAppear() {
        log.info("Waiting for User Management button to be visible..");
        log.info("Current URL: {}", page.url());

        try {
            userManagementBtn.waitFor(new Locator.WaitForOptions().setTimeout(30000));
            log.info("User Management button is visible, continue test");
        } catch (Exception e) {
            log.error("Failed to find User Management button. Current URL: {}", page.url());
            log.error("Page title: {}", page.title());
            throw new RuntimeException("User Management button not found. Possible login failure. URL: " + page.url(), e);
        }

        return this;
    }

    /**
     * Close all pop-ups that appear after login
     * Waits for user management button to be visible and closes any popups that appear
     */
    public AdminPortalMenuPage closePopUpsAfterLogin() {
        log.info("Starting popup close procedure...");

        // Wait for page to load
        page.waitForLoadState();

        log.info("Waiting for User Management button to be visible...");
        userManagementBtn.waitFor(new Locator.WaitForOptions().setTimeout(30000));
        log.info("User Management button is visible, proceeding to close popups");

        if (isUserManagementVisible()) {
            int maxAttempts = 5;
            int closedPopups = 0;

            for (int attempt = 1; attempt <= maxAttempts; attempt++) {
                log.info("Popup check attempt {}/{}", attempt, maxAttempts);

                try {
                    // Wait for popup to appear with timeout
                    popUpAfterLogin.waitFor(new Locator.WaitForOptions()
                            .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                            .setTimeout(4000));

                    if (isPopUpDisplayed()) {
                        log.info("Pop-up #{} appeared. Attempting to close...", closedPopups + 1);
                        clickCloseUserExperiencePopUpBtn();

                        // Wait for popup to disappear
                        try {
                            popUpAfterLogin.waitFor(new Locator.WaitForOptions()
                                    .setState(com.microsoft.playwright.options.WaitForSelectorState.HIDDEN)
                                    .setTimeout(3000));
                            log.info("Pop-up #{} was closed successfully.", ++closedPopups);
                        } catch (Exception e) {
                            log.warn("Pop-up did not disappear after clicking close button");
                        }

                        // Wait a bit before checking for next popup
                        page.waitForTimeout(1000);
                    }
                } catch (Exception e) {
                    log.info("No popup appeared within timeout. Ending popup close procedure.");
                    break;
                }
            }

            log.info("Popup close procedure completed. Total popups closed: {}", closedPopups);
        } else {
            log.warn("User management button not visible, skipping popup close procedure.");
        }

        return this;
    }
}
