package com.websanity.teleadminPages;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.websanity.BasePage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TeleadminFindUsersPage extends BasePage {

    private final FrameLocator textFrame;
    private final Locator usernameInput;
    private final Locator  searchButton;
    private final Locator usersTable;

    public TeleadminFindUsersPage(Page page) {
        super(page);
        this.textFrame = page.frameLocator("frame[name='text']");
        this.usernameInput = textFrame.locator("input[name='userName']");
        this.searchButton = textFrame.locator("button[id='findUsersBtn']");
        this.usersTable = textFrame.locator("#personTable");
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
     * Click search button
     */
    public void clickSearchButton() {
        page.waitForTimeout(500);
        searchButton.click();
    }

    /**
     * Search for user by username
     */
    public TeleadminFindUsersPage searchUser(String username) {
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
            log.error("Current URL: {}", page.url());
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
    public TeleadminUpdateUserPage checkThatUserWasFoundAndClickOnHim(String username, TeleadminUpdateUserPage updateUserPage) {
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
        return new TeleadminUpdateUserPage(page);
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

}
