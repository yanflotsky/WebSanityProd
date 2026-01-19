package com.websanity.teleadminPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.websanity.BasePage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CompanyArchiveManagementPage extends BasePage {

    // Locators
    private final Locator addNewSourceButton;
    private final Locator addNewPolicyButton;
    private final Locator addNewPlanButton;
    private final Locator pleaseConfigureDomainListButton;
    private final Locator sourcePoliciesTable;
    private final Locator sourcePoliciesTableHeaderRow;
    private final Locator sourcePoliciesTableHeaders;
    private final Locator policiesTable;
    private final Locator policiesTableHeaderRow;
    private final Locator policiesTableHeaders;
    private final Locator plansTable;
    private final Locator plansTableHeaderRow;
    private final Locator plansTableHeaders;
    private final Locator addSourceButton;
    private final Locator sourceTypesListSelectBox;
    private final Locator sourcesNetworkListSelectBox;
    private final Locator sourceDescriptionInput;
    private final Locator storageSelectBox;
    private final Locator storageDescriptionInput;
    private final Locator planDescriptionInput;
    private final Locator sourceIdSelectBox;
    private final Locator policyIdSelectBox;
    // Policy parameters
    private final Locator personalArchiveSelectBox;
    private final Locator storageDestinationEmailInput;
    private final Locator participantHeaderFormatSelectBox;
    private final Locator spoofingHandlingSelectBox;
    private final Locator showLocalAddressBookNamesSelectBox;
    private final Locator addPolicyButton;
    private final Locator addPlanButton;
    // Manage Users modal/page elements
    private final Locator assignUsersTable;
    private final Locator assignUsersSearchInput;
    private final Locator assignUsersSelectAllCheckbox;
    private final Locator assignUsersApplyButton;
    private final Locator confirmActionYesButton;

    public CompanyArchiveManagementPage(Page page) {
        super(page);
        this.addNewSourceButton = page.locator("#create-source");
        this.addNewPolicyButton = page.locator("#create-policy");
        this.addNewPlanButton = page.locator("#create-plan");
        this.pleaseConfigureDomainListButton = page.locator("#configure-domain-list");
        this.sourcePoliciesTable = page.locator("#source-policies");
        this.sourcePoliciesTableHeaderRow = sourcePoliciesTable.locator("thead tr");
        this.sourcePoliciesTableHeaders = sourcePoliciesTableHeaderRow.locator("th");
        this.policiesTable = page.locator("#policies");
        this.policiesTableHeaderRow = policiesTable.locator("thead tr");
        this.policiesTableHeaders = policiesTableHeaderRow.locator("th");
        this.plansTable = page.locator("#plans");
        this.plansTableHeaderRow = plansTable.locator("thead tr");
        this.plansTableHeaders = plansTableHeaderRow.locator("th");
        this.addSourceButton = page.locator("#add-source");
        this.sourceTypesListSelectBox = page.locator("#source-types-list");
        this.sourcesNetworkListSelectBox = page.locator("#sources-network-list");
        this.sourceDescriptionInput = page.locator("#source-description");
        this.storageSelectBox = page.locator("#storage");
        this.storageDescriptionInput = page.locator("#description");
        this.planDescriptionInput = page.locator("#description");
        this.sourceIdSelectBox = page.locator("#source-policies");
        this.policyIdSelectBox = page.locator("#policyId");
        // Policy parameters
        this.personalArchiveSelectBox = page.locator("#param-60");
        this.storageDestinationEmailInput = page.locator("#param-61");
        this.participantHeaderFormatSelectBox = page.locator("tr[data-param-id='62'] select.param-value");
        this.spoofingHandlingSelectBox = page.locator("tr[data-param-id='66'] select.param-value");
        this.showLocalAddressBookNamesSelectBox = page.locator("tr[data-param-id='67'] select.param-value");
        this.addPolicyButton = page.locator("#add-policy");
        this.addPlanButton = page.locator("#add-plan");
        // Manage Users modal/page elements
        this.assignUsersTable = page.locator("#assign-users");
        this.assignUsersSearchInput = page.locator("input[aria-controls='assign-users'][type='search']");
        this.assignUsersSelectAllCheckbox = page.locator("#users-checkbox-all");
        this.assignUsersApplyButton = page.locator("#assign-users-apply");
        this.confirmActionYesButton = page.locator("#confirm-action");
    }

    /**
     * Check if Company Archive Management page is opened
     * @return true if page is opened
     */
    public boolean isCompanyArchiveManagementPageOpened() {
        try {
            addNewPlanButton.waitFor(new Locator.WaitForOptions()
                    .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                    .setTimeout(30000));
            return true;
        } catch (Exception e) {
            log.error("Company Archive Management page did not open: " + e.getMessage());
            return false;
        }
    }

    /**
     * Click on Add New Source button and wait for form to load
     */
    public CompanyArchiveManagementPage clickAddNewSourceButton() {
        log.info("Clicking Add New Source button");
        addNewSourceButton.click();

        log.info("Waiting for Create Archive Source form to load");
        sourceTypesListSelectBox.waitFor(new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                .setTimeout(30000));

        return this;
    }

    /**
     * Click on Add New Policy button
     */
    public CompanyArchiveManagementPage clickAddNewPolicyButton() {
        log.info("Clicking Add New Policy button");
        addNewPolicyButton.click();
        return this;
    }

    /**
     * Click on Add New Plan button
     */
    public CompanyArchiveManagementPage clickAddNewPlanButton() {
        log.info("Clicking Add New Plan button");
        addNewPlanButton.click();
        return this;
    }

    /**
     * Check if source policies table is visible
     * @return true if table is visible
     */
    public boolean isSourcePoliciesTableVisible() {
        try {
            return sourcePoliciesTable.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get table header text by index (0-based)
     * @param index the column index (0 = Source Policy ID, 1 = Source Type, 2 = Network Type, 3 = Description)
     * @return header text
     */
    public String getTableHeaderText(int index) {
        return sourcePoliciesTableHeaders.nth(index).textContent().trim();
    }

    /**
     * Get all table headers count
     * @return number of headers
     */
    public int getTableHeadersCount() {
        return sourcePoliciesTableHeaders.count();
    }

    /**
     * Get first row from source policies table
     * @return Locator for the first row
     */
    public Locator getFirstSourcePolicyRow() {
        return sourcePoliciesTable.locator("tbody tr").first();
    }

    /**
     * Get cell text from first row by index (0-based)
     * @param index the column index (0 = Source Policy ID, 1 = Source Type, 2 = Network Type, 3 = Description)
     * @return cell text
     */
    public String getFirstRowCellText(int index) {
        return getFirstSourcePolicyRow().locator("td").nth(index).textContent().trim();
    }

    /**
     * Get row by index (0-based)
     * @param rowIndex the row index
     * @return Locator for the row
     */
    public Locator getRowByIndex(int rowIndex) {
        return sourcePoliciesTable.locator("tbody tr").nth(rowIndex);
    }

    /**
     * Get cell text from specific row by row and column index
     * @param rowIndex the row index (0-based)
     * @param cellIndex the column index (0-based)
     * @return cell text
     */
    public String getRowCellText(int rowIndex, int cellIndex) {
        return getRowByIndex(rowIndex).locator("td").nth(cellIndex).textContent().trim();
    }

    /**
     * Click Edit button in the first row
     */
    public CompanyArchiveManagementPage clickFirstRowEditButton() {
        log.info("Clicking Edit button in first row");
        getFirstSourcePolicyRow().locator("button.edit-source-policy").click();
        return this;
    }

    /**
     * Click Delete button in the first row
     */
    public CompanyArchiveManagementPage clickFirstRowDeleteButton() {
        log.info("Clicking Delete button in first row");
        getFirstSourcePolicyRow().locator("button.delete-source-policy").click();
        return this;
    }

    /**
     * Get row by Source Policy ID
     * @param sourcePolicyId the ID to search for
     * @return Locator for the row with matching ID
     */
    public Locator getRowBySourcePolicyId(String sourcePolicyId) {
        return sourcePoliciesTable.locator("tbody tr[data-id='" + sourcePolicyId + "']");
    }

    /**
     * Click Edit button for specific source policy ID
     * @param sourcePolicyId the ID of the source policy
     */
    public CompanyArchiveManagementPage clickEditButtonForSourcePolicy(String sourcePolicyId) {
        log.info("Clicking Edit button for source policy: " + sourcePolicyId);
        getRowBySourcePolicyId(sourcePolicyId).locator("button.edit-source-policy").click();
        return this;
    }

    /**
     * Click Delete button for specific source policy ID
     * @param sourcePolicyId the ID of the source policy
     */
    public CompanyArchiveManagementPage clickDeleteButtonForSourcePolicy(String sourcePolicyId) {
        log.info("Clicking Delete button for source policy: " + sourcePolicyId);
        getRowBySourcePolicyId(sourcePolicyId).locator("button.delete-source-policy").click();
        return this;
    }

    /**
     * Select source type from dropdown
     * @param sourceType SourceType enum value
     */
    public CompanyArchiveManagementPage selectSourceType(com.websanity.enums.SourceType sourceType) {
        log.info("Selecting source type: " + sourceType.getDisplayName());
        sourceTypesListSelectBox.selectOption(sourceType.getValue());
        return this;
    }

    /**
     * Select network type from dropdown
     * @param networkType NetworkType enum value
     */
    public CompanyArchiveManagementPage selectNetworkType(com.websanity.enums.NetworkType networkType) {
        log.info("Selecting network type: " + networkType.getDisplayName());
        sourcesNetworkListSelectBox.selectOption(networkType.getValue());
        return this;
    }

    /**
     * Fill source description input field
     * @param description description to fill
     */
    public CompanyArchiveManagementPage fillSourceDescription(String description) {
        log.info("Filling source description: " + description);
        sourceDescriptionInput.clear();
        sourceDescriptionInput.fill(description);
        return this;
    }

    /**
     * Select storage type from dropdown
     * @param storage Storage enum value
     */
    public CompanyArchiveManagementPage selectStorage(com.websanity.enums.Storage storage) {
        log.info("Selecting storage: " + storage.getDisplayName());
        storageSelectBox.selectOption(storage.getValue());
        return this;
    }

    /**
     * Get currently selected storage value
     * @return selected storage value
     */
    public String getSelectedStorageValue() {
        return storageSelectBox.inputValue();
    }

    /**
     * Get currently selected storage display text
     * @return selected storage display text
     */
    public String getSelectedStorageText() {
        return storageSelectBox.locator("option:checked").textContent().trim();
    }

    /**
     * Fill storage description input field
     * @param description description to fill
     */
    public CompanyArchiveManagementPage fillStorageDescription(String description) {
        log.info("Filling storage description: " + description);
        storageDescriptionInput.clear();
        storageDescriptionInput.fill(description);
        return this;
    }

    /**
     * Get storage description value
     * @return current description value
     */
    public String getStorageDescription() {
        return storageDescriptionInput.inputValue();
    }

    // ========== Policy Parameters Methods ==========

    /**
     * Select personal archive from dropdown
     * @param yesNo YesNo enum value
     */
    public CompanyArchiveManagementPage selectPersonalArchive(com.websanity.enums.YesNo yesNo) {
        log.info("Selecting personal archive: " + yesNo.getDisplayName());
        personalArchiveSelectBox.selectOption(yesNo.getValue());
        return this;
    }

    /**
     * Get currently selected personal archive value
     * @return selected value
     */
    public String getSelectedPersonalArchiveValue() {
        return personalArchiveSelectBox.inputValue();
    }

    /**
     * Get currently selected personal archive display text
     * @return selected display text
     */
    public String getSelectedPersonalArchiveText() {
        return personalArchiveSelectBox.locator("option:checked").textContent().trim();
    }

    /**
     * Fill storage destination email input field
     * @param email email to fill
     */
    public CompanyArchiveManagementPage fillStorageDestinationEmail(String email) {
        log.info("Filling storage destination email: " + email);
        storageDestinationEmailInput.clear();
        storageDestinationEmailInput.fill(email);
        return this;
    }

    /**
     * Get storage destination email value
     * @return current email value
     */
    public String getStorageDestinationEmail() {
        return storageDestinationEmailInput.inputValue();
    }

    /**
     * Select participant header format from dropdown
     * @param format ParticipantHeaderFormat enum value
     */
    public CompanyArchiveManagementPage selectParticipantHeaderFormat(com.websanity.enums.ParticipantHeaderFormat format) {
        log.info("Selecting participant header format: " + format.getDisplayName());
        participantHeaderFormatSelectBox.selectOption(format.getValue());
        return this;
    }

    /**
     * Get currently selected participant header format value
     * @return selected format value
     */
    public String getSelectedParticipantHeaderFormatValue() {
        return participantHeaderFormatSelectBox.inputValue();
    }

    /**
     * Get currently selected participant header format display text
     * @return selected format display text
     */
    public String getSelectedParticipantHeaderFormatText() {
        return participantHeaderFormatSelectBox.locator("option:checked").textContent().trim();
    }

    /**
     * Select spoofing handling from dropdown
     * @param spoofingHandling SpoofingHandling enum value
     */
    public CompanyArchiveManagementPage selectSpoofingHandling(com.websanity.enums.SpoofingHandling spoofingHandling) {
        log.info("Selecting spoofing handling: " + spoofingHandling.getDisplayName());
        spoofingHandlingSelectBox.selectOption(spoofingHandling.getValue());
        return this;
    }

    /**
     * Get currently selected spoofing handling value
     * @return selected spoofing handling value
     */
    public String getSelectedSpoofingHandlingValue() {
        return spoofingHandlingSelectBox.inputValue();
    }

    /**
     * Get currently selected spoofing handling display text
     * @return selected spoofing handling display text
     */
    public String getSelectedSpoofingHandlingText() {
        return spoofingHandlingSelectBox.locator("option:checked").textContent().trim();
    }

    /**
     * Select show local address book names from dropdown
     * @param yesNo YesNo enum value
     */
    public CompanyArchiveManagementPage selectShowLocalAddressBookNames(com.websanity.enums.YesNo yesNo) {
        log.info("Selecting show local address book names: " + yesNo.getDisplayName());
        showLocalAddressBookNamesSelectBox.selectOption(yesNo.getValue());
        return this;
    }

    /**
     * Get currently selected show local address book names value
     * @return selected value
     */
    public String getSelectedShowLocalAddressBookNamesValue() {
        return showLocalAddressBookNamesSelectBox.inputValue();
    }

    /**
     * Get currently selected show local address book names display text
     * @return selected display text
     */
    public String getSelectedShowLocalAddressBookNamesText() {
        return showLocalAddressBookNamesSelectBox.locator("option:checked").textContent().trim();
    }

    /**
     * Click on Add Policy button to submit the policy
     */
    public CompanyArchiveManagementPage clickAddPolicyButton() {
        log.info("Clicking Add Policy button");
        addPolicyButton.click();

        log.info("Waiting for page to load after adding policy");
        addNewPlanButton.waitFor(new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                .setTimeout(30000));

        return this;
    }

    /**
     * Fill plan description input field
     * @param description description to fill
     */
    public CompanyArchiveManagementPage fillPlanDescription(String description) {
        log.info("Filling plan description: " + description);
        planDescriptionInput.clear();
        planDescriptionInput.fill(description);
        return this;
    }

    /**
     * Get plan description value
     * @return current description value
     */
    public String getPlanDescription() {
        return planDescriptionInput.inputValue();
    }

    /**
     * Select source policy from dropdown by source ID
     * @param sourceId the source policy ID to select
     */
    public CompanyArchiveManagementPage selectSourcePolicy(String sourceId) {
        log.info("Selecting source policy: " + sourceId);
        sourceIdSelectBox.selectOption(sourceId);
        return this;
    }

    /**
     * Get currently selected source policy value
     * @return selected source policy ID
     */
    public String getSelectedSourcePolicy() {
        return sourceIdSelectBox.inputValue();
    }

    /**
     * Select policy ID from dropdown by policy ID
     * @param policyId the policy ID to select
     */
    public CompanyArchiveManagementPage selectPolicyId(String policyId) {
        log.info("Selecting policy ID: " + policyId);
        policyIdSelectBox.selectOption(policyId);
        return this;
    }

    /**
     * Get currently selected policy ID value
     * @return selected policy ID
     */
    public String getSelectedPolicyId() {
        return policyIdSelectBox.inputValue();
    }

    /**
     * Click on Add Plan button to submit the plan
     */
    public CompanyArchiveManagementPage clickAddPlanButton() {
        log.info("Clicking Add Plan button");
        addPlanButton.click();

        log.info("Waiting for plans table to load after adding plan");
        plansTable.waitFor(new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                .setTimeout(30000));

        return this;
    }

    /**
     * Click on Add Source button to submit the source
     */
    public CompanyArchiveManagementPage clickAddSourceButton() {
        log.info("Clicking Add Source button");
        addSourceButton.click();

        log.info("Waiting for page to load after adding source");
        addNewPlanButton.waitFor(new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                .setTimeout(30000));

        return this;
    }

    // ========== Policies Table Methods ==========

    /**
     * Check if policies table is visible
     * @return true if table is visible
     */
    public boolean isPoliciesTableVisible() {
        try {
            return policiesTable.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get policies table header text by index (0-based)
     * @param index the column index
     * @return header text
     */
    public String getPoliciesTableHeaderText(int index) {
        return policiesTableHeaders.nth(index).textContent().trim();
    }

    /**
     * Get all policies table headers count
     * @return number of headers
     */
    public int getPoliciesTableHeadersCount() {
        return policiesTableHeaders.count();
    }

    /**
     * Get first row from policies table
     * @return Locator for the first row
     */
    public Locator getFirstPolicyRow() {
        return policiesTable.locator("tbody tr").first();
    }

    /**
     * Get cell text from first row in policies table by index (0-based)
     * @param index the column index
     * @return cell text
     */
    public String getFirstPolicyRowCellText(int index) {
        return getFirstPolicyRow().locator("td").nth(index).textContent().trim();
    }

    /**
     * Get row from policies table by index (0-based)
     * @param rowIndex the row index
     * @return Locator for the row
     */
    public Locator getPolicyRowByIndex(int rowIndex) {
        return policiesTable.locator("tbody tr").nth(rowIndex);
    }

    /**
     * Get cell text from specific row in policies table by row and column index
     * @param rowIndex the row index (0-based)
     * @param cellIndex the column index (0-based)
     * @return cell text
     */
    public String getPolicyRowCellText(int rowIndex, int cellIndex) {
        return getPolicyRowByIndex(rowIndex).locator("td").nth(cellIndex).textContent().trim();
    }

    /**
     * Click Edit button in the first row of policies table
     */
    public CompanyArchiveManagementPage clickFirstPolicyRowEditButton() {
        log.info("Clicking Edit button in first policy row");
        getFirstPolicyRow().locator("button.edit-policy").click();
        return this;
    }

    /**
     * Click Delete button in the first row of policies table
     */
    public CompanyArchiveManagementPage clickFirstPolicyRowDeleteButton() {
        log.info("Clicking Delete button in first policy row");
        getFirstPolicyRow().locator("button.delete-policy").click();
        return this;
    }

    /**
     * Get row from policies table by Policy ID
     * @param policyId the ID to search for
     * @return Locator for the row with matching ID
     */
    public Locator getPolicyRowByPolicyId(String policyId) {
        return policiesTable.locator("tbody tr[data-id='" + policyId + "']");
    }

    /**
     * Click Edit button for specific policy ID
     * @param policyId the ID of the policy
     */
    public CompanyArchiveManagementPage clickEditButtonForPolicy(String policyId) {
        log.info("Clicking Edit button for policy: " + policyId);
        getPolicyRowByPolicyId(policyId).locator("button.edit-policy").click();
        return this;
    }

    /**
     * Click Delete button for specific policy ID
     * @param policyId the ID of the policy
     */
    public CompanyArchiveManagementPage clickDeleteButtonForPolicy(String policyId) {
        log.info("Clicking Delete button for policy: " + policyId);
        getPolicyRowByPolicyId(policyId).locator("button.delete-policy").click();
        return this;
    }

    // ========== Plans Table Methods ==========

    /**
     * Check if plans table is visible
     * @return true if table is visible
     */
    public boolean isPlansTableVisible() {
        try {
            return plansTable.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get plans table header text by index (0-based)
     * @param index the column index (0=Plan ID, 1=Description, 2=Source Policy, 3=Archive Storage, 4=Policy ID, 5=Assign, 6=Thread)
     * @return header text
     */
    public String getPlansTableHeaderText(int index) {
        return plansTableHeaders.nth(index).textContent().trim();
    }

    /**
     * Get all plans table headers count
     * @return number of headers
     */
    public int getPlansTableHeadersCount() {
        return plansTableHeaders.count();
    }

    /**
     * Get first row from plans table
     * @return Locator for the first row
     */
    public Locator getFirstPlanRow() {
        return plansTable.locator("tbody tr").first();
    }

    /**
     * Get cell text from first row in plans table by index (0-based)
     * @param index the column index (0=Plan ID, 1=Description, 2=Source Policy, 3=Archive Storage, 4=Policy ID, 5=Assign, 6=Thread)
     * @return cell text
     */
    public String getFirstPlanRowCellText(int index) {
        return getFirstPlanRow().locator("td").nth(index).textContent().trim();
    }

    /**
     * Get row from plans table by index (0-based)
     * @param rowIndex the row index
     * @return Locator for the row
     */
    public Locator getPlanRowByIndex(int rowIndex) {
        return plansTable.locator("tbody tr").nth(rowIndex);
    }

    /**
     * Get cell text from specific row in plans table by row and column index
     * @param rowIndex the row index (0-based)
     * @param cellIndex the column index (0-based)
     * @return cell text
     */
    public String getPlanRowCellText(int rowIndex, int cellIndex) {
        return getPlanRowByIndex(rowIndex).locator("td").nth(cellIndex).textContent().trim();
    }

    /**
     * Click Edit button in the first row of plans table
     */
    public CompanyArchiveManagementPage clickFirstPlanRowEditButton() {
        log.info("Clicking Edit button in first plan row");
        getFirstPlanRow().locator("button.edit-plan").click();
        return this;
    }

    /**
     * Click Delete button in the first row of plans table
     */
    public CompanyArchiveManagementPage clickFirstPlanRowDeleteButton() {
        log.info("Clicking Delete button in first plan row");
        getFirstPlanRow().locator("button.delete-plan").click();
        return this;
    }

    /**
     * Click Manage Users button in the first row of plans table
     */
    public CompanyArchiveManagementPage clickFirstPlanRowManageUsersButton() {
        log.info("Clicking Manage Users button in first plan row");
        getFirstPlanRow().locator("button.manage-users").click();
        page.waitForTimeout(1500);
        return this;
    }

    /**
     * Click Bulk Provisioning button in the first row of plans table
     */
    public CompanyArchiveManagementPage clickFirstPlanRowBulkProvisioningButton() {
        log.info("Clicking Bulk Provisioning button in first plan row");
        getFirstPlanRow().locator("button.bulk-prov").click();
        return this;
    }

    /**
     * Click Bulk Deprovisioning button in the first row of plans table
     */
    public CompanyArchiveManagementPage clickFirstPlanRowBulkDeprovisioningButton() {
        log.info("Clicking Bulk Deprovisioning button in first plan row");
        getFirstPlanRow().locator("button.bulk-deprov").click();
        return this;
    }

    /**
     * Get row from plans table by Plan ID
     * @param planId the ID to search for
     * @return Locator for the row with matching ID
     */
    public Locator getPlanRowByPlanId(String planId) {
        return plansTable.locator("tbody tr[data-id='" + planId + "']");
    }

    /**
     * Click Edit button for specific plan ID
     * @param planId the ID of the plan
     */
    public CompanyArchiveManagementPage clickEditButtonForPlan(String planId) {
        log.info("Clicking Edit button for plan: " + planId);
        getPlanRowByPlanId(planId).locator("button.edit-plan").click();
        return this;
    }

    /**
     * Click Delete button for specific plan ID
     * @param planId the ID of the plan
     */
    public CompanyArchiveManagementPage clickDeleteButtonForPlan(String planId) {
        log.info("Clicking Delete button for plan: " + planId);
        getPlanRowByPlanId(planId).locator("button.delete-plan").click();
        return this;
    }

    /**
     * Click Manage Users button for specific plan ID
     * @param planId the ID of the plan
     */
    public CompanyArchiveManagementPage clickManageUsersButtonForPlan(String planId) {
        log.info("Clicking Manage Users button for plan: " + planId);
        getPlanRowByPlanId(planId).locator("button.manage-users").click();
        return this;
    }

    /**
     * Click Bulk Provisioning button for specific plan ID
     * @param planId the ID of the plan
     */
    public CompanyArchiveManagementPage clickBulkProvisioningButtonForPlan(String planId) {
        log.info("Clicking Bulk Provisioning button for plan: " + planId);
        getPlanRowByPlanId(planId).locator("button.bulk-prov").click();
        return this;
    }

    /**
     * Click Bulk Deprovisioning button for specific plan ID
     * @param planId the ID of the plan
     */
    public CompanyArchiveManagementPage clickBulkDeprovisioningButtonForPlan(String planId) {
        log.info("Clicking Bulk Deprovisioning button for plan: " + planId);
        getPlanRowByPlanId(planId).locator("button.bulk-deprov").click();
        return this;
    }

    // ========== Manage Users Methods ==========

    /**
     * Wait for Assign Users table to be visible
     * @return true if table is visible
     */
    public boolean waitForAssignUsersTableVisible() {
        try {
            assignUsersTable.waitFor(new Locator.WaitForOptions()
                    .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                    .setTimeout(30000));
            return true;
        } catch (Exception e) {
            log.error("Assign Users table did not appear: " + e.getMessage());
            return false;
        }
    }

    /**
     * Check if Assign Users table is visible
     * @return true if table is visible
     */
    public boolean isAssignUsersTableVisible() {
        try {
            return assignUsersTable.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Wait for Assign Users search input to be visible
     * @return true if search input is visible
     */
    public boolean waitForAssignUsersSearchInputVisible() {
        try {
            assignUsersSearchInput.waitFor(new Locator.WaitForOptions()
                    .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                    .setTimeout(30000));
            return true;
        } catch (Exception e) {
            log.error("Assign Users search input did not appear: " + e.getMessage());
            return false;
        }
    }

    /**
     * Check if Assign Users search input is visible
     * @return true if search input is visible
     */
    public boolean isAssignUsersSearchInputVisible() {
        try {
            return assignUsersSearchInput.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Fill the Assign Users search input field
     * @param searchText text to search for
     */
    public CompanyArchiveManagementPage fillAssignUsersSearch(String searchText) {
        log.info("Filling Assign Users search field with: " + searchText);
        assignUsersSearchInput.clear();
        assignUsersSearchInput.fill(searchText);
        page.waitForTimeout(1000); // Wait for search to complete
        return this;
    }

    /**
     * Get the current value of Assign Users search input
     * @return current search text
     */
    public String getAssignUsersSearchValue() {
        return assignUsersSearchInput.inputValue();
    }

    /**
     * Clear the Assign Users search input field
     */
    public CompanyArchiveManagementPage clearAssignUsersSearch() {
        log.info("Clearing Assign Users search field");
        assignUsersSearchInput.clear();
        page.waitForTimeout(1000); // Wait for search to clear
        return this;
    }

    /**
     * Get row count from Assign Users table
     * @return number of rows in the table
     */
    public int getAssignUsersTableRowCount() {
        return assignUsersTable.locator("tbody tr").count();
    }

    /**
     * Get specific row from Assign Users table by index (0-based)
     * @param rowIndex the row index
     * @return Locator for the row
     */
    public Locator getAssignUsersRowByIndex(int rowIndex) {
        return assignUsersTable.locator("tbody tr").nth(rowIndex);
    }

    /**
     * Get first row from Assign Users table
     * @return Locator for the first row
     */
    public Locator getFirstAssignUsersRow() {
        return assignUsersTable.locator("tbody tr").first();
    }

    /**
     * Click checkbox for the first user in Assign Users table
     */
    public CompanyArchiveManagementPage clickFirstUserCheckbox() {
        log.info("Clicking checkbox for first user in Assign Users table");
        getFirstAssignUsersRow().locator("input.assign-plan-check").click();
        page.waitForTimeout(500); // Wait for checkbox state to update
        return this;
    }

    /**
     * Click checkbox for specific user by data-id in Assign Users table
     * @param userId the user ID (data-id attribute)
     */
    public CompanyArchiveManagementPage clickUserCheckboxById(String userId) {
        log.info("Clicking checkbox for user ID: " + userId);
        assignUsersTable.locator("tr[data-id='" + userId + "'] input.assign-plan-check").click();
        page.waitForTimeout(500); // Wait for checkbox state to update
        return this;
    }

    /**
     * Click checkbox for specific user by row index (0-based) in Assign Users table
     * @param rowIndex the row index
     */
    public CompanyArchiveManagementPage clickUserCheckboxByIndex(int rowIndex) {
        log.info("Clicking checkbox for user at row index: " + rowIndex);
        getAssignUsersRowByIndex(rowIndex).locator("input.assign-plan-check").click();
        page.waitForTimeout(500); // Wait for checkbox state to update
        return this;
    }

    /**
     * Click the "Select All" checkbox in Assign Users table
     */
    public CompanyArchiveManagementPage clickSelectAllUsersCheckbox() {
        log.info("Clicking Select All checkbox in Assign Users table");
        assignUsersSelectAllCheckbox.click();
        page.waitForTimeout(500); // Wait for all checkboxes to update
        return this;
    }

    /**
     * Check if first user checkbox is checked in Assign Users table
     * @return true if checkbox is checked
     */
    public boolean isFirstUserCheckboxChecked() {
        return getFirstAssignUsersRow().locator("input.assign-plan-check").isChecked();
    }

    /**
     * Check if user checkbox is checked by user ID in Assign Users table
     * @param userId the user ID (data-id attribute)
     * @return true if checkbox is checked
     */
    public boolean isUserCheckboxCheckedById(String userId) {
        return assignUsersTable.locator("tr[data-id='" + userId + "'] input.assign-plan-check").isChecked();
    }

    /**
     * Check if Select All checkbox is checked in Assign Users table
     * @return true if checkbox is checked
     */
    public boolean isSelectAllCheckboxChecked() {
        return assignUsersSelectAllCheckbox.isChecked();
    }

    /**
     * Get username of the first user in Assign Users table
     * @return username text
     */
    public String getFirstUserUsername() {
        return getFirstAssignUsersRow().locator(".user-username").textContent().trim();
    }

    /**
     * Get first name of the first user in Assign Users table
     * @return first name text
     */
    public String getFirstUserFirstName() {
        return getFirstAssignUsersRow().locator(".user-first-name").textContent().trim();
    }

    /**
     * Get user ID (data-id) of the first user in Assign Users table
     * @return user ID
     */
    public String getFirstUserId() {
        return getFirstAssignUsersRow().getAttribute("data-id");
    }

    /**
     * Get username by row index (0-based) in Assign Users table
     * @param rowIndex the row index
     * @return username text
     */
    public String getUserUsernameByIndex(int rowIndex) {
        return getAssignUsersRowByIndex(rowIndex).locator(".user-username").textContent().trim();
    }

    /**
     * Find and click checkbox for user by username in Assign Users table
     * @param username the username to search for
     * @return true if user was found and checkbox clicked, false otherwise
     */
    public boolean clickUserCheckboxByUsername(String username) {
        log.info("Searching for user by username: " + username);
        Locator allRows = assignUsersTable.locator("tbody tr");
        int rowCount = allRows.count();

        for (int i = 0; i < rowCount; i++) {
            Locator row = allRows.nth(i);
            String currentUsername = row.locator(".user-username").textContent().trim();

            if (currentUsername.equals(username)) {
                log.info("Found user " + username + " at row " + i + ", clicking checkbox");
                row.locator("input.assign-plan-check").click();
                page.waitForTimeout(500);
                return true;
            }
        }

        log.warn("User with username " + username + " not found in Assign Users table");
        return false;
    }

    /**
     * Click the Apply button in Manage Users modal
     * Note: The button is only enabled when at least one checkbox state has changed
     */
    public CompanyArchiveManagementPage clickAssignUsersApplyButton() {
        log.info("Clicking Apply button in Manage Users modal");
        assignUsersApplyButton.click();
        page.waitForTimeout(1000); // Wait for modal to close and changes to apply
        return this;
    }

    /**
     * Check if Apply button is enabled in Manage Users modal
     * @return true if button is enabled (clickable)
     */
    public boolean isAssignUsersApplyButtonEnabled() {
        return assignUsersApplyButton.isEnabled();
    }

    /**
     * Check if Apply button is visible in Manage Users modal
     * @return true if button is visible
     */
    public boolean isAssignUsersApplyButtonVisible() {
        try {
            return assignUsersApplyButton.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Wait for Apply button to be enabled in Manage Users modal
     * Useful after checking/unchecking users
     * @return this page object for method chaining
     */
    public CompanyArchiveManagementPage waitForAssignUsersApplyButtonEnabled() {
        try {
            page.waitForCondition(() -> assignUsersApplyButton.isEnabled(),
                    new Page.WaitForConditionOptions().setTimeout(10000));
            log.info("Apply button is now enabled");
        } catch (Exception e) {
            log.error("Apply button did not become enabled: " + e.getMessage());
        }
        return this;
    }

    // ========== Confirmation Dialog Methods ==========

    /**
     * Click the Yes button in confirmation dialog
     * Used to confirm actions like deletions or changes
     */
    public CompanyArchiveManagementPage clickConfirmActionYesButton() {
        log.info("Clicking Yes button in confirmation dialog");
        confirmActionYesButton.click();
        page.waitForTimeout(2000); // Wait for action to complete
        return this;
    }

    /**
     * Wait for Yes confirmation button to be visible
     * @return true if button is visible
     */
    public boolean waitForConfirmActionYesButtonVisible() {
        try {
            confirmActionYesButton.waitFor(new Locator.WaitForOptions()
                    .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                    .setTimeout(10000));
            log.info("Yes confirmation button is now visible");
            return true;
        } catch (Exception e) {
            log.error("Yes confirmation button did not appear: " + e.getMessage());
            return false;
        }
    }

    /**
     * Check if Yes confirmation button is visible
     * @return true if button is visible
     */
    public boolean isConfirmActionYesButtonVisible() {
        try {
            return confirmActionYesButton.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if Yes confirmation button is enabled
     * @return true if button is enabled
     */
    public boolean isConfirmActionYesButtonEnabled() {
        return confirmActionYesButton.isEnabled();
    }

}
