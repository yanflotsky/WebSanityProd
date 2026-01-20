package com.websanity.teleadminPages;

import com.websanity.BasePage;
import com.microsoft.playwright.Page;
import com.websanity.utils.SecureConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogInPage extends BasePage {

    String username = System.getProperty("teleadmin.username");
    String password = SecureConfig.decrypt(System.getProperty("teleadmin.password"));

    // Locators
    private static final String usernameInput = "input[name='userid'][type='text']";
    private static final String passwordInput = "input[name='password'][type='password']";
    private static final String logInBtn = "a[onclick='clickLogin();'][class='loginbtn']";

    public LogInPage(Page page) {
        super(page);
    }

    public LogInPage open() {
        navigate(System.getProperty("env.url"));
        page.waitForLoadState();
        page.waitForTimeout(2000);
        page.locator(logInBtn).waitFor(); // Wait for page to fully load
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

    public FindUsersPage logInToTeleadmin() {
        log.info("Logging in as user: {}", System.getProperty("teleadmin.username"));
        enterUsername(username);
        page.waitForTimeout(500);
        enterPassword(password);
        page.waitForTimeout(500);

        // Click login and wait for navigation to complete
        log.info("Clicking login button and waiting for navigation...");
        clickLogInBtn();

        // Wait for page to load after login (increased timeout for Docker)
        page.waitForLoadState();
        page.waitForTimeout(2000);  // Additional wait for frames to load

        log.info("Login navigation completed");
        return new FindUsersPage(page);
    }
}

