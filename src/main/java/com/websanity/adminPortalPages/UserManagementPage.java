package com.websanity.adminPortalPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.websanity.BasePage;
import com.websanity.enums.UserTypes;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserManagementPage extends BasePage {

    private final Locator loadingOverlay;
    private final Locator addUserBtn;
    private final Locator serviceLevel;
    private final Locator firstNameInput;
    private final Locator lastNameInput;
    private final Locator mobileCountrySelect;
    private final Locator mobileTelephoneInput;
    private final Locator communicationEmailInput;
    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator uccInput;
    private final Locator subManagerCheckbox;
    private final Locator addToGlobalAddressBookCheckbox;
    private final Locator saveBtn;
    private final Locator addUserSummaryPopUp;
    private final Locator addUserSummaryPopUpSuccessUsers;
    private final Locator addUserSummaryPopUpArchivePlanMessage;
    private final Locator bulkUploadSummaryContent;
    private final Locator closeErrorsSummaryBtn;
    private final Locator searchInp;
    private final Locator searchBtn;
    private final Locator contentTable;
    private final Locator deleteBtn;
    private final Locator suspendBtn;
    private final Locator activateBtn;
    private final Locator confirmYesBtn;
    private final Locator errorMsg;
    private final Locator successMsg;
    private final Locator languageSelect;
    private final Locator timeZoneSelect;
    private final Locator countrySelect;
    private final Locator checkAll;
    private final Locator bulkActionsBtn;
    private final Locator addUsersBtn;
    private final Locator updateUsersBtn;
    private final Locator deleteUsersBtn;
    private final Locator addUsersFromFilePopUp;
    private final Locator bulkActionsServiceLevelSelect;
    private final Locator fileUploadInput;
    private final Locator AddBtnInBulkUpload;
    private final Locator updateBtnInBulkUpload;
    private final Locator deleteBtnInBulkUpload;

    public UserManagementPage(Page page) {
        super(page);
        this.addUserBtn = page.locator(".addUserSubmenu");
        this.loadingOverlay = page.locator("#fade");
        this.serviceLevel = page.locator("#type");
        this.firstNameInput = page.locator("#firstName");
        this.lastNameInput = page.locator("#lastName");
        this.mobileCountrySelect = page.locator("#mobileCountry1");
        this.mobileTelephoneInput = page.locator("#mobileTelephone1");
        this.communicationEmailInput = page.locator("#communicationEmail");
        this.usernameInput = page.locator("#username");
        this.passwordInput = page.locator("#password");
        this.uccInput = page.locator("#ucc");
        this.subManagerCheckbox = page.locator("#account_sub_manager");
        this.addToGlobalAddressBookCheckbox = page.locator("#gl_address_book");
        this.saveBtn = page.locator("#updateBtn");
        this.addUserSummaryPopUp = page.locator("#import_users_response");
        this.addUserSummaryPopUpSuccessUsers = page.locator("#successUsers");
        this.addUserSummaryPopUpArchivePlanMessage = page.locator("#assignedArchivePlanMessage");
        this.bulkUploadSummaryContent = page.locator("#import_popup_content");
        this.closeErrorsSummaryBtn = page.locator("#closeErrorsSummaryBtn");
        this.searchInp = page.locator("#searchInp");
        this.searchBtn = page.locator("#searchContactsSbm");
        this.contentTable = page.locator("#contentTable");
        this.deleteBtn = page.locator("#deleteBtn");
        this.suspendBtn = page.locator("#suspendBtn");
        this.activateBtn = page.locator("#activateBtn");
        this.confirmYesBtn = page.locator("#confirmYesBtn");
        this.errorMsg = page.locator("#errorMsg");
        this.successMsg = page.locator("#successMsg");
        this.languageSelect = page.locator("#languageID");
        this.timeZoneSelect = page.locator("#timeZone");
        this.countrySelect = page.locator("#countryID");
        this.checkAll = page.locator("#checkAll");
        this.bulkActionsBtn = page.locator(".importSubmenu");
        this.addUsersBtn = page.locator("#newSubMenuDiv .submenuDropdownItem").filter(new Locator.FilterOptions().setHasText("Add Users"));
        this.updateUsersBtn = page.locator("#newSubMenuDiv .submenuDropdownItem").filter(new Locator.FilterOptions().setHasText("Update Users"));
        this.deleteUsersBtn = page.locator("#newSubMenuDiv .submenuDropdownItem").filter(new Locator.FilterOptions().setHasText("Delete Users"));
        this.addUsersFromFilePopUp = page.locator("#confirmationDivContainer");
        this.bulkActionsServiceLevelSelect = page.locator("#utSelect");
        this.fileUploadInput = page.locator("#fileUpload");
        this.AddBtnInBulkUpload = page.locator("#loadUsers");
        this.updateBtnInBulkUpload = page.locator("#loadUsers");
        this.deleteBtnInBulkUpload = page.locator("#loadUsers");
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
     * Click on Add User button
     */
    public UserManagementPage clickAddUser() {
        log.info("Clicking Add User button");
        addUserBtn.click();
        waitForLoadingToDisappear();
        return this;
    }

    /**
     * Select service level by enum UserTypes
     * @param userType The usertype to select
     */
    public UserManagementPage selectServiceLevel(UserTypes userType) {
        log.info("Selecting service level: {}", userType.getDisplayName());
        serviceLevel.selectOption(userType.getValue());
        waitForLoadingToDisappear();
        return this;
    }

    /**
     * Select language by enum Language
     * @param language The language to select
     */
    public UserManagementPage selectLanguage(com.websanity.enums.Language language) {
        log.info("Selecting language: {}", language.getDisplayName());
        languageSelect.selectOption(language.getValue());
        waitForLoadingToDisappear();
        return this;
    }

    /**
     * Get currently selected language from selectbox
     * @return Language enum value
     */
    public com.websanity.enums.Language getSelectedLanguage() {
        try {
            String languageValue = languageSelect.inputValue();
            com.websanity.enums.Language language = com.websanity.enums.Language.getByValue(languageValue);
            log.info("Selected language: {}", language.getDisplayName());
            return language;
        } catch (Exception e) {
            log.error("Failed to get selected language: {}", e.getMessage());
            return com.websanity.enums.Language.EMPTY;
        }
    }

    /**
     * Select time zone by enum TimeZone
     * @param timeZone The time zone to select
     */
    public UserManagementPage selectTimeZone(com.websanity.enums.TimeZone timeZone) {
        log.info("Selecting time zone: {}", timeZone.getDisplayName());
        timeZoneSelect.selectOption(timeZone.getValue());
        waitForLoadingToDisappear();
        return this;
    }

    /**
     * Get currently selected time zone from selectbox
     * @return TimeZone enum value
     */
    public com.websanity.enums.TimeZone getSelectedTimeZone() {
        try {
            String timeZoneValue = timeZoneSelect.inputValue();
            com.websanity.enums.TimeZone timeZone = com.websanity.enums.TimeZone.getByValue(timeZoneValue);
            log.info("Selected time zone: {}", timeZone.getDisplayName());
            return timeZone;
        } catch (Exception e) {
            log.error("Failed to get selected time zone: {}", e.getMessage());
            return com.websanity.enums.TimeZone.UTC;
        }
    }

    /**
     * Select country by enum Country
     * @param country The country to select
     */
    public UserManagementPage selectCountry(com.websanity.enums.Country country) {
        log.info("Selecting country: {}", country.getDisplayName());
        countrySelect.selectOption(country.getCode());
        waitForLoadingToDisappear();
        return this;
    }

    /**
     * Get currently selected country from selectbox
     * @return Country enum value
     */
    public com.websanity.enums.Country getSelectedCountry() {
        try {
            String countryCode = countrySelect.inputValue();
            com.websanity.enums.Country country = com.websanity.enums.Country.getByCode(countryCode);
            log.info("Selected country: {}", country.getDisplayName());
            return country;
        } catch (Exception e) {
            log.error("Failed to get selected country: {}", e.getMessage());
            return com.websanity.enums.Country.ALL_COUNTRIES;
        }
    }

    /**
     * Fill in first name
     * @param firstName The first name to fill in
     */
    public UserManagementPage fillFirstName(String firstName) {
        log.info("Filling first name: {}", firstName);
        firstNameInput.clear();
        firstNameInput.fill(firstName);
        return this;
    }

    /**
     * Fill in last name
     * @param lastName The last name to fill in
     */
    public UserManagementPage fillLastName(String lastName) {
        log.info("Filling last name: {}", lastName);
        lastNameInput.clear();
        lastNameInput.fill(lastName);
        return this;
    }

    /**
     * Fill in mobile phone - select country and enter phone number
     * @param country The country enum to select
     * @param phoneNumber The phone number to fill in
     */
    public UserManagementPage fillMobilePhone(com.websanity.enums.Country country, String phoneNumber) {
        log.info("Filling mobile phone: {} - {}", country.getDisplayName(), phoneNumber);
        mobileCountrySelect.selectOption(country.getCode());
        mobileTelephoneInput.clear();
        mobileTelephoneInput.fill(phoneNumber);
        return this;
    }

    /**
     * Fill in communication email
     * @param email The email address to fill in
     */
    public UserManagementPage fillEmail(String email) {
        log.info("Filling communication email: {}", email);
        communicationEmailInput.clear();
        communicationEmailInput.fill(email);
        return this;
    }

    /**
     * Fill in username
     * @param username The username to fill in
     */
    public UserManagementPage fillUsername(String username) {
        log.info("Filling username: {}", username);
        usernameInput.clear();
        usernameInput.fill(username);
        return this;
    }

    /**
     * Fill in password
     * @param password The password to fill in
     */
    public UserManagementPage fillPassword(String password) {
        log.info("Filling password");
        passwordInput.clear();
        passwordInput.fill(password);
        return this;
    }

    /**
     * Fill in UCC (User Cost Center)
     * @param ucc The UCC value to fill in
     */
    public UserManagementPage fillUcc(String ucc) {
        log.info("Filling UCC: {}", ucc);
        uccInput.clear();
        uccInput.fill(ucc);
        return this;
    }

    /**
     * Click on Sub Manager checkbox
     */
    public UserManagementPage clickSubManagerCheckbox() {
        log.info("Clicking Sub Manager checkbox");
        subManagerCheckbox.click();
        return this;
    }

    /**
     * Click on Add to Global Address Book checkbox
     */
    public UserManagementPage clickAddToGlobalAddressBookCheckbox() {
        log.info("Clicking Add to Global Address Book checkbox");
        addToGlobalAddressBookCheckbox.click();
        return this;
    }

    /**
     * Click on Save button
     */
    public UserManagementPage clickSaveBtn() {
        log.info("Clicking Save button");
        saveBtn.click();
        page.waitForTimeout(1000);
        return this;
    }

    /**
     * Click on Save button
     */
    public UserManagementPage clickSaveBtnAndWaitSummaryPopUp() {
        log.info("Clicking Save button");
        saveBtn.click();
        log.info("Waiting for Add User Summary popup to appear");
        addUserSummaryPopUp.waitFor(new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                .setTimeout(30000));
        return this;
    }

    /**
     * Check if Add User Summary popup is visible
     * @return true if popup is visible, false otherwise
     */
    public boolean isAddUserSummaryPopUpVisible() {
        try {
            return addUserSummaryPopUp.isVisible();
        } catch (Exception e) {
            log.debug("Add User Summary popup not visible: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Get text from Add User Summary popup success users element
     * @return text content of the success users element
     */
    public String getAddUserSummaryPopUpSuccessUsersText() {
        try {
            String text = addUserSummaryPopUpSuccessUsers.textContent();
            log.info("Success users text: {}", text);
            return text;
        } catch (Exception e) {
            log.error("Failed to get success users text: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Get text from Assigned Archive Plan Message element
     * @return full text content of the archive plan message (trimmed)
     */
    public String getAssignedArchivePlanMessageText() {
        try {
            String text = addUserSummaryPopUpArchivePlanMessage.textContent();
            // Trim and clean up the text (remove extra whitespaces and line breaks)
            text = text.trim().replaceAll("\\s+", " ");
            log.info("Archive plan message text: {}", text);
            return text;
        } catch (Exception e) {
            log.error("Failed to get archive plan message text: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Click on Close Errors Summary button and verify popup is closed
     */
    public UserManagementPage clickCloseErrorsSummaryBtn() {
        log.info("Clicking Close Errors Summary button");
        closeErrorsSummaryBtn.click();

        // Verify the popup is no longer visible
        addUserSummaryPopUp.waitFor(new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.HIDDEN)
                .setTimeout(5000));
        log.info("Errors summary popup is now hidden");

        return this;
    }

    /**
     * Fill in search input
     * @param searchText The text to search for
     */
    public UserManagementPage fillSearchInp(String searchText) {
        log.info("Filling search input: {}", searchText);
        searchInp.clear();
        searchInp.fill(searchText);
        return this;
    }

    /**
     * Click on Search Contacts Submit button
     */
    public UserManagementPage clickSearchBtn() {
        log.info("Clicking Search Contacts Submit button");
        searchBtn.click();
        page.waitForTimeout(1000);
        return this;
    }

    /**
     * Get first name from the first user row in the table
     * @return first name of the user
     */
    public String getFirstUserFirstName() {
        try {
            // Get second tr (index 1) - first data row, then get 5th td (index 4) - First Name column
            String firstName = contentTable.locator("tr").nth(1).locator("td").nth(4).textContent().trim();
            log.info("First user first name: {}", firstName);
            return firstName;
        } catch (Exception e) {
            log.error("Failed to get first name: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Get last name from the first user row in the table
     * @return last name of the user
     */
    public String getFirstUserLastName() {
        try {
            // Get second tr (index 1) - first data row, then get 6th td (index 5) - Last Name column
            String lastName = contentTable.locator("tr").nth(1).locator("td").nth(5).textContent().trim();
            log.info("First user last name: {}", lastName);
            return lastName;
        } catch (Exception e) {
            log.error("Failed to get last name: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Get username from the first user row in the table
     * @return username of the user
     */
    public String getFirstUserUsername() {
        try {
            // Get second tr (index 1) - first data row, then get 7th td (index 6) - Username column
            String username = contentTable.locator("tr").nth(1).locator("td").nth(6).textContent().trim();
            log.info("First user username: {}", username);
            return username;
        } catch (Exception e) {
            log.error("Failed to get username: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Click on username of the first user in the table
     */
    public UserManagementPage clickFirstUserUsername() {
        try {
            log.info("Clicking first user username");
            // Get second tr (index 1) - first data row, then get 7th td (index 6) - Username column
            contentTable.locator("tr").nth(1).locator("td").nth(6).click();
            page.waitForTimeout(500);
            return this;
        } catch (Exception e) {
            log.error("Failed to click username: {}", e.getMessage());
            return this;
        }
    }

    /**
     * Get service level from the first user row in the table
     * @return service level of the user
     */
    public String getFirstUserServiceLevel() {
        try {
            // Get second tr (index 1) - first data row, then get 4th td (index 3) - Service Level column
            String serviceLevel = contentTable.locator("tr").nth(1).locator("td").nth(3).textContent().trim();
            log.info("First user service level: {}", serviceLevel);
            return serviceLevel;
        } catch (Exception e) {
            log.error("Failed to get service level: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Get email from the first user row in the table
     * @return email of the user
     */
    public String getFirstUserEmail() {
        try {
            // Get second tr (index 1) - first data row, then get 8th td (index 7) - Email column
            String email = contentTable.locator("tr").nth(1).locator("td").nth(7).textContent().trim();
            log.info("First user email: {}", email);
            return email;
        } catch (Exception e) {
            log.error("Failed to get email: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Get mobile from the first user row in the table
     * @return mobile number of the user
     */
    public String getFirstUserMobile() {
        try {
            // Get second tr (index 1) - first data row, then get 9th td (index 8) - Mobile column
            String mobile = contentTable.locator("tr").nth(1).locator("td").nth(8).textContent().trim();
            log.info("First user mobile: {}", mobile);
            return mobile;
        } catch (Exception e) {
            log.error("Failed to get mobile: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Get status from the first user row in the table
     * @return status of the user (e.g., "Activated")
     */
    public String getFirstUserStatus() {
        try {
            // Get second tr (index 1) - first data row, then get 2nd td (index 1) - Status column
            // Get the img element's title attribute
            String status = contentTable.locator("tr").nth(1).locator("td").nth(1).locator("img").getAttribute("title");
            log.info("First user status: {}", status);
            return status;
        } catch (Exception e) {
            log.error("Failed to get status: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Click on checkbox for the first user in the table
     */
    public UserManagementPage clickFirstUserCheckbox() {
        try {
            log.info("Clicking first user checkbox");
            // Get second tr (index 1) - first data row, then get 1st td (index 0) - Checkbox column
            contentTable.locator("tr").nth(1).locator("td").nth(0).locator("input[type='checkbox']").click();
            return this;
        } catch (Exception e) {
            log.error("Failed to click checkbox: {}", e.getMessage());
            return this;
        }
    }

    /**
     * Check if first user checkbox is selected
     * @return true if checkbox is checked, false otherwise
     */
    public boolean isFirstUserCheckboxSelected() {
        try {
            boolean isChecked = contentTable.locator("tr").nth(1).locator("td").nth(0).locator("input[type='checkbox']").isChecked();
            log.info("First user checkbox is checked: {}", isChecked);
            return isChecked;
        } catch (Exception e) {
            log.error("Failed to check checkbox state: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Click on Check All checkbox to select/deselect all users
     */
    public UserManagementPage clickCheckAll() {
        log.info("Clicking Check All checkbox");
        checkAll.click();
        return this;
    }

    /**
     * Check if Check All checkbox is selected
     * @return true if checkbox is checked, false otherwise
     */
    public boolean isCheckAllSelected() {
        try {
            boolean isChecked = checkAll.isChecked();
            log.info("Check All checkbox is checked: {}", isChecked);
            return isChecked;
        } catch (Exception e) {
            log.error("Failed to check Check All checkbox state: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Click on Delete button
     */
    public UserManagementPage clickDeleteBtn() {
        log.info("Clicking Delete button");
        deleteBtn.click();
        return this;
    }

    /**
     * Click on Suspend button
     */
    public UserManagementPage clickSuspendBtn() {
        log.info("Clicking Suspend button");
        suspendBtn.click();
        waitForLoadingToDisappear();
        return this;
    }

    /**
     * Click on Activate button
     */
    public UserManagementPage clickActivateBtn() {
        log.info("Clicking Activate button");
        activateBtn.click();
        waitForLoadingToDisappear();
        return this;
    }

    /**
     * Click on Confirm Yes button
     */
    public UserManagementPage clickConfirmYesBtn() {
        log.info("Clicking Confirm Yes button");
        confirmYesBtn.click();
        waitForLoadingToDisappear();
        page.waitForTimeout(500);
        return this;
    }

    /**
     * Get error message text
     * @return text content of the error message element
     */
    public String getErrorMsgText() {
        try {
            String text = errorMsg.textContent().trim();
            log.info("Error message text: {}", text);
            return text;
        } catch (Exception e) {
            log.error("Failed to get error message text: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Check if error message is visible
     * @return true if error message is visible, false otherwise
     */
    public boolean isErrorMsgVisible() {
        try {
            boolean isVisible = errorMsg.isVisible();
            log.info("Error message is visible: {}", isVisible);
            return isVisible;
        } catch (Exception e) {
            log.debug("Error message not visible: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Check if content table is empty (has no data rows, only header row)
     * @return true if table has no data rows, false otherwise
     */
    public boolean isContentTableEmpty() {
        try {
            int rowCount = contentTable.locator("tr").count();
            // rowCount = 1 means only header row exists (no data rows)
            boolean isEmpty = rowCount <= 1;
            log.info("Content table is empty: {} (row count: {})", isEmpty, rowCount);
            return isEmpty;
        } catch (Exception e) {
            log.error("Failed to check if content table is empty: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Get success message text
     * @return text content of the success message element
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

    /**
     * Click on Bulk Actions button
     */
    public UserManagementPage clickBulkActionsBtn() {
        log.info("Clicking Bulk Actions button");
        bulkActionsBtn.click();
        waitForLoadingToDisappear();
        return this;
    }

    /**
     * Click on Add Users button in the bulk actions dropdown
     */
    public UserManagementPage clickAddUsersBtn() {
        log.info("Clicking Add Users button in bulk actions dropdown");
        addUsersBtn.click();

        // Wait for the Add Users from File popup to be visible
        addUsersFromFilePopUp.waitFor(new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                .setTimeout(5000));
        log.info("Add Users from File popup is visible");

        return this;
    }

    /**
     * Click on Update Users button in the bulk actions dropdown
     */
    public UserManagementPage clickUpdateUsersBtn() {
        log.info("Clicking Update Users button in bulk actions dropdown");
        updateUsersBtn.click();

        // Wait for the Add Users from File popup to be visible
        addUsersFromFilePopUp.waitFor(new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                .setTimeout(5000));
        log.info("Add Users from File popup is visible");

        return this;
    }

    /**
     * Click on Delete Users button in the bulk actions dropdown
     */
    public UserManagementPage clickDeleteUsersBtn() {
        log.info("Clicking Delete Users button in bulk actions dropdown");
        deleteUsersBtn.click();

        // Wait for the Add Users from File popup to be visible
        addUsersFromFilePopUp.waitFor(new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                .setTimeout(5000));
        log.info("Add Users from File popup is visible");
        return this;
    }

    /**
     * Select service level in the bulk actions popup
     * @param userType The user type to select (should be PROUSER or SUB_MANAGER for bulk actions)
     */
    public UserManagementPage selectBulkActionsServiceLevel(UserTypes userType) {
        log.info("Selecting bulk actions service level: {}", userType.getDisplayName());
        bulkActionsServiceLevelSelect.selectOption(userType.getValue());
        page.waitForTimeout(500);
        return this;
    }

    /**
     * Upload a file for bulk user actions
     * @param filePath The absolute path to the file to upload (CSV, XLS, or XLSX)
     */
    public UserManagementPage uploadFile(String filePath) {
        log.info("Uploading file: {}", filePath);
        fileUploadInput.setInputFiles(java.nio.file.Paths.get(filePath));
        page.waitForTimeout(1000); // Wait for file to be processed
        return this;
    }

    /**
     * Click on Load Users button (Add button in the bulk actions popup)
     */
    public UserManagementPage clickAddBtnInBulkUpload() {

        log.info("Clicking Add button");
        AddBtnInBulkUpload.click();

        // Wait for the bulk upload summary popup to be visible
        addUserSummaryPopUp.waitFor(new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                .setTimeout(30000));
        log.info("Bulk upload summary popup is visible");

        return this;
    }

    /**
     * Click on Load Users button (Update button in the bulk actions popup)
     */
    public UserManagementPage clickUpdateBtnInBulkUpload() {

        log.info("Clicking Update button");
        updateBtnInBulkUpload.click();

        // Wait for the bulk upload summary popup to be visible
        addUserSummaryPopUp.waitFor(new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                .setTimeout(30000));
        log.info("Bulk upload summary popup is visible");

        return this;
    }

    /**
     * Click on Load Users button (Delete button in the bulk actions popup)
     */
    public UserManagementPage clickDeleteBtnInBulkUpload() {

        log.info("Clicking Update button");
        deleteBtnInBulkUpload.click();

        // Wait for the bulk upload summary popup to be visible
        addUserSummaryPopUp.waitFor(new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                .setTimeout(30000));
        log.info("Bulk upload summary popup is visible");

        return this;
    }

    /**
     * Get text from bulk upload summary popup
     * @return summary text content
     */
    public String getBulkUploadSummaryText() {
        try {
            String text = bulkUploadSummaryContent.textContent().trim();
            log.info("Bulk upload summary text: {}", text);
            return text;
        } catch (Exception e) {
            log.error("Failed to get bulk upload summary text: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Check if bulk upload summary contains expected text
     * @param expectedText the text to check for
     * @return true if the summary contains the expected text
     */
    public boolean isBulkUploadSummaryContains(String expectedText) {
        String summaryText = getBulkUploadSummaryText();
        boolean contains = summaryText.contains(expectedText);
        log.info("Summary contains '{}': {}", expectedText, contains);
        return contains;
    }

    /**
     * Check if Add Users from File popup is visible
     * @return true if popup is visible, false otherwise
     */
    public boolean isAddUsersFromFilePopUpVisible() {
        try {
            boolean isVisible = addUsersFromFilePopUp.isVisible();
            log.info("Add Users from File popup is visible: {}", isVisible);
            return isVisible;
        } catch (Exception e) {
            log.debug("Add Users from File popup not visible: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Check if specific users exist in the content table by their usernames
     * @param usernames List of usernames to verify
     * @return true if all users exist in the table, false otherwise
     */
    public boolean areUsersExistInTable(java.util.List<String> usernames) {
        log.info("Verifying {} users exist in the table", usernames.size());

        try {
            // Get all username cells from the table (7th column, index 6)
            Locator usernameLinks = contentTable.locator("tr td:nth-child(7) a");
            int count = usernameLinks.count();

            // Collect all usernames from the table
            java.util.List<String> tableUsernames = new java.util.ArrayList<>();
            for (int i = 0; i < count; i++) {
                String username = usernameLinks.nth(i).getAttribute("title");
                if (username != null && !username.isEmpty()) {
                    tableUsernames.add(username);
                }
            }

            log.info("Found {} users in the table: {}", tableUsernames.size(), tableUsernames);

            // Check if all expected usernames exist in the table
            boolean allExist = true;
            for (String username : usernames) {
                if (tableUsernames.contains(username)) {
                    log.info("✓ User '{}' found in table", username);
                } else {
                    log.error("✗ User '{}' NOT found in table", username);
                    allExist = false;
                }
            }

            return allExist;
        } catch (Exception e) {
            log.error("Failed to verify users in table: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Check if specific users exist in the content table by their usernames (varargs version)
     * @param usernames Usernames to verify
     * @return true if all users exist in the table, false otherwise
     */
    public boolean areUsersExistInTable(String... usernames) {
        return areUsersExistInTable(java.util.Arrays.asList(usernames));
    }

    /**
     * Verify that the table contains only one user and that user is a manager
     * Manager types include: "Pro Manager", "Standard Manager", "Basic Manager"
     * @return true if only one user exists and it's a manager, false otherwise
     */
    public boolean isOnlyOneManagerInTable() {
        log.info("Verifying that table contains only one user and it's a manager");

        try {
            // Get all table rows (excluding header)
            Locator tableRows = contentTable.locator("tr").filter(new Locator.FilterOptions().setHas(page.locator("td")));
            int rowCount = tableRows.count();

            log.info("Found {} user(s) in the table", rowCount);

            // Check if only one user exists
            if (rowCount != 1) {
                log.error("Expected 1 user, but found {}", rowCount);
                return false;
            }

            // Get the service level of the single user (4th column, index 3)
            Locator serviceLevelCell = tableRows.first().locator("td:nth-child(4) a");
            String serviceLevel = serviceLevelCell.getAttribute("title");

            log.info("User service level: {}", serviceLevel);

            // Check if service level contains "Manager"
            boolean isManager = serviceLevel != null && serviceLevel.contains("Pro Manager");

            if (isManager) {
                log.info("✓ Table contains only one user and it's a manager: {}", serviceLevel);
            } else {
                log.error("✗ Table contains only one user but it's NOT a manager: {}", serviceLevel);
            }

            return isManager;

        } catch (Exception e) {
            log.error("Failed to verify single manager in table: {}", e.getMessage());
            return false;
        }
    }

}
