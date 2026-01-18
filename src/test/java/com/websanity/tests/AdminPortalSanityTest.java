package com.websanity.tests;

import com.websanity.AdminPortalBaseTest;
import com.websanity.adminPortalPages.*;
import com.websanity.enums.Country;
import com.websanity.enums.Language;
import com.websanity.enums.TimeZone;
import com.websanity.enums.UserTypes;
import com.websanity.models.ContactParams;
import com.websanity.models.UserParams;
import io.qameta.allure.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

/**
 * Admin Portal tests with single login session
 * Browser and session remain open between tests
 * Login is performed once before all tests via AdminPortalBaseTest
 */
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Epic("Admin Portal")
@Feature("Admin Portal Features")
public class AdminPortalSanityTest extends AdminPortalBaseTest {

    private AdminPortalUserManagementPage userManagementPage;
    private AdminPortalComposeMessagePage composeMessagePage;
    private AdminPortalSentItemsPage sentItemsPage;
    private AdminPortalMyContactsPage myContactsPage;
    private AdminPortalArchiveManagementPage archiveManagement;

    private static final String randnum = String.format("%07d", System.currentTimeMillis() % 10000000);

    private final UserParams managerParams = UserParams.builder()
            .firstName("websanityfn")
            .lastName("websanityln")
            .username("websanityun")
            .build();

    private final UserParams userParams = UserParams.builder()
            .firstName("wsfn"+randnum)
            .lastName("wsln"+randnum)
            .username("wsprous"+randnum)
            .email("ws"+randnum+"@gmail.com")
            .mobileCountryCode(Country.ISRAEL)
            .mobileArea("58")
            .mobilePhone(randnum)
            .password("Aa123123123123123")
            .userType(UserTypes.PROUSER)
            .build();


    @Test
    @Order(1)
    @Story("User Management")
    @Severity(SeverityLevel.CRITICAL)
    @Description("User Management - Add User")
    void addProUser() {

        log.info("User Management - Add User");

        userManagementPage = menuPage.clickUserManagement()
                .clickAddUser()
                .selectServiceLevel(userParams.getUserType())
                .fillFirstName(userParams.getFirstName())
                .fillLastName(userParams.getLastName())
                .fillMobilePhone(userParams.getMobileCountryCode(),userParams.getMobileArea() + userParams.getMobilePhone())
                .fillEmail(userParams.getEmail())
                .fillUsername(userParams.getUsername())
                .fillPassword(userParams.getPassword())
                .clickSaveBtn();

        log.info("Check Summary Pop Up");
        Assertions.assertTrue(userManagementPage.isAddUserSummaryPopUpVisible(), "Add user summary popup should be visible");
        Assertions.assertEquals("User was added successfully\n",userManagementPage.getAddUserSummaryPopUpSuccessUsersText(), "Text of summary popup is not correct");
        Assertions.assertEquals("Users need to be assigned to an archive plan in order to archivemessages. Please navigate to the Archive Management section to complete this required step.",userManagementPage.getAssignedArchivePlanMessageText(), "Archive Management info in the summary popup is not correct");

        userManagementPage.clickCloseErrorsSummaryBtn();

        log.info("Search for created user and verify data in table");
        userManagementPage = menuPage.clickUserManagement()
                .fillSearchInp(userParams.getUsername())
                .clickSearchBtn();

        Assertions.assertEquals("Activated",userManagementPage.getFirstUserStatus(),"Status of the user is not correct");
        Assertions.assertEquals(userParams.getUserType().getDisplayName(), userManagementPage.getFirstUserServiceLevel(), "Service Level in table doesn't match");
        Assertions.assertEquals(userParams.getFirstName(), userManagementPage.getFirstUserFirstName(), "First Name in table doesn't match");
        Assertions.assertEquals(userParams.getLastName(), userManagementPage.getFirstUserLastName(), "Last Name in table doesn't match");
        Assertions.assertEquals(userParams.getUsername(), userManagementPage.getFirstUserUsername(), "Username in table doesn't match");
        Assertions.assertEquals(userParams.getEmail(), userManagementPage.getFirstUserEmail(), "Email in table doesn't match");
        Assertions.assertEquals(userParams.getMobileCountryCode().getDialingCode().substring(1) + userParams.getMobileArea() + userParams.getMobilePhone(), userManagementPage.getFirstUserMobile(), "Mobile in table doesn't match");

        log.info("✅ Test completed successfully");

    }

