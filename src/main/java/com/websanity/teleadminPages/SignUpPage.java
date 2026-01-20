package com.websanity.teleadminPages;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.websanity.BasePage;
import com.websanity.enums.Country;
import com.websanity.enums.Language;
import com.websanity.enums.UserTypes;
import com.websanity.enums.TimeZone;
import com.websanity.models.UserParams;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class SignUpPage extends BasePage {

    private final FrameLocator textFrame;

    // Locators
    private final Locator serviceLevelSelectBox;
    private final Locator customerAdministratorSelectBox;
    private final Locator prepaidFactorSelectBox;
    private final Locator monthlyChargeInput;
    private final Locator firstNameInput;
    private final Locator lastNameInput;
    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator confirmPasswordInput;
    private final Locator platformAccountIdInput;
    private final Locator timeZoneSelectBox;
    private final Locator languageSelectBox;
    private final Locator countrySelectBox;
    private final Locator homeCountryCodeSelectBox;
    private final Locator homeAreaInput;
    private final Locator homePhoneInput;
    private final Locator mobileCountryCodeSelectBox;
    private final Locator mobileAreaInput;
    private final Locator mobileInput;
    private final Locator businessCountryCodeSelectBox;
    private final Locator businessAreaInput;
    private final Locator businessPhoneInput;
    private final Locator businessExtInput;
    private final Locator faxCountrySelectBox;
    private final Locator faxAreaInput;
    private final Locator faxPhoneInput;
    private final Locator emailInput;
    private final Locator companyInput;
    private final Locator addToGlobalAddressBookCheckbox;
    private final Locator allowedManagedUsersInput;
    private final Locator uniqueCustomerCodeInput;
    private final Locator udidInput;
    private final Locator sendWelcomeMessageEmailCheckbox;
    private final Locator sendWelcomeMessageMobileCheckbox;
    private final Locator registerButton;
    private final Locator successMessage;


    public SignUpPage(Page page) {
        super(page);
        this.textFrame = page.frameLocator("frame[name='text']");
        this.serviceLevelSelectBox = textFrame.locator("#type");
        this.customerAdministratorSelectBox = textFrame.locator("#exclusive_admin_id");
        this.prepaidFactorSelectBox = textFrame.locator("#prepaidFactor");
        this.monthlyChargeInput = textFrame.locator("#monthlyCharge");
        this.firstNameInput = textFrame.locator("#firstName");
        this.lastNameInput = textFrame.locator("#lastName");
        this.usernameInput = textFrame.locator("#userid");
        this.passwordInput = textFrame.locator("#password");
        this.confirmPasswordInput = textFrame.locator("#confirmPassword");
        this.platformAccountIdInput = textFrame.locator("#platformAccountID");
        this.timeZoneSelectBox = textFrame.locator("#timeZone");
        this.languageSelectBox = textFrame.locator("#languageID");
        this.countrySelectBox = textFrame.locator("#countryID");
        this.homeCountryCodeSelectBox = textFrame.locator("#homeCountry1");
        this.homeAreaInput = textFrame.locator("#homeArea1");
        this.homePhoneInput = textFrame.locator("#homeTelephone1");
        this.mobileCountryCodeSelectBox = textFrame.locator("#mobileCountry1");
        this.mobileAreaInput = textFrame.locator("#mobileArea1");
        this.mobileInput = textFrame.locator("#mobileTelephone1");
        this.businessCountryCodeSelectBox = textFrame.locator("#businessCountry1");
        this.businessAreaInput = textFrame.locator("#businessArea1");
        this.businessPhoneInput = textFrame.locator("#businessTelephone1");
        this.businessExtInput = textFrame.locator("#businessExt1");
        this.faxCountrySelectBox = textFrame.locator("#faxCountry1");
        this.faxAreaInput = textFrame.locator("#businessTelephone1");
        this.faxPhoneInput = textFrame.locator("#businessExt1");
        this.emailInput = textFrame.locator("#communicationEmail");
        this.companyInput = textFrame.locator("#company");
        this.addToGlobalAddressBookCheckbox = textFrame.locator("#gl_address_book");
        this.allowedManagedUsersInput = textFrame.locator("#allowedManagedUsers");
        this.uniqueCustomerCodeInput = textFrame.locator("#ucc");
        this.udidInput = textFrame.locator("#udid");
        this.sendWelcomeMessageEmailCheckbox = textFrame.locator("#sendWelcomeMessageEmail");
        this.sendWelcomeMessageMobileCheckbox = textFrame.locator("#sendWelcomeMessageMobile");
        this.registerButton = textFrame.locator("img[src*='button_register.gif']");
        this.successMessage = textFrame.locator("#sysMsgTable span.sysMsg");
    }

    // ==================== Service Level & Admin ====================

    /**
     * Select service level from dropdown using enum
     */
    public void selectServiceLevel(UserTypes userTypes) {
        serviceLevelSelectBox.selectOption(userTypes.getValue());
    }

    /**
     * Select customer administrator
     */
    public void selectCustomerAdministrator(String adminValue) {
        customerAdministratorSelectBox.selectOption(adminValue);
    }

    /**
     * Select prepaid factor
     */
    public void selectPrepaidFactor(String factor) {
        prepaidFactorSelectBox.selectOption(factor);
    }

    /**
     * Enter monthly charge
     */
    public void enterMonthlyCharge(String charge) {
        monthlyChargeInput.fill(charge);
    }

    // ==================== User Information ====================

    /**
     * Enter first name
     */
    public void enterFirstName(String firstName) {
        firstNameInput.fill(firstName);
    }

    /**
     * Enter last name
     */
    public void enterLastName(String lastName) {
        lastNameInput.fill(lastName);
    }

    /**
     * Enter username
     */
    public void enterUsername(String username) {
        usernameInput.fill(username);
    }

    /**
     * Enter password
     */
    public void enterPassword(String password) {
        passwordInput.fill(password);
    }

    /**
     * Enter confirm password
     */
    public void enterConfirmPassword(String confirmPassword) {
        confirmPasswordInput.fill(confirmPassword);
    }

    /**
     * Enter platform account ID
     */
    public void enterPlatformAccountId(String accountId) {
        platformAccountIdInput.fill(accountId);
    }

    // ==================== Location & Language ====================

    /**
     * Select timezone from dropdown using enum
     */
    public void selectTimeZone(TimeZone timeZone) {
        timeZoneSelectBox.selectOption(timeZone.getValue());
    }

    /**
     * Select language from dropdown using enum
     */
    public void selectLanguage(Language language) {
        languageSelectBox.selectOption(language.getValue());
    }

    /**
     * Select country from dropdown using enum
     */
    public void selectCountry(Country country) {
        countrySelectBox.selectOption(country.getCode());
    }

    // ==================== Phone Numbers ====================

    /**
     * Select home country code
     */
    public void selectHomeCountryCode(Country country) {
        homeCountryCodeSelectBox.selectOption(country.getCode());
    }

    /**
     * Enter home area code
     */
    public void enterHomeArea(String area) {
        homeAreaInput.fill(area);
    }

    /**
     * Enter home phone number
     */
    public void enterHomePhone(String phone) {
        homePhoneInput.fill(phone);
    }

    /**
     * Fill complete home phone (country, area, number)
     */
    public void fillHomePhone(Country country, String area, String phone) {
        selectHomeCountryCode(country);
        enterHomeArea(area);
        enterHomePhone(phone);
    }

    /**
     * Select mobile country code
     */
    public void selectMobileCountryCode(Country country) {
        mobileCountryCodeSelectBox.selectOption(country.getCode());
    }

    /**
     * Enter mobile area code
     */
    public void enterMobileArea(String area) {
        mobileAreaInput.fill(area);
    }

    /**
     * Enter mobile phone number
     */
    public void enterMobilePhone(String phone) {
        mobileInput.fill(phone);
    }

    /**
     * Fill complete mobile phone (country, area, number)
     */
    public void fillMobilePhone(Country country, String area, String phone) {
        selectMobileCountryCode(country);
        enterMobileArea(area);
        enterMobilePhone(phone);
    }

    /**
     * Select business country code
     */
    public void selectBusinessCountryCode(Country country) {
        businessCountryCodeSelectBox.selectOption(country.getCode());
    }

    /**
     * Enter business area code
     */
    public void enterBusinessArea(String area) {
        businessAreaInput.fill(area);
    }

    /**
     * Enter business phone number
     */
    public void enterBusinessPhone(String phone) {
        businessPhoneInput.fill(phone);
    }

    /**
     * Enter business extension
     */
    public void enterBusinessExt(String ext) {
        businessExtInput.fill(ext);
    }

    /**
     * Fill complete business phone (country, area, number, ext)
     */
    public void fillBusinessPhone(Country country, String area, String phone, String ext) {
        selectBusinessCountryCode(country);
        enterBusinessArea(area);
        enterBusinessPhone(phone);
        enterBusinessExt(ext);
    }

    /**
     * Select fax country code
     */
    public void selectFaxCountryCode(Country country) {
        faxCountrySelectBox.selectOption(country.getCode());
    }

    /**
     * Enter fax area code
     */
    public void enterFaxArea(String area) {
        faxAreaInput.fill(area);
    }

    /**
     * Enter fax phone number
     */
    public void enterFaxPhone(String phone) {
        faxPhoneInput.fill(phone);
    }

    /**
     * Fill complete fax phone (country, area, number)
     */
    public void fillFaxPhone(Country country, String area, String phone) {
        selectFaxCountryCode(country);
        enterFaxArea(area);
        enterFaxPhone(phone);
    }

    // ==================== Additional Information ====================

    /**
     * Enter email address
     */
    public void enterEmail(String email) {
        emailInput.fill(email);
    }

    /**
     * Enter company name
     */
    public void enterCompany(String company) {
        companyInput.fill(company);
    }

    /**
     * Check/uncheck "Add to Global Address Book"
     */
    public void setAddToGlobalAddressBook(boolean check) {
        if (check) {
            addToGlobalAddressBookCheckbox.check();
        } else {
            addToGlobalAddressBookCheckbox.uncheck();
        }
    }

    /**
     * Enter allowed managed users
     */
    public void enterAllowedManagedUsers(String count) {
        allowedManagedUsersInput.fill(count);
    }

    /**
     * Enter unique customer code
     */
    public void enterUniqueCustomerCode(String code) {
        uniqueCustomerCodeInput.fill(code);
    }

    /**
     * Enter UDID
     */
    public void enterUdid(String udid) {
        udidInput.fill(udid);
    }

    /**
     * Check/uncheck "Send Welcome Message by Email"
     */
    public void setSendWelcomeMessageEmail(boolean check) {
        if (check) {
            sendWelcomeMessageEmailCheckbox.check();
        } else {
            sendWelcomeMessageEmailCheckbox.uncheck();
        }
    }

    /**
     * Check/uncheck "Send Welcome Message by Mobile"
     */
    public void setSendWelcomeMessageMobile(boolean check) {
        if (check) {
            sendWelcomeMessageMobileCheckbox.check();
        } else {
            sendWelcomeMessageMobileCheckbox.uncheck();
        }
    }

    // ==================== Submit ====================

    /**
     * Click Register Now button
     */
    public void clickRegisterButton() {
        registerButton.click();
    }

    /**
     * Wait for success message to appear after registration
     * @param timeout timeout in milliseconds
     */
    public void waitForSuccessMessage(int timeout) {
        try {
            successMessage.waitFor(new com.microsoft.playwright.Locator.WaitForOptions()
                    .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE)
                    .setTimeout(timeout));
        } catch (Exception e) {
            // Check if error messages are present
            Locator errorMessages = textFrame.locator("#sysMsgTable div.sysMsgError");
            if (errorMessages.count() > 0) {
                StringBuilder errors = new StringBuilder("Registration failed with errors:\n");
                for (int i = 0; i < errorMessages.count(); i++) {
                    String errorText = errorMessages.nth(i).textContent().trim();
                    errors.append(errorText).append("\n");
                }
                log.error(errors.toString());
                throw new RuntimeException(errors.toString());
            }
            throw e;
        }
    }

    /**
     * Wait for success message to appear with default timeout (30 seconds)
     */
    public void waitForSuccessMessage() {
        log.info("Waiting for success message to appear");
        waitForSuccessMessage(30000);
    }

    /**
     * Check if success message is visible
     */
    public boolean isSuccessMessageVisible() {
        return successMessage.isVisible();
    }

    /**
     * Get success message text
     */
    public String getSuccessMessageText() {
        return successMessage.textContent().trim();
    }


    // ==================== Complete Registration ====================

    /**
     * Fill complete user registration form using UserParams object
     * This is the recommended method for creating new users
     * @param user UserParams object containing all user data
     * @return TeleadminSignUpPage instance for method chaining
     */
    public SignUpPage registerNewUser(UserParams user) {
        log.info("=== Starting user registration ===");

        // Service Level & Admin
        if (user.getUserType() != null) {
            log.info("Entering Service Level: {}", user.getUserType().getDisplayName());
            selectServiceLevel(user.getUserType());
            page.waitForTimeout(3000);
        }
        if (user.getCustomerAdministrator() != null) {
            log.info("Entering Customer Administrator: {}", user.getCustomerAdministrator());
            selectCustomerAdministrator(user.getCustomerAdministrator());
        }
        if (user.getPrepaidFactor() != null) {
            log.info("Entering Prepaid Factor: {}", user.getPrepaidFactor());
            selectPrepaidFactor(user.getPrepaidFactor());
        }
        if (user.getMonthlyCharge() != null) {
            log.info("Entering Monthly Charge: {}", user.getMonthlyCharge());
            enterMonthlyCharge(user.getMonthlyCharge());
        }

        // User Information
        if (user.getFirstName() != null) {
            log.info("Entering First Name: {}", user.getFirstName());
            enterFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            log.info("Entering Last Name: {}", user.getLastName());
            enterLastName(user.getLastName());
        }
        if (user.getUsername() != null) {
            log.info("Entering Username: {}", user.getUsername());
            enterUsername(user.getUsername());
        }
        if (user.getPassword() != null) {
            log.info("Entering Password: *******");
            enterPassword(user.getPassword());
            enterConfirmPassword(user.getPassword());
        }
        if (user.getPlatformAccountId() != null) {
            log.info("Entering Platform Account ID: {}", user.getPlatformAccountId());
            enterPlatformAccountId(user.getPlatformAccountId());
        }

        // Location & Language
        if (user.getTimeZone() != null) {
            log.info("Entering Time Zone: {}", user.getTimeZone().getDisplayName());
            selectTimeZone(user.getTimeZone());
        }
        if (user.getLanguage() != null) {
            log.info("Entering Language: {}", user.getLanguage().getDisplayName());
            selectLanguage(user.getLanguage());
        }
        if (user.getCountry() != null) {
            log.info("Entering Country: {}", user.getCountry().getDisplayName());
            selectCountry(user.getCountry());
        }

        // Phone Numbers - Home
        if (user.getHomeCountryCode() != null || user.getHomeArea() != null || user.getHomePhone() != null) {
            if (user.getHomeCountryCode() != null) {
                log.info("Entering Home Country Code: {}", user.getHomeCountryCode().getCode());
                selectHomeCountryCode(user.getHomeCountryCode());
            }
            if (user.getHomeArea() != null) {
                log.info("Entering Home Area: {}", user.getHomeArea());
                enterHomeArea(user.getHomeArea());
            }
            if (user.getHomePhone() != null) {
                log.info("Entering Home Phone: {}", user.getHomePhone());
                enterHomePhone(user.getHomePhone());
            }
        }

        // Phone Numbers - Mobile
        if (user.getMobileCountryCode() != null || user.getMobileArea() != null || user.getMobilePhone() != null) {
            if (user.getMobileCountryCode() != null) {
                log.info("Entering Mobile Country Code: {}", user.getMobileCountryCode().getCode());
                selectMobileCountryCode(user.getMobileCountryCode());
            }
            if (user.getMobileArea() != null) {
                log.info("Entering Mobile Area: {}", user.getMobileArea());
                enterMobileArea(user.getMobileArea());
            }
            if (user.getMobilePhone() != null) {
                log.info("Entering Mobile Phone: {}", user.getMobilePhone());
                enterMobilePhone(user.getMobilePhone());
            }
        }

        // Phone Numbers - Business
        if (user.getBusinessCountryCode() != null || user.getBusinessArea() != null ||
            user.getBusinessPhone() != null || user.getBusinessExt() != null) {
            if (user.getBusinessCountryCode() != null) {
                log.info("Entering Business Country Code: {}", user.getBusinessCountryCode().getCode());
                selectBusinessCountryCode(user.getBusinessCountryCode());
            }
            if (user.getBusinessArea() != null) {
                log.info("Entering Business Area: {}", user.getBusinessArea());
                enterBusinessArea(user.getBusinessArea());
            }
            if (user.getBusinessPhone() != null) {
                log.info("Entering Business Phone: {}", user.getBusinessPhone());
                enterBusinessPhone(user.getBusinessPhone());
            }
            if (user.getBusinessExt() != null) {
                log.info("Entering Business Ext: {}", user.getBusinessExt());
                enterBusinessExt(user.getBusinessExt());
            }
        }

        // Phone Numbers - Fax
        if (user.getFaxCountryCode() != null || user.getFaxArea() != null || user.getFaxPhone() != null) {
            if (user.getFaxCountryCode() != null) {
                log.info("Entering Fax Country Code: {}", user.getFaxCountryCode().getCode());
                selectFaxCountryCode(user.getFaxCountryCode());
            }
            if (user.getFaxArea() != null) {
                log.info("Entering Fax Area: {}", user.getFaxArea());
                enterFaxArea(user.getFaxArea());
            }
            if (user.getFaxPhone() != null) {
                log.info("Entering Fax Phone: {}", user.getFaxPhone());
                enterFaxPhone(user.getFaxPhone());
            }
        }

        // Additional Information
        if (user.getEmail() != null) {
            log.info("Entering Email: {}", user.getEmail());
            enterEmail(user.getEmail());
        }
        if (user.getCompany() != null) {
            log.info("Entering Company: {}", user.getCompany());
            enterCompany(user.getCompany());
        }
        if (user.getAddToGlobalAddressBook() != null) {
            log.info("Setting 'Add to Global Address Book' to: {}", user.getAddToGlobalAddressBook());
            setAddToGlobalAddressBook(user.getAddToGlobalAddressBook());
        }
        if (user.getAllowedManagedUsers() != null) {
            log.info("Entering Allowed Managed Users: {}", user.getAllowedManagedUsers());
            enterAllowedManagedUsers(user.getAllowedManagedUsers());
        }
        if (user.getUniqueCustomerCode() != null) {
            log.info("Entering Unique Customer Code: {}", user.getUniqueCustomerCode());
            enterUniqueCustomerCode(user.getUniqueCustomerCode());
        }
        if (user.getUdid() != null) {
            log.info("Entering UDID: {}", user.getUdid());
            enterUdid(user.getUdid());
        }
        if (user.getSendWelcomeMessageEmail() != null) {
            log.info("Setting 'Send Welcome Message by Email' to: {}", user.getSendWelcomeMessageEmail());
            setSendWelcomeMessageEmail(user.getSendWelcomeMessageEmail());
        }
        if (user.getSendWelcomeMessageMobile() != null) {
            log.info("Setting 'Send Welcome Message by Mobile' to: {}", user.getSendWelcomeMessageMobile());
            setSendWelcomeMessageMobile(user.getSendWelcomeMessageMobile());
        }

        // Submit
        log.info("Clicking Register button");
        clickRegisterButton();
        log.info("=== User registration form submitted ===");

        return this;
    }

}
