package com.websanity.tests;

import com.websanity.BaseTest;
import com.websanity.enums.*;
import com.websanity.models.UserParams;
import com.websanity.teleadminPages.*;
import com.websanity.utils.APIs;
import io.qameta.allure.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Epic("TeleAdmin Sanity Tests")
@Feature("Account Settings/Applicatoin Settings functionality")
public class TeleadminSanityTest extends BaseTest {

    private static LogInPage logInPage;
    private static MenuPage menuPage;
    private static FindUsersPage findUsersPage;
    private static UpdateUserPage updateUserPage;
    private static SignUpPage signUpPage;
    private static CompanyArchiveManagementPage companyArchivePage;
    private static ApplicationsSettingPage applicationsSettingPage;
    private static AdminsPortalSettingsPage adminsPortalPage;

    private static UserParams user;

    @BeforeAll
    static void setUpAll() {
        log.info("🔧 Setting up Teleadmin session - performing login once for all tests...");

        // Initialize page objects
        logInPage = new LogInPage(page);
        menuPage = new MenuPage(page);
        findUsersPage = new FindUsersPage(page);
        updateUserPage = new UpdateUserPage(page);
        signUpPage = new SignUpPage(page);

        // Login once for all tests
        logInPage.open()
                .logInToTeleadmin()
                .waitForFindUsersPageToLoad();

        log.info("✅ Login completed successfully, session will be kept open for all tests");

        // Setup user params
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

    @AfterAll
    static void tearDownAll() {
        log.info("🧹 Closing Teleadmin session after all tests...");
    }

    @BeforeEach
    void setUp() {
        log.debug("🔄 Preparing for next test - cleaning up extra tabs...");

        // Close all extra tabs except the first one
        while (page.context().pages().size() > 1) {
            int lastIndex = page.context().pages().size() - 1;
            page.context().pages().get(lastIndex).close();
            log.debug("Closed extra tab, remaining tabs: {}", page.context().pages().size());
        }

        // Just wait a bit for any animations to finish
        page.waitForTimeout(500);

        log.debug("✅ Cleanup complete - ready for next test");
    }

    @Test
    @Order(1)
    @Story("User Registration")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Register a new Pro Manager user and verify success message")
    void registerNewUserTest() {

        log.info("Starting test: Register new Pro Manager");

        menuPage.clickSignUpButton()
                .registerNewUser(user)
                .waitForSuccessMessage();

        log.info("Verifying success message is visible");
        assertTrue(signUpPage.isSuccessMessageVisible(), "Success message should be visible after registration");

        log.info("Verifying success message text");
        assertEquals("User registered successfully", signUpPage.getSuccessMessageText(), "Success message text should match");

        log.info("User registration completed successfully");
        log.info("✅ Test completed successfully");

    }

    @Test
    @Order(2)
    @Story("Find User")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Find user and open his Update User page")
    void findUserAndOpenHisUpdateUserPage() {

        log.info("Starting test: Search for user and click on username in table");

        String searchUsername = user.getUsername();

        menuPage.clickFindUsersButton()
                .searchUserByUsername(searchUsername)
                .checkThatUserWasFoundAndClickOnHim(searchUsername, updateUserPage);

        log.info("✅ Test completed successfully");

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

        menuPage.clickFindUsersButton()
                .searchUserByUsername(searchUsername)
                .checkThatUserWasFoundAndClickOnHim(searchUsername, updateUserPage)
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
        assertEquals("User profile was updated successfully.", updateUserPage.getSuccessMessageText(),
                "Update success message text should match");

        log.info("Verifying all updated field values");
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

        log.info("✅ Test completed successfully");

    }

    @Test
    @Order(4)
    @Story("Account Management")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Suspend and Activate user, verifying status changes and success messages")
    void suspendAndActivateUser() {

        log.info("Starting test: Suspend and Activate user");
        page.waitForTimeout(2000);

        String username = user.getUsername();

        menuPage.clickFindUsersButton()
                .searchUserByUsername(username)
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

        log.info("✅ Test completed successfully");

    }

    @Test
    @Order(5)
    @Story("Account Management")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Check flavors (App Text Support, Voice Call Support, Voice Mail)")
    void flavors() {
        //Successfully updated App Text And voice Call Support - success message
        log.info("Check flavors (App Text Support, Voice Call Support, Voice Mail)");

        String username = user.getUsername();

        updateUserPage = menuPage.clickFindUsersButton()
                .searchUserByUsername(username)
                .checkThatUserWasFoundAndClickOnHim(username, updateUserPage);

        log.info("Check that 'App Text Support' radiobutton is selected by default and 'Voice Call Support', 'Voice Mail' are not selected, and 'Incoming Call To Mobile' is not enabled");

        //App Text Support: Yes
        Assertions.assertTrue(updateUserPage.isAppTextSupportYesSelected(), "'App Text Support' Yes radiobutton should be selected");
        Assertions.assertFalse(updateUserPage.isAppTextSupportNoSelected(), "'App Text Support' No radiobutton should not be selected");

        //Voice Call Support:  No
        Assertions.assertFalse(updateUserPage.isVoiceCallSupportYesSelected(), "'Voice Call Support' Yes radiobutton should not be selected");
        Assertions.assertTrue(updateUserPage.isVoiceCallSupportNoSelected(), "'Voice Call Support' No radiobutton should be selected");

        //Voice Mail: No
        Assertions.assertFalse(updateUserPage.isVoiceMailYesSelected(), "'Voice mail' Yes radiobutton should not be selected");
        Assertions.assertTrue(updateUserPage.isVoiceMailNoSelected(), "'Voice Mail' No radiobutton should be selected");

        //Incoming Calls To Mobile: Not Enabled and Not checked
        Assertions.assertFalse(updateUserPage.isIncomingCallsToMobileEnabled(), "'Incoming Calls To Mobile' should not be enabled");
        Assertions.assertFalse(updateUserPage.isIncomingCallsToMobileChecked(), "'Incoming Calls To Mobile' should not be checked");

        //Check GetAppSettings
        Assertions.assertEquals("true",APIs.getValueFromMobileCaptureGetAppSettingsByName("APP_TEXT_SUPPORT",user.getUsername(), user.getPassword(), "","524288"), "GetAppSettings 'APP_TEXT_SUPPORT' not correct");
        Assertions.assertEquals("false",APIs.getValueFromMobileCaptureGetAppSettingsByName("VOICE_CALL_SUPPORT",user.getUsername(), user.getPassword(), "","524288"), "GetAppSettings 'VOICE_CALL_SUPPORT' not correct");
        Assertions.assertEquals("false",APIs.getValueFromMobileCaptureGetAppSettingsByName("VOICE_MAIL",user.getUsername(), user.getPassword(), "","524288"), "GetAppSettings 'VOICE_MAIL' not correct");

        log.info("Select 'App Text Support' No");

        updateUserPage.clickAppTextSupportRadio(YesNo.NO)
                .clickUpdateFlavorsButton()
                .waitForUpdateSuccessMessage();

        //Check success message
        Assertions.assertTrue(updateUserPage.isUpdateSuccessMessageVisible(), "Success message should be visible after updating flavors");
        Assertions.assertEquals("Successfully updated App Text And voice Call Support", updateUserPage.getSuccessMessageText(), "Success message text should be empty after updating flavors");

        //App Text Support: No
        Assertions.assertFalse(updateUserPage.isAppTextSupportYesSelected(), "'App Text Support' Yes radiobutton should not be selected");
        Assertions.assertTrue(updateUserPage.isAppTextSupportNoSelected(), "'App Text Support' No radiobutton should be selected");

        //Voice Call Support:  No
        Assertions.assertFalse(updateUserPage.isVoiceCallSupportYesSelected(), "'Voice Call Support' Yes radiobutton should not be selected");
        Assertions.assertTrue(updateUserPage.isVoiceCallSupportNoSelected(), "'Voice Call Support' No radiobutton should be selected");

        //Voice Mail: No
        Assertions.assertFalse(updateUserPage.isVoiceMailYesSelected(), "'Voice mail' Yes radiobutton should not be selected");
        Assertions.assertTrue(updateUserPage.isVoiceMailNoSelected(), "'Voice Mail' No radiobutton should be selected");

        //Incoming Calls To Mobile: Not Enabled and Not Checked
        Assertions.assertFalse(updateUserPage.isIncomingCallsToMobileEnabled(), "'Incoming Calls To Mobile' should not be enabled");
        Assertions.assertFalse(updateUserPage.isIncomingCallsToMobileChecked(), "'Incoming Calls To Mobile' should not be checked");

        //Check GetAppSettings
        Assertions.assertEquals("false",APIs.getValueFromMobileCaptureGetAppSettingsByName("APP_TEXT_SUPPORT",user.getUsername(), user.getPassword(), "","524288"), "GetAppSettings 'APP_TEXT_SUPPORT' not correct");
        Assertions.assertEquals("false",APIs.getValueFromMobileCaptureGetAppSettingsByName("VOICE_CALL_SUPPORT",user.getUsername(), user.getPassword(), "","524288"), "GetAppSettings 'VOICE_CALL_SUPPORT' not correct");
        Assertions.assertEquals("false",APIs.getValueFromMobileCaptureGetAppSettingsByName("VOICE_MAIL",user.getUsername(), user.getPassword(), "","524288"), "GetAppSettings 'VOICE_MAIL' not correct");

        log.info("Select 'Voice Call Support' Yes");

        updateUserPage.clickVoiceCallSupportRadio(YesNo.YES)
                .clickUpdateFlavorsButton()
                .waitForUpdateSuccessMessage();

        //Check success message
        Assertions.assertTrue(updateUserPage.isUpdateSuccessMessageVisible(), "Success message should be visible after updating flavors");
        Assertions.assertEquals("Successfully updated App Text And voice Call Support", updateUserPage.getSuccessMessageText(), "Success message text should be empty after updating flavors");

        //App Text Support: No
        Assertions.assertFalse(updateUserPage.isAppTextSupportYesSelected(), "'App Text Support' Yes radiobutton should not be selected");
        Assertions.assertTrue(updateUserPage.isAppTextSupportNoSelected(), "'App Text Support' No radiobutton should be selected");

        //Voice Call Support:  Yes
        Assertions.assertTrue(updateUserPage.isVoiceCallSupportYesSelected(), "'Voice Call Support' Yes radiobutton should be selected");
        Assertions.assertFalse(updateUserPage.isVoiceCallSupportNoSelected(), "'Voice Call Support' No radiobutton should not be selected");

        //Voice Mail: Yes
        Assertions.assertTrue(updateUserPage.isVoiceMailYesSelected(), "'Voice mail' Yes radiobutton should be selected");
        Assertions.assertFalse(updateUserPage.isVoiceMailNoSelected(), "'Voice Mail' No radiobutton should not be selected");

        //Incoming Calls To Mobile: Not Enabled and Not Checked
        Assertions.assertFalse(updateUserPage.isIncomingCallsToMobileEnabled(), "'Incoming Calls To Mobile' should not be enabled");
        Assertions.assertFalse(updateUserPage.isIncomingCallsToMobileChecked(), "'Incoming Calls To Mobile' should not be checked");

        //Check GetAppSettings
        Assertions.assertEquals("false",APIs.getValueFromMobileCaptureGetAppSettingsByName("APP_TEXT_SUPPORT",user.getUsername(), user.getPassword(), "","524288"), "GetAppSettings 'APP_TEXT_SUPPORT' not correct");
        Assertions.assertEquals("true",APIs.getValueFromMobileCaptureGetAppSettingsByName("VOICE_CALL_SUPPORT",user.getUsername(), user.getPassword(), "","524288"), "GetAppSettings 'VOICE_CALL_SUPPORT' not correct");
        Assertions.assertEquals("true",APIs.getValueFromMobileCaptureGetAppSettingsByName("VOICE_MAIL",user.getUsername(), user.getPassword(), "","524288"), "GetAppSettings 'VOICE_MAIL' not correct");

        log.info("Select 'App Text Support' Yes and Incoming calls To Mobile - Yes");

        updateUserPage.clickAppTextSupportRadio(YesNo.YES)
                .clickIncomingCallsToMobileCheckbox()
                .clickUpdateFlavorsButton()
                .waitForUpdateSuccessMessage();

        //Check success message
        Assertions.assertTrue(updateUserPage.isUpdateSuccessMessageVisible(), "Success message should be visible after updating flavors");
        Assertions.assertEquals("Successfully updated App Text And voice Call Support", updateUserPage.getSuccessMessageText(), "Success message text should be empty after updating flavors");

        //App Text Support: Yes
        Assertions.assertTrue(updateUserPage.isAppTextSupportYesSelected(), "'App Text Support' Yes radiobutton should be selected");
        Assertions.assertFalse(updateUserPage.isAppTextSupportNoSelected(), "'App Text Support' No radiobutton should not be selected");

        //Voice Call Support:  Yes
        Assertions.assertTrue(updateUserPage.isVoiceCallSupportYesSelected(), "'Voice Call Support' Yes radiobutton should be selected");
        Assertions.assertFalse(updateUserPage.isVoiceCallSupportNoSelected(), "'Voice Call Support' No radiobutton should not be selected");

        //Voice Mail: Yes
        Assertions.assertTrue(updateUserPage.isVoiceMailYesSelected(), "'Voice mail' Yes radiobutton should be selected");
        Assertions.assertFalse(updateUserPage.isVoiceMailNoSelected(), "'Voice Mail' No radiobutton should not be selected");

        //Incoming Calls To Mobile: Enabled and Checked
        Assertions.assertTrue(updateUserPage.isIncomingCallsToMobileEnabled(), "'Incoming Calls To Mobile' should be enabled");
        Assertions.assertTrue(updateUserPage.isIncomingCallsToMobileChecked(), "'Incoming Calls To Mobile' should be checked");

        //Check GetAppSettings
        Assertions.assertEquals("true",APIs.getValueFromMobileCaptureGetAppSettingsByName("APP_TEXT_SUPPORT",user.getUsername(), user.getPassword(), "","524288"), "GetAppSettings 'APP_TEXT_SUPPORT' not correct");
        Assertions.assertEquals("true",APIs.getValueFromMobileCaptureGetAppSettingsByName("VOICE_CALL_SUPPORT",user.getUsername(), user.getPassword(), "","524288"), "GetAppSettings 'VOICE_CALL_SUPPORT' not correct");
        Assertions.assertEquals("true",APIs.getValueFromMobileCaptureGetAppSettingsByName("VOICE_MAIL",user.getUsername(), user.getPassword(), "","524288"), "GetAppSettings 'VOICE_MAIL' not correct");

        log.info("Select 'Voice Mail' No and Incoming calls To Mobile - No");

        updateUserPage.clickVoiceMailRadio(YesNo.NO)
                .clickIncomingCallsToMobileCheckbox()
                .clickUpdateFlavorsButton()
                .waitForUpdateSuccessMessage();

        //Check success message
        Assertions.assertTrue(updateUserPage.isUpdateSuccessMessageVisible(), "Success message should be visible after updating flavors");
        Assertions.assertEquals("Successfully updated App Text And voice Call Support", updateUserPage.getSuccessMessageText(), "Success message text should be empty after updating flavors");

        //App Text Support: Yes
        Assertions.assertTrue(updateUserPage.isAppTextSupportYesSelected(), "'App Text Support' Yes radiobutton should be selected");
        Assertions.assertFalse(updateUserPage.isAppTextSupportNoSelected(), "'App Text Support' No radiobutton should not be selected");

        //Voice Call Support:  Yes
        Assertions.assertTrue(updateUserPage.isVoiceCallSupportYesSelected(), "'Voice Call Support' Yes radiobutton should be selected");
        Assertions.assertFalse(updateUserPage.isVoiceCallSupportNoSelected(), "'Voice Call Support' No radiobutton should not be selected");

        //Voice Mail: No
        Assertions.assertFalse(updateUserPage.isVoiceMailYesSelected(), "'Voice mail' Yes radiobutton should not be selected");
        Assertions.assertTrue(updateUserPage.isVoiceMailNoSelected(), "'Voice Mail' No radiobutton should be selected");

        //Incoming Calls To Mobile: Enabled and Not Checked
        Assertions.assertTrue(updateUserPage.isIncomingCallsToMobileEnabled(), "'Incoming Calls To Mobile' should be enabled");
        Assertions.assertFalse(updateUserPage.isIncomingCallsToMobileChecked(), "'Incoming Calls To Mobile' should not be checked");

        //Check GetAppSettings
        Assertions.assertEquals("true",APIs.getValueFromMobileCaptureGetAppSettingsByName("APP_TEXT_SUPPORT",user.getUsername(), user.getPassword(), "","524288"), "GetAppSettings 'APP_TEXT_SUPPORT' not correct");
        Assertions.assertEquals("true",APIs.getValueFromMobileCaptureGetAppSettingsByName("VOICE_CALL_SUPPORT",user.getUsername(), user.getPassword(), "","524288"), "GetAppSettings 'VOICE_CALL_SUPPORT' not correct");
        Assertions.assertEquals("false",APIs.getValueFromMobileCaptureGetAppSettingsByName("VOICE_MAIL",user.getUsername(), user.getPassword(), "","524288"), "GetAppSettings 'VOICE_MAIL' not correct");

        log.info("✅ Test completed successfully");


    }

    @Test
    @Order(6)
    @Story("Enterprise Numbers Management")
    @Severity(SeverityLevel.NORMAL)
    @Description("Add and Remove Enterprise Number for user, verifying success messages and EN details")
    void addRemoveEnterpriseNumber() {

        log.info("Starting test: Add and Remove Enterprise Number");

        String username = user.getUsername();
        String enterpriseNumber = "97258" + String.format("%07d", System.currentTimeMillis() % 10000000);

        // Add Enterprise Number
        log.info("Adding Enterprise Number: " + enterpriseNumber);
        menuPage.clickFindUsersButton()
                .searchUserByUsername(username)
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
        updateUserPage.clickEnDeleteCheckbox()
                .clickEnUpdateButton()
                .waitForEnSuccessMessage();

        log.info("Verifying EN removal success message is visible");
        assertTrue(updateUserPage.isEnSuccessMessageVisible(), "EN removal success message should be visible");

        log.info("Verifying EN removal success message text");
        assertEquals("User Enterprise Number(s) updated successfully", updateUserPage.getEnSuccessMessageText(),
                "EN removal success message text should match");

        log.info("Verifying Enterprise Number row is no longer visible");
        assertFalse(updateUserPage.isEnterpriseNumberRowVisible(), "Enterprise Number row should not be visible after deletion");

        log.info("✅ Test completed successfully");

    }

    @Test
    @Order(7)
    @Story("Allowed Domains Management")
    @Severity(SeverityLevel.NORMAL)
    @Description("Add allowed email domain for user and verify success message and domain presence")
    void addAllowedDomain() {

        log.info("Starting test: Add allowed domain");

        String username = user.getUsername();
        String domain = "autod" + String.format("%05d", System.currentTimeMillis() % 100000) + ".com";

        menuPage.clickFindUsersButton()
                .searchUserByUsername(username)
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

        log.info("✅ Test completed successfully");

    }

    @Test
    @Order(8)
    @Story("Company Archive Management")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Create Source, Destination, and Plan in Company Archive Management and verify all details")
    void createSourceDestinationAndPlanTest() {

        log.info("Starting test: Training Test");

        String username = user.getUsername();
        String sourceDescription = "WhatsApp Source " + String.format("%07d", System.currentTimeMillis() % 10000000);
        SourceType sourceType = SourceType.WHATSAPP_ARCHIVER;

        //Create Source
        companyArchivePage = menuPage.clickFindUsersButton()
                .searchUserByUsername(username)
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

        companyArchivePage.clickAddNewPolicyButton()
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

        companyArchivePage.clickAddNewPlanButton()
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

        // Assign user to the plan
        companyArchivePage.clickFirstPlanRowManageUsersButton()
                .fillAssignUsersSearch(user.getUsername())
                .clickFirstUserCheckbox()
                .waitForAssignUsersApplyButtonEnabled()
                .clickAssignUsersApplyButton()
                .clickConfirmActionYesButton();

        log.info("Verifying that user has been assigned to the archive plan");

        companyArchivePage.clickFirstPlanRowManageUsersButton()
                .fillAssignUsersSearch(user.getUsername());

        log.info("Verifying that user checkbox is checked");
        assertTrue(companyArchivePage.isFirstUserCheckboxChecked(), "User checkbox should be checked after assignment");


        log.info("✅ Test completed successfully");

    }

    @Test
    @Order(9)
    @Story("Company Portal Settings Management")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify Company Portal Settings checkboxes, enable them, save, and verify persistence")
    void companyPortalSettingsTest() {

        log.info("Starting test: Verify Company Portal Settings checkboxes");

        String username = user.getUsername();

        adminsPortalPage = menuPage.clickFindUsersButton()
                .searchUserByUsername(username)
                .checkThatUserWasFoundAndClickOnHim(username, updateUserPage)
                .clickCompanyAdminPortalSettingsButton();

        log.info("Verifying All checkboxes are NOT selected");
        assertFalse(adminsPortalPage.isAllItemsCheckboxSelected(), "All Items checkbox should NOT be selected");
        assertFalse(adminsPortalPage.isComposeCheckboxSelected(), "Compose checkbox should NOT be selected");
        assertFalse(adminsPortalPage.isOutboxCheckboxSelected(), "Outbox checkbox should NOT be selected");
        assertFalse(adminsPortalPage.isSentItemsCheckboxSelected(), "Sent Items checkbox should NOT be selected");
        assertFalse(adminsPortalPage.isInboxCheckboxSelected(), "Inbox checkbox should NOT be selected");
        assertFalse(adminsPortalPage.isAllSettingsCheckboxSelected(), "All Settings checkbox should NOT be selected");
        assertFalse(adminsPortalPage.isMessengerAppSettingsCheckboxSelected(), "Messenger App Settings checkbox should NOT be selected");
        assertFalse(adminsPortalPage.isAdvancedSettingsCheckboxSelected(), "Advanced Settings checkbox should NOT be selected");
        assertFalse(adminsPortalPage.isMessageSettingsCheckboxSelected(), "Message Settings checkbox should NOT be selected");
        assertTrue(adminsPortalPage.isArchiveManagementCheckboxSelected(), "Archive Management checkbox SHOULD be selected");
        assertFalse(adminsPortalPage.isDisplayMyContactsCheckboxSelected(), "Display My Contacts checkbox should NOT be selected");
        assertFalse(adminsPortalPage.isDisplayGlobalContactsCheckboxSelected(), "Display Global Contacts checkbox should NOT be selected");
        assertFalse(adminsPortalPage.isDisplayMessageQueryCheckboxSelected(), "Display Message Query checkbox should NOT be selected");

        // Click on checkboxes to enable them
        log.info("Clicking on checkboxes to enable them");
        adminsPortalPage.clickAllItemsCheckbox()
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

        log.info("Verifying All checkboxes ARE now selected");
        assertTrue(adminsPortalPage.isAllItemsCheckboxSelected(), "All Items checkbox SHOULD be selected");
        assertTrue(adminsPortalPage.isComposeCheckboxSelected(), "Compose checkbox SHOULD be selected");
        assertTrue(adminsPortalPage.isOutboxCheckboxSelected(), "Outbox checkbox SHOULD be selected");
        assertTrue(adminsPortalPage.isSentItemsCheckboxSelected(), "Sent Items checkbox SHOULD be selected");
        assertTrue(adminsPortalPage.isInboxCheckboxSelected(), "Inbox checkbox SHOULD be selected");
        assertTrue(adminsPortalPage.isAllSettingsCheckboxSelected(), "All Settings checkbox SHOULD be selected");
        assertTrue(adminsPortalPage.isMessengerAppSettingsCheckboxSelected(), "Messenger App Settings checkbox SHOULD be selected");
        assertTrue(adminsPortalPage.isAdvancedSettingsCheckboxSelected(), "Advanced Settings checkbox SHOULD be selected");
        assertTrue(adminsPortalPage.isMessageSettingsCheckboxSelected(), "Message Settings checkbox SHOULD be selected");
        assertTrue(adminsPortalPage.isArchiveManagementCheckboxSelected(), "Archive Management checkbox SHOULD be selected");
        assertTrue(adminsPortalPage.isDisplayMyContactsCheckboxSelected(), "Display My Contacts checkbox SHOULD be selected");
        assertTrue(adminsPortalPage.isDisplayGlobalContactsCheckboxSelected(), "Display Global Contacts checkbox SHOULD be selected");
        assertTrue(adminsPortalPage.isDisplayMessageQueryCheckboxSelected(), "Display Message Query checkbox SHOULD be selected");

        log.info("✅ Test completed successfully");

    }

    @Test
    @Order(10)
    @Story("Application Settings")
    @Severity(SeverityLevel.NORMAL)
    @Description("WhatsApp Settings - Signature - User Level")
    void wpcSignatureUserLevel() {

        log.info("Starting test: WPC Settings - Signature - User Level");

        String username = user.getUsername();
        SignatureType signatureType = SignatureType.FIRST_MESSAGE_THREAD;
        SignatureTextInheritance signatureTextInheritance = SignatureTextInheritance.MANUAL;
        String signatureText = "Auto signature text";

        applicationsSettingPage = menuPage.clickFindUsersButton()
                .searchUserByUsername(username)
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

        log.info("✅ Test completed successfully");

    }

    @Test
    @Order(11)
    @Story("Application Settings")
    @Severity(SeverityLevel.NORMAL)
    @Description("Telegram Settings - Signature - User Level")
    void telegramSignatureUserLevel() {

        log.info("Starting test: Telegram Settings - Signature - User Level");

        String username = user.getUsername();
        SignatureType signatureType = SignatureType.FIRST_MESSAGE_THREAD;
        SignatureTextInheritance signatureTextInheritance = SignatureTextInheritance.MANUAL;
        String signatureText = "Auto signature text";

        applicationsSettingPage = menuPage.clickFindUsersButton()
                .searchUserByUsername(username)
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

        log.info("✅ Test completed successfully");

    }

    @Test
    @Order(12)
    @Story("Application Settings")
    @Severity(SeverityLevel.NORMAL)
    @Description("Signal Settings - Signature - User Level")
    void signalSignatureUserLevel() {

        log.info("Starting test: Signal Settings - Signature - User Level");

        String username = user.getUsername();
        SignatureType signatureType = SignatureType.FIRST_MESSAGE_THREAD;
        SignatureTextInheritance signatureTextInheritance = SignatureTextInheritance.MANUAL;
        String signatureText = "Auto signature text";

        applicationsSettingPage = menuPage.clickFindUsersButton()
                .searchUserByUsername(username)
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

        log.info("✅ Test completed successfully");

    }

    @Test
    @Order(13)
    @Story("Application Settings")
    @Severity(SeverityLevel.NORMAL)
    @Description("Enterprise Number Capture Settings - Signature - User Level")
    void encSignatureUserLevel() {

        log.info("Starting test: Enterprise Number Capture Settings - Signature - User Level");

        String username = user.getUsername();
        SignatureType signatureType = SignatureType.FIRST_MESSAGE_THREAD;
        SignatureTextInheritance signatureTextInheritance = SignatureTextInheritance.MANUAL;
        String signatureText = "Auto signature text";

        applicationsSettingPage = menuPage.clickFindUsersButton()
                .searchUserByUsername(username)
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

        log.info("✅ Test completed successfully");

    }

    @Test
    @Order(14)
    @Story("Application Settings")
    @Severity(SeverityLevel.NORMAL)
    @Description("Android Capture Settings - RCS Support Checkbox")
    void androidCaptureSettings() {

        log.info("Starting test: Android Capture Settings - RCS Checkbox");

        String username = user.getUsername();

        applicationsSettingPage = menuPage.clickFindUsersButton()
                .searchUserByUsername(username)
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

        log.info("✅ Test completed successfully");

    }

    @Test
    @Order(15)
    @Story("Delete User")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Delete user via Update User Page, verifying success messages and deleted status")
    void deleteUserViaUpdateUserPage() {

        log.info("Starting test: Delete user via Update User Page");

        String username = user.getUsername();

        menuPage.clickFindUsersButton()
                .searchUserByUsername(username)
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



