package com.websanity.adminPortalPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.websanity.BasePage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AdminPortalArchiveManagementPage extends BasePage {

    private final Locator archivePlansTable;
    private final Locator tableHeaders;
    private final Locator firstRow;
    private final Locator loadingOverlay;
    private final Locator searchInput;
    private final Locator searchSubmitButton;
    private final Locator numOfAssignedUsersLabel;
    private final Locator assignActionUsersTable;
    private final Locator assignActionFirstRow;
    private final Locator assignUsersButton;
    private final Locator unassignActionUsersTable;
    private final Locator unassignActionFirstRow;
    private final Locator unassignUsersButton;
    private final Locator successMessage;

    public AdminPortalArchiveManagementPage(Page page) {
        super(page);
        this.loadingOverlay = page.locator("#fade");
        this.archivePlansTable = page.locator("#contentTable");
        this.tableHeaders = archivePlansTable.locator("th");
        this.firstRow = archivePlansTable.locator("tr").nth(1);
        this.searchInput = page.locator("#searchInp");
        this.searchSubmitButton = page.locator("#searchContactsSbm");
        this.numOfAssignedUsersLabel = page.locator("#numOfAssignedUsers");
        this.assignActionUsersTable = page.locator("#assignActioncontentTable");
        this.assignActionFirstRow = assignActionUsersTable.locator("tr").nth(1);
        this.assignUsersButton = page.locator("#assign-button");
        this.unassignActionUsersTable = page.locator("#unassignActioncontentTable");
        this.unassignActionFirstRow = unassignActionUsersTable.locator("tr").nth(1);
        this.unassignUsersButton = page.locator("#unassign-button");
        this.successMessage = page.locator("#successMsg");
    }

    /**
     * Get header text by index (0-based)
     */
    public String getHeaderText(int index) {
        return tableHeaders.nth(index).textContent().trim();
    }

    /**
     * Verify all table headers match expected values
     */
    public boolean verifyAllHeadersOfArchivePlansTable() {
        page.waitForTimeout(1000);
        String[] expectedHeaders = {
            "Source",
            "Destination",
            "Email",
            "Thread Archiving",
            "Number Of Assigned Users",
            "Export Users To CSV",
            "Actions"
        };

        for (int i = 0; i < expectedHeaders.length; i++) {
            String actualHeader = getHeaderText(i);
            if (!actualHeader.equals(expectedHeaders[i])) {
                System.err.println("Header mismatch at index " + i + ": expected '" + expectedHeaders[i] + "' but got '" + actualHeader + "'");
                return false;
            }
        }
        return true;
    }

    /**
     * Get Source column value from first row
     */
    public String getSourceFromFirstRow() {
        return firstRow.locator("td").nth(0).textContent().trim();
    }

    /**
     * Get Destination column value from first row
     */
    public String getDestinationFromFirstRow() {
        return firstRow.locator("td").nth(1).textContent().trim();
    }

    /**
     * Get Email column value from first row
     */
    public String getEmailFromFirstRow() {
        return firstRow.locator("td").nth(2).textContent().trim();
    }

    /**
     * Get Number Of Assigned Users column value from first row
     */
    public String getNumberOfAssignedUsersFromFirstRow() {
        return firstRow.locator("td").nth(4).textContent().trim();
    }

    /**
     * Verify that Assign button exists in the first row
     */
    public boolean verifyAssignButtonExists() {
        return firstRow.locator("input[value='Assign']").count() > 0;
    }

    /**
     * Verify that Unassign button exists in the first row
     */
    public boolean verifyUnassignButtonExists() {
        return firstRow.locator("input[value='Unassign']").count() > 0;
    }

    /**
     * Verify that both Assign and Unassign buttons exist in the Actions column
     */
    public boolean verifyActionButtonsExist() {
        return verifyAssignButtonExists() && verifyUnassignButtonExists();
    }

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
     * Click the Assign button in the first row
     */
    public AdminPortalArchiveManagementPage clickAssignButton() {
        log.info("Clicking Assign button");
        firstRow.locator("input[value='Assign']").click();
        waitForLoadingToDisappear();
        page.waitForTimeout(1000);
        return this;
    }

    /**
     * Click the Unassign button in the first row
     */
    public AdminPortalArchiveManagementPage clickUnassignButton() {
        log.info("Clicking Unassign button");
        firstRow.locator("input[value='Unassign']").click();
        waitForLoadingToDisappear();
        log.info("Waiting for Unassign Users Table to be visible");
        unassignActionUsersTable.waitFor(new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        page.waitForTimeout(1000);
        return this;
    }

    /**
     * Get the number of rows in the table (excluding header)
     */
    public int getRowCount() {
        return archivePlansTable.locator("tr").count() - 1;
    }

    /**
     * Fill the search input field
     */
    public AdminPortalArchiveManagementPage fillSearchInput(String searchText) {
        searchInput.clear();
        searchInput.fill(searchText);
        return this;
    }

    /**
     * Clear the search input field
     */
    public AdminPortalArchiveManagementPage clearSearchInput() {
        searchInput.clear();
        return this;
    }

    /**
     * Get the current value of the search input field
     */
    public String getSearchInputValue() {
        return searchInput.inputValue();
    }

    /**
     * Click the search submit button
     */
    public AdminPortalArchiveManagementPage clickSearchButton() {
        searchSubmitButton.click();
        page.waitForTimeout(1000);
        return this;
    }

    /**
     * Get the text from the numOfAssignedUsers label
     */
    public String getNumOfAssignedUsersCounter() {
        return numOfAssignedUsersLabel.textContent().trim();
    }

    // ========== Assign Action Users Table Methods ==========

    /**
     * Get First Name from first row of assignAction users table
     */
    public String getFirstNameFromAssignActionFirstRow() {
        return assignActionFirstRow.locator("td").nth(1).textContent().trim();
    }

    /**
     * Get Last Name from first row of assignAction users table
     */
    public String getLastNameFromAssignActionFirstRow() {
        return assignActionFirstRow.locator("td").nth(2).textContent().trim();
    }

    /**
     * Get Username from first row of assignAction users table
     */
    public String getUsernameFromAssignActionFirstRow() {
        return assignActionFirstRow.locator("td").nth(3).textContent().trim();
    }

    /**
     * Get Email from first row of assignAction users table
     */
    public String getEmailFromAssignActionFirstRow() {
        return assignActionFirstRow.locator("td").nth(4).textContent().trim();
    }

    /**
     * Get Mobile from first row of assignAction users table
     */
    public String getMobileFromAssignActionFirstRow() {
        return assignActionFirstRow.locator("td").nth(5).textContent().trim();
    }

    /**
     * Click the checkbox in the first row of assignAction users table
     */
    public AdminPortalArchiveManagementPage clickAssignActionFirstRowCheckbox() {
        assignActionFirstRow.locator("input[type='checkbox']").click();
        return this;
    }

    /**
     * Verify that the first row checkbox is checked in assignAction users table
     */
    public boolean isAssignActionFirstRowCheckboxChecked() {
        return assignActionFirstRow.locator("input[type='checkbox']").isChecked();
    }

    /**
     * Get the number of rows in assignAction users table (excluding header)
     */
    public int getAssignActionUsersRowCount() {
        return assignActionUsersTable.locator("tr").count() - 1;
    }

    /**
     * Check if assignAction users table is empty (contains only header row, no data rows)
     */
    public boolean isAssignActionUsersTableEmpty() {
        return getAssignActionUsersRowCount() == 0;
    }

    /**
     * Click the "Assign users" button
     * Throws IllegalStateException if the button is disabled
     */
    public AdminPortalArchiveManagementPage clickAssignUsersButton() {
        if (!assignUsersButton.isEnabled()) {
            throw new IllegalStateException("The 'Assign users' button is disabled and cannot be clicked");
        }
        log.info("Clicking 'Assign users' button");
        assignUsersButton.click();
        waitForLoadingToDisappear();
        page.waitForTimeout(3000);
        return this;
    }

    /**
     * Check if the "Assign users" button is enabled
     */
    public boolean isAssignUsersButtonEnabled() {
        return assignUsersButton.isEnabled();
    }

    // ========== Unassign Action Users Table Methods ==========

    /**
     * Get First Name from first row of unassignAction users table
     */
    public String getFirstNameFromUnassignActionFirstRow() {
        return unassignActionFirstRow.locator("td").nth(1).textContent().trim();
    }

    /**
     * Get Last Name from first row of unassignAction users table
     */
    public String getLastNameFromUnassignActionFirstRow() {
        return unassignActionFirstRow.locator("td").nth(2).textContent().trim();
    }

    /**
     * Get Username from first row of unassignAction users table
     */
    public String getUsernameFromUnassignActionFirstRow() {
        return unassignActionFirstRow.locator("td").nth(3).textContent().trim();
    }

    /**
     * Get Email from first row of unassignAction users table
     */
    public String getEmailFromUnassignActionFirstRow() {
        return unassignActionFirstRow.locator("td").nth(4).textContent().trim();
    }

    /**
     * Get Mobile from first row of unassignAction users table
     */
    public String getMobileFromUnassignActionFirstRow() {
        return unassignActionFirstRow.locator("td").nth(5).textContent().trim();
    }

    /**
     * Click the checkbox in the first row of unassignAction users table
     */
    public AdminPortalArchiveManagementPage clickUnassignActionFirstRowCheckbox() {
        unassignActionFirstRow.locator("input[type='checkbox']").click();
        return this;
    }

    /**
     * Verify that the first row checkbox is checked in unassignAction users table
     */
    public boolean isUnassignActionFirstRowCheckboxChecked() {
        return unassignActionFirstRow.locator("input[type='checkbox']").isChecked();
    }

    /**
     * Get the number of rows in unassignAction users table (excluding header)
     */
    public int getUnassignActionUsersRowCount() {
        return unassignActionUsersTable.locator("tr").count() - 1;
    }

    /**
     * Check if unassignAction users table is empty (contains only header row, no data rows)
     */
    public boolean isUnassignActionUsersTableEmpty() {
        return getUnassignActionUsersRowCount() == 0;
    }

    /**
     * Click the "Unassign users" button
     * Throws IllegalStateException if the button is disabled
     */
    public AdminPortalArchiveManagementPage clickUnassignUsersButton() {
        if (!unassignUsersButton.isEnabled()) {
            throw new IllegalStateException("The 'Unassign users' button is disabled and cannot be clicked");
        }
        log.info("Clicking 'Unassign users' button");
        unassignUsersButton.click();
        waitForLoadingToDisappear();
        page.waitForTimeout(3000);
        return this;
    }

    /**
     * Check if the "Unassign users" button is enabled
     */
    public boolean isUnassignUsersButtonEnabled() {
        return unassignUsersButton.isEnabled();
    }

    /**
     * Check if success message is visible
     */
    public boolean isSuccessMessageVisible() {
        return successMessage.isVisible();
    }

    /**
     * Get the text from the success message
     */
    public String getSuccessMessageText() {
        return successMessage.textContent().trim();
    }

    /**
     * Wait for success message to appear and verify it's visible
     */
    public AdminPortalArchiveManagementPage waitForSuccessMessage() {
        successMessage.waitFor(new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                .setTimeout(10000));
        return this;
    }
}
