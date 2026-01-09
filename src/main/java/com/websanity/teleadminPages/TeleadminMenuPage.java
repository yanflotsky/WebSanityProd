package com.websanity.teleadminPages;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.websanity.BasePage;

public class TeleadminMenuPage extends BasePage {

    private final FrameLocator textFrame;

    // Locators
    private final Locator signUpButton;
    private final Locator signUpFromFileButton;
    private final Locator updateUsersFromFileButton;
    private final Locator deleteUsersFromFileButton;
    private final Locator findUsersButton;
    private final Locator setPermissionsButton;
    private final Locator signOutButton;

    public TeleadminMenuPage(Page page) {
        super(page);
        this.textFrame = page.frameLocator("frame[name='menu']");
        this.signUpButton = textFrame.locator("#signup_page_menu");
        this.signUpFromFileButton = textFrame.locator("#massreg_page_menu");
        this.updateUsersFromFileButton = textFrame.locator("#massupd_page_menu");
        this.deleteUsersFromFileButton = textFrame.locator("#massdelete_page_menu");
        this.findUsersButton = textFrame.locator("#findusers_page_menu");
        this.setPermissionsButton = textFrame.locator("#permissions_page_menu");
        this.signOutButton = textFrame.locator("#signoff_page_menu");
    }

    /**
     * Click on Sign Up button to navigate to user registration page
     * @return TeleadminSignUpPage instance for method chaining
     */
    public TeleadminSignUpPage clickSignUpButton() {
        signUpButton.click();
        return new TeleadminSignUpPage(page);
    }

    /**
     * Click on Sign Up From File button
     */
    public void clickSignUpFromFileButton() {
        signUpFromFileButton.click();
    }

    /**
     * Click on Update Users From File button
     */
    public void clickUpdateUsersFromFileButton() {
        updateUsersFromFileButton.click();
    }

    /**
     * Click on Delete Users From File button
     */
    public void clickDeleteUsersFromFileButton() {
        deleteUsersFromFileButton.click();
    }

    /**
     * Click on Find Users button
     */
    public void clickFindUsersButton() {
        findUsersButton.click();
    }

    /**
     * Click on Set Permissions button
     */
    public void clickSetPermissionsButton() {
        setPermissionsButton.click();
    }

    /**
     * Click on Sign Out button
     */
    public void clickSignOutButton() {
        signOutButton.click();
    }

}
