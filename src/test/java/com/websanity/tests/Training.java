package com.websanity.tests;

import com.websanity.BaseTest;
import com.websanity.enums.*;
import com.websanity.models.UserParams;
import com.websanity.teleadminPages.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Training extends BaseTest {

    private TeleadminLogInPage teleadminLogInPage;
    private TeleadminMenuPage teleadminMenuPage;
    private TeleadminFindUsersPage findUsersPage;
    private TeleadminUpdateUserPage updateUserPage;
    private TeleadminSignUpPage signUpPage;
    private TeleadminAdminsPortalSettingsPage adminsPortalPage;

    private static UserParams user;

    @BeforeAll
    static void setUpAll() {

        String uniqueNum = String.format("%07d", System.currentTimeMillis() % 10000000);

        user = UserParams.builder()
                .userType(UserTypes.PROMANAGER)
                .firstName("trainfn" + uniqueNum)
                .lastName("trainln" + uniqueNum)
                .username("trainun" + uniqueNum)
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
                .email("trainem" + uniqueNum + "@gmail.com")
                .company("traincomp" + uniqueNum)
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
    void registerNewUserTest() {

        log.info("Starting test: Register new Pro Manager for training");

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
    void myTrainingTest() {

        log.info("Starting test: WPC Settings - Signature - User Level");

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
    @Order(3)
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

