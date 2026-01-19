package com.websanity.adminPortalPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.websanity.BasePage;
import com.websanity.enums.Country;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyContactsPage extends BasePage {

    private final Locator newBtn;
    private final Locator loadingOverlay;
    private final Locator newSubMenuDiv;
    private final Locator newContactLink;
    private final Locator confirmYesBtn;
    private final Locator successMsg;

    // Contact form inputs
    private final Locator firstNameInput;
    private final Locator lastNameInput;
    private final Locator jobTitleInput;
    private final Locator mobileCountry1Select;
    private final Locator homeCountry1Select;
    private final Locator businessCountry1Select;
    private final Locator faxCountry1Select;
    private final Locator mobileTelephone1Input;
    private final Locator homeTelephone1Input;
    private final Locator businessTelephone1Input;
    private final Locator businessExt1Input;
    private final Locator faxTelephone1Input;
    private final Locator communicationEmailInput;
    private final Locator companyInput;
    private final Locator departmentInput;
    private final Locator saveBtn;

    // Contacts list table
    private final Locator contactsListTable;
    private final Locator firstContactRow;

    // Search elements
    private final Locator searchInp;
    private final Locator searchBtn;

    // Action buttons
    private final Locator moreBtn;
    private final Locator deleteSelectedContactBtn;

    public MyContactsPage(Page page) {
        super(page);
        this.newBtn = page.locator("li.createNewSubMenu, li.newSubmenu");
        this.loadingOverlay = page.locator("#fade");
        this.newSubMenuDiv = page.locator("#newSubMenuDiv");
        this.confirmYesBtn = page.locator("#confirmYesBtn:not([onclick])").first();
        this.successMsg = page.locator("#successMsg");
        this.newContactLink = newSubMenuDiv.locator("a:has-text('New Contact')");

        // Initialize form inputs
        this.firstNameInput = page.locator("#firstName");
        this.lastNameInput = page.locator("#lastName");
        this.jobTitleInput = page.locator("#jobtitle");
        this.mobileCountry1Select = page.locator("#mobileCountry1");
        this.homeCountry1Select = page.locator("#homeCountry1");
        this.businessCountry1Select = page.locator("#businessCountry1");
        this.faxCountry1Select = page.locator("#faxCountry1");
        this.mobileTelephone1Input = page.locator("#mobileTelephone1");
        this.homeTelephone1Input = page.locator("#homeTelephone1");
        this.businessTelephone1Input = page.locator("#businessTelephone1");
        this.businessExt1Input = page.locator("#businessExt1");
        this.faxTelephone1Input = page.locator("#faxTelephone1");
        this.communicationEmailInput = page.locator("#communicationEmail");
        this.companyInput = page.locator("#company");
        this.departmentInput = page.locator("#department");
        this.saveBtn = page.locator("#createBtn");

        // Contacts list table
        this.contactsListTable = page.locator("#contactsListTable");
        this.firstContactRow = contactsListTable.locator("tbody tr.atRow").first();

        // Search elements
        this.searchInp = page.locator("#searchInp");
        this.searchBtn = page.locator("#searchContactsSbm");

        // Action buttons
        this.moreBtn = page.locator("#moreContactsClick");
        this.deleteSelectedContactBtn = page.locator("#deleteFromGroupLink");
    }

    /**
     * Click on New button
     * @return AdminPortalMyContactsPage
     */
    public MyContactsPage clickNewBtn() {
        log.info("Clicking New button");
        newBtn.click();
        return this;
    }

    /**
     * Click on New Contact from dropdown menu
     * @return AdminPortalMyContactsPage
     */
    public MyContactsPage clickNewContact() {
        log.info("Clicking New Contact");
        newContactLink.click();
        waitForLoadingToDisappear();
        return this;
    }

    /**
     * Fill First Name input
     * @param firstName First name value
     * @return AdminPortalMyContactsPage
     */
    public MyContactsPage fillFirstName(String firstName) {
        log.info("Filling First Name: {}", firstName);
        firstNameInput.fill(firstName);
        return this;
    }

    /**
     * Fill Last Name input
     * @param lastName Last name value
     * @return AdminPortalMyContactsPage
     */
    public MyContactsPage fillLastName(String lastName) {
        log.info("Filling Last Name: {}", lastName);
        lastNameInput.fill(lastName);
        return this;
    }

    /**
     * Fill Job Title input
     * @param jobTitle Job title value
     * @return AdminPortalMyContactsPage
     */
    public MyContactsPage fillJobTitle(String jobTitle) {
        log.info("Filling Job Title: {}", jobTitle);
        jobTitleInput.fill(jobTitle);
        return this;
    }

    /**
     * Select Mobile Country from dropdown
     * @param country Country enum value
     * @return AdminPortalMyContactsPage
     */
    public MyContactsPage selectMobileCountry(Country country) {
        log.info("Selecting Mobile Country: {}", country.getDisplayName());
        mobileCountry1Select.selectOption(country.getDisplayName());
        return this;
    }

    /**
     * Select Home Country from dropdown
     * @param country Country enum value
     * @return AdminPortalMyContactsPage
     */
    public MyContactsPage selectHomeCountry(Country country) {
        log.info("Selecting Home Country: {}", country.getDisplayName());
        homeCountry1Select.selectOption(country.getDisplayName());
        return this;
    }

    /**
     * Select Business Country from dropdown
     * @param country Country enum value
     * @return AdminPortalMyContactsPage
     */
    public MyContactsPage selectBusinessCountry(Country country) {
        log.info("Selecting Business Country: {}", country.getDisplayName());
        businessCountry1Select.selectOption(country.getDisplayName());
        return this;
    }

    /**
     * Select Fax Country from dropdown
     * @param country Country enum value
     * @return AdminPortalMyContactsPage
     */
    public MyContactsPage selectFaxCountry(Country country) {
        log.info("Selecting Fax Country: {}", country.getDisplayName());
        faxCountry1Select.selectOption(country.getDisplayName());
        return this;
    }

    /**
     * Fill Mobile Telephone input
     * @param telephone Mobile telephone number
     * @return AdminPortalMyContactsPage
     */
    public MyContactsPage fillMobileTelephone(String telephone) {
        log.info("Filling Mobile Telephone: {}", telephone);
        mobileTelephone1Input.fill(telephone);
        return this;
    }

    /**
     * Fill Home Telephone input
     * @param telephone Home telephone number
     * @return AdminPortalMyContactsPage
     */
    public MyContactsPage fillHomeTelephone(String telephone) {
        log.info("Filling Home Telephone: {}", telephone);
        homeTelephone1Input.fill(telephone);
        return this;
    }

    /**
     * Fill Business Telephone input
     * @param telephone Business telephone number
     * @return AdminPortalMyContactsPage
     */
    public MyContactsPage fillBusinessTelephone(String telephone) {
        log.info("Filling Business Telephone: {}", telephone);
        businessTelephone1Input.fill(telephone);
        return this;
    }

    /**
     * Fill Business Extension input
     * @param extension Business extension number
     * @return AdminPortalMyContactsPage
     */
    public MyContactsPage fillBusinessExt(String extension) {
        log.info("Filling Business Extension: {}", extension);
        businessExt1Input.fill(extension);
        return this;
    }

    /**
     * Fill Fax Telephone input
     * @param telephone Fax telephone number
     * @return AdminPortalMyContactsPage
     */
    public MyContactsPage fillFaxTelephone(String telephone) {
        log.info("Filling Fax Telephone: {}", telephone);
        faxTelephone1Input.fill(telephone);
        return this;
    }

    /**
     * Fill Communication Email input
     * @param email Communication email address
     * @return AdminPortalMyContactsPage
     */
    public MyContactsPage fillCommunicationEmail(String email) {
        log.info("Filling Communication Email: {}", email);
        communicationEmailInput.fill(email);
        return this;
    }

    /**
     * Fill Company input
     * @param company Company name
     * @return AdminPortalMyContactsPage
     */
    public MyContactsPage fillCompany(String company) {
        log.info("Filling Company: {}", company);
        companyInput.fill(company);
        return this;
    }

    /**
     * Fill Department input
     * @param department Department name
     * @return AdminPortalMyContactsPage
     */
    public MyContactsPage fillDepartment(String department) {
        log.info("Filling Department: {}", department);
        departmentInput.fill(department);
        return this;
    }

    /**
     * Click on Save button
     * @return AdminPortalMyContactsPage
     */
    public MyContactsPage clickSaveBtn() {
        log.info("Clicking Save button");
        saveBtn.click();
        page.waitForTimeout(1000);
        return this;
    }

    // ========================================================================
    // SEARCH METHODS
    // ========================================================================

    /**
     * Fill Search input field
     * @param searchText Text to search for
     * @return AdminPortalMyContactsPage
     */
    public MyContactsPage fillSearchInp(String searchText) {
        log.info("Filling Search input: {}", searchText);
        searchInp.fill(searchText);
        return this;
    }

    /**
     * Click on Search Contacts Submit button
     * @return AdminPortalMyContactsPage
     */
    public MyContactsPage clickSearchBtn() {
        log.info("Clicking Search Contacts Submit button");
        searchBtn.click();
        return this;
    }

    /**
     * Click on More Contacts button
     * @return AdminPortalMyContactsPage
     */
    public MyContactsPage clickMoreBtn() {
        log.info("Clicking More button");
        moreBtn.click();
        return this;
    }

    /**
     * Click on Delete Selected Contact button
     * @return AdminPortalMyContactsPage
     */
    public MyContactsPage clickDeleteSelectedContactBtn() {
        log.info("Clicking Delete Selected Contact button");
        deleteSelectedContactBtn.click();
        waitForLoadingToDisappear();
        return this;
    }

    // ========================================================================
    // CONTACTS LIST TABLE METHODS (#contactsListTable)
    // ========================================================================

    /**
     * Wait for contacts list table to be visible
     * @return AdminPortalMyContactsPage
     */
    public MyContactsPage waitForContactsListToLoad() {
        log.info("Waiting for contacts list table to load");
        contactsListTable.waitFor(new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE));
        return this;
    }

    /**
     * Click on the first contact checkbox (column 0)
     * @return AdminPortalMyContactsPage
     */
    public MyContactsPage clickFirstContactCheckbox() {
        log.info("Clicking first contact checkbox");
        firstContactRow.locator("td").first().locator("div.checkbox_container").click();
        return this;
    }

    /**
     * Get text from specific cell in the first contact row
     * @param cellIndex 0-based index (0=checkbox, 1=name, 2=department, 3=mobile, 4=home, 5=business, 6=fax, 7=email, 8=groups)
     * @return Text content of the cell
     */
    public String getFirstContactCellText(int cellIndex) {
        log.info("Getting text from cell {} in first contact row", cellIndex);
        return firstContactRow.locator("td").nth(cellIndex).textContent().trim();
    }

    /**
     * Get first contact name (First + Last name)
     * @return Contact name (e.g., "confn5039568 conln5039568")
     */
    public String getFirstContactName() {
        log.info("Getting first contact name");
        return firstContactRow.locator("td.contactsName a").textContent().trim();
    }

    /**
     * Click on Confirm Yes button
     */
    public MyContactsPage clickConfirmYesBtn() {
        log.info("Clicking Confirm Yes button");
        confirmYesBtn.click();
        waitForLoadingToDisappear();
        page.waitForTimeout(500);
        return this;
    }

    /**
     * Get first contact department
     * @return Department name
     */
    public String getFirstContactDepartment() {
        log.info("Getting first contact department");
        return firstContactRow.locator("td.department div").textContent().trim();
    }

    /**
     * Get first contact mobile phone
     * @return Mobile phone (e.g., "972-58-5039568")
     */
    public String getFirstContactMobile() {
        log.info("Getting first contact mobile phone");
        return firstContactRow.locator("td.phoneCell").nth(0).locator("div").textContent().trim();
    }

    /**
     * Get first contact home phone
     * @return Home phone (e.g., "1-607-5039568")
     */
    public String getFirstContactHome() {
        log.info("Getting first contact home phone");
        return firstContactRow.locator("td.phoneCell").nth(1).locator("div").textContent().trim();
    }

    /**
     * Get first contact business phone
     * @return Business phone (e.g., "375-296-299929 Ext. 321")
     */
    public String getFirstContactBusiness() {
        log.info("Getting first contact business phone");
        return firstContactRow.locator("td.phoneCell").nth(2).locator("div").textContent().trim();
    }

    /**
     * Get first contact fax phone
     * @return Fax phone
     */
    public String getFirstContactFax() {
        log.info("Getting first contact fax phone");
        return firstContactRow.locator("td.phoneCell").nth(3).locator("div").textContent().trim();
    }

    /**
     * Get first contact email
     * @return Email address (e.g., "webautcon5039568@gmail.com")
     */
    public String getFirstContactEmail() {
        log.info("Getting first contact email");
        return firstContactRow.locator("td.emailCell div").textContent().trim();
    }

    /**
     * Check if contacts list table is visible
     * @return true if visible, false otherwise
     */
    public boolean isContactsListVisible() {
        log.info("Checking if contacts list table is visible");
        return contactsListTable.isVisible();
    }

    /**
     * Get count of contacts in the list
     * @return Number of contact rows
     */
    public int getContactsCount() {
        log.info("Getting contacts count");
        return contactsListTable.locator("tbody tr.atRow").count();
    }

    /**
     * Check if contacts list table is empty (no contact rows)
     * @return true if table is empty (no contacts), false otherwise
     */
    public boolean isContactsListEmpty() {
        log.info("Checking if contacts list is empty");
        int contactCount = getContactsCount();
        boolean isEmpty = contactCount == 0;
        log.info("Contacts list empty: {} (count: {})", isEmpty, contactCount);
        return isEmpty;
    }

    /**
     * Verify contact data matches expected values
     * @param expectedDepartment Expected department
     * @param expectedEmail Expected email
     * @return true if all values match
     */
    public boolean verifyFirstContactData(String expectedName, String expectedDepartment, String expectedEmail) {
        log.info("Verifying first contact data - Name: {}, Department: {}, Email: {}",
                expectedName, expectedDepartment, expectedEmail);

        String actualName = getFirstContactName();
        String actualDepartment = getFirstContactDepartment();
        String actualEmail = getFirstContactEmail();

        boolean nameMatches = actualName.equals(expectedName);
        boolean departmentMatches = actualDepartment.equals(expectedDepartment);
        boolean emailMatches = actualEmail.equals(expectedEmail);

        log.info("Verification result - Name matches: {}, Department matches: {}, Email matches: {}",
                nameMatches, departmentMatches, emailMatches);

        return nameMatches && departmentMatches && emailMatches;
    }

    /**
     * Wait for loading overlay to disappear
     */
    private void waitForLoadingToDisappear() {
        try {
            loadingOverlay.waitFor(new Locator.WaitForOptions()
                    .setState(com.microsoft.playwright.options.WaitForSelectorState.HIDDEN)
                    .setTimeout(10000));
        } catch (Exception e) {
            log.debug("Loading overlay not found or already hidden");
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

}
