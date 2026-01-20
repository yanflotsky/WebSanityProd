package com.websanity.teleadminPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.websanity.BasePage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AdminsPortalSettingsPage extends BasePage {

    private final Locator saveDownloadSettingsBtn;
    private final Locator saveAdminPortalSettingsBtn;
    private final Locator adminPortalAlertSuccess;
    // Checkboxes
    private final Locator allItemsCheckbox;
    private final Locator composeCheckbox;
    private final Locator outboxCheckbox;
    private final Locator sentItemsCheckbox;
    private final Locator inboxCheckbox;
    private final Locator allSettingsCheckbox;
    private final Locator messengerAppSettingsCheckbox;
    private final Locator advancedSettingsCheckbox;
    private final Locator messageSettingsCheckbox;
    private final Locator archiveManagementCheckbox;
    private final Locator displayMyContactsCheckbox;
    private final Locator displayGlobalContactsCheckbox;
    private final Locator displayMessageQueryCheckbox;

    public AdminsPortalSettingsPage(Page page) {
        super(page);
        this.saveDownloadSettingsBtn = page.locator("#saveDownloadSettingsBtn");
        this.saveAdminPortalSettingsBtn = page.locator("#saveAdminPortalSettingsBtn");
        this.adminPortalAlertSuccess = page.locator("#adminPortalAlertSuccess");
        // Initialize checkboxes
        this.allItemsCheckbox = page.locator("#allItemsCheckbox");
        this.composeCheckbox = page.locator("#composeCheckbox");
        this.outboxCheckbox = page.locator("#outboxCheckbox");
        this.sentItemsCheckbox = page.locator("#sentItemsCheckbox");
        this.inboxCheckbox = page.locator("#inboxCheckbox");
        this.allSettingsCheckbox = page.locator("#allSettingsCheckbox");
        this.messengerAppSettingsCheckbox = page.locator("#messengerAppSettingsCheckbox");
        this.advancedSettingsCheckbox = page.locator("#advancedSettingsCheckbox");
        this.messageSettingsCheckbox = page.locator("#messageSettingsCheckbox");
        this.archiveManagementCheckbox = page.locator("#archiveManagementCheckbox");
        this.displayMyContactsCheckbox = page.locator("#displayMyContactsCheckbox");
        this.displayGlobalContactsCheckbox = page.locator("#displayGlobalContactsCheckbox");
        this.displayMessageQueryCheckbox = page.locator("#displayMessageQueryCheckbox");
    }

    // ========== All Items Checkbox ==========

    /**
     * Click on All Items checkbox
     */
    public AdminsPortalSettingsPage clickAllItemsCheckbox() {
        log.info("Clicking All Items checkbox");
        allItemsCheckbox.click();
        return this;
    }

    /**
     * Check if All Items checkbox is selected
     * @return true if checked
     */
    public boolean isAllItemsCheckboxSelected() {
        return allItemsCheckbox.isChecked();
    }

    // ========== Compose Checkbox ==========

    /**
     * Click on Compose checkbox
     */
    public AdminsPortalSettingsPage clickComposeCheckbox() {
        log.info("Clicking Compose checkbox");
        composeCheckbox.click();
        return this;
    }

    /**
     * Check if Compose checkbox is selected
     * @return true if checked
     */
    public boolean isComposeCheckboxSelected() {
        return composeCheckbox.isChecked();
    }

    // ========== Outbox Checkbox ==========

    /**
     * Click on Outbox checkbox
     */
    public AdminsPortalSettingsPage clickOutboxCheckbox() {
        log.info("Clicking Outbox checkbox");
        outboxCheckbox.click();
        return this;
    }

    /**
     * Check if Outbox checkbox is selected
     * @return true if checked
     */
    public boolean isOutboxCheckboxSelected() {
        return outboxCheckbox.isChecked();
    }

    // ========== Sent Items Checkbox ==========

    /**
     * Click on Sent Items checkbox
     */
    public AdminsPortalSettingsPage clickSentItemsCheckbox() {
        log.info("Clicking Sent Items checkbox");
        sentItemsCheckbox.click();
        return this;
    }

    /**
     * Check if Sent Items checkbox is selected
     * @return true if checked
     */
    public boolean isSentItemsCheckboxSelected() {
        return sentItemsCheckbox.isChecked();
    }

    // ========== Inbox Checkbox ==========

    /**
     * Click on Inbox checkbox
     */
    public AdminsPortalSettingsPage clickInboxCheckbox() {
        log.info("Clicking Inbox checkbox");
        inboxCheckbox.click();
        return this;
    }

    /**
     * Check if Inbox checkbox is selected
     * @return true if checked
     */
    public boolean isInboxCheckboxSelected() {
        return inboxCheckbox.isChecked();
    }

    // ========== All Settings Checkbox ==========

    /**
     * Click on All Settings checkbox
     */
    public AdminsPortalSettingsPage clickAllSettingsCheckbox() {
        log.info("Clicking All Settings checkbox");
        allSettingsCheckbox.click();
        return this;
    }

    /**
     * Check if All Settings checkbox is selected
     * @return true if checked
     */
    public boolean isAllSettingsCheckboxSelected() {
        return allSettingsCheckbox.isChecked();
    }

    // ========== Messenger App Settings Checkbox ==========

    /**
     * Click on Messenger App Settings checkbox
     */
    public AdminsPortalSettingsPage clickMessengerAppSettingsCheckbox() {
        log.info("Clicking Messenger App Settings checkbox");
        messengerAppSettingsCheckbox.click();
        return this;
    }

    /**
     * Check if Messenger App Settings checkbox is selected
     * @return true if checked
     */
    public boolean isMessengerAppSettingsCheckboxSelected() {
        return messengerAppSettingsCheckbox.isChecked();
    }

    // ========== Advanced Settings Checkbox ==========

    /**
     * Click on Advanced Settings checkbox
     */
    public AdminsPortalSettingsPage clickAdvancedSettingsCheckbox() {
        log.info("Clicking Advanced Settings checkbox");
        advancedSettingsCheckbox.click();
        return this;
    }

    /**
     * Check if Advanced Settings checkbox is selected
     * @return true if checked
     */
    public boolean isAdvancedSettingsCheckboxSelected() {
        return advancedSettingsCheckbox.isChecked();
    }

    // ========== Message Settings Checkbox ==========

    /**
     * Click on Message Settings checkbox
     */
    public AdminsPortalSettingsPage clickMessageSettingsCheckbox() {
        log.info("Clicking Message Settings checkbox");
        messageSettingsCheckbox.click();
        return this;
    }

    /**
     * Check if Message Settings checkbox is selected
     * @return true if checked
     */
    public boolean isMessageSettingsCheckboxSelected() {
        return messageSettingsCheckbox.isChecked();
    }

    // ========== Archive Management Checkbox ==========

    /**
     * Click on Archive Management checkbox
     */
    public AdminsPortalSettingsPage clickArchiveManagementCheckbox() {
        log.info("Clicking Archive Management checkbox");
        archiveManagementCheckbox.click();
        return this;
    }

    /**
     * Check if Archive Management checkbox is selected
     * @return true if checked
     */
    public boolean isArchiveManagementCheckboxSelected() {
        return archiveManagementCheckbox.isChecked();
    }

    // ========== Display My Contacts Checkbox ==========

    /**
     * Click on Display My Contacts checkbox
     */
    public AdminsPortalSettingsPage clickDisplayMyContactsCheckbox() {
        log.info("Clicking Display My Contacts checkbox");
        displayMyContactsCheckbox.click();
        return this;
    }

    /**
     * Check if Display My Contacts checkbox is selected
     * @return true if checked
     */
    public boolean isDisplayMyContactsCheckboxSelected() {
        return displayMyContactsCheckbox.isChecked();
    }

    // ========== Display Global Contacts Checkbox ==========

    /**
     * Click on Display Global Contacts checkbox
     */
    public AdminsPortalSettingsPage clickDisplayGlobalContactsCheckbox() {
        log.info("Clicking Display Global Contacts checkbox");
        displayGlobalContactsCheckbox.click();
        return this;
    }

    /**
     * Check if Display Global Contacts checkbox is selected
     * @return true if checked
     */
    public boolean isDisplayGlobalContactsCheckboxSelected() {
        return displayGlobalContactsCheckbox.isChecked();
    }

    // ========== Display Message Query Checkbox ==========

    /**
     * Click on Display Message Query checkbox
     */
    public AdminsPortalSettingsPage clickDisplayMessageQueryCheckbox() {
        log.info("Clicking Display Message Query checkbox");
        displayMessageQueryCheckbox.click();
        return this;
    }

    /**
     * Check if Display Message Query checkbox is selected
     * @return true if checked
     */
    public boolean isDisplayMessageQueryCheckboxSelected() {
        return displayMessageQueryCheckbox.isChecked();
    }

    // ========== Save Admin Portal Settings Button ==========

    /**
     * Click on Save Admin Portal Settings button
     */
    public AdminsPortalSettingsPage clickSaveAdminPortalSettingsBtn() {
        log.info("Clicking Save Admin Portal Settings button");
        saveAdminPortalSettingsBtn.click();
        page.waitForTimeout(2000);
        return this;
    }

    // ========== Admin Portal Alert Success ==========

    /**
     * Check if admin portal success message is visible
     * @return true if visible
     */
    public boolean isAdminPortalAlertSuccessVisible() {
        try {
            return adminPortalAlertSuccess.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get admin portal success message text
     * @return success message text
     */
    public String getAdminPortalAlertSuccessText() {
        return adminPortalAlertSuccess.textContent().trim();
    }

}
