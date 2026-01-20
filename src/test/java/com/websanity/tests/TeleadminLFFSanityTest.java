package com.websanity.tests;

import com.websanity.BaseTest;
import com.websanity.enums.*;
import com.websanity.models.UserParams;
import com.websanity.teleadminPages.*;
import com.websanity.utils.ExcelFileGenerator;
import com.websanity.utils.TestUsers;
import io.qameta.allure.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Epic("TeleAdmin LFF Tests")
@Feature("LFF (Load From File) functionality")
public class TeleadminLFFSanityTest extends BaseTest {

    private static LogInPage logInPage;
    private static MenuPage menuPage;
    private static FindUsersPage findUsersPage;
    private static LFFPage lffPage;
    private static DFFPage dffPage;
    private static UserParams manager;
    private static List<UserParams> usersForLff;
    private static String lffFileName;
    private static String dffFileName;

    @BeforeAll
    static void setUpAll() {
        log.info("🔧 Setting up Teleadmin LFF session - performing login once for all tests...");

        log.info("=========================================================================");
        log.info("🔧 GENERAL SERVER CACHE BUG https://smarsh.atlassian.net/browse/SST-36674");
        log.info("=========================================================================");

        // Initialize page objects
        logInPage = new LogInPage(page);
        menuPage = new MenuPage(page);

        // Get LFF Manager from TestUsers class
        manager = TestUsers.getLffTeleadminManager();

        // Generate Excel files
        usersForLff = ExcelFileGenerator.createSampleUsers(5);
        lffFileName = ExcelFileGenerator.generateExcelFileForLFF(usersForLff);
        dffFileName = ExcelFileGenerator.generateExcelFileForDFF(usersForLff);

        // Login once for all tests
        logInPage
                .open()
                .logInToTeleadmin()
                .waitForFindUsersPageToLoad();


        log.info("✅ Login completed successfully, session will be kept open for all tests");

    }

    @AfterAll
    static void tearDownAll() {
        log.info("🧹 Closing Teleadmin LFF session after all tests...");
    }

    @BeforeEach
    void setUp(TestInfo testInfo) {
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
        log.info("▶️ Starting test: {}", testInfo.getDisplayName());
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        log.info("Finished test: {}", testInfo.getDisplayName());
    }

    @Test
    @Order(1)
    @Story("Sign Up From File")
    @Severity(SeverityLevel.CRITICAL)
    @Description("LFF - Sign Up users from file")
    void signUpFromFile() {

        log.info("Test: Sign Up From File");

        // Sign up users from file
        lffPage = menuPage.clickSignUpFromFileButton()
                .selectUserType(UserTypes.PROUSER)
                .selectExclusiveAdmin(manager.getUserID());

        Assertions.assertEquals(TestUsers.StoragePlans.WEBSAN_PLAN_1, lffPage.getPlanDescriptionFromFirstRow(),
                "Plan description in Storage Plans table is wrong");

        lffPage.uploadFile(ExcelFileGenerator.getFullPath(lffFileName))
                .clickAddBtn()
                .waitForImportResultText();

        Assertions.assertTrue(lffPage.getImportResultText().contains("5 users were imported and created successfully."), "Import Result text is wrong. Actual text is: " + lffPage.getImportResultText());

        //RETURN AFTER https://smarsh.atlassian.net/browse/SST-36674 will be fixed
        /*log.info("Go to Find Users page and find uploaded users to verify imported users exist now in the system");
        findUsersPage = menuPage.clickFindUsersButton()
                .clickAdvancedOptions()
                .fillCustomerAdministrator(manager.getUsername())
                .clickSearchButton();

        List<String> users = TestUsers.LFFUsers.usernamesForLFFList();
        Assertions.assertTrue(findUsersPage.checkTableHasUsers(users), "All imported users should be found in the table");*/

        log.info("✅ Sign Up From File test - Completed Successfully");

    }

    //RETURN DEVELOPING AFTER https://smarsh.atlassian.net/browse/SST-36674 will be fixed

    /*@Test
    @Order(2)
    @Story("Update Users From File")
    @Severity(SeverityLevel.CRITICAL)
    @Description("LFF - Update users from file")
    void updateUsersFromFile() {
        log.info("Test: Update Users From File");

        String filePath = TestDataFileUtils.getUpdateUsersFile();
        log.info("Using file: {}", filePath);

        // updatePage.uploadFile(filePath)
        //          .clickSubmit()
        //          .waitForSuccessMessage();
        //
        // Assertions.assertTrue(updatePage.isSuccessMessageVisible());

        log.info("Update Users From File test - TO BE IMPLEMENTED");
    }*/

    @Test
    @Order(3)
    @Story("Delete Users From File")
    @Severity(SeverityLevel.CRITICAL)
    @Description("LFF - Delete users from file")
    void deleteUsersFromFile() {

        log.info("Test: Delete Users From File");

        dffPage = menuPage.clickDeleteUsersFromFileButton()
                .selectExclusiveAdmin(manager.getUserID())
                .uploadFile(ExcelFileGenerator.getFullPath(dffFileName))
                .clickAddBtn()
                .waitForImportResultText();

        Assertions.assertTrue(dffPage.getImportResultText().contains("5 users were deleted successfully."), "Import Result text is wrong. Actual text is: " + lffPage.getImportResultText());

        //RETURN AFTER https://smarsh.atlassian.net/browse/SST-36674 will be fixed
        /*log.info("Go to Find Users page and try to find deleted users to verify they no longer exist in the system");
        findUsersPage = menuPage.clickFindUsersButton()
                .clickAdvancedOptions()
                .fillCustomerAdministrator(manager.getUsername())
                .clickSearchButton();

         Assertions.assertTrue(findUsersPage.isTableEmpty(), "Find Users table should be empty after deleting users");*/

        log.info("✅ Delete Users From File test - Completed Successfully");
    }
}
