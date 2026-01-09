package com.websanity.tests;

import com.websanity.BaseTest;
import com.websanity.enums.*;
import com.websanity.models.UserParams;
import com.websanity.teleadminPages.*;
import io.qameta.allure.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Epic("TeleAdmin Sanity Tests")
@Feature("Account Settings/Applicatoin Settings functionality")
public class TeleadminSanityTest extends BaseTest {

    private TeleadminLogInPage teleadminLogInPage;
    private TeleadminMenuPage teleadminMenuPage;
    private TeleadminFindUsersPage findUsersPage;
    private TeleadminUpdateUserPage updateUserPage;
    private TeleadminSignUpPage signUpPage;
    private TeleadminCompanyArchiveManagementPage companyArchivePage;
    private TeleadminAdminsPortalSettingsPage adminsPortalPage;

    private static UserParams user;

    @BeforeAll
    static void setUpAll() {

        String uniqueNum = String.format("%07d", System.currentTimeMillis() % 10000000);

         user = UserParams.builder()
                .userType(UserTypes.PROMANAGER)
                .firstName("autofn" + uniqueNum)
                .lastName("autoln" + uniqueNum)
                .username("autoun" + uniqueNum)
                .password("Aa123123123123123")
                .platformAccountId("AAAAAAAAAAAAAAAAAA")
                .country(Country.UNITED_STATES)
                .timeZone(TimeZone.EST)
                .language(Language.ENGLISH_US)
                .homeCountryCode(Country.ISRAEL)
                .homeArea("58")
                .homePhone(uniqueNum)
                .mobileCountryCode(Country.ISRAEL)
                .mobileArea("58")
                .mobilePhone(uniqueNum)
                .email("autoem" + uniqueNum + "@gmail.com")
                .company("autocomp" + uniqueNum)
                .build();

    }

    @BeforeEach
    void setUp() {
        teleadminLogInPage = new TeleadminLogInPage(page);
        teleadminMenuPage = new TeleadminMenuPage(page);
        findUsersPage = new TeleadminFindUsersPage(page);
        updateUserPage = new TeleadminUpdateUserPage(page);
        signUpPage = new TeleadminSignUpPage(page);

        teleadminLogInPage
                .open()
                .logInToTeleadmin()
                .waitForFindUsersPageToLoad();

    }

    @Test
    @Order(1)
    @Story("User Registration")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Register a new Pro Manager user and verify success message")
    void registerNewUserTest() {

        log.info("Starting test: Register new Pro Manager");

        teleadminMenuPage
                .clickSignUpButton()
                .registerNewUser(user)
                .waitForSuccessMessage();

        log.info("Verifying success message is visible");
        assertTrue(signUpPage.isSuccessMessageVisible(), "Success message should be visible after registration");

        log.info("Verifying success message text");
        assertEquals("User registered successfully", signUpPage.getSuccessMessageText(), "Success message text should match");

        log.info("User registration completed successfully");

    }

    @Test
    @Order(2)
    @Story("Find User")
    void findUserAndOpenHisUpdateUserPage() {

        log.info("Starting test: Search for user and click on username in table");

        String searchUsername = user.getUsername();

        findUsersPage
                .searchUser(searchUsername)
                .checkThatUserWasFoundAndClickOnHim(searchUsername, updateUserPage);

        log.info("Test completed successfully");

    }