    @Test
    @Order(2)
    @Story("User Management")
    @Severity(SeverityLevel.CRITICAL)
    @Description("User Management - Update User Details")
    void updateUserDetails() {

        log.info("User Management - Update User Details");
        String randnum = String.format("%07d", System.currentTimeMillis() % 10000000);
        UserParams userParamsForUpd = UserParams.builder()
                .firstName("wsupdfn"+randnum)
                .lastName("wsupdln"+randnum)
                .language(Language.HEBREW)
                .timeZone(TimeZone.EUROPE_LONDON)
                .country(Country.UNITED_KINGDOM)
                .email("wsudp"+randnum+"@gmail.com")
                .build();

        userManagementPage = menuPage.clickUserManagement()
                .fillSearchInp(userParams.getUsername())
                .clickSearchBtn()
                .clickFirstUserUsername()
                .fillFirstName(userParamsForUpd.getFirstName())
                .fillLastName(userParamsForUpd.getLastName())
                .selectLanguage(userParamsForUpd.getLanguage())
                .selectCountry(userParamsForUpd.getCountry())
                .selectTimeZone(userParamsForUpd.getTimeZone())
                .fillEmail(userParamsForUpd.getEmail())
                .clickSaveBtn();

        log.info("Search for edited user and verify data in table");
        userManagementPage = menuPage.clickUserManagement()
                .fillSearchInp(userParams.getUsername())
                .clickSearchBtn();

        Assertions.assertEquals(userParamsForUpd.getFirstName(), userManagementPage.getFirstUserFirstName(), "First Name in table doesn't match");
        Assertions.assertEquals(userParamsForUpd.getLastName(), userManagementPage.getFirstUserLastName(), "Last Name in table doesn't match");
        Assertions.assertEquals(userParamsForUpd.getEmail(), userManagementPage.getFirstUserEmail(), "Email in table doesn't match");

        userManagementPage = menuPage.clickUserManagement()
                .fillSearchInp(userParams.getUsername())
                .clickSearchBtn()
                .clickFirstUserUsername();

        Assertions.assertEquals(userParamsForUpd.getLanguage(), userManagementPage.getSelectedLanguage(), "Language selected is not correct");
        Assertions.assertEquals(userParamsForUpd.getCountry(), userManagementPage.getSelectedCountry(), "Country selected is not correct");
        Assertions.assertEquals(userParamsForUpd.getTimeZone(), userManagementPage.getSelectedTimeZone(), "TimeZone selected is not correct");

        // Update userParams with new values for next tests
        userParams.setFirstName(userParamsForUpd.getFirstName());
        userParams.setLastName(userParamsForUpd.getLastName());
        userParams.setEmail(userParamsForUpd.getEmail());
        userParams.setLanguage(userParamsForUpd.getLanguage());
        userParams.setCountry(userParamsForUpd.getCountry());
        userParams.setTimeZone(userParamsForUpd.getTimeZone());

        log.info("✅ Test completed successfully");

    }


