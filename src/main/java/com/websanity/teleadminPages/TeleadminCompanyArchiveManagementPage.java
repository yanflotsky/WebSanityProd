package com.websanity.teleadminPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.websanity.BasePage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TeleadminCompanyArchiveManagementPage extends BasePage {

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

    public TeleadminCompanyArchiveManagementPage(Page page) {
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
    public TeleadminCompanyArchiveManagementPage clickAddNewSourceButton() {
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
    public TeleadminCompanyArchiveManagementPage clickAddNewPolicyButton() {
        log.info("Clicking Add New Policy button");
        addNewPolicyButton.click();
        return this;
    }

    /**
     * Click on Add New Plan button
     */
    public TeleadminCompanyArchiveManagementPage clickAddNewPlanButton() {
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
    public TeleadminCompanyArchiveManagementPage clickFirstRowEditButton() {
        log.info("Clicking Edit button in first row");
        getFirstSourcePolicyRow().locator("button.edit-source-policy").click();
        return this;
    }

    /**
     * Click Delete button in the first row
     */
    public TeleadminCompanyArchiveManagementPage clickFirstRowDeleteButton() {
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
    public TeleadminCompanyArchiveManagementPage clickEditButtonForSourcePolicy(String sourcePolicyId) {
        log.info("Clicking Edit button for source policy: " + sourcePolicyId);
        getRowBySourcePolicyId(sourcePolicyId).locator("button.edit-source-policy").click();
        return this;
    }

    /**
     * Click Delete button for specific source policy ID
     * @param sourcePolicyId the ID of the source policy
     */
    public TeleadminCompanyArchiveManagementPage clickDeleteButtonForSourcePolicy(String sourcePolicyId) {
        log.info("Clicking Delete button for source policy: " + sourcePolicyId);
        getRowBySourcePolicyId(sourcePolicyId).locator("button.delete-source-policy").click();
        return this;
    }

    /**
     * Select source type from dropdown
     * @param sourceType SourceType enum value
     */
    public TeleadminCompanyArchiveManagementPage selectSourceType(com.websanity.enums.SourceType sourceType) {
        log.info("Selecting source type: " + sourceType.getDisplayName());
        sourceTypesListSelectBox.selectOption(sourceType.getValue());
        return this;
    }

    /**
     * Select network type from dropdown
     * @param networkType NetworkType enum value
     */
    public TeleadminCompanyArchiveManagementPage selectNetworkType(com.websanity.enums.NetworkType networkType) {
        log.info("Selecting network type: " + networkType.getDisplayName());
        sourcesNetworkListSelectBox.selectOption(networkType.getValue());
        return this;
    }

    /**
     * Fill source description input field
     * @param description description to fill
     */
    public TeleadminCompanyArchiveManagementPage fillSourceDescription(String description) {
        log.info("Filling source description: " + description);
        sourceDescriptionInput.clear();
        sourceDescriptionInput.fill(description);
        return this;
    }

    /**
     * Select storage type from dropdown
     * @param storage Storage enum value
     */
    public TeleadminCompanyArchiveManagementPage selectStorage(com.websanity.enums.Storage storage) {
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
    public TeleadminCompanyArchiveManagementPage fillStorageDescription(String description) {
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
    public TeleadminCompanyArchiveManagementPage selectPersonalArchive(com.websanity.enums.YesNo yesNo) {
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
    public TeleadminCompanyArchiveManagementPage fillStorageDestinationEmail(String email) {
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
    public TeleadminCompanyArchiveManagementPage selectParticipantHeaderFormat(com.websanity.enums.ParticipantHeaderFormat format) {
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
    public TeleadminCompanyArchiveManagementPage selectSpoofingHandling(com.websanity.enums.SpoofingHandling spoofingHandling) {
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
    public TeleadminCompanyArchiveManagementPage selectShowLocalAddressBookNames(com.websanity.enums.YesNo yesNo) {
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
    public TeleadminCompanyArchiveManagementPage clickAddPolicyButton() {
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
    public TeleadminCompanyArchiveManagementPage fillPlanDescription(String description) {
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
    public TeleadminCompanyArchiveManagementPage selectSourcePolicy(String sourceId) {
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
    public TeleadminCompanyArchiveManagementPage selectPolicyId(String policyId) {
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
    public TeleadminCompanyArchiveManagementPage clickAddPlanButton() {
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
    public TeleadminCompanyArchiveManagementPage clickAddSourceButton() {
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
    public TeleadminCompanyArchiveManagementPage clickFirstPolicyRowEditButton() {
        log.info("Clicking Edit button in first policy row");
        getFirstPolicyRow().locator("button.edit-policy").click();
        return this;
    }

    /**
     * Click Delete button in the first row of policies table
     */
    public TeleadminCompanyArchiveManagementPage clickFirstPolicyRowDeleteButton() {
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
    public TeleadminCompanyArchiveManagementPage clickEditButtonForPolicy(String policyId) {
        log.info("Clicking Edit button for policy: " + policyId);
        getPolicyRowByPolicyId(policyId).locator("button.edit-policy").click();
        return this;
    }

    /**
     * Click Delete button for specific policy ID
     * @param policyId the ID of the policy
     */
    public TeleadminCompanyArchiveManagementPage clickDeleteButtonForPolicy(String policyId) {
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
    public TeleadminCompanyArchiveManagementPage clickFirstPlanRowEditButton() {
        log.info("Clicking Edit button in first plan row");
        getFirstPlanRow().locator("button.edit-plan").click();
        return this;
    }

    /**
     * Click Delete button in the first row of plans table
     */
    public TeleadminCompanyArchiveManagementPage clickFirstPlanRowDeleteButton() {
        log.info("Clicking Delete button in first plan row");
        getFirstPlanRow().locator("button.delete-plan").click();
        return this;
    }

    /**
     * Click Manage Users button in the first row of plans table
     */
    public TeleadminCompanyArchiveManagementPage clickFirstPlanRowManageUsersButton() {
        log.info("Clicking Manage Users button in first plan row");
        getFirstPlanRow().locator("button.manage-users").click();
        return this;
    }

    /**
     * Click Bulk Provisioning button in the first row of plans table
     */
    public TeleadminCompanyArchiveManagementPage clickFirstPlanRowBulkProvisioningButton() {
        log.info("Clicking Bulk Provisioning button in first plan row");
        getFirstPlanRow().locator("button.bulk-prov").click();
        return this;
    }

    /**
     * Click Bulk Deprovisioning button in the first row of plans table
     */
    public TeleadminCompanyArchiveManagementPage clickFirstPlanRowBulkDeprovisioningButton() {
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
    public TeleadminCompanyArchiveManagementPage clickEditButtonForPlan(String planId) {
        log.info("Clicking Edit button for plan: " + planId);
        getPlanRowByPlanId(planId).locator("button.edit-plan").click();
        return this;
    }

    /**
     * Click Delete button for specific plan ID
     * @param planId the ID of the plan
     */
    public TeleadminCompanyArchiveManagementPage clickDeleteButtonForPlan(String planId) {
        log.info("Clicking Delete button for plan: " + planId);
        getPlanRowByPlanId(planId).locator("button.delete-plan").click();
        return this;
    }

    /**
     * Click Manage Users button for specific plan ID
     * @param planId the ID of the plan
     */
    public TeleadminCompanyArchiveManagementPage clickManageUsersButtonForPlan(String planId) {
        log.info("Clicking Manage Users button for plan: " + planId);
        getPlanRowByPlanId(planId).locator("button.manage-users").click();
        return this;
    }

    /**
     * Click Bulk Provisioning button for specific plan ID
     * @param planId the ID of the plan
     */
    public TeleadminCompanyArchiveManagementPage clickBulkProvisioningButtonForPlan(String planId) {
        log.info("Clicking Bulk Provisioning button for plan: " + planId);
        getPlanRowByPlanId(planId).locator("button.bulk-prov").click();
        return this;
    }

    /**
     * Click Bulk Deprovisioning button for specific plan ID
     * @param planId the ID of the plan
     */
    public TeleadminCompanyArchiveManagementPage clickBulkDeprovisioningButtonForPlan(String planId) {
        log.info("Clicking Bulk Deprovisioning button for plan: " + planId);
        getPlanRowByPlanId(planId).locator("button.bulk-deprov").click();
        return this;
    }

}