    @Test
    @Order(3)
    @Story("User Update")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Update user account information and verify all fields are updated correctly")
    void updateAccountInformation() {

        log.info("Starting test: Update account information");

        String uniqueNum = String.format("%07d", System.currentTimeMillis() % 10000000);

        UserParams paramsToUpdate = UserParams.builder()
                .firstName("autoupdfn" + uniqueNum)
                .lastName("autoupdln" + uniqueNum)
                .username("autoupdun" + uniqueNum)
                .country(Country.UNITED_KINGDOM)
                .timeZone(TimeZone.EUROPE_LONDON)
                .language(Language.HEBREW)
                .mobileCountryCode(Country.UNITED_STATES)
                .mobileArea("603")
                .mobilePhone(uniqueNum)
                .email("autoupdem" + uniqueNum + "@gmail.com")
                .company("autocomp" + uniqueNum)
                .build();

        String searchUsername = user.getUsername();

        findUsersPage
                .searchUser(searchUsername)
                .checkThatUserWasFoundAndClickOnHim(searchUsername, updateUserPage)
                .fillUsername(paramsToUpdate.getUsername())
                .fillFirstName(paramsToUpdate.getFirstName())
                .fillLastName(paramsToUpdate.getLastName())
                .selectCountry(paramsToUpdate.getCountry())
                .selectTimeZone(paramsToUpdate.getTimeZone())
                .selectLanguage(paramsToUpdate.getLanguage())
                .fillEmail(paramsToUpdate.getEmail())
                .selectMobileCountryCode(paramsToUpdate.getMobileCountryCode())
                .fillMobileArea(paramsToUpdate.getMobileArea())
                .fillMobile(paramsToUpdate.getMobilePhone())
                .clickUpdateAccountInformationButton()
                .waitForUpdateSuccessMessage();

        log.info("Verifying update success message is visible");
        assertTrue(updateUserPage.isUpdateSuccessMessageVisible(), "Update success message should be visible");

        log.info("Verifying update success message text");
        assertEquals("User profile was updated successfully.", updateUserPage.getUpdateSuccessMessageText(),
                "Update success message text should match");

        log.info("Verifying all updated field values");
        assertEquals(paramsToUpdate.getUsername(), updateUserPage.getUsername(), "Username should match updated value");
        assertEquals(paramsToUpdate.getFirstName(), updateUserPage.getFirstName(), "First name should match updated value");
        assertEquals(paramsToUpdate.getLastName(), updateUserPage.getLastName(), "Last name should match updated value");
        assertEquals(paramsToUpdate.getEmail(), updateUserPage.getEmail(), "Email should match updated value");
        assertEquals(paramsToUpdate.getCountry().getCode(), updateUserPage.getSelectedCountryCode(), "Country should match updated value");
        assertEquals(paramsToUpdate.getTimeZone().getValue(), updateUserPage.getSelectedTimeZone(), "Time zone should match updated value");
        assertEquals(paramsToUpdate.getLanguage().getValue(), updateUserPage.getSelectedLanguage(), "Language should match updated value");
        assertEquals(paramsToUpdate.getMobileCountryCode().getCode(), updateUserPage.getSelectedMobileCountryCode(), "Mobile country code should match updated value");
        assertEquals(paramsToUpdate.getMobileArea(), updateUserPage.getMobileArea(), "Mobile area should match updated value");
        assertEquals(paramsToUpdate.getMobilePhone(), updateUserPage.getMobile(), "Mobile phone should match updated value");

        user = user.toBuilder()
                .username(paramsToUpdate.getUsername())
                .firstName(paramsToUpdate.getFirstName())
                .lastName(paramsToUpdate.getLastName())
                .email(paramsToUpdate.getEmail())
                .country(paramsToUpdate.getCountry())
                .timeZone(paramsToUpdate.getTimeZone())
                .language(paramsToUpdate.getLanguage())
                .mobileCountryCode(paramsToUpdate.getMobileCountryCode())
                .mobileArea(paramsToUpdate.getMobileArea())
                .mobilePhone(paramsToUpdate.getMobilePhone())
                .build();

        log.info("Test completed successfully");

    }

    @Test
    @Order(4)
    @Story("User Status Management")
    void suspendAndActivateUser() {

        log.info("Starting test: Suspend and Activate user");

        String username = user.getUsername();

        findUsersPage
                .searchUser(username)
                .checkThatUserWasFoundAndClickOnHim(username, updateUserPage)
                .selectStatus(UserStatus.SUSPENDED)
                .clickUpdateStatusButton()
                .waitForSuspendSuccessMessage();

        log.info("Verifying suspend success message is visible");
        assertTrue(updateUserPage.isSuspendSuccessMessageVisible(), "Suspend success message should be visible");

        log.info("Verifying suspend success message text");
        assertEquals("User(s) suspended successfully", updateUserPage.getSuspendSuccessMessageText(),
                "Suspend success message text should match");

        log.info("Verifying user status is Suspended");
        assertEquals(UserStatus.SUSPENDED, updateUserPage.getSelectedStatus(), "User status should be Suspended");

        log.info("Selecting Active status");
        updateUserPage
                .selectStatus(UserStatus.ACTIVE)
                .clickUpdateStatusButton()
                .waitForActivateSuccessMessage();

        log.info("Verifying activate success message is visible");
        assertTrue(updateUserPage.isActivateSuccessMessageVisible(), "Activate success message should be visible");

        log.info("Verifying activate success message text");
        assertEquals("User(s) activated successfully", updateUserPage.getActivateSuccessMessageText(),
                "Activate success message text should match");

        log.info("Verifying user status is Active");
        assertEquals(UserStatus.ACTIVE, updateUserPage.getSelectedStatus(), "User status should be Active");

        log.info("Test completed successfully");


    }