    @Test
    @Order(3)
    @Story("User Management")
    @Severity(SeverityLevel.CRITICAL)
    @Description("User Management - Change User Status")
    void changeUserStatus() {

        log.info("User Management - Change User Status");

        log.info("Suspend User");
        userManagementPage = menuPage.clickUserManagement()
                .fillSearchInp(userParams.getUsername())
                .clickSearchBtn()
                .clickFirstUserCheckbox()
                .clickSuspendBtn();

        log.info("Verify Success Message and Status in the table");
        Assertions.assertTrue(userManagementPage.isSuccessMsgVisible(), "Success message was not appeared");
        Assertions.assertEquals("User(s) suspended successfully",userManagementPage.getSuccessMsgText(), "Text of the success message is not correct");
        Assertions.assertEquals("Suspended",userManagementPage.getFirstUserStatus(),"Status of the user is not correct");

        log.info("Activate User");
        userManagementPage = menuPage.clickUserManagement()
                .fillSearchInp(userParams.getUsername())
                .clickSearchBtn()
                .clickFirstUserCheckbox()
                .clickActivateBtn();

        log.info("Verify Success Message and Status in the table");
        Assertions.assertTrue(userManagementPage.isSuccessMsgVisible(), "Success message was not appeared");
        Assertions.assertEquals("User(s) activated successfully",userManagementPage.getSuccessMsgText(), "Text of the success message is not correct");
        Assertions.assertEquals("Activated",userManagementPage.getFirstUserStatus(),"Status of the user is not correct");

        log.info("✅ Test completed successfully");
    }


    @Test
    @Order(4)
    @Story("Compose Message and Sent Items")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Compose Message/Sent Items - Send Message")
    void sendMessage() {

        log.info("Compose Message/Sent Items - Send Message");

        String to = "972585529693";
        String subject = "autosubject" + System.currentTimeMillis();
        String messageText = "Hello, this is a test message sent at " + System.currentTimeMillis();

        composeMessagePage = menuPage.clickComposeMessageBtn()
                .clickMoreBtn()
                .clickAddSubject()
                .fillSubjectInput(subject)
                .fillToInput(to)
                .fillMessageInput(messageText)
                .clickSendBtn();

        log.info("Verify Success Message");
        Assertions.assertTrue(composeMessagePage.isSuccessMsgVisible(), "Success message was not appeared");
        Assertions.assertEquals("Your message has been sent.\n" +
                "Click here to view message delivery progress.",composeMessagePage.getSuccessMsgText(), "Text of the success message is not correct");

        page.waitForTimeout(20000); // Wait for 20 seconds to ensure message appears in Sent Items

        sentItemsPage = menuPage.clickSentItems()
                .waitForMessagesListToLoad();

        log.info("Verify Sent Items for a sent message");
        Assertions.assertEquals("Text", sentItemsPage.getFirstMessageType(), "Message type is not correct");
        Assertions.assertEquals("Message delivered successfully - Click for detailed delivery information", sentItemsPage.getFirstMessageStatus(), "Status of the message is not correct");
        Assertions.assertTrue(sentItemsPage.getFirstMessageRecipient().contains(to), "Recipient of the message is not correct. Actual: " + sentItemsPage.getFirstMessageRecipient());
        Assertions.assertEquals(subject, sentItemsPage.getFirstMessageText(), "Text of the message is not correct");
        Assertions.assertNotNull(sentItemsPage.getFirstMessageDate(), "Sent date of the message should not be null or empty");

        sentItemsPage.clickFirstMessage()
                .waitForMessageDetailsToLoad();

        log.info("Verify Message Details for the sent message");
        Assertions.assertNotNull(sentItemsPage.getMessageDetailsDate(), "Sent date in message details should not be null or empty");
        Assertions.assertEquals(managerParams.getFirstName() + " " + managerParams.getLastName(), sentItemsPage.getMessageDetailsFrom(), "Sender in message details is not correct");
        Assertions.assertTrue(sentItemsPage.getMessageDetailsTo().contains(to), "'To' is not correct in Message Details. Actual: " + sentItemsPage.getMessageDetailsTo());
        Assertions.assertEquals(subject, sentItemsPage.getMessageDetailsSubject(), "Subject in Message Details is not correct");
        Assertions.assertEquals(messageText, sentItemsPage.getMessageDetailsText(), "Message text in Message Details is not correct");

        sentItemsPage.clickMessageStatusBtn()
               .waitForRecipientTableToLoad();

        log.info("Verify Message Status Table for the sent message");
        Assertions.assertEquals(to, sentItemsPage.getRecipientNumberFromMessageStatusTable().replace("-", ""), "Recipient number from status table not correct");
        Assertions.assertTrue(sentItemsPage.isMessageDelivered(), "Message status should be Delivered in status table");

        sentItemsPage = menuPage.clickSentItems()
                .waitForMessagesListToLoad()
                .clickDeleteRecycleBinBtn()
                .clickConfirmYesBtn();

        log.info("Verify Sent Items is empty after clicking Delete Recycle Bin");
        Assertions.assertTrue(sentItemsPage.isMessagesListEmpty(), "No messages should remain");

        log.info("✅ Test completed successfully");

    }


