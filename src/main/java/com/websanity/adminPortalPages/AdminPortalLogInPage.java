package com.websanity.adminPortalPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.websanity.BasePage;
import com.websanity.utils.ConfigLoader;
import com.websanity.utils.EmailHelper;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class AdminPortalLogInPage extends BasePage {

    private static final String MANAGER_PORTAL_URL = "https://secure.telemessage.com/members";

    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator signInButton;
    private final Locator forgotPasswordLink;
    private final Locator emailButton;
    private final Locator mfaCodeInput;
    private final Locator continueButton;

    public AdminPortalLogInPage(Page page) {
        super(page);
        this.usernameInput = page.locator("input#username[name='username']");
        this.passwordInput = page.locator("input#password[name='password'][type='password']");
        this.signInButton = page.getByRole(com.microsoft.playwright.options.AriaRole.BUTTON,
            new Page.GetByRoleOptions().setName("Sign In"));
        this.forgotPasswordLink = page.getByRole(com.microsoft.playwright.options.AriaRole.LINK,
            new Page.GetByRoleOptions().setName("Forgot password?"));
        this.emailButton = page.getByRole(com.microsoft.playwright.options.AriaRole.BUTTON,
            new Page.GetByRoleOptions().setName("Email"));
        this.mfaCodeInput = page.locator("input#code[name='code'][type='text']");
        this.continueButton = page.getByRole(com.microsoft.playwright.options.AriaRole.BUTTON,
            new Page.GetByRoleOptions().setName("Continue"));
    }

    public AdminPortalLogInPage fillUsername(String username) {
        log.info("Filling in username: {}", username);
        usernameInput.fill(username);
        return this;
    }

    public AdminPortalLogInPage fillPassword(String password) {
        log.info("Filling in password: ***********");
        passwordInput.fill(password);
        return this;
    }

    public void clickSignIn() {
        log.info("Clicking Sign In button");
        signInButton.click();
    }

    public AdminPortalLogInPage waitForEmailButton() {
        log.info("Waiting for Email button to appear...");
        emailButton.waitFor(new Locator.WaitForOptions().setTimeout(10000));
        return this;
    }

    public void clickEmailButton() {
        log.info("Clicking Email button to select email verification");
        emailButton.click();
    }

    public void clickContinue() {
        log.info("Clicking Continue button");
        continueButton.click();
    }

    public void clickForgotPassword() {
        log.info("Clicking 'Forgot password?' link");
        forgotPasswordLink.click();
    }

    /**
     * Fill MFA code manually
     */
    public AdminPortalLogInPage fillMfaCode(String code) {
        log.info("Filling in MFA code: {}", code);
        mfaCodeInput.fill(code);
        return this;
    }

    /**
     * Wait for MFA page to appear
     */
    public AdminPortalLogInPage waitForMfaPage() {
        log.info("Waiting for MFA code input page...");
        mfaCodeInput.waitFor(new Locator.WaitForOptions().setTimeout(10000));
        return this;
    }

    /**
     * Get MFA code from email and fill it automatically
     * Uses credentials from config.properties
     *
     * @param afterTimestamp Only search for emails received after this timestamp
     */
    public AdminPortalLogInPage fillMfaCodeFromEmail(Date afterTimestamp) {
        log.info("📧 Retrieving MFA code from email...");

        String email = ConfigLoader.getTestEmail();
        String appPassword = ConfigLoader.getEmailAppPassword();

        log.info("Email configured: {}", email);
        log.debug("App password configured: {}", appPassword != null && !appPassword.isEmpty() ? "Yes (length: " + appPassword.length() + ")" : "No");

        if (email == null || appPassword == null) {
            throw new RuntimeException("Email credentials not configured in config.properties");
        }

        log.info("⏳ Searching for MFA email received after: {}", afterTimestamp);

        String mfaCode;
        try {
            // Get code from the most recent email received AFTER the given timestamp
            mfaCode = EmailHelper.getMfaCodeAfterTimestamp(email, appPassword, afterTimestamp);
            log.info("✅ MFA code retrieved: {}", mfaCode);
        } catch (Exception e) {
            log.error("❌ Failed to retrieve MFA code", e);
            throw new RuntimeException("Could not find MFA code in email. Check: 1) Email was sent, 2) Email format contains 6-digit code, 3) Email credentials are correct", e);
        }

        return fillMfaCode(mfaCode);
    }

    /**
     * Complete login with MFA - gets code from email automatically
     */
    public AdminPortalMenuPage loginToAdminPortalWithAutoUser() {

        String username = "websanityun";
        String password = "QAautoweb12345678!!";

        log.info("Starting login with MFA for user: {}", username);

        // Navigate to Manager Portal
        page.navigate(MANAGER_PORTAL_URL);

        fillUsername(username);
        fillPassword(password);
        clickSignIn();

        // Wait for Email button to appear and click it to select email verification method
        waitForEmailButton();

        // Record timestamp BEFORE clicking Email button with 10 second buffer
        // (subtract 10 seconds to handle Gmail timestamp precision issues)
        Date beforeEmailRequest = new Date(System.currentTimeMillis() - 10000);
        log.info("🕐 Timestamp for email search: {} (10 seconds before now)", beforeEmailRequest);

        clickEmailButton();

        waitForMfaPage();
        fillMfaCodeFromEmail(beforeEmailRequest); // Will wait for email in a loop until it arrives

        // Click Continue and wait for navigation to complete
        log.info("Clicking Continue button and waiting for navigation...");
        clickContinue(); // Click Continue button after MFA code

        // Wait for navigation to complete - the page should redirect to the admin portal
        // We wait for the URL to change away from the Auth0 login pages
        try {
            page.waitForURL(url -> !url.contains("auth.telemessage.com"),
                new Page.WaitForURLOptions().setTimeout(30000));
            log.info("Navigation completed, URL: {}", page.url());
        } catch (Exception e) {
            log.error("Navigation timeout or error after MFA. Current URL: {}", page.url());
            throw new RuntimeException("Failed to navigate to Admin Portal after MFA login. Current URL: " + page.url(), e);
        }

        // Additional wait for any post-login redirects to settle
        page.waitForLoadState();
        log.info("Login with MFA completed");

        return new AdminPortalMenuPage(page);
    }

}
