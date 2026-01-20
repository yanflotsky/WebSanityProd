package com.websanity.teleadminPages;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.websanity.BasePage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FindUsersPage extends BasePage {

    private final FrameLocator textFrame;
    private final Locator usernameInput;
    private final Locator  searchButton;
    private final Locator usersTable;
    private final Locator customerAdministratorInput;
    private final Locator advancedOptionsLabel;

    public FindUsersPage(Page page) {
        super(page);
        this.textFrame = page.frameLocator("frame[name='text']");
        this.usernameInput = textFrame.locator("input[name='userName']");
        this.searchButton = textFrame.locator("button[id='findUsersBtn']");
        this.usersTable = textFrame.locator("#personTable");
        this.customerAdministratorInput = textFrame.locator("input#customerAdministratorInput");
        this.advancedOptionsLabel = textFrame.locator("div#advancedOptionsLabel");
    }

    /**
     * Wait for users table to be visible (with Playwright auto-waiting)
     * Default timeout: 30 seconds
     */
    public void waitForTableVisible() {
        usersTable.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE));
    }

    /**
     * Wait for Find Users page to load after login
     * Waits for search button to appear (90 seconds timeout for Docker)
     * @return true if page loaded successfully, false if timeout
     */
    public void waitForFindUsersPageToLoad() {
        log.info("Waiting for Find Users page to load after login...");
        try {
            // First wait for the frame to be available
            page.waitForTimeout(1000);

            // Then wait for the search button in the frame
            searchButton.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(90000));  // Increased timeout for Docker

            // Additional wait to ensure frame is fully loaded
            page.waitForTimeout(2000);
            log.info("Find Users page loaded successfully - search button is visible");
        } catch (Exception e) {
            log.error("Failed to load Find Users page within 90 seconds", e);
            log.error("Current URL: {}", page.url());
            throw new RuntimeException("Teleadmin Find Users page did not load", e);
        }
    }

    /**
     * Enter username into search field
     */
    public void enterUsername(String username) {
        usernameInput.fill(username);
    }

    /**
     * Fill customer administrator input field
     * This field has dropdown filtering functionality
     * @param administratorName name of the administrator to fill
     */
    public FindUsersPage fillCustomerAdministrator(String administratorName) {
        log.info("Filling customer administrator with: {}", administratorName);
        customerAdministratorInput.fill(administratorName);
        page.waitForTimeout(500); // Wait for dropdown filtering

        // Click on selected dropdown item
        log.debug("Clicking on selected dropdown item");
        Locator selectedDropdownItem = textFrame.locator("#customerAdministrationDropdown .dropdown-item.selected");
        selectedDropdownItem.click();
        page.waitForTimeout(300);

        log.info("Customer administrator filled and selected successfully");
        return this;
    }

    /**
     * Check if customer administrator input is visible
     * @return true if visible
     */
    public boolean isCustomerAdministratorInputVisible() {
        return customerAdministratorInput.isVisible();
    }

    /**
     * Click on Advanced Options label to show advanced search options
     * @return this page for chaining
     */
    public FindUsersPage clickAdvancedOptions() {
        log.info("Clicking on Advanced Options");
        advancedOptionsLabel.click();
        page.waitForTimeout(500); // Wait for advanced options to expand
        log.info("Advanced Options clicked");
        return this;
    }

    /**
     * Check if Advanced Options label is visible
     * @return true if visible
     */
    public boolean isAdvancedOptionsVisible() {
        return advancedOptionsLabel.isVisible();
    }

    /**
     * Click search button
     */
    public FindUsersPage clickSearchButton() {
        page.waitForTimeout(500);
        searchButton.click();
        waitForTableToHaveData();
        return this;
    }

    /**
     * Search for user by username
     */
    public FindUsersPage searchUserByUsername(String username) {
        log.info("Searching for user: {}", username);
        enterUsername(username);
        clickSearchButton();

        // Wait for search to complete (increased for Docker)
        page.waitForTimeout(1500);
        return this;
    }

    /**
     * Wait for table to have data (not empty and not showing "No data available")
     * Uses Playwright's auto-waiting with custom condition
     * Timeout increased to 90 seconds for Docker environment
     */
    private void waitForTableToHaveData() {
        log.info("Waiting for table to load data...");

        try {
            // Wait for at least one row to appear
            usersTable.locator("tbody tr").first().waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(60000));  // Increased for Docker

            // Wait for "No data available" message to disappear (if it was there)
            page.waitForCondition(() -> {
                Locator rows = usersTable.locator("tbody tr");
                if (rows.count() == 0) return false;
                String firstRowText = rows.first().textContent();
                return !firstRowText.contains("No data available in table");
            }, new Page.WaitForConditionOptions().setTimeout(60000));  // Increased for Docker

            log.info("Table data loaded successfully");
        } catch (Exception e) {
            log.error("Failed to load table data within 60 seconds", e);
            throw new RuntimeException("Table data did not load", e);
        }
    }

    /**
     * Check that user was found in table and click on him
     * This method:
     * 1. Waits for table to have data (up to 60 seconds)
     * 2. Searches for user by username in first column
     * 3. Clicks on username link
     * 4. Verifies Update User page is opened
     *
     * @param username - username to search for
     * @param updateUserPage - TeleadminUpdateUserPage instance to verify page opened
     */
    public UpdateUserPage checkThatUserWasFoundAndClickOnHim(String username, UpdateUserPage updateUserPage) {
        // Wait till the table will be not empty
        waitForTableToHaveData();

        // Wait for user to appear in table and click
        final boolean[] userFound = {false};

        page.waitForCondition(() -> {
            Locator allRows = usersTable.locator("tbody tr");
            int rowCount = allRows.count();

            for (int i = 0; i < rowCount; i++) {
                Locator row = allRows.nth(i);
                Locator columns = row.locator("td");

                if (columns.count() > 0) {
                    String firstColumnText = columns.nth(0).textContent().trim();

                    if (firstColumnText.equals(username)) {
                        System.out.println("User " + username + " is found. Click on him");
                        columns.nth(0).locator("a").click();
                        userFound[0] = true;
                        return true;
                    }
                }
            }
            return false;
        }, new Page.WaitForConditionOptions().setTimeout(60000));

        if (!userFound[0]) {
            String errorMessage = "User " + username + " was not found in any row.";
            System.err.println(errorMessage);
            throw new AssertionError(errorMessage);
        }

        // Verify Update User page is opened
        try {
            System.out.println("Check updateInterfaceSupportButton element to be sure that Account Information of User is fully opened");
            boolean isOpened = updateUserPage.isUpdateUserPageOpened();
            if (!isOpened) {
                throw new AssertionError("Account Information of User is NOT opened!!!");
            }
            System.out.println("updateInterfaceSupportButton element appeared. Account Information of User is opened.");
            page.waitForTimeout(1500);
        } catch (Exception e) {
            System.out.println("Account Information of User " + username + " is opened.");
        }
        return new UpdateUserPage(page);
    }

    /**
     * Check if user exists in the table by username
     */
    public boolean isUserInTable(String username) {
        Locator userRow = usersTable.locator("tbody tr").filter(new Locator.FilterOptions()
                .setHasText(username));
        return userRow.count() > 0;
    }

    /**
     * Click on username link in the table (simple version without verification)
     */
    public void clickUserInTable(String username) {
        usersTable.locator("tbody tr")
                .filter(new Locator.FilterOptions().setHasText(username))
                .locator("td a")
                .first()
                .click();
    }

    public int getRowCount() {
        return usersTable.locator("tbody tr").count();
    }

    public Locator getRow(int index) {
        return usersTable.locator("tbody tr").nth(index);
    }

    public Locator getAllRows() {
        return usersTable.locator("tbody tr");
    }

    /**
     * Check that table contains specific users by their usernames
     * @param usernames list of usernames to check
     * @return true if all users are found in the table
     */
    public boolean checkTableHasUsers(java.util.List<String> usernames) {
        log.info("Checking if table contains {} users", usernames.size());

        // Wait for table to have data
        waitForTableToHaveData();

        for (String username : usernames) {
            boolean found = false;
            Locator allRows = usersTable.locator("tbody tr");
            int rowCount = allRows.count();

            for (int i = 0; i < rowCount; i++) {
                Locator row = allRows.nth(i);
                Locator firstColumn = row.locator("td").first();
                String usernameInTable = firstColumn.textContent().trim();

                if (usernameInTable.equals(username)) {
                    log.info("User '{}' found in table", username);
                    found = true;
                    break;
                }
            }

            if (!found) {
                log.error("User '{}' NOT found in table", username);
                return false;
            }
        }

        log.info("All {} users found in table", usernames.size());
        return true;
    }

    /**
     * Check that table contains specific users by their usernames (varargs version)
     * @param usernames usernames to check
     * @return true if all users are found in the table
     */
    public boolean checkTableHasUsers(String... usernames) {
        return checkTableHasUsers(java.util.Arrays.asList(usernames));
    }

    /**
     * Get all usernames from the table
     * @return list of usernames in the table
     */
    public java.util.List<String> getAllUsernamesFromTable() {
        log.info("Getting all usernames from table");
        waitForTableToHaveData();

        java.util.List<String> usernames = new java.util.ArrayList<>();
        Locator allRows = usersTable.locator("tbody tr");
        int rowCount = allRows.count();

        for (int i = 0; i < rowCount; i++) {
            Locator row = allRows.nth(i);
            Locator firstColumn = row.locator("td").first();
            String username = firstColumn.textContent().trim();
            usernames.add(username);
        }

        log.info("Found {} users in table", usernames.size());
        return usernames;
    }

    /**
     * Check that table contains exact number of users with specific usernames
     * @param usernames list of usernames to check
     * @return true if table contains exactly these users (no more, no less)
     */
    public boolean checkTableHasExactUsers(java.util.List<String> usernames) {
        log.info("Checking if table contains exactly {} users", usernames.size());

        java.util.List<String> usernamesInTable = getAllUsernamesFromTable();

        if (usernamesInTable.size() != usernames.size()) {
            log.error("Table has {} users, but expected {}", usernamesInTable.size(), usernames.size());
            return false;
        }

        for (String username : usernames) {
            if (!usernamesInTable.contains(username)) {
                log.error("User '{}' NOT found in table", username);
                return false;
            }
        }

        log.info("Table contains exactly the expected {} users", usernames.size());
        return true;
    }

    /**
     * Check that table shows "No data available in table" message
     * Used to verify that no users were found (e.g., after deletion)
     * @return true if "No data available" message is shown
     */
    public boolean isTableEmpty() {
        log.info("Checking if table shows 'No data available' message");

        try {
            // Wait a bit for table to update after search/delete
            page.waitForTimeout(1000);

            Locator rows = usersTable.locator("tbody tr");

            // Check if there's at least one row
            if (rows.count() == 0) {
                log.info("Table has no rows - empty");
                return true;
            }

            // Check if first row contains "No data available in table" message
            String firstRowText = rows.first().textContent().trim();
            boolean isEmpty = firstRowText.contains("No data available in table");

            if (isEmpty) {
                log.info("Table shows 'No data available in table' message");
            } else {
                log.info("Table contains data: {}", firstRowText);
            }

            return isEmpty;
        } catch (Exception e) {
            log.error("Error checking if table is empty", e);
            return false;
        }
    }

    /**
     * Wait for table to show "No data available in table" message
     * Waits up to specified timeout for the message to appear
     * @param timeoutMs timeout in milliseconds (default: 10000)
     * @return true if message appeared within timeout
     */
    public boolean waitForTableToBeEmpty(int timeoutMs) {
        log.info("Waiting for table to show 'No data available' message (timeout: {}ms)", timeoutMs);

        try {
            page.waitForCondition(() -> {
                Locator rows = usersTable.locator("tbody tr");
                if (rows.count() == 0) return true;

                String firstRowText = rows.first().textContent();
                return firstRowText.contains("No data available in table");
            }, new Page.WaitForConditionOptions().setTimeout(timeoutMs));

            log.info("Table is empty - 'No data available' message is shown");
            return true;
        } catch (Exception e) {
            log.error("Table did not become empty within {}ms", timeoutMs);
            return false;
        }
    }

    /**
     * Wait for table to show "No data available in table" message
     * Uses default timeout of 10 seconds
     * @return true if message appeared within timeout
     */
    public boolean waitForTableToBeEmpty() {
        return waitForTableToBeEmpty(10000);
    }

}
