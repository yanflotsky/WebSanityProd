package com.websanity.teleadminPages;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.websanity.BasePage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MenuPage extends BasePage {

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

    public MenuPage(Page page) {
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
    public SignUpPage clickSignUpButton() {
        signUpButton.click();
        return new SignUpPage(page);
    }

    /**
     * Click on Sign Up From File button
     */
    public LFFPage clickSignUpFromFileButton() {
        log.info("Click Sign Up Users From File Button");
        signUpFromFileButton.click();
        return new LFFPage(page);
    }

    /**
     * Click on Update Users From File button
     */
    public UFFPage clickUpdateUsersFromFileButton() {
        log.info("Click Update Users From File Button");
        updateUsersFromFileButton.click();
        return new UFFPage(page);
    }

    /**
     * Click on Delete Users From File button
     */
    public DFFPage clickDeleteUsersFromFileButton() {
        log.info("Click Delete Users From File Button");
        deleteUsersFromFileButton.click();
        return new DFFPage(page);
    }

    /**
     * Click on Find Users button
     */
    public FindUsersPage clickFindUsersButton() {
        log.info("Click 'Find Users' Button");
        findUsersButton.click();
        usersTable.waitFor(new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE));
        page.waitForTimeout(1000);
        return new FindUsersPage(page);
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
