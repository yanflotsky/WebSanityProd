package com.websanity.adminPortalPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.websanity.BasePage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AdminPortalComposeMessagePage extends BasePage {

    private final Locator toInput;
    private final Locator messageInput;
    private final Locator sendBtn;
    private final Locator assistContainer;
    private final Locator assistContainerDiv;
    private final Locator successMsg;
    private final Locator loadingOverlay;
    private final Locator moreBtn;
    private final Locator moreAddSubject;
    private final Locator subjectInput;

    public AdminPortalComposeMessagePage(Page page) {
        super(page);
        this.toInput = page.locator("#to");
        this.loadingOverlay = page.locator("#fade");
        this.messageInput = page.locator("#message");
        this.sendBtn = page.locator("#sendBtn");
        this.successMsg = page.locator("#successMsg");
        this.assistContainer = page.locator("#assistContainer");
        this.moreBtn = page.locator("li[class='moreSubmenu']");
        this.moreAddSubject = page.locator("#moreLabelUpMenuSubject");
        this.subjectInput = page.locator("#subject");
        this.assistContainerDiv = page.locator("#assistContainer div.popuplist");
    }

    /**
     * Fill in the To input field
     * @param recipient The recipient email or username
     * @return AdminPortalComposeMessagePage
     */
    public AdminPortalComposeMessagePage fillToInput(String recipient) {
        log.info("Filling To input: {}", recipient);
        toInput.fill(recipient);

        log.info("Waiting for assist container to appear");
        assistContainer.waitFor(new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE));

        log.info("Clicking on assist container div");
        assistContainerDiv.first().click();

        return this;
    }

    /**
     * Click on assist container div (autocomplete suggestion)
     * @return AdminPortalComposeMessagePage
     */
    public AdminPortalComposeMessagePage clickAssistContainerDiv() {
        log.info("Clicking on assist container div");
        assistContainerDiv.first().click();
        return this;
    }

    /**
     * Fill in the Message input field
     * @param message The message text
     * @return AdminPortalComposeMessagePage
     */
    public AdminPortalComposeMessagePage fillMessageInput(String message) {
        log.info("Filling Message input: {}", message);
        messageInput.fill(message);
        return this;
    }

    /**
     * Fill in the Subject input field
     * @param subject The subject text
     * @return AdminPortalComposeMessagePage
     */
    public AdminPortalComposeMessagePage fillSubjectInput(String subject) {
        log.info("Filling Subject input: {}", subject);
        subjectInput.fill(subject);
        return this;
    }

    /**
     * Wait for Subject input to be visible
     * @return AdminPortalComposeMessagePage
     */
    public AdminPortalComposeMessagePage waitForSubjectInputToBeVisible() {
        log.info("Waiting for Subject input to be visible");
        subjectInput.waitFor(new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE));
        return this;
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
     * Click on Send button
     * @return AdminPortalComposeMessagePage
     */
    public AdminPortalComposeMessagePage clickSendBtn() {
        log.info("Clicking Send button");
        sendBtn.click();
        waitForLoadingToDisappear();
        return this;
    }

    /**
     * Click on More button
     * @return AdminPortalComposeMessagePage
     */
    public AdminPortalComposeMessagePage clickMoreBtn() {
        log.info("Clicking More button");
        moreBtn.click();
        waitForLoadingToDisappear();
        return this;
    }

    /**
     * Click on More Label Up Menu Subject
     * @return AdminPortalComposeMessagePage
     */
    public AdminPortalComposeMessagePage clickAddSubject() {
        log.info("Clicking More Label Up Menu Subject");
        moreAddSubject.click();
        return this;
    }

    /**
     * Get success message text
     * @return Success message text or empty string if not found
     */
    public String getSuccessMsgText() {
        try {
            String text = successMsg.textContent().trim();
            log.info("Success message text: {}", text);
            return text;
        } catch (Exception e) {
            log.error("Failed to get success message text: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Check if success message is visible
     * @return true if success message is visible, false otherwise
     */
    public boolean isSuccessMsgVisible() {
        try {
            boolean isVisible = successMsg.isVisible();
            log.info("Success message is visible: {}", isVisible);
            return isVisible;
        } catch (Exception e) {
            log.debug("Success message not visible: {}", e.getMessage());
            return false;
        }
    }

}