    @Test
    @Order(5)
    @Story("Enterprise Numbers Management")
    void addRemoveEnterpriseNumber() {

        log.info("Starting test: Add and Remove Enterprise Number");

        String username = user.getUsername();
        String enterpriseNumber = "97258" + String.format("%07d", System.currentTimeMillis() % 10000000);

        // Add Enterprise Number
        log.info("Adding Enterprise Number: " + enterpriseNumber);
        findUsersPage
                .searchUser(username)
                .checkThatUserWasFoundAndClickOnHim(username, updateUserPage)
                .selectEnCountry(Country.ALL_COUNTRIES)
                .selectIncomingProvider(Provider.CELLCOM)
                .selectOutgoingProvider(OutgoingProvider.BANDWIDTH_CHARLIE_USA)
                .fillEnInput(enterpriseNumber)
                .clickEnUpdateButton()
                .waitForEnSuccessMessage();

        log.info("Verifying EN success message is visible");
        assertTrue(updateUserPage.isEnSuccessMessageVisible(), "EN success message should be visible");

        log.info("Verifying EN success message text");
        assertEquals("User Enterprise Number(s) updated successfully", updateUserPage.getEnSuccessMessageText(),
                "EN success message text should match");

        log.info("Verifying added Enterprise Number details");
        assertEquals("All Countries", updateUserPage.getAddedEnCountry(), "EN Country should be 'All Countries'");
        assertEquals("1 (Cellcom)", updateUserPage.getAddedEnIncomingProvider(), "EN Incoming Provider should match");
        assertEquals("13 (Bandwidth-CHARLIE-USA)", updateUserPage.getAddedEnOutgoingProvider(), "EN Outgoing Provider should match");
        assertEquals(enterpriseNumber, updateUserPage.getAddedEnterpriseNumber(), "EN number should match");

        log.info("Enterprise Number added successfully. Now removing it...");

        // Remove Enterprise Number
        updateUserPage
                .clickEnDeleteCheckbox()
                .clickEnUpdateButton()
                .waitForEnSuccessMessage();

        log.info("Verifying EN removal success message is visible");
        assertTrue(updateUserPage.isEnSuccessMessageVisible(), "EN removal success message should be visible");

        log.info("Verifying EN removal success message text");
        assertEquals("User Enterprise Number(s) updated successfully", updateUserPage.getEnSuccessMessageText(),
                "EN removal success message text should match");

        log.info("Verifying Enterprise Number row is no longer visible");
        assertFalse(updateUserPage.isEnterpriseNumberRowVisible(), "Enterprise Number row should not be visible after deletion");

        log.info("Test completed successfully");

    }

    @Test
    @Order(6)
    @Story("Allowed Domains Management")
    void addAllowedDomain() {

        log.info("Starting test: Add allowed domain");

        String username = user.getUsername();
        String domain = "autod" + String.format("%05d", System.currentTimeMillis() % 100000) + ".com";

        findUsersPage
                .searchUser(username)
                .checkThatUserWasFoundAndClickOnHim(username, updateUserPage)
                .clickManageAllowedEmailDomainButton()
                .fillDomain(domain)
                .clickAddDomainButton()
                .clickUpdateArchivingDomainButton()
                .waitForAllowedDomainSuccessMessage();

        log.info("Verifying allowed domain success message is visible");
        assertTrue(updateUserPage.isAllowedDomainSuccessMessageVisible(), "Allowed domain success message should be visible");

        log.info("Verifying allowed domain success message text");
        assertEquals("Allowed Archiving Domains updated successfully", updateUserPage.getAllowedDomainSuccessMessageText(),
                "Allowed domain success message text should match");

        log.info("Verifying added domain is present in domain0 input field");
        assertEquals(domain, updateUserPage.getDomain0Value(),
                "Domain0 input should contain the added domain");

        log.info("Test completed successfully");

    }

