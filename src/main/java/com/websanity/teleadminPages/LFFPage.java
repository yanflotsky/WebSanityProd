package com.websanity.teleadminPages;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.websanity.BasePage;
import com.websanity.enums.UserTypes;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LFFPage extends BasePage {

    private final FrameLocator textFrame;
    private final Locator userTypeSelect;
    private final Locator fileUploadInput;
    private final Locator addBtn;
    private final Locator exclusiveAdminSelect;
    private final Locator storagePlanTable;
    private final Locator signUpPlanTableBody;
    private final Locator importResultText;

    public LFFPage(Page page) {
        super(page);
        this.textFrame = page.frameLocator("frame[name='text']");
        this.userTypeSelect = textFrame.locator("select#type");
        this.fileUploadInput = textFrame.locator("input[name='fileupload'][type='file']");
        this.addBtn = textFrame.locator("img[alt='Import the contacts'][src='/images/en/button_add.gif']").first();
        this.exclusiveAdminSelect = textFrame.locator("select#exclusive_admin_id");
        this.storagePlanTable = textFrame.locator("table#sign-up-plan-list-table");
        this.signUpPlanTableBody = textFrame.locator("tbody#sign-up-plan-list-table-body");
        this.importResultText = textFrame.locator("td.importText.text");
    }

    /**
     * Select user type from dropdown
     * @param userType UserTypes enum value
     * @return this page for chaining
     */
    public LFFPage selectUserType(UserTypes userType) {
        log.info("Selecting user type: {}", userType.getDisplayName());
        userTypeSelect.selectOption(userType.getValue());

        // Wait for file upload input to become visible after user type selection
        log.debug("Waiting for file upload input to become visible...");
        fileUploadInput.waitFor(new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        log.debug("File upload input is now visible");
        page.waitForTimeout(1000);

        return this;
    }

    /**
     * Select user type by display name
     * @param displayName display name of user type (e.g., "Pro Manager")
     * @return this page for chaining
     */
    public LFFPage selectUserTypeByName(String displayName) {
        UserTypes userType = UserTypes.getByDisplayName(displayName);
        log.info("Selecting user type: {}", userType.getDisplayName());
        userTypeSelect.selectOption(userType.getValue());

        // Wait for file upload input to become visible after user type selection
        log.debug("Waiting for file upload input to become visible...");
        fileUploadInput.waitFor(new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        log.debug("File upload input is now visible");

        return this;
    }

    /**
     * Get currently selected user type display name
     * @return display name of selected user type
     */
    public String getSelectedUserType() {
        String value = userTypeSelect.inputValue();
        if (value == null || value.isEmpty()) {
            return "";
        }
        UserTypes userType = UserTypes.getByValue(value);
        return userType.getDisplayName();
    }

    /**
     * Upload file for LFF operation
     * @param filePath absolute or relative path to the file
     * @return this page for chaining
     */
    public LFFPage uploadFile(String filePath) {
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
    public LFFPage clickAddBtn() {
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
     * Select exclusive admin by value
     * @param value value of the option to select
     * @return this page for chaining
     */
    public LFFPage selectExclusiveAdmin(String value) {
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
     * Select exclusive admin by label/text
     * @param label visible text of the option to select
     * @return this page for chaining
     * @deprecated Use fillExclusiveAdmin instead when dealing with many options
     */
    @Deprecated
    public LFFPage selectExclusiveAdminByLabel(String label) {
        log.info("Selecting exclusive admin by label: {}", label);
        exclusiveAdminSelect.selectOption(new com.microsoft.playwright.options.SelectOption().setLabel(label));
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

    /**
     * Check if sign-up plan table is visible
     * @return true if visible
     */
    public boolean isStoragePlanTableVisible() {
        return storagePlanTable.isVisible();
    }

    /**
     * Get Plan ID from first row of sign-up plan list table
     * @return plan ID
     */
    public String getPlanIdFromFirstRow() {
        log.info("Getting plan ID from first row of sign-up plan list table");
        Locator firstRow = signUpPlanTableBody.locator("tr").first();
        Locator planIdCell = firstRow.locator("td").first(); // First column (Plan ID)
        String planId = planIdCell.textContent().trim();
        log.info("Plan ID from first row: {}", planId);
        return planId;
    }

    /**
     * Wait for import result text to be visible
     * @return this page for chaining
     */
    public LFFPage waitForImportResultText() {
        log.info("Waiting for import result text to be visible");
        importResultText.waitFor(new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                .setTimeout(20000));
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
