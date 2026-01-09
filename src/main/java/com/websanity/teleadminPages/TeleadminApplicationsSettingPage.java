package com.websanity.teleadminPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.websanity.BasePage;
import com.websanity.enums.SignatureTextInheritance;
import com.websanity.enums.SignatureType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TeleadminApplicationsSettingPage extends BasePage {

    private final Locator signatureButton;
    private final Locator signaturePopUp;
    private final Locator signatureTypeSelectBox;
    private final Locator signatureTextTypesSelectBox;
    private final Locator signatureTextArea;
    private final Locator applySignatureButton;
    private final Locator confirmActionButton;
    private final Locator signatureAlertSuccess;
    private final Locator searchSignatureUsersInput;
    private final Locator rcsSupportCheckbox;
    private final Locator saveCompanyLevelSettingsButton;

    public TeleadminApplicationsSettingPage(Page page) {
        super(page);
        this.signatureButton = page.locator("input[type='button'][value='Signature']");
        this.signaturePopUp = page.locator("#assign-signature");
        this.signatureTypeSelectBox = page.locator("#signature-select-types");
        this.signatureTextTypesSelectBox = page.locator("#signature-text-types");
        this.signatureTextArea = page.locator("#signature_user_text");
        this.applySignatureButton = page.locator("#assign-user-signature-apply");
        this.confirmActionButton = page.locator("#confirm-action");
        this.signatureAlertSuccess = page.locator("#signatureAlertSuccess");
        this.searchSignatureUsersInput = page.locator("#assign-signature-users_filter input[type='search']");
        this.rcsSupportCheckbox = page.locator("#rcs_support");
        this.saveCompanyLevelSettingsButton = page.locator("#save_company_level_settings");
    }

    /**
     * Click on Signature button
     */
    public TeleadminApplicationsSettingPage clickSignatureButton() {
        log.info("Clicking Signature button");
        signatureButton.click();

        log.info("Waiting for Signature popup to appear");
        try {
            signaturePopUp.waitFor(new Locator.WaitForOptions().setTimeout(30000));
            log.info("Signature popup appeared successfully");
        } catch (Exception e) {
            String errorMsg = "Signature popup did not appear after clicking Signature button";
            log.error(errorMsg);
            throw new RuntimeException(errorMsg, e);
        }

        return this;
    }

    /**
     * Select signature type
     * @param signatureType SignatureType enum value
     */
    public TeleadminApplicationsSettingPage selectSignatureType(SignatureType signatureType) {
        log.info("Selecting signature type: {}", signatureType.getDisplayName());
        signatureTypeSelectBox.selectOption(signatureType.getValue());
        return this;
    }

    /**
     * Get currently selected signature type value
     * @return Selected signature type value
     */
    public String getSelectedSignatureType() {
        return signatureTypeSelectBox.inputValue();
    }

    /**
     * Verify that signature type is selected
     * @param expectedSignatureType Expected SignatureType enum value
     * @return true if selected, false otherwise
     */
    public boolean isSignatureTypeSelected(SignatureType expectedSignatureType) {
        String actualValue = getSelectedSignatureType();
        log.info("Verifying signature type. Expected: {}, Actual: {}",
                expectedSignatureType.getValue(), actualValue);
        return actualValue.equals(expectedSignatureType.getValue());
    }

    /**
     * Select signature text inheritance type
     * @param signatureTextInheritance SignatureTextInheritance enum value
     */
    public TeleadminApplicationsSettingPage selectSignatureTextInheritance(SignatureTextInheritance signatureTextInheritance) {
        log.info("Selecting signature text inheritance: {}", signatureTextInheritance.getDisplayName());
        signatureTextTypesSelectBox.selectOption(signatureTextInheritance.getValue());
        return this;
    }

    /**
     * Get currently selected signature text inheritance value
     * @return Selected signature text inheritance value
     */
    public String getSelectedSignatureTextInheritance() {
        return signatureTextTypesSelectBox.inputValue();
    }

    /**
     * Verify that signature text inheritance is selected
     * @param expectedSignatureTextInheritance Expected SignatureTextInheritance enum value
     * @return true if selected, false otherwise
     */
    public boolean isSignatureTextInheritanceSelected(SignatureTextInheritance expectedSignatureTextInheritance) {
        String actualValue = getSelectedSignatureTextInheritance();
        log.info("Verifying signature text inheritance. Expected: {}, Actual: {}",
                expectedSignatureTextInheritance.getValue(), actualValue);
        return actualValue.equals(expectedSignatureTextInheritance.getValue());
    }

    /**
     * Fill signature text (clears existing text first)
     * @param text Text to enter in signature textarea
     */
    public TeleadminApplicationsSettingPage fillSignatureText(String text) {
        log.info("Filling signature text: {}", text);
        signatureTextArea.clear();
        signatureTextArea.fill(text);
        return this;
    }

    /**
     * Get signature text content
     * @return Text content of signature textarea
     */
    public String getSignatureText() {
        return signatureTextArea.textContent();
    }

    /**
     * Click Apply button in signature popup
     */
    public TeleadminApplicationsSettingPage clickApplySignatureButton() {
        log.info("Clicking Apply Signature button");
        applySignatureButton.click();
        return this;
    }

    /**
     * Click Confirm Action (Yes) button
     */
    public TeleadminApplicationsSettingPage clickConfirmActionButton() {
        log.info("Clicking Confirm Action (Yes) button");
        confirmActionButton.click();
        return this;
    }

    /**
     * Wait for signature success alert to become visible
     */
    public TeleadminApplicationsSettingPage waitForSignatureSuccessAlert() {
        log.info("Waiting for signature success alert to appear");
        try {
            signatureAlertSuccess.waitFor(new Locator.WaitForOptions().setTimeout(30000));
            log.info("Signature success alert appeared successfully");
        } catch (Exception e) {
            String errorMsg = "Signature success alert did not appear";
            log.error(errorMsg);
            throw new RuntimeException(errorMsg, e);
        }
        return this;
    }

    /**
     * Check if signature success alert is visible
     * @return true if visible, false otherwise
     */
    public boolean isSignatureSuccessAlertVisible() {
        boolean isVisible = signatureAlertSuccess.isVisible();
        log.info("Signature success alert visibility: {}", isVisible);
        return isVisible;
    }

    /**
     * Search for signature users
     * @param searchText Text to search for users
     */
    public TeleadminApplicationsSettingPage searchSignatureUsers(String searchText) {
        log.info("Searching for signature users: {}", searchText);
        searchSignatureUsersInput.fill(searchText);
        return this;
    }

    /**
     * Click RCS Support checkbox
     */
    public TeleadminApplicationsSettingPage clickRcsSupportCheckbox() {
        log.info("Clicking RCS Support checkbox");
        rcsSupportCheckbox.click();
        return this;
    }

    /**
     * Check if RCS Support checkbox is selected
     * @return true if checked, false otherwise
     */
    public boolean isRcsSupportCheckboxSelected() {
        boolean isChecked = rcsSupportCheckbox.isChecked();
        log.info("RCS Support checkbox is checked: {}", isChecked);
        return isChecked;
    }

    /**
     * Click Save Company Level Settings (Apply) button
     */
    public TeleadminApplicationsSettingPage clickSaveCompanyLevelSettingsButton() {
        log.info("Clicking Save Company Level Settings (Apply) button");
        saveCompanyLevelSettingsButton.click();
        return this;
    }

}