    @Test
    @Order(7)
    @Story("Company Archive Management")
    void createSourceDestinationAndPlanTest() {

        log.info("Starting test: Training Test");

        String username = user.getUsername();
        String sourceDescription = "WhatsApp Source " + String.format("%07d", System.currentTimeMillis() % 10000000);
        SourceType sourceType = SourceType.WHATSAPP_ARCHIVER;

        //Create Source
        companyArchivePage = findUsersPage
                .searchUser(username)
                .checkThatUserWasFoundAndClickOnHim(username, updateUserPage)
                .clickCompanyArchiveManagementButton()
                .clickAddNewSourceButton()
                .selectSourceType(sourceType)
                .fillSourceDescription(sourceDescription)
                .clickAddSourceButton();

        log.info("Verifying Source Type in the first row");
        assertEquals("WHATSAPP_ARCHIVER", companyArchivePage.getFirstRowCellText(1),
                "Source Type should be WHATSAPP_ARCHIVER");

        log.info("Verifying Network Type in the first row");
        assertEquals("WHATSAPP_ARCHIVER", companyArchivePage.getFirstRowCellText(2),
                "Network Type should be WHATSAPP_ARCHIVER");

        log.info("Verifying Description in the first row");
        assertEquals(sourceDescription, companyArchivePage.getFirstRowCellText(3),
                "Description should match the entered description");

        // Get and store Source Policy ID
        String sourcePolicyId = companyArchivePage.getFirstRowCellText(0);
        log.info("Source Policy ID: " + sourcePolicyId);

        //Create Policy
        String storageDescription = "Generic SMTP " + String.format("%07d", System.currentTimeMillis() % 10000000);
        String email = "trainem" + String.format("%07d", System.currentTimeMillis() % 10000000) + "@gmail.com";

        companyArchivePage
                .clickAddNewPolicyButton()
                .selectStorage(Storage.GENERIC_SMTP_ARCHIVER)
                .fillStorageDescription(storageDescription)
                .selectPersonalArchive(YesNo.NO)
                .fillStorageDestinationEmail(email)
                .selectParticipantHeaderFormat(ParticipantHeaderFormat.FIRST_NAME_LAST_NAME_MOBILE_EMAIL)
                .selectSpoofingHandling(SpoofingHandling.CONFIG_FROM_HEADERS)
                .selectShowLocalAddressBookNames(YesNo.YES)
                .clickAddPolicyButton();

        log.info("Verifying that policy was added to the policies table");
        assertTrue(companyArchivePage.isPoliciesTableVisible(), "Policies table should be visible");

        log.info("Verifying Storage Type in the first policy row");
        String expectedStorageText = companyArchivePage.getFirstPolicyRowCellText(1);
        assertTrue(expectedStorageText.contains("GENERIC SMTP ARCHIVER"), "Storage Type should contain GENERIC SMTP ARCHIVER");

        log.info("Verifying Description in the first policy row");
        assertEquals(storageDescription, companyArchivePage.getFirstPolicyRowCellText(2), "Description should match the entered storage description");

        // Get and store Storage Policy ID
        String storagePolicyId = companyArchivePage.getFirstPolicyRowCellText(0);
        log.info("Storage Policy ID: " + storagePolicyId);

        //Create Plan
        String planDescription = "AutoPlan " + String.format("%07d", System.currentTimeMillis() % 10000000);

        companyArchivePage
                .clickAddNewPlanButton()
                .selectSourcePolicy(sourcePolicyId)
                .selectPolicyId(storagePolicyId)
                .fillPlanDescription(planDescription)
                .clickAddPlanButton();

        log.info("Verifying that plan was added to the plans table");
        assertTrue(companyArchivePage.isPlansTableVisible(), "Plans table should be visible");

        log.info("Verifying Description in the first plan row");
        String actualPlanDescription = companyArchivePage.getFirstPlanRowCellText(1);
        assertTrue(actualPlanDescription.contains(planDescription), "Plan description should contain: " + planDescription);

        log.info("Verifying Source Policy in the first plan row");
        assertEquals(sourcePolicyId, companyArchivePage.getFirstPlanRowCellText(2), "Source Policy should match the selected source policy ID");

        log.info("Verifying Archive Storage in the first plan row");
        assertEquals("GENERIC_SMTP", companyArchivePage.getFirstPlanRowCellText(3), "Archive Storage should be GENERIC_SMTP");

        log.info("Verifying Policy ID in the first plan row");
        assertEquals(storagePolicyId, companyArchivePage.getFirstPlanRowCellText(4), "Policy ID should match the selected storage policy ID");

        // Get and log Plan ID
        String planId = companyArchivePage.getFirstPlanRowCellText(0);
        log.info("Plan ID: " + planId);

        log.info("Test completed successfully");

    }

