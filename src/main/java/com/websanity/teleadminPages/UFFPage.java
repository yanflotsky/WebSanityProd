package com.websanity.teleadminPages;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.websanity.BasePage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UFFPage extends BasePage {

    private final FrameLocator textFrame;
    private final Locator usernameInput;
    private final Locator exclusiveAdminSelect;
    private final Locator storagePlanTable;
    private final Locator fileUploadInput;
    private final Locator updateButton;
    private final Locator importResultText;
    private final Locator signUpPlanTableBody;

    public UFFPage(Page page) {
        super(page);
        this.textFrame = page.frameLocator("frame[name='text']");
        this.usernameInput = textFrame.locator("input[name='userName']");
        this.exclusiveAdminSelect = textFrame.locator("select#exclusive_admin_id");
        this.storagePlanTable = textFrame.locator("table#sign-up-plan-list-table");
        this.fileUploadInput = textFrame.locator("input[name='fileupload'][type='file']");
        this.updateButton = textFrame.locator("img[alt='Update'][src='/images/en/button_update.gif']").first();
        this.importResultText = textFrame.locator("td.importText.text");
        this.signUpPlanTableBody = textFrame.locator("tbody#sign-up-plan-list-table-body");
    }

    public UFFPage selectExclusiveAdmin(String value) {
        log.info("Selecting Customer Administrator with value: {}", value);
        exclusiveAdminSelect.selectOption(value);

        // Wait for storage plan table to become visible after exclusive admin selection
        log.debug("Waiting for storage plan table to become visible...");
        storagePlanTable.waitFor(new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        log.debug("Storage plan table is now visible");

        return this;
    }

    /**
     * Get description text from first row of sign-up plan list table
     * @return description text (e.g., "websanPlan1")
     */
    public String getPlanDescriptionFromFirstRow() {
        log.info("Getting description from first row of sign-up plan list table");
        Locator firstRow = signUpPlanTableBody.locator("tr").first();
        Locator descriptionCell = firstRow.locator("td").nth(1); // Second column (Description)
        String description = descriptionCell.textContent().trim();
        log.info("Description from first row: {}", description);
        return description;
    }

    public String getImportResultText() {
        log.info("Getting import result text");
        String resultText = importResultText.textContent().trim();
        log.info("Import result: {}", resultText);
        return resultText;
    }

    public UFFPage waitForImportResultText() {
        log.info("Waiting for import result text to be visible");
        importResultText.waitFor(new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        log.info("Import result text is now visible");
        return this;
    }

    public UFFPage uploadFile(String filePath) {
        log.info("Uploading file: {}", filePath);

        // Convert to absolute path if needed
        java.nio.file.Path path = java.nio.file.Paths.get(filePath);
        if (!path.isAbsolute()) {
            // If relative path, make it absolute from project root
            path = java.nio.file.Paths.get(System.getProperty("user.dir"), filePath).toAbsolutePath();
            log.debug("Converted to absolute path: {}", path);
        }

        // Check if file exists
        if (!java.nio.file.Files.exists(path)) {
            String errorMsg = "File not found: " + path.toAbsolutePath();
            log.error(errorMsg);
            throw new RuntimeException(errorMsg);
        }

        fileUploadInput.setInputFiles(path);
        log.info("File uploaded successfully: {}", path.getFileName());
        page.waitForTimeout(500);
        return this;
    }

    /**
     * Click the Update button
     */
    public UFFPage clickUpdateButton() {
        log.info("Clicking Update button");
        updateButton.click();
        return this;
    }

}
