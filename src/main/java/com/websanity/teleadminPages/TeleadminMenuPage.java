package com.websanity.teleadminPages;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.websanity.BasePage;

public class TeleadminMenuPage extends BasePage {

    private final FrameLocator frameMenu;
    private final FrameLocator frameText;

    // Locators
    private final Locator signUpButton;
    private final Locator signUpFromFileButton;
    private final Locator updateUsersFromFileButton;
    private final Locator deleteUsersFromFileButton;
    private final Locator findUsersButton;
    private final Locator setPermissionsButton;
    private final Locator signOutButton;
    private final Locator usersTable;

    public TeleadminMenuPage(Page page) {
        super(page);
        this.frameMenu = page.frameLocator("frame[name='menu']");
        this.frameText = page.frameLocator("frame[name='text']");
        this.usersTable = frameText.locator("#personTable");
        this.signUpButton = frameMenu.locator("#signup_page_menu");
        this.signUpFromFileButton = frameMenu.locator("#massreg_page_menu");
        this.updateUsersFromFileButton = frameMenu.locator("#massupd_page_menu");
        this.deleteUsersFromFileButton = frameMenu.locator("#massdelete_page_menu");
        this.findUsersButton = frameMenu.locator("#findusers_page_menu");
        this.setPermissionsButton = frameMenu.locator("#permissions_page_menu");
        this.signOutButton = frameMenu.locator("#signoff_page_menu");
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
    public TeleadminFindUsersPage clickFindUsersButton() {
        findUsersButton.click();
        usersTable.waitFor(new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE));
        page.waitForTimeout(1000);
        return new TeleadminFindUsersPage(page);
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