    @Test
    @Order(8)
    @Story("Company Portal Settings Management")
    void companyPortalSettingsTest() {

        log.info("Starting test: Verify Company Portal Settings checkboxes");

        String username = user.getUsername();

        adminsPortalPage = findUsersPage
                .searchUser(username)
                .checkThatUserWasFoundAndClickOnHim(username, updateUserPage)
                .clickCompanyAdminPortalSettingsButton();

        log.info("Verifying All Items checkbox is NOT selected");
        assertFalse(adminsPortalPage.isAllItemsCheckboxSelected(), "All Items checkbox should NOT be selected");

        log.info("Verifying Compose checkbox is NOT selected");
        assertFalse(adminsPortalPage.isComposeCheckboxSelected(), "Compose checkbox should NOT be selected");

        log.info("Verifying Outbox checkbox is NOT selected");
        assertFalse(adminsPortalPage.isOutboxCheckboxSelected(), "Outbox checkbox should NOT be selected");

        log.info("Verifying Sent Items checkbox is NOT selected");
        assertFalse(adminsPortalPage.isSentItemsCheckboxSelected(), "Sent Items checkbox should NOT be selected");

        log.info("Verifying Inbox checkbox is NOT selected");
        assertFalse(adminsPortalPage.isInboxCheckboxSelected(), "Inbox checkbox should NOT be selected");

        log.info("Verifying All Settings checkbox is NOT selected");
        assertFalse(adminsPortalPage.isAllSettingsCheckboxSelected(), "All Settings checkbox should NOT be selected");

        log.info("Verifying Messenger App Settings checkbox is NOT selected");
        assertFalse(adminsPortalPage.isMessengerAppSettingsCheckboxSelected(), "Messenger App Settings checkbox should NOT be selected");

        log.info("Verifying Advanced Settings checkbox is NOT selected");
        assertFalse(adminsPortalPage.isAdvancedSettingsCheckboxSelected(), "Advanced Settings checkbox should NOT be selected");

        log.info("Verifying Message Settings checkbox is NOT selected");
        assertFalse(adminsPortalPage.isMessageSettingsCheckboxSelected(), "Message Settings checkbox should NOT be selected");

        log.info("Verifying Archive Management checkbox IS selected");
        assertTrue(adminsPortalPage.isArchiveManagementCheckboxSelected(), "Archive Management checkbox SHOULD be selected");

        log.info("Verifying Display My Contacts checkbox is NOT selected");
        assertFalse(adminsPortalPage.isDisplayMyContactsCheckboxSelected(), "Display My Contacts checkbox should NOT be selected");

        log.info("Verifying Display Global Contacts checkbox is NOT selected");
        assertFalse(adminsPortalPage.isDisplayGlobalContactsCheckboxSelected(), "Display Global Contacts checkbox should NOT be selected");

        log.info("Verifying Display Message Query checkbox is NOT selected");
        assertFalse(adminsPortalPage.isDisplayMessageQueryCheckboxSelected(), "Display Message Query checkbox should NOT be selected");

        // Click on checkboxes to enable them
        log.info("Clicking on checkboxes to enable them");
        adminsPortalPage
                .clickAllItemsCheckbox()
                .clickInboxCheckbox()
                .clickAllSettingsCheckbox()
                .clickDisplayMyContactsCheckbox()
                .clickDisplayGlobalContactsCheckbox()
                .clickDisplayMessageQueryCheckbox()
                .clickSaveAdminPortalSettingsBtn();

        log.info("Verifying admin portal success message is visible");
        assertTrue(adminsPortalPage.isAdminPortalAlertSuccessVisible(), "Admin portal success message should be visible");

        log.info("Verifying admin portal success message text");
        assertEquals("Manager's settings updated successfully", adminsPortalPage.getAdminPortalAlertSuccessText(), "Admin portal success message text should match");

        // Refresh the page to verify settings were saved
        log.info("Refreshing the page to verify settings were saved");
        adminsPortalPage.refreshPage();

        log.info("Verifying All Items checkbox IS now selected");
        assertTrue(adminsPortalPage.isAllItemsCheckboxSelected(), "All Items checkbox SHOULD be selected");

        log.info("Verifying Compose checkbox IS now selected");
        assertTrue(adminsPortalPage.isComposeCheckboxSelected(), "Compose checkbox SHOULD be selected");

        log.info("Verifying Outbox checkbox IS now selected");
        assertTrue(adminsPortalPage.isOutboxCheckboxSelected(), "Outbox checkbox SHOULD be selected");

        log.info("Verifying Sent Items checkbox IS now selected");
        assertTrue(adminsPortalPage.isSentItemsCheckboxSelected(), "Sent Items checkbox SHOULD be selected");

        log.info("Verifying Inbox checkbox IS now selected");
        assertTrue(adminsPortalPage.isInboxCheckboxSelected(), "Inbox checkbox SHOULD be selected");

        log.info("Verifying All Settings checkbox IS now selected");
        assertTrue(adminsPortalPage.isAllSettingsCheckboxSelected(), "All Settings checkbox SHOULD be selected");

        log.info("Verifying Messenger App Settings checkbox IS now selected");
        assertTrue(adminsPortalPage.isMessengerAppSettingsCheckboxSelected(), "Messenger App Settings checkbox SHOULD be selected");

        log.info("Verifying Advanced Settings checkbox IS now selected");
        assertTrue(adminsPortalPage.isAdvancedSettingsCheckboxSelected(), "Advanced Settings checkbox SHOULD be selected");

        log.info("Verifying Message Settings checkbox IS now selected");
        assertTrue(adminsPortalPage.isMessageSettingsCheckboxSelected(), "Message Settings checkbox SHOULD be selected");

        log.info("Verifying Archive Management checkbox IS still selected");
        assertTrue(adminsPortalPage.isArchiveManagementCheckboxSelected(), "Archive Management checkbox SHOULD be selected");

        log.info("Verifying Display My Contacts checkbox IS now selected");
        assertTrue(adminsPortalPage.isDisplayMyContactsCheckboxSelected(), "Display My Contacts checkbox SHOULD be selected");

        log.info("Verifying Display Global Contacts checkbox IS now selected");
        assertTrue(adminsPortalPage.isDisplayGlobalContactsCheckboxSelected(), "Display Global Contacts checkbox SHOULD be selected");

        log.info("Verifying Display Message Query checkbox IS now selected");
        assertTrue(adminsPortalPage.isDisplayMessageQueryCheckboxSelected(), "Display Message Query checkbox SHOULD be selected");

        log.info("Test completed successfully");

    }

