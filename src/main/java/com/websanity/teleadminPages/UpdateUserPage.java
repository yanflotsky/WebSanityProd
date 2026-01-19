package com.websanity.teleadminPages;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.websanity.BasePage;
import com.websanity.enums.UserStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UpdateUserPage extends BasePage {

    private final FrameLocator textFrame;

    // Locators
    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator firstNameInput;
    private final Locator lastNameInput;
    private final Locator emailInput;
    private final Locator mobileCountryCodeSelectbox;
    private final Locator mobileAreaInput;
    private final Locator mobileInput;
    private final Locator timeZoneSelectBox;
    private final Locator countrySelectBox;
    private final Locator languageSelectBox;
    private final Locator updateInterfaceSupportButton;
    private final Locator statusSelectBox;
    private final Locator updateStatusButton;
    private final Locator successMessage;
    private final Locator deletedUserStatusLabel;
    private final Locator updateAccountInformationButton;
    private final Locator enCountrySelectBox;
    private final Locator enIncToProviderSelectBox;
    private final Locator enOutViaProviderSelectBox;
    private final Locator enInput;
    private final Locator enUpdateButton;
    private final Locator enSuccessMessage;
    private final Locator addedEnterpriseNumberRow;
    private final Locator enDeleteCheckbox;
    private final Locator companyArchiveManagementButton;
    private final Locator manageAllowedEmailDomainButton;
    private final Locator companyAdminPortalSettingsButton;
    private final Locator whatsAppPhoneCaptureSettingsButton;
    private final Locator whatsAppCloudCaptureSettingsButton;
    private final Locator telegramCaptureSettingsButton;
    private final Locator signalCaptureSettingsButton;
    private final Locator enterpriseNumberCaptureSettingsButton;
    private final Locator androidCaptureSettingsButton;
    private final Locator domainInput;
    private final Locator addDomainButton;
    private final Locator updateArchivingDomainButton;
    private final Locator domain0Input;

    public UpdateUserPage(Page page) {
        super(page);
        this.textFrame = page.frameLocator("frame[name='text']");
        this.usernameInput = textFrame.locator("#userid");
        this.passwordInput = textFrame.locator("#password");
        this.firstNameInput = textFrame.locator("#firstName");
        this.lastNameInput = textFrame.locator("#lastName");
        this.emailInput = textFrame.locator("#communicationEmail1");
        this.mobileCountryCodeSelectbox = textFrame.locator("#mobileCountry1");
        this.mobileAreaInput = textFrame.locator("#mobileArea1");
        this.mobileInput = textFrame.locator("#mobileTelephone1");
        this.timeZoneSelectBox = textFrame.locator("#updateTimeZone");
        this.countrySelectBox = textFrame.locator("#updateCountryID");
        this.languageSelectBox = textFrame.locator("#updateLanguageID");
        this.updateInterfaceSupportButton = textFrame.locator("#updateInterfaceSupport");
        this.statusSelectBox = textFrame.locator("#status");
        this.updateStatusButton = textFrame.locator("#updateStatusSbmBtn");
        this.successMessage = textFrame.locator("#sysMsgTable span.sysMsg");
        this.deletedUserStatusLabel = textFrame.locator("form#TheForm font b");
        this.updateAccountInformationButton = textFrame.locator("#updatePersonalData");
        this.enCountrySelectBox = textFrame.locator("#shortcodeCountryID");
        this.enIncToProviderSelectBox = textFrame.locator("#incomingProviderValue");
        this.enOutViaProviderSelectBox = textFrame.locator("#outgoingProviderValue");
        this.enInput = textFrame.locator("#shortcodeValue");
        this.enUpdateButton = textFrame.locator("div.adminUpdateUserBtn a[onclick*='updateShortcodeAdmin']");
        this.enSuccessMessage = textFrame.locator("#sysMsgTable span.sysMsg");
        this.addedEnterpriseNumberRow = textFrame.locator("tr:has(td:text('Enterprise Numbers')) table.profileBgColor tbody tr:has(input[name='shortcodesList'])")
                .first();
        this.enDeleteCheckbox = textFrame.locator("#shortcodesList").first();
        this.companyArchiveManagementButton = textFrame.locator("text=Company Archive Management");
        this.manageAllowedEmailDomainButton = textFrame.locator("text=Manage allowed email/domain");
        this.companyAdminPortalSettingsButton = textFrame.locator("text=Company Admin's Portal Settings");
        this.whatsAppPhoneCaptureSettingsButton = textFrame.locator("text=WhatsApp Phone Capture Settings");
        this.whatsAppCloudCaptureSettingsButton = textFrame.locator("text=WhatsApp Cloud Capture Settings");
        this.telegramCaptureSettingsButton = textFrame.locator("text=Telegram Capture Settings");
        this.signalCaptureSettingsButton = textFrame.locator("text=Signal Capture Settings");
        this.enterpriseNumberCaptureSettingsButton = textFrame.locator("text=Enterprise Number Capture Settings");
        this.androidCaptureSettingsButton = textFrame.locator("text=Android Capture Settings");
        this.domainInput = textFrame.locator("#domain");
        this.addDomainButton = textFrame.locator("a[onclick*='addAllowedDomain()'] img");
        this.updateArchivingDomainButton = textFrame.locator("a[onclick*='submitDomain()'] img");
        this.domain0Input = textFrame.locator("#domain0");
    }

    /**
     * Check if Update User page is opened by verifying button visibility
     * This method waits up to 60 seconds for the element to appear (increased for Docker)
     */
    public boolean isUpdateUserPageOpened() {
        try {
            updateInterfaceSupportButton.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(30000));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Fill username input field
     * @param username username to fill
     */
    public UpdateUserPage fillUsername(String username) {
        usernameInput.clear();
        usernameInput.fill(username);
        return this;
    }

    /**
     * Get username value
     * @return username value
     */
    public String getUsername() {
        return usernameInput.inputValue();
    }

    /**
     * Fill password input field
     * @param password password to fill
     */
    public UpdateUserPage fillPassword(String password) {
        passwordInput.clear();
        passwordInput.fill(password);
        return this;
    }

    /**
     * Get password value
     * @return password value
     */
    public String getPassword() {
        return passwordInput.inputValue();
    }

    /**
     * Fill first name input field
     * @param firstName first name to fill
     */
    public UpdateUserPage fillFirstName(String firstName) {
        firstNameInput.clear();
        firstNameInput.fill(firstName);
        return this;
    }

    /**
     * Get first name value
     * @return first name value
     */
    public String getFirstName() {
        return firstNameInput.inputValue();
    }

    /**
     * Fill last name input field
     * @param lastName last name to fill
     */
    public UpdateUserPage fillLastName(String lastName) {
        lastNameInput.clear();
        lastNameInput.fill(lastName);
        return this;
    }

    /**
     * Get last name value
     * @return last name value
     */
    public String getLastName() {
        return lastNameInput.inputValue();
    }

    /**
     * Fill email input field
     * @param email email to fill
     */
    public UpdateUserPage fillEmail(String email) {
        emailInput.clear();
        emailInput.fill(email);
        return this;
    }

    /**
     * Get email value
     * @return email value
     */
    public String getEmail() {
        return emailInput.inputValue();
    }

    /**
     * Fill mobile area input field
     * @param mobileArea mobile area code to fill
     */
    public UpdateUserPage fillMobileArea(String mobileArea) {
        mobileAreaInput.clear();
        mobileAreaInput.fill(mobileArea);
        return this;
    }

    /**
     * Get mobile area value
     * @return mobile area value
     */
    public String getMobileArea() {
        return mobileAreaInput.inputValue();
    }

    /**
     * Fill mobile input field
     * @param mobile mobile number to fill
     */
    public UpdateUserPage fillMobile(String mobile) {
        mobileInput.clear();
        mobileInput.fill(mobile);
        return this;
    }

    /**
     * Get mobile value
     * @return mobile value
     */
    public String getMobile() {
        return mobileInput.inputValue();
    }

    /**
     * select time zone input selectbox
     * @param timeZone time zone to fill
     */
    public UpdateUserPage selectTimeZone(com.websanity.enums.TimeZone timeZone) {
        timeZoneSelectBox.selectOption(timeZone.getValue());
        return this;
    }

    /**
     * select language selectbox field
     * @param language time zone to fill
     */
    public UpdateUserPage selectLanguage(com.websanity.enums.Language language) {
        languageSelectBox.selectOption(language.getValue());
        return this;
    }

    /**
     * Get time zone value
     * @return time zone value
     */
    public String getSelectedTimeZone() {
        return timeZoneSelectBox.inputValue();
    }

    /**
     * Get currently selected language code
     * @return language code value
     */
    public String getSelectedLanguage() {
        return languageSelectBox.inputValue();
    }

    /**
     * Select country from dropdown
     * @param country Country enum value
     */
    public UpdateUserPage selectCountry(com.websanity.enums.Country country) {
        countrySelectBox.selectOption(country.getCode());
        return this;
    }

    /**
     * Get currently selected country code
     * @return country code value
     */
    public String getSelectedCountryCode() {
        return countrySelectBox.inputValue();
    }

    /**
     * Select mobile country code from dropdown
     * @param country Country enum value
     */
    public UpdateUserPage selectMobileCountryCode(com.websanity.enums.Country country) {
        mobileCountryCodeSelectbox.selectOption(country.getCode());
        return this;
    }

    /**
     * Get currently selected mobile country code
     * @return mobile country code value
     */
    public String getSelectedMobileCountryCode() {
        return mobileCountryCodeSelectbox.inputValue();
    }

    /**
     * Select user status from dropdown
     * @param status UserStatus enum value
     */
    public UpdateUserPage selectStatus(UserStatus status) {
        statusSelectBox.selectOption(status.getValue());
        return this;
    }

    /**
     * Get currently selected user status
     * @return UserStatus enum value
     */
    public UserStatus getSelectedStatus() {
        String selectedValue = statusSelectBox.inputValue();
        for (UserStatus status : UserStatus.values()) {
            if (status.getValue().equals(selectedValue)) {
                return status;
            }
        }
        return null;
    }

    /**
     * Get status display name (text)
     * @return status display name as String
     */
    public String getSelectedStatusText() {
        return statusSelectBox.locator("option:checked").textContent();
    }

    /**
     * Click on Update Status button and handle any alerts that appear
     * Automatically accepts confirmation alerts and enters "autoqa" as reason if prompted
     */
    public UpdateUserPage clickUpdateStatusButton() {
        setupAlertHandler();
        updateStatusButton.click();
        page.waitForTimeout(1000);
        return this;
    }
    /**
     * Click on Update Account Information button and handle any alerts that appear
     * Automatically accepts confirmation alerts and enters "autoqa" as reason if prompted
     */
    public UpdateUserPage clickUpdateAccountInformationButton() {
        setupAlertHandler();
        updateAccountInformationButton.click();
        page.waitForTimeout(1000);
        return this;
    }

    /**
     * Wait for success message to appear after deletion
     * Waits up to 30 seconds for the message to be visible
     */
    public UpdateUserPage waitForDeleteSuccessMessage() {
        log.info("Waiting for delete success message to appear");
        successMessage.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(30000));
        return this;
    }

    /**
     * Check if delete success message is visible
     * @return true if success message is visible
     */
    public boolean isDeleteSuccessMessageVisible() {
        try {
            return successMessage.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get the text of the success message
     * @return success message text
     */
    public String getDeleteSuccessMessageText() {
        return successMessage.textContent().trim();
    }

    /**
     * Wait for success message to appear after profile update
     * Waits up to 30 seconds for the message to be visible
     */
    public UpdateUserPage waitForUpdateSuccessMessage() {
        log.info("Waiting for update success message to appear");
        successMessage.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(30000));
        return this;
    }

    /**
     * Check if update success message is visible
     * @return true if success message is visible
     */
    public boolean isUpdateSuccessMessageVisible() {
        try {
            return successMessage.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get the text of the update success message
     * @return success message text
     */
    public String getUpdateSuccessMessageText() {
        return successMessage.textContent().trim();
    }

    /**
     * Wait for success message to appear after suspend
     * Waits up to 45 seconds for the message to be visible (increased for Docker)
     */
    public UpdateUserPage waitForSuspendSuccessMessage() {
        log.info("Waiting for suspend success message to appear");
        successMessage.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(45000));
        return this;
    }

    /**
     * Check if suspend success message is visible
     * @return true if success message is visible
     */
    public boolean isSuspendSuccessMessageVisible() {
        try {
            return successMessage.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get the text of the suspend success message
     * @return success message text
     */
    public String getSuspendSuccessMessageText() {
        return successMessage.textContent().trim();
    }

    /**
     * Wait for success message to appear after activate
     * Waits up to 30 seconds for the message to be visible
     */
    public UpdateUserPage waitForActivateSuccessMessage() {
        log.info("Waiting for activate success message to appear");
        successMessage.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(30000));
        return this;
    }

    /**
     * Check if activate success message is visible
     * @return true if success message is visible
     */
    public boolean isActivateSuccessMessageVisible() {
        try {
            return successMessage.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get the text of the activate success message
     * @return success message text
     */
    public String getActivateSuccessMessageText() {
        return successMessage.textContent().trim();
    }

    /**
     * Check if deleted user status label is visible
     * @return true if "Deleted User" label is visible
     */
    public boolean isDeletedUserStatusVisible() {
        try {
            return deletedUserStatusLabel.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get the text of deleted user status label
     * @return deleted user status text
     */
    public String getDeletedUserStatusText() {
        return deletedUserStatusLabel.textContent().trim();
    }

    /**
     * Select EN country from dropdown
     * @param country Country enum value
     */
    public UpdateUserPage selectEnCountry(com.websanity.enums.Country country) {
        enCountrySelectBox.selectOption(country.getCode());
        return this;
    }

    /**
     * Get currently selected EN country code
     * @return country code value
     */
    public String getSelectedEnCountryCode() {
        return enCountrySelectBox.inputValue();
    }

    /**
     * Select incoming provider from dropdown
     * @param provider Provider enum value
     */
    public UpdateUserPage selectIncomingProvider(com.websanity.enums.Provider provider) {
        enIncToProviderSelectBox.selectOption(provider.getValue());
        return this;
    }

    /**
     * Get currently selected incoming provider value
     * @return incoming provider value
     */
    public String getSelectedIncomingProvider() {
        return enIncToProviderSelectBox.inputValue();
    }

    /**
     * Select outgoing provider from dropdown
     * @param provider OutgoingProvider enum value
     */
    public UpdateUserPage selectOutgoingProvider(com.websanity.enums.OutgoingProvider provider) {
        enOutViaProviderSelectBox.selectOption(provider.getValue());
        return this;
    }

    /**
     * Get currently selected outgoing provider value
     * @return outgoing provider value
     */
    public String getSelectedOutgoingProvider() {
        return enOutViaProviderSelectBox.inputValue();
    }

    /**
     * Fill EN input field
     * @param enValue EN value to fill
     */
    public UpdateUserPage fillEnInput(String enValue) {
        enInput.clear();
        enInput.fill(enValue);
        return this;
    }

    /**
     * Get EN input value
     * @return EN input value
     */
    public String getEnInputValue() {
        return enInput.inputValue();
    }

    /**
     * Click on EN Update button
     */
    public UpdateUserPage clickEnUpdateButton() {
        log.info("Clicking EN Update button");
        enUpdateButton.click();
        return this;
    }

    /**
     * Wait for EN success message to appear
     */
    public UpdateUserPage waitForEnSuccessMessage() {
        log.info("Waiting for EN success message to appear");
        try {
            enSuccessMessage.waitFor(new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(30000));
        } catch (Exception e) {
            log.error("EN success message did not appear within 30 seconds: " + e.getMessage());
            throw e;
        }
        return this;
    }

    /**
     * Check if EN success message is visible
     * @return true if EN success message is visible
     */
    public boolean isEnSuccessMessageVisible() {
        try {
            return enSuccessMessage.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get EN success message text
     * @return success message text
     */
    public String getEnSuccessMessageText() {
        return enSuccessMessage.textContent().trim();
    }

    /**
     * Get added Enterprise Number value from the first row
     * @return enterprise number value
     */
    public String getAddedEnterpriseNumber() {
        return addedEnterpriseNumberRow.locator("td").nth(3).textContent().trim();
    }

    /**
     * Get added Enterprise Number country
     * @return country text
     */
    public String getAddedEnCountry() {
        return addedEnterpriseNumberRow.locator("td").nth(0).textContent().trim();
    }

    /**
     * Get added Enterprise Number incoming provider
     * @return incoming provider text
     */
    public String getAddedEnIncomingProvider() {
        return addedEnterpriseNumberRow.locator("td").nth(1).textContent().trim();
    }

    /**
     * Get added Enterprise Number outgoing provider
     * @return outgoing provider text
     */
    public String getAddedEnOutgoingProvider() {
        return addedEnterpriseNumberRow.locator("td").nth(2).textContent().trim();
    }

    /**
     * Click on EN delete checkbox to mark it for deletion
     */
    public UpdateUserPage clickEnDeleteCheckbox() {
        log.info("Clicking EN delete checkbox");
        enDeleteCheckbox.check();
        return this;
    }

    /**
     * Check if Enterprise Number row exists
     * @return true if EN row exists
     */
    public boolean isEnterpriseNumberRowVisible() {
        try {
            return addedEnterpriseNumberRow.count() > 0 && addedEnterpriseNumberRow.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click on Company Archive Management button (opens new tab)
     * @return TeleadminCompanyArchiveManagementPage instance for the new tab
     */
    public CompanyArchiveManagementPage clickCompanyArchiveManagementButton() {
        log.info("Clicking Company Archive Management button");

        // Wait for new page/tab to open
        Page newPage = page.context().waitForPage(companyArchiveManagementButton::click);

        log.info("New tab opened, switching to Company Archive Management page");
        newPage.waitForTimeout(2000);
        return new CompanyArchiveManagementPage(newPage);
    }

    /**
     * Click on Manage allowed email/domain button
     */
    public UpdateUserPage clickManageAllowedEmailDomainButton() {
        log.info("Clicking Manage allowed email/domain button");
        manageAllowedEmailDomainButton.click();
        return this;
    }

    /**
     * Click on Company Admin's Portal Settings button (opens new tab)
     * @return TeleadminAdminsPortalSettingsPage instance for the new tab
     */
    public AdminsPortalSettingsPage clickCompanyAdminPortalSettingsButton() {
        log.info("Clicking Company Admin's Portal Settings button");

        // Wait for new page/tab to open
        Page newPage = page.context().waitForPage(companyAdminPortalSettingsButton::click);

        log.info("New tab opened, switching to Admins Portal Settings page");
        newPage.waitForTimeout(2000);
        return new AdminsPortalSettingsPage(newPage);
    }

    /**
     * Click on WhatsApp Phone Capture Settings button (opens new tab)
     * @return TeleadminApplicationsSettingPage instance for the new tab
     */
    public ApplicationsSettingPage clickWhatsAppPhoneCaptureSettingsButton() {
        log.info("Clicking WhatsApp Phone Capture Settings button");

        // Wait for new page/tab to open
        Page newPage = page.context().waitForPage(whatsAppPhoneCaptureSettingsButton::click);

        log.info("New tab opened, switching to Applications Setting page");
        newPage.waitForTimeout(2000);
        return new ApplicationsSettingPage(newPage);
    }

    /**
     * Click on WhatsApp Cloud Capture Settings button (opens new tab)
     * @return TeleadminApplicationsSettingPage instance for the new tab
     */
    public ApplicationsSettingPage clickWhatsAppCloudCaptureSettingsButton() {
        log.info("Clicking WhatsApp Cloud Capture Settings button");

        // Wait for new page/tab to open
        Page newPage = page.context().waitForPage(whatsAppCloudCaptureSettingsButton::click);

        log.info("New tab opened, switching to Applications Setting page");
        newPage.waitForTimeout(2000);
        return new ApplicationsSettingPage(newPage);
    }

    /**
     * Click on Telegram Capture Settings button (opens new tab)
     * @return TeleadminApplicationsSettingPage instance for the new tab
     */
    public ApplicationsSettingPage clickTelegramCaptureSettingsButton() {
        log.info("Clicking Telegram Capture Settings button");

        // Wait for new page/tab to open
        Page newPage = page.context().waitForPage(telegramCaptureSettingsButton::click);

        log.info("New tab opened, switching to Applications Setting page");
        newPage.waitForTimeout(2000);
        return new ApplicationsSettingPage(newPage);
    }

    /**
     * Click on Signal Capture Settings button (opens new tab)
     * @return TeleadminApplicationsSettingPage instance for the new tab
     */
    public ApplicationsSettingPage clickSignalCaptureSettingsButton() {
        log.info("Clicking Signal Capture Settings button");

        // Wait for new page/tab to open
        Page newPage = page.context().waitForPage(signalCaptureSettingsButton::click);

        log.info("New tab opened, switching to Applications Setting page");
        newPage.waitForTimeout(2000);
        return new ApplicationsSettingPage(newPage);
    }

    /**
     * Click on Enterprise Number Capture Settings button (opens new tab)
     * @return TeleadminApplicationsSettingPage instance for the new tab
     */
    public ApplicationsSettingPage clickEnterpriseNumberCaptureSettingsButton() {
        log.info("Clicking Enterprise Number Capture Settings button");

        // Wait for new page/tab to open
        Page newPage = page.context().waitForPage(enterpriseNumberCaptureSettingsButton::click);

        log.info("New tab opened, switching to Applications Setting page");
        newPage.waitForTimeout(2000);
        return new ApplicationsSettingPage(newPage);
    }

    /**
     * Click on Android Capture Settings button (opens new tab)
     * @return TeleadminApplicationsSettingPage instance for the new tab
     */
    public ApplicationsSettingPage clickAndroidCaptureSettingsButton() {
        log.info("Clicking Android Capture Settings button");

        // Wait for new page/tab to open
        Page newPage = page.context().waitForPage(androidCaptureSettingsButton::click);

        log.info("New tab opened, switching to Applications Setting page");
        newPage.waitForTimeout(2000);
        return new ApplicationsSettingPage(newPage);
    }

    /**
     * Fill domain input field
     * @param domain domain to fill
     */
    public UpdateUserPage fillDomain(String domain) {
        log.info("Filling domain: " + domain);
        domainInput.clear();
        domainInput.fill(domain);
        return this;
    }

    /**
     * Get domain value
     * @return domain value
     */
    public String getDomain() {
        return domainInput.inputValue();
    }

    /**
     * Click on Add Domain button
     */
    public UpdateUserPage clickAddDomainButton() {
        log.info("Clicking Add Domain button");
        addDomainButton.click();
        return this;
    }

    /**
     * Click on Update Archiving Domain button
     */
    public UpdateUserPage clickUpdateArchivingDomainButton() {
        log.info("Clicking Update Archiving Domain button");
        updateArchivingDomainButton.click();
        return this;
    }

    /**
     * Wait for Allowed Archiving Domains success message to appear
     */
    public UpdateUserPage waitForAllowedDomainSuccessMessage() {
        log.info("Waiting for Allowed Archiving Domains success message to appear");
        Locator successMessage = textFrame.locator("span.sysMsg");
        successMessage.waitFor(new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                .setTimeout(60000));
        return this;
    }

    /**
     * Check if Allowed Archiving Domains success message is visible
     * @return true if message is visible
     */
    public boolean isAllowedDomainSuccessMessageVisible() {
        try {
            return textFrame.locator("span.sysMsg").isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get Allowed Archiving Domains success message text
     * @return success message text
     */
    public String getAllowedDomainSuccessMessageText() {
        return textFrame.locator("span.sysMsg").textContent().trim();
    }

    /**
     * Get domain0 input value
     * @return domain0 value
     */
    public String getDomain0Value() {
        return domain0Input.inputValue();
    }

}
