package com.websanity.tests;

import com.websanity.AdminPortalBaseTest;
import com.websanity.adminPortalPages.AdminPortalUserManagementPage;
import com.websanity.enums.Country;
import com.websanity.enums.UserTypes;
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
    private static String randnum = String.format("%07d", System.currentTimeMillis() % 10000000);
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

        Assertions.assertTrue(userManagementPage.isSuccessMsgVisible(),"Success message is not visible");
        Assertions.assertEquals("No users found", userManagementPage.getSuccessMsgText(),"Error message text is not correct");
        Assertions.assertTrue(userManagementPage.isContentTableEmpty(), "Content table should be empty after deleting user");

        log.info("✅ Test completed successfully");

    }

}