    @Test
    @Order(9)
    @Story("Application Settings")
    void wpcSignatureUserLevel() {

        log.info("Starting test: WPC Settings - Signature - User Level");

        String username = user.getUsername();
        SignatureType signatureType = SignatureType.FIRST_MESSAGE_THREAD;
        SignatureTextInheritance signatureTextInheritance = SignatureTextInheritance.MANUAL;
        String signatureText = "Auto signature text";

        TeleadminApplicationsSettingPage applicationsSettingPage = findUsersPage
                .searchUser(username)
                .checkThatUserWasFoundAndClickOnHim(username, updateUserPage)
                .clickWhatsAppPhoneCaptureSettingsButton()
                .clickSignatureButton()
                .searchSignatureUsers(username)
                .selectSignatureType(signatureType)
                .selectSignatureTextInheritance(signatureTextInheritance)
                .fillSignatureText(signatureText)
                .clickApplySignatureButton()
                .clickConfirmActionButton()
                .waitForSignatureSuccessAlert();

        log.info("Verifying signature success alert is visible");
        assertTrue(applicationsSettingPage.isSignatureSuccessAlertVisible(), "Signature success alert should be visible after confirming action");

        log.info("Re-opening signature popup to verify saved values");
        applicationsSettingPage
                .clickSignatureButton()
                .searchSignatureUsers(username);

        log.info("Verifying signature type is selected: {}", signatureType.getDisplayName());
        assertTrue(applicationsSettingPage.isSignatureTypeSelected(signatureType), "Signature type should be " + signatureType.getDisplayName());

        log.info("Verifying signature text inheritance is selected: {}", signatureTextInheritance.getDisplayName());
        assertTrue(applicationsSettingPage.isSignatureTextInheritanceSelected(signatureTextInheritance), "Signature text inheritance should be " + signatureTextInheritance.getDisplayName());

        log.info("Verifying signature text content");
        assertEquals(signatureText, applicationsSettingPage.getSignatureText(), "Signature text should match the previously entered text");

        log.info("Test completed successfully");

    }

