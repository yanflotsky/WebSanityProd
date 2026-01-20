package com.websanity.teleadminPages;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.websanity.BasePage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DFFPage extends BasePage {

    private final FrameLocator textFrame;
    private final Locator exclusiveAdminSelect;
    private final Locator fileUploadInput;
    private final Locator addBtn;
    private final Locator importResultText;

    public DFFPage(Page page) {
        super(page);
        this.textFrame = page.frameLocator("frame[name='text']");
        this.exclusiveAdminSelect = textFrame.locator("select#exclusive_admin_id");
        this.fileUploadInput = textFrame.locator("input[name='fileupload'][type='file']");
        this.addBtn = textFrame.locator("img[alt='Import the contacts'][src='/images/en/button_add.gif']").first();
        this.importResultText = textFrame.locator("td.importText.text");
    }

    /**
     * Select exclusive admin by value
     * @param value value of the option to select
     * @return this page for chaining
     */
    public DFFPage selectExclusiveAdmin(String value) {
        log.info("Selecting exclusive admin with value: {}", value);
        exclusiveAdminSelect.selectOption(value);

        return this;
    }

    /**
     * Get currently selected exclusive admin value
     * @return value of selected option
     */
    public String getSelectedExclusiveAdmin() {
        return exclusiveAdminSelect.inputValue();
    }

    /**
     * Check if exclusive admin select is visible
     * @return true if visible
     */
    public boolean isExclusiveAdminSelectVisible() {
        return exclusiveAdminSelect.isVisible();
    }

    /**
     * Upload file for DFF operation
     * @param filePath absolute or relative path to the file
     * @return this page for chaining
     */
    public DFFPage uploadFile(String filePath) {
        log.info("Uploading file: {}", filePath);
        fileUploadInput.setInputFiles(java.nio.file.Paths.get(filePath));
        log.info("File uploaded successfully");
        page.waitForTimeout(500);
        return this;
    }

    /**
     * Check if file upload input is visible
     * @return true if visible
     */
    public boolean isFileUploadVisible() {
        return fileUploadInput.isVisible();
    }



    /**
     * Click on Add/Import button to submit the file
     * @return this page for chaining
     */
    public DFFPage clickAddBtn() {
        log.info("Clicking Add/Import button");
        addBtn.click();
        page.waitForTimeout(2000);
        return this;
    }

    /**
     * Check if Add button is visible
     * @return true if visible
     */
    public boolean isAddBtnVisible() {
        return addBtn.isVisible();
    }

    /**
     * Wait for import result text to be visible
     * @return this page for chaining
     */
    public DFFPage waitForImportResultText() {
        log.info("Waiting for import result text to be visible");
        importResultText.waitFor(new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        log.info("Import result text is now visible");
        return this;
    }

    /**
     * Get import result text
     * @return result text (e.g., "5 users were imported and created successfully.")
     */
    public String getImportResultText() {
        log.info("Getting import result text");
        String resultText = importResultText.textContent().trim();
        log.info("Import result: {}", resultText);
        return resultText;
    }

    /**
     * Check if import result text is visible
     * @return true if visible
     */
    public boolean isImportResultTextVisible() {
        return importResultText.isVisible();
    }

}
