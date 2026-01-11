package com.websanity.adminPortalPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.websanity.BasePage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AdminPortalSentItemsPage extends BasePage {

    private final Locator messagesList;
    private final Locator firstMessageRow;
    private final Locator deleteRecycleBinBtn;
    private final Locator loadingOverlay;
    private final Locator confirmYesBtn;
    private final Locator messageStatusBtn;

    // Message details table
    private final Locator messageVisibleTable;
    private final Locator dateInfo;
    private final Locator fromInfo;
    private final Locator toInfo;
    private final Locator subjectInfo;
    private final Locator messageInfo;

    // Recipient status table
    private final Locator recipientTable;
    private final Locator recipientNumber;
    private final Locator recipientStatusImg;

    public AdminPortalSentItemsPage(Page page) {
        super(page);
        this.messagesList = page.locator("#messagesList");
        this.confirmYesBtn = page.locator("#confirmYesBtn");
        this.loadingOverlay = page.locator("#fade");
        this.deleteRecycleBinBtn = page.locator("li[class='deleteSubmenu']");
        this.messageStatusBtn = page.locator("#statusBtn");
        // First row after header (tbody tr with class rowEntryFolder)
        this.firstMessageRow = messagesList.locator("tbody tr.rowEntryFolder").first();

        // Message details table elements
        this.messageVisibleTable = page.locator("#message_visible");
        this.dateInfo = page.locator("#dateInfo");
        this.fromInfo = page.locator("#fromInfo");
        this.toInfo = page.locator("#toInfo");
        this.subjectInfo = page.locator("#subjectInfo");
        this.messageInfo = messageVisibleTable.locator("tr").nth(4).locator("td").nth(1);

        // Recipient status table elements
        this.recipientTable = page.locator("#recipientTable");
        this.recipientNumber = recipientTable.locator("tbody tr.tableRow").nth(1).locator("td.messageStatusText");
        this.recipientStatusImg = recipientTable.locator("tbody tr.tableRow").nth(1).locator("td.deviceStatus img");
    }

    /**
     * Get text from specific cell in the first message row
     * @param cellIndex 0-based index of the cell (0=checkbox, 1=type, 2=attachment, 3=status, 4=to, 5=message, 6=replies, 7=date)
     * @return Text content of the cell
     */
    public String getFirstMessageCellText(int cellIndex) {
        log.info("Getting text from cell {} in first message row", cellIndex);
        return firstMessageRow.locator("td").nth(cellIndex).textContent().trim();
    }

    /**
     * Get recipient from the first message row (column 4)
     * @return Recipient text
     */
    public String getFirstMessageRecipient() {
        log.info("Getting recipient from first message row");
        return getFirstMessageCellText(4);
    }

    /**
     * Get message text from the first message row (column 5)
     * @return Message text
     */
    public String getFirstMessageText() {
        log.info("Getting message text from first message row");
        return getFirstMessageCellText(5);
    }

    /**
     * Get sent date from the first message row (column 7)
     * @return Sent date text
     */
    public String getFirstMessageDate() {
        log.info("Getting sent date from first message row");
        return getFirstMessageCellText(7);
    }

    /**
     * Get message type from the first message row (column 1)
     * @return Message type alt text (e.g., "Text")
     */
    public String getFirstMessageType() {
        log.info("Getting message type from first message row");
        return firstMessageRow.locator("td").nth(1).locator("img").getAttribute("alt");
    }

    /**
     * Get message status from the first message row (column 3)
     * @return Status alt text (e.g., "Message delivered successfully...")
     */
    public String getFirstMessageStatus() {
        log.info("Getting message status from first message row");
        return firstMessageRow.locator("td").nth(3).locator("img").getAttribute("alt");
    }

    /**
     * Check if the first message has an attachment (column 2)
     * @return true if attachment icon is present, false otherwise
     */
    public boolean isFirstMessageHasAttachment() {
        log.info("Checking if first message has attachment");
        String cellText = getFirstMessageCellText(2).trim();
        return !cellText.isEmpty() && !cellText.equals("&nbsp;");
    }

    /**
     * Click on the first message to open it
     * @return AdminPortalSentItemsPage
     */
    public AdminPortalSentItemsPage clickFirstMessage() {
        log.info("Clicking on first message");
        firstMessageRow.locator("td").nth(5).locator("a").click();
        return this;
    }

    /**
     * Click on the first message checkbox
     * @return AdminPortalSentItemsPage
     */
    public AdminPortalSentItemsPage clickFirstMessageCheckbox() {
        log.info("Clicking first message checkbox");
        firstMessageRow.locator("td").first().locator("div.checkbox_container").click();
        return this;
    }

    /**
     * Check if messages list is visible
     * @return true if visible, false otherwise
     */
    public boolean isMessagesListVisible() {
        log.info("Checking if messages list is visible");
        return messagesList.isVisible();
    }

    /**
     * Wait for messages list to be visible
     * @return AdminPortalSentItemsPage
     */
    public AdminPortalSentItemsPage waitForMessagesListToLoad() {
        log.info("Waiting for messages list to load");
        messagesList.waitFor(new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE));
        return this;
    }

    /**
     * Get count of messages in the list (excluding header)
     * @return Number of message rows
     */
    public int getMessagesCount() {
        log.info("Getting messages count");
        return messagesList.locator("tbody tr.rowEntryFolder").count();
    }

    /**
     * Check if messages list table is empty (no message rows)
     * When empty, tbody only contains indicatorTR row, no tr.rowEntryFolder elements
     * @return true if table is empty (no messages), false otherwise
     */
    public boolean isMessagesListEmpty() {
        log.info("Checking if messages list is empty");
        int messageCount = getMessagesCount();
        boolean isEmpty = messageCount == 0;
        log.info("Messages list empty: {} (count: {})", isEmpty, messageCount);
        return isEmpty;
    }

    /**
     * Click on Delete (Recycle Bin) button
     * @return AdminPortalSentItemsPage
     */
    public AdminPortalSentItemsPage clickDeleteRecycleBinBtn() {
        log.info("Clicking Delete (Recycle Bin) button");
        deleteRecycleBinBtn.click();
        return this;
    }

    /**
     * Click on Confirm Yes button
     */
    public AdminPortalSentItemsPage clickConfirmYesBtn() {
        log.info("Clicking Confirm Yes button");
        confirmYesBtn.click();
        waitForLoadingToDisappear();
        page.waitForTimeout(500);
        return this;
    }

    /**
     * Click on Status button
     * @return AdminPortalSentItemsPage
     */
    public AdminPortalSentItemsPage clickMessageStatusBtn() {
        log.info("Clicking Status button");
        messageStatusBtn.click();
        waitForLoadingToDisappear();
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

    // ========================================================================
    // MESSAGE DETAILS TABLE METHODS (#message_visible)
    // ========================================================================

    /**
     * Check if message details table is visible
     * @return true if visible, false otherwise
     */
    public boolean isMessageDetailsVisible() {
        log.info("Checking if message details table is visible");
        return messageVisibleTable.isVisible();
    }

    /**
     * Wait for message details table to be visible
     * @return AdminPortalSentItemsPage
     */
    public AdminPortalSentItemsPage waitForMessageDetailsToLoad() {
        log.info("Waiting for message details table to load");
        messageVisibleTable.waitFor(new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE));
        return this;
    }

    /**
     * Get date from message details (dateInfo)
     * @return Date text (e.g., "Sunday, January 11, 2026, 6:22 AM")
     */
    public String getMessageDetailsDate() {
        log.info("Getting date from message details");
        return dateInfo.textContent().trim();
    }

    /**
     * Get sender from message details (fromInfo)
     * @return Sender text (e.g., "websanityfn websanityln")
     */
    public String getMessageDetailsFrom() {
        log.info("Getting sender from message details");
        return fromInfo.textContent().trim();
    }

    /**
     * Get recipient from message details (toInfo)
     * @return Recipient text (e.g., "972585529693 (Mobile)")
     */
    public String getMessageDetailsTo() {
        log.info("Getting recipient from message details");
        return toInfo.textContent().trim();
    }

    /**
     * Get subject from message details (subjectInfo)
     * @return Subject text (e.g., "No Text")
     */
    public String getMessageDetailsSubject() {
        log.info("Getting subject from message details");
        return subjectInfo.textContent().trim();
    }

    /**
     * Get message text from message details
     * @return Message text content
     */
    public String getMessageDetailsText() {
        log.info("Getting message text from message details");
        return messageInfo.textContent().trim();
    }

    /**
     * Verify message details contain expected values
     * @param expectedTo Expected recipient
     * @param expectedMessagePart Expected part of message text
     * @return true if all expected values are found
     */
    public boolean verifyMessageDetails(String expectedTo, String expectedMessagePart) {
        log.info("Verifying message details - To: {}, Message contains: {}", expectedTo, expectedMessagePart);
        String actualTo = getMessageDetailsTo();
        String actualMessage = getMessageDetailsText();

        boolean toMatches = actualTo.equals(expectedTo);
        boolean messageContains = actualMessage.contains(expectedMessagePart);

        log.info("Verification result - To matches: {}, Message contains: {}", toMatches, messageContains);

        return toMatches && messageContains;
    }

    // ========================================================================
    // RECIPIENT STATUS TABLE METHODS (#recipientTable)
    // ========================================================================

    /**
     * Check if recipient table is visible
     * @return true if visible, false otherwise
     */
    public boolean isRecipientTableVisible() {
        log.info("Checking if recipient table is visible");
        return recipientTable.isVisible();
    }

    /**
     * Wait for recipient table to be visible
     * @return AdminPortalSentItemsPage
     */
    public AdminPortalSentItemsPage waitForRecipientTableToLoad() {
        log.info("Waiting for recipient table to load");
        recipientTable.waitFor(new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE));
        return this;
    }

    /**
     * Get recipient number from recipient status table
     * @return Recipient number (e.g., "972-58-5529693")
     */
    public String getRecipientNumberFromMessageStatusTable() {
        log.info("Getting recipient number from recipient table");
        return recipientNumber.textContent().trim();
    }

    /**
     * Get recipient status title (hover text) from status image
     * @return Status title text (e.g., "972-58-5529693\nSMS was delivered to handset")
     */
    public String getRecipientStatusTitle() {
        log.info("Getting recipient status title");
        return recipientStatusImg.getAttribute("title");
    }

    /**
     * Get recipient status image source
     * @return Image src (e.g., "/newjsp/images/status/ic_delivered.png")
     */
    public String getRecipientStatusImageSrc() {
        log.info("Getting recipient status image source");
        return recipientStatusImg.getAttribute("src");
    }

    /**
     * Get recipient status alt text from status image
     * @return Status alt text
     */
    public String getRecipientStatusAlt() {
        log.info("Getting recipient status alt text");
        return recipientStatusImg.getAttribute("alt");
    }

    /**
     * Check if message was delivered successfully
     * @return true if delivered status icon is present
     */
    public boolean isMessageDelivered() {
        log.info("Checking if message was delivered");
        String statusSrc = getRecipientStatusImageSrc();
        return statusSrc != null && statusSrc.contains("ic_delivered.png");
    }
}
