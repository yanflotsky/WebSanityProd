package com.websanity.teleadminPages;

import com.websanity.BasePage;
import com.microsoft.playwright.Page;
import com.websanity.utils.SecureConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TeleadminLogInPage extends BasePage {

    String username = System.getProperty("teleadmin.username");
    String password = SecureConfig.decrypt(System.getProperty("teleadmin.password"));

    // Locators
    private static final String usernameInput = "input[name='userid'][type='text']";
    private static final String passwordInput = "input[name='password'][type='password']";
    private static final String logInBtn = "a[onclick='clickLogin();'][class='loginbtn']";

    public TeleadminLogInPage(Page page) {
        super(page);
    }

    public TeleadminLogInPage open() {
        navigate(System.getProperty("env.url"));
        page.waitForTimeout(1500);
        return this;
    }
    public void enterUsername(String val) {
        page.fill(usernameInput, val);
    }
    public void enterPassword(String val) {
        page.fill(passwordInput, val);
    }
    public void clickLogInBtn() {
        page.click(logInBtn);
    }

    public TeleadminFindUsersPage logInToTeleadmin() {
        log.info("Logging in as user: {}", System.getProperty("teleadmin.username"));
        enterUsername(username);
        page.waitForTimeout(500);
        enterPassword(password);
        page.waitForTimeout(500);
        clickLogInBtn();
        return new TeleadminFindUsersPage(page);
    }
}