    @Test
    @Order(5)
    @Story("My Contacts")
    @Severity(SeverityLevel.NORMAL)
    @Description("My Contacts - Add Local Contact")
    void addLocalContact() {

        log.info("My Contacts - Add Local Contact");

        String contactRandNum = String.format("%07d", System.currentTimeMillis() % 10000000);

        ContactParams contactParams = ContactParams.builder()
                .firstName("confn" + contactRandNum)
                .lastName("conln" + contactRandNum)
                .jobTitle("jobtitle" + contactRandNum)
                .mobileCountry(Country.ISRAEL)
                .mobileTelephone("58" + contactRandNum)
                .homeCountry(Country.UNITED_STATES)
                .homeTelephone("607"+contactRandNum)
                .businessCountry(Country.BELARUS)
                .businessTelephone("296299929")
                .businessExt("321")
                .communicationEmail("webautcon" + contactRandNum + "@gmail.com")
                .company("websancomp")
                .department("websandep")
                .build();

        myContactsPage = menuPage.clickMyContacts()
                .clickNewBtn()
                .clickNewContact()
                .fillFirstName(contactParams.getFirstName())
                .fillLastName(contactParams.getLastName())
                .fillJobTitle(contactParams.getJobTitle())
                .selectMobileCountry(contactParams.getMobileCountry())
                .fillMobileTelephone(contactParams.getMobileTelephone())
                .selectHomeCountry(contactParams.getHomeCountry())
                .fillHomeTelephone(contactParams.getHomeTelephone())
                .selectBusinessCountry(contactParams.getBusinessCountry())
                .fillBusinessTelephone(contactParams.getBusinessTelephone())
                .fillBusinessExt(contactParams.getBusinessExt())
                .fillCommunicationEmail(contactParams.getCommunicationEmail())
                .fillCompany(contactParams.getCompany())
                .fillDepartment(contactParams.getDepartment())
                .clickSaveBtn();

        log.info("Verify success message after creating contact");
        Assertions.assertTrue(myContactsPage.isSuccessMsgVisible(), "Success message was not appeared after creating contact");
        Assertions.assertEquals("Contact was created successfully.",myContactsPage.getSuccessMsgText(), "Text of the success message is not correct");

        log.info("Go back to My Contacts and search for created contact");
        myContactsPage = menuPage.clickMyContacts()
                .fillSearchInp(contactParams.getFirstName())
                .clickSearchBtn();

        // Verify contact data in table
        Assertions.assertEquals(contactParams.getFirstName() + " " + contactParams.getLastName(), myContactsPage.getFirstContactName(), "Contact name in table should match");
        Assertions.assertEquals(contactParams.getDepartment(), myContactsPage.getFirstContactDepartment(), "Department in table should match");
        Assertions.assertEquals(contactParams.getCommunicationEmail(), myContactsPage.getFirstContactEmail(), "Email in table should match");

        // Verify mobile phone (format: CountryCode-Area-Number)
        String expectedMobile = contactParams.getMobileCountry().getDialingCode().substring(1) +
                "-" + contactParams.getMobileTelephone().substring(0, 2) +
                "-" + contactParams.getMobileTelephone().substring(2);
        Assertions.assertEquals(expectedMobile, myContactsPage.getFirstContactMobile(),
                "Mobile phone in table should match");

        // Verify home phone
        String expectedHome = contactParams.getHomeCountry().getDialingCode().substring(1) +
                "-" + contactParams.getHomeTelephone().substring(0, 3) +
                "-" + contactParams.getHomeTelephone().substring(3);
        Assertions.assertEquals(expectedHome, myContactsPage.getFirstContactHome(),
                "Home phone in table should match");

        // Verify business phone with extension
        String expectedBusiness = contactParams.getBusinessCountry().getDialingCode().substring(1) +
                "-" + contactParams.getBusinessTelephone().substring(0, 3) +
                "-" + contactParams.getBusinessTelephone().substring(3) +
                " Ext. " + contactParams.getBusinessExt();
        Assertions.assertEquals(expectedBusiness, myContactsPage.getFirstContactBusiness(),
                "Business phone in table should match");

        log.info("Delete contact");
        myContactsPage.clickFirstContactCheckbox()
                .clickMoreBtn()
                .clickDeleteSelectedContactBtn()
                .clickConfirmYesBtn();

        log.info("Verify success message after deleting contact");
        Assertions.assertTrue(myContactsPage.isSuccessMsgVisible(), "Success message was not appeared after deleting contact");
        Assertions.assertEquals("Address book entry deleted", myContactsPage.getSuccessMsgText(), "Text of the success message after deletion is not correct");
        Assertions.assertTrue(myContactsPage.isContactsListEmpty(), "Contacts list should be empty after deleting the contact");

        log.info("✅ Test completed successfully");

    }