    @Test
    @Order(10)
    @Story("Application Settings")
    void telegramSignatureUserLevel() {

        log.info("Starting test: Telegram Settings - Signature - User Level");

        String username = user.getUsername();
        SignatureType signatureType = SignatureType.FIRST_MESSAGE_THREAD;
        SignatureTextInheritance signatureTextInheritance = SignatureTextInheritance.MANUAL;
        String signatureText = "Auto signature text";

        TeleadminApplicationsSettingPage applicationsSettingPage = findUsersPage
                .searchUser(username)
                .checkThatUserWasFoundAndClickOnHim(username, updateUserPage)
                .clickTelegramCaptureSettingsButton()
                .clickSignatureButton()
                .searchSignatureUsers(username)
                .selectSignatureType(signatureType)
                .selectSignatureTextInheritance(signatureTextInheritance)
                .fillSignatureText(signatureText)
                .clickApplySignatureButton()
                .clickConfirmActionButton()
                .waitForSignatureSuccessAlert();

        log.info("Verifying signature success alert is visible");
        assertTrue(applicationsSettingPage.isSignatureSuccessAlertVisible(), "Signature success alert should be visible after confirming action");

        log.info("Re-opening signature popup to verify saved values");
        applicationsSettingPage
                .clickSignatureButton()
                .searchSignatureUsers(username);

        log.info("Verifying signature type is selected: {}", signatureType.getDisplayName());
        assertTrue(applicationsSettingPage.isSignatureTypeSelected(signatureType), "Signature type should be " + signatureType.getDisplayName());

        log.info("Verifying signature text inheritance is selected: {}", signatureTextInheritance.getDisplayName());
        assertTrue(applicationsSettingPage.isSignatureTextInheritanceSelected(signatureTextInheritance), "Signature text inheritance should be " + signatureTextInheritance.getDisplayName());

        log.info("Verifying signature text content");
        assertEquals(signatureText, applicationsSettingPage.getSignatureText(), "Signature text should match the previously entered text");

        log.info("Test completed successfully");

    }

    @Test
    @Order(11)
    @Story("Application Settings")
    void signalSignatureUserLevel() {

        log.info("Starting test: Signal Settings - Signature - User Level");

        String username = user.getUsername();
        SignatureType signatureType = SignatureType.FIRST_MESSAGE_THREAD;
        SignatureTextInheritance signatureTextInheritance = SignatureTextInheritance.MANUAL;
        String signatureText = "Auto signature text";

        TeleadminApplicationsSettingPage applicationsSettingPage = findUsersPage
                .searchUser(username)
                .checkThatUserWasFoundAndClickOnHim(username, updateUserPage)
                .clickSignalCaptureSettingsButton()
                .clickSignatureButton()
                .searchSignatureUsers(username)
                .selectSignatureType(signatureType)
                .selectSignatureTextInheritance(signatureTextInheritance)
                .fillSignatureText(signatureText)
                .clickApplySignatureButton()
                .clickConfirmActionButton()
                .waitForSignatureSuccessAlert();

        log.info("Verifying signature success alert is visible");
        assertTrue(applicationsSettingPage.isSignatureSuccessAlertVisible(), "Signature success alert should be visible after confirming action");

        log.info("Re-opening signature popup to verify saved values");
        applicationsSettingPage
                .clickSignatureButton()
                .searchSignatureUsers(username);

        log.info("Verifying signature type is selected: {}", signatureType.getDisplayName());
        assertTrue(applicationsSettingPage.isSignatureTypeSelected(signatureType), "Signature type should be " + signatureType.getDisplayName());

        log.info("Verifying signature text inheritance is selected: {}", signatureTextInheritance.getDisplayName());
        assertTrue(applicationsSettingPage.isSignatureTextInheritanceSelected(signatureTextInheritance), "Signature text inheritance should be " + signatureTextInheritance.getDisplayName());

        log.info("Verifying signature text content");
        assertEquals(signatureText, applicationsSettingPage.getSignatureText(), "Signature text should match the previously entered text");

        log.info("Test completed successfully");

    }

