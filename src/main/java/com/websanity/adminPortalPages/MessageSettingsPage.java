package com.websanity.adminPortalPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.websanity.BasePage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageSettingsPage extends BasePage {

    private final Locator loadingOverlay;
    private final Locator successMsg;

    // Flow ID radio buttons
    private final Locator smsTextMessagesOnlyRadio;
    private final Locator ipPushNotificationsRadio;
    private final Locator smsFallbackRadio;
    private final Locator registeredUsersIpOthersSmsRadio;
    private final Locator ipFallbackDelayInput;

    // Save button
    private final Locator saveBtn;

    public MessageSettingsPage(Page page) {
        super(page);
        this.loadingOverlay = page.locator("#fade");
        this.successMsg = page.locator("#successMsg");

        // Initialize flow ID radio buttons
        this.smsTextMessagesOnlyRadio = page.locator("#flowId1");
        this.ipPushNotificationsRadio = page.locator("#flowId2");
        this.smsFallbackRadio = page.locator("#flowId3");
        this.registeredUsersIpOthersSmsRadio = page.locator("#flowId4");
        this.ipFallbackDelayInput = page.locator("#ipFallbackDelay");

        // Initialize Save button
        this.saveBtn = page.locator("#updateBtn");
    }

    // Click methods for radio buttons
    public MessageSettingsPage clickSmsTextMessagesOnlyRadio() {
        log.info("Clicking on 'SMS Text Messages only' radio button");
        smsTextMessagesOnlyRadio.click();
        return this;
    }

    public MessageSettingsPage clickIpPushNotificationsRadio() {
        log.info("Clicking on 'IP Push Notifications' radio button");
        ipPushNotificationsRadio.click();
        return this;
    }

    public MessageSettingsPage clickSmsFallbackRadio() {
        log.info("Clicking on 'SMS Fallback' radio button");
        smsFallbackRadio.click();
        return this;
    }

    public MessageSettingsPage clickRegisteredUsersIpOthersSmsRadio() {
        log.info("Clicking on 'Registered users IP, others SMS' radio button");
        registeredUsersIpOthersSmsRadio.click();
        return this;
    }

    // Method to set IP fallback delay
    public MessageSettingsPage setIpFallbackDelay(String delay) {
        log.info("Setting IP fallback delay to: {} minutes", delay);
        ipFallbackDelayInput.fill(delay);
        return this;
    }

    // Check methods for radio buttons
    public boolean isSmsTextMessagesOnlySelected() {
        return smsTextMessagesOnlyRadio.isChecked();
    }

    public boolean isIpPushNotificationsSelected() {
        return ipPushNotificationsRadio.isChecked();
    }

    public boolean isSmsFallbackSelected() {
        return smsFallbackRadio.isChecked();
    }

    public boolean isRegisteredUsersIpOthersSmsSelected() {
        return registeredUsersIpOthersSmsRadio.isChecked();
    }

    public String getIpFallbackDelay() {
        return ipFallbackDelayInput.inputValue();
    }

    // Click Save button
    public MessageSettingsPage clickSaveBtn() {
        log.info("Clicking Save button");
        saveBtn.click();
        return this;
    }

    // Check if success message is visible
    public boolean isSuccessMsgVisible() {
        log.info("Checking if success message is visible");
        boolean isVisible = successMsg.isVisible();
        log.info("Success message visible: {}", isVisible);
        return isVisible;
    }

    // Get success message text
    public String getSuccessMsgText() {
        log.info("Getting success message text");
        String text = successMsg.textContent();
        log.info("Success message text: {}", text);
        return text;
    }

    // Wait until success message is visible
    public MessageSettingsPage waitForSuccessMsgVisible() {
        log.info("Waiting for success message to be visible");
        successMsg.waitFor(new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        log.info("Success message is now visible");
        return this;
    }

    // Wait until success message is not visible (hidden)
    public MessageSettingsPage waitForSuccessMsgNotVisible() {
        log.info("Waiting for success message to be hidden");
        successMsg.waitFor(new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.HIDDEN)
                .setTimeout(20000));
        log.info("Success message is now hidden");
        return this;
    }

}