    @Test
    @Order(6)
    @Story("Archive Management")
    @Severity(SeverityLevel.NORMAL)
    @Description("Archive Management - Assign/Unassign user to Archive Plan")
    void archiveManagement() {

        log.info("Archive Management - Assign/Unasssign User to Archive Plan");

        archiveManagement = menuPage.clickArchiveManagement();

        log.info("Verifying table headers...");
        Assertions.assertTrue(archiveManagement.verifyAllHeadersOfArchivePlansTable(), "All table headers should match expected values");

        log.info("Verifying first row cells contain expected data...");
        Assertions.assertEquals("WhatsApp Phone Capture", archiveManagement.getSourceFromFirstRow(), "Source column should be 'WhatsApp Phone Capture'");
        Assertions.assertEquals("Generic SMTP Archiver", archiveManagement.getDestinationFromFirstRow(), "Destination column should be 'Generic SMTP Archiver'");
        Assertions.assertEquals("yanflotskysmarsh@gmail.com", archiveManagement.getEmailFromFirstRow(), "Email column should be 'yanflotskysmarsh@gmail.com'");
        Assertions.assertEquals("0", archiveManagement.getNumberOfAssignedUsersFromFirstRow(), "Number of Assigned Users column should be '1'");

        log.info("Verifying Assign and Unassign buttons exist...");
        Assertions.assertTrue(archiveManagement.verifyActionButtonsExist(), "Both Assign and Unassign buttons should exist");

        archiveManagement.clickAssignButton();

        log.info("Verifying 'Users Assigned To This Plan' counter'");
        Assertions.assertEquals("0", archiveManagement.getNumOfAssignedUsersCounter(), "Number of Assigned Users should be '0' before assignment");

        archiveManagement.fillSearchInput(userParams.getUsername())
                .clickSearchButton();

        log.info("Verifying 'Assign Users' table data'");
        Assertions.assertEquals(userParams.getFirstName(), archiveManagement.getFirstNameFromAssignActionFirstRow(), "First Name in Assign Action table should match");
        Assertions.assertEquals(userParams.getLastName(), archiveManagement.getLastNameFromAssignActionFirstRow(), "Last Name in Assign Action table should match");
        Assertions.assertEquals(userParams.getUsername(), archiveManagement.getUsernameFromAssignActionFirstRow(), "Username in Assign Action table should match");
        Assertions.assertEquals(userParams.getEmail(), archiveManagement.getEmailFromAssignActionFirstRow(), "Email in Assign Action table should match");
        Assertions.assertEquals(userParams.getMobileCountryCode().getDialingCode().substring(1) + userParams.getMobileArea() + userParams.getMobilePhone(), archiveManagement.getMobileFromAssignActionFirstRow(), "Mobile Number in Assign Action table should match");

        archiveManagement.clickAssignActionFirstRowCheckbox()
                .clickAssignUsersButton();

        log.info("Verifying success message appears");
        Assertions.assertTrue(archiveManagement.isSuccessMessageVisible(), "Success message should be visible");
        Assertions.assertEquals("Success!1 users were assigned to the WhatsApp Phone Capture to Generic SMTP Archiver archive plan!",archiveManagement.getSuccessMessageText(), "Success message should contain 'Success!'");

        log.info("Verifying 'Users Assigned To This Plan' counter'");
        Assertions.assertEquals("1", archiveManagement.getNumOfAssignedUsersCounter(), "Number of Assigned Users should be '1' after assignment");

        archiveManagement.fillSearchInput(userParams.getUsername())
                .clickSearchButton();

        log.info("Verifying there is no "+userParams.getUsername()+" in Assign Action table anymore");
        Assertions.assertTrue(archiveManagement.isAssignActionUsersTableEmpty(), "Assign Action Users table should not contain assigned users");

        archiveManagement = menuPage.clickArchiveManagement()
                .clickUnassignButton();

        log.info("Verifying 'Users Assigned To This Plan' counter'");
        Assertions.assertEquals("1", archiveManagement.getNumOfAssignedUsersCounter(), "Number of Assigned Users should be '1' after assignment");

        archiveManagement.fillSearchInput(userParams.getUsername())
                .clickSearchButton();

        log.info("Verifying 'Assign Users' table data'");
        Assertions.assertEquals(userParams.getFirstName(), archiveManagement.getFirstNameFromUnassignActionFirstRow(), "First Name in Unassign Action table should match");
        Assertions.assertEquals(userParams.getLastName(), archiveManagement.getLastNameFromUnassignActionFirstRow(), "Last Name in Unassign Action table should match");
        Assertions.assertEquals(userParams.getUsername(), archiveManagement.getUsernameFromUnassignActionFirstRow(), "Username in Unassign Action table should match");
        Assertions.assertEquals(userParams.getEmail(), archiveManagement.getEmailFromUnassignActionFirstRow(), "Email in Unassign Action table should match");
        Assertions.assertEquals(userParams.getMobileCountryCode().getDialingCode().substring(1) + userParams.getMobileArea() + userParams.getMobilePhone(), archiveManagement.getMobileFromUnassignActionFirstRow(), "Mobile Number in Unassign Action table should match");

        archiveManagement.clickUnassignActionFirstRowCheckbox()
                .clickUnassignUsersButton();

        log.info("Verifying success message appears");
        Assertions.assertTrue(archiveManagement.isSuccessMessageVisible(), "Success message should be visible");
        Assertions.assertEquals("Success!1 users were unassigned to the WhatsApp Phone Capture to Generic SMTP Archiver archive plan!",archiveManagement.getSuccessMessageText(), "Success message should contain 'Success!'");

        archiveManagement.fillSearchInput(userParams.getUsername())
                .clickSearchButton();

        log.info("Verifying there is no "+userParams.getUsername()+" in Assign Action table anymore");
        Assertions.assertTrue(archiveManagement.isUnassignActionUsersTableEmpty(), "Unassign Action Users table should not contain assigned users");

        log.info("Verifying 'Users Assigned To This Plan' counter'");
        Assertions.assertEquals("0", archiveManagement.getNumOfAssignedUsersCounter(), "Number of Assigned Users should be '1' after assignment");

        log.info("✅ Test completed successfully");

    }


    @Test
    @Order(7)
    @Story("User Management")
    @Severity(SeverityLevel.CRITICAL)
    @Description("User Management - Delete User")
    void deleteUser() {

        log.info("User Management - Delete User");

        userManagementPage = menuPage.clickUserManagement()
                .fillSearchInp(userParams.getUsername())
                .clickSearchBtn()
                .clickFirstUserCheckbox()
                .clickDeleteBtn()
                .clickConfirmYesBtn()
                .fillSearchInp(userParams.getUsername())
                .clickSearchBtn();

        Assertions.assertTrue(userManagementPage.isContentTableEmpty(), "Content table should be empty after deleting user");

        log.info("✅ Test completed successfully");

    }

}

