package com.websanity.adminPortalPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.websanity.BasePage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessengerAppSettingsPage extends BasePage {

    private final Locator loadingOverlay;
    private final Locator saveBtn;
    private final Locator successMsg;

    // Checkboxes
    private final Locator disableScreenCaptureCheckbox;
    private final Locator disableCopyCheckbox;
    private final Locator disableShareMediaCheckbox;
    private final Locator disableNotificationPreviewCheckbox;
    private final Locator disableAttachmentsCheckbox;
    private final Locator disableLocalContactsCheckbox;
    private final Locator disableNativePhoneDialerCheckbox;
    private final Locator disableBackUpAndRestoreCheckbox;
    private final Locator disableDistributionOfNewVersions;

    public MessengerAppSettingsPage(Page page) {
        super(page);
        this.loadingOverlay = page.locator("#fade");
        this.saveBtn = page.locator("#updateBtn");
        this.successMsg = page.locator("#successMsg");
        this.disableScreenCaptureCheckbox = page.locator("#disableScreenCapture_visible");
        this.disableCopyCheckbox = page.locator("#disableCopy_visible");
        this.disableShareMediaCheckbox = page.locator("#disableShare_Media_visible");
        this.disableNotificationPreviewCheckbox = page.locator("#disable_Notification_Preview_visible");
        this.disableAttachmentsCheckbox = page.locator("#disable_Attachments_visible");
        this.disableLocalContactsCheckbox = page.locator("#disable_Local_Contacts_visible");
        this.disableNativePhoneDialerCheckbox = page.locator("#disable_Native_Phone_Dialer_visible");
        this.disableBackUpAndRestoreCheckbox = page.locator("#disable_Back_Up_And_Restore_visible");
        this.disableDistributionOfNewVersions = page.locator("#disable_Auto_Upgrade_Popup_visible");
    }

    /**
     * Click on Disable Screen Capture checkbox
     */
    public MessengerAppSettingsPage clickDisableScreenCapture() {
        log.info("Clicking Disable Screen Capture checkbox");
        disableScreenCaptureCheckbox.click();
        return this;
    }

    /**
     * Click on Disable Copy checkbox
     */
    public MessengerAppSettingsPage clickDisableCopy() {
        log.info("Clicking Disable Copy checkbox");
        disableCopyCheckbox.click();
        return this;
    }

    /**
     * Click on Disable Share Media checkbox
     */
    public MessengerAppSettingsPage clickDisableShareMedia() {
        log.info("Clicking Disable Share Media checkbox");
        disableShareMediaCheckbox.click();
        return this;
    }

    /**
     * Click on Disable Notification's Preview checkbox
     */
    public MessengerAppSettingsPage clickDisableNotificationPreview() {
        log.info("Clicking Disable Notification's Preview checkbox");
        disableNotificationPreviewCheckbox.click();
        return this;
    }

    /**
     * Click on Disable Attachments checkbox
     */
    public MessengerAppSettingsPage clickDisableAttachments() {
        log.info("Clicking Disable Attachments checkbox");
        disableAttachmentsCheckbox.click();
        return this;
    }

    /**
     * Click on Disable Local Contacts checkbox
     */
    public MessengerAppSettingsPage clickDisableLocalContacts() {
        log.info("Clicking Disable Local Contacts checkbox");
        disableLocalContactsCheckbox.click();
        return this;
    }

    /**
     * Click on Disable Native Phone Dialer checkbox
     */
    public MessengerAppSettingsPage clickDisableNativePhoneDialer() {
        log.info("Clicking Disable Native Phone Dialer checkbox");
        disableNativePhoneDialerCheckbox.click();
        return this;
    }

    /**
     * Click on Disable Back Up and Restore checkbox
     */
    public MessengerAppSettingsPage clickDisableBackUpAndRestore() {
        log.info("Clicking Disable Back Up and Restore checkbox");
        disableBackUpAndRestoreCheckbox.click();
        return this;
    }

    /**
     * Click on Disable Distribution of New Versions checkbox
     */
    public MessengerAppSettingsPage clickDisableDistributionOfNewVersions() {
        log.info("Clicking Disable Distribution of New Versions checkbox");
        disableDistributionOfNewVersions.click();
        return this;
    }

    /**
     * Check if Disable Screen Capture checkbox is checked
     */
    public boolean isDisableScreenCaptureChecked() {
        return disableScreenCaptureCheckbox.isChecked();
    }

    /**
     * Check if Disable Copy checkbox is checked
     */
    public boolean isDisableCopyChecked() {
        return disableCopyCheckbox.isChecked();
    }

    /**
     * Check if Disable Share Media checkbox is checked
     */
    public boolean isDisableShareMediaChecked() {
        return disableShareMediaCheckbox.isChecked();
    }

    /**
     * Check if Disable Notification's Preview checkbox is checked
     */
    public boolean isDisableNotificationPreviewChecked() {
        return disableNotificationPreviewCheckbox.isChecked();
    }

    /**
     * Check if Disable Attachments checkbox is checked
     */
    public boolean isDisableAttachmentsChecked() {
        return disableAttachmentsCheckbox.isChecked();
    }

    /**
     * Check if Disable Local Contacts checkbox is checked
     */
    public boolean isDisableLocalContactsChecked() {
        return disableLocalContactsCheckbox.isChecked();
    }

    /**
     * Check if Disable Native Phone Dialer checkbox is checked
     */
    public boolean isDisableNativePhoneDialerChecked() {
        return disableNativePhoneDialerCheckbox.isChecked();
    }

    /**
     * Check if Disable Back Up and Restore checkbox is checked
     */
    public boolean isDisableBackUpAndRestoreChecked() {
        return disableBackUpAndRestoreCheckbox.isChecked();
    }

    /**
     * Check if Disable Distribution of New Versions checkbox is checked
     */
    public boolean isDisableAutoUpgradePopupChecked() {
        return disableDistributionOfNewVersions.isChecked();
    }

    /**
     * Check if Disable Distribution of New Versions checkbox is checked
     */
    public boolean isDisableDistributionOfNewVersionsChecked() {
        return disableDistributionOfNewVersions.isChecked();
    }

    /**
     * Disable all checkboxes if they are enabled and save settings
     */
    public MessengerAppSettingsPage disableAllCheckboxes() {
        log.info("Disabling all checkboxes if they are enabled");
        boolean anyCheckboxWasDisabled = false;

        if (isDisableScreenCaptureChecked()) {
            log.info("Disable Screen Capture is checked, unchecking it");
            clickDisableScreenCapture();
            anyCheckboxWasDisabled = true;
        }

        if (isDisableCopyChecked()) {
            log.info("Disable Copy is checked, unchecking it");
            clickDisableCopy();
            anyCheckboxWasDisabled = true;
        }

        if (isDisableShareMediaChecked()) {
            log.info("Disable Share Media is checked, unchecking it");
            clickDisableShareMedia();
            anyCheckboxWasDisabled = true;
        }

        if (isDisableNotificationPreviewChecked()) {
            log.info("Disable Notification's Preview is checked, unchecking it");
            clickDisableNotificationPreview();
            anyCheckboxWasDisabled = true;
        }

        if (isDisableAttachmentsChecked()) {
            log.info("Disable Attachments is checked, unchecking it");
            clickDisableAttachments();
            anyCheckboxWasDisabled = true;
        }

        if (isDisableLocalContactsChecked()) {
            log.info("Disable Local Contacts is checked, unchecking it");
            clickDisableLocalContacts();
            anyCheckboxWasDisabled = true;
        }

        if (isDisableNativePhoneDialerChecked()) {
            log.info("Disable Native Phone Dialer is checked, unchecking it");
            clickDisableNativePhoneDialer();
            anyCheckboxWasDisabled = true;
        }

        if (isDisableBackUpAndRestoreChecked()) {
            log.info("Disable Back Up and Restore is checked, unchecking it");
            clickDisableBackUpAndRestore();
            anyCheckboxWasDisabled = true;
        }

        if (isDisableDistributionOfNewVersionsChecked()) {
            log.info("Disable Distribution of New Versions is checked, unchecking it");
            clickDisableDistributionOfNewVersions();
            anyCheckboxWasDisabled = true;
        }

        if (anyCheckboxWasDisabled) {
            log.info("At least one checkbox was disabled, now saving changes");
            clickSave();
            page.waitForTimeout(5000);
        } else {
            log.info("No checkboxes were enabled");
        }

        return this;
    }

    /**
     * Click on Save button
     */
    public MessengerAppSettingsPage clickSave() {
        log.info("Clicking Save button");
        page.waitForTimeout(3000);
        saveBtn.click();
        page.waitForTimeout(1500);
        return this;
    }

    /**
     * Check if success message is visible
     */
    public boolean isSuccessMessageVisible() {
        return successMsg.isVisible();
    }

    /**
     * Wait for success message to be visible
     */
    public MessengerAppSettingsPage waitForSuccessMessage() {
        log.info("Waiting for success message to appear");
        successMsg.waitFor(new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE).setTimeout(10000));
        page.waitForTimeout(500); // Additional wait to ensure settings are persisted
        return this;
    }

    /**
     * Get success message text
     */
    public String getSuccessMessageText() {
        log.info("Getting success message text");
        return successMsg.textContent().trim();
    }

}