    @Test
    @Order(12)
    @Story("Application Settings")
    void encSignatureUserLevel() {

        log.info("Starting test: Enterprise Number Capture Settings - Signature - User Level");

        String username = user.getUsername();
        SignatureType signatureType = SignatureType.FIRST_MESSAGE_THREAD;
        SignatureTextInheritance signatureTextInheritance = SignatureTextInheritance.MANUAL;
        String signatureText = "Auto signature text";

        TeleadminApplicationsSettingPage applicationsSettingPage = findUsersPage
                .searchUser(username)
                .checkThatUserWasFoundAndClickOnHim(username, updateUserPage)
                .clickEnterpriseNumberCaptureSettingsButton()
                .clickSignatureButton()
                .searchSignatureUsers(username)
                .selectSignatureType(signatureType)
                .selectSignatureTextInheritance(signatureTextInheritance)
                .fillSignatureText(signatureText)
                .clickApplySignatureButton()
                .clickConfirmActionButton()
                .waitForSignatureSuccessAlert();

        log.info("Verifying signature success alert is visible");
        assertTrue(applicationsSettingPage.isSignatureSuccessAlertVisible(), "Signature success alert should be visible after confirming action");

        log.info("Re-opening signature popup to verify saved values");
        applicationsSettingPage
                .clickSignatureButton()
                .searchSignatureUsers(username);

        log.info("Verifying signature type is selected: {}", signatureType.getDisplayName());
        assertTrue(applicationsSettingPage.isSignatureTypeSelected(signatureType), "Signature type should be " + signatureType.getDisplayName());

        log.info("Verifying signature text inheritance is selected: {}", signatureTextInheritance.getDisplayName());
        assertTrue(applicationsSettingPage.isSignatureTextInheritanceSelected(signatureTextInheritance), "Signature text inheritance should be " + signatureTextInheritance.getDisplayName());

        log.info("Verifying signature text content");
        assertEquals(signatureText, applicationsSettingPage.getSignatureText(), "Signature text should match the previously entered text");

        log.info("Test completed successfully");

    }

    @Test
    @Order(13)
    @Story("Application Settings")
    void androidCaptureSettings() {

        log.info("Starting test: Android Capture Settings - RCS Checkbox");

        String username = user.getUsername();

        TeleadminApplicationsSettingPage applicationsSettingPage = findUsersPage
                .searchUser(username)
                .checkThatUserWasFoundAndClickOnHim(username, updateUserPage)
                .clickAndroidCaptureSettingsButton();

        log.info("Verifying RCS checkbox is unchecked by default");
        assertFalse(applicationsSettingPage.isRcsSupportCheckboxSelected(), "RCS Support checkbox should be unchecked by default");

        log.info("Check RCS Support checkbox, save settings and verify success alert");
        applicationsSettingPage
                .clickRcsSupportCheckbox()
                .clickSaveCompanyLevelSettingsButton()
                .waitForSignatureSuccessAlert();

        log.info("Verifying signature success alert is visible and RCS checkbox is checked");
        assertTrue(applicationsSettingPage.isSignatureSuccessAlertVisible(), "Signature success alert should be visible after confirming action");
        assertTrue(applicationsSettingPage.isRcsSupportCheckboxSelected(), "RCS Support checkbox should be checked");

        page.waitForTimeout(8000);

        log.info("Uncheck RCS Support checkbox, save settings and verify success alert");
        applicationsSettingPage
                .clickRcsSupportCheckbox()
                .clickSaveCompanyLevelSettingsButton()
                .waitForSignatureSuccessAlert();

        log.info("Verifying signature success alert is visible and RCS checkbox is unchecked");
        assertTrue(applicationsSettingPage.isSignatureSuccessAlertVisible(), "Signature success alert should be visible after confirming action");
        assertFalse(applicationsSettingPage.isRcsSupportCheckboxSelected(), "RCS Support checkbox should be unchecked");

        log.info("Test completed successfully");

    }

    @Test
    @Order(14)
    @Story("Delete User")
    void deleteUserViaUpdateUserPage() {

        log.info("Starting test: Delete user via Update User Page");

        String username = user.getUsername();

        findUsersPage
                .searchUser(username)
                .checkThatUserWasFoundAndClickOnHim(username, updateUserPage)
                .selectStatus(UserStatus.DELETED)
                .clickUpdateStatusButton()
                .waitForDeleteSuccessMessage();

        log.info("Verifying delete success message is visible");
        assertTrue(updateUserPage.isDeleteSuccessMessageVisible(), "Delete success message should be visible");

        log.info("Verifying delete success message text");
        assertEquals("User(s) deleted successfully", updateUserPage.getDeleteSuccessMessageText(),
                "Delete success message text should match");

        log.info("Verifying deleted user status is visible");
        assertTrue(updateUserPage.isDeletedUserStatusVisible(), "Deleted User status should be visible");

        log.info("Verifying deleted user status text");
        assertEquals("Deleted User", updateUserPage.getDeletedUserStatusText(),
                "Deleted user status text should match");

        log.info("Test completed successfully");

    }

}



