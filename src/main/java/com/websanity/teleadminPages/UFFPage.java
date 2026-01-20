package com.websanity.teleadminPages;

import com.microsoft.playwright.FrameLocator;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.websanity.BasePage;

public class UFFPage extends BasePage {

    private final FrameLocator textFrame;
    private final Locator usernameInput;

    public UFFPage(Page page) {
        super(page);
        this.textFrame = page.frameLocator("frame[name='text']");
        this.usernameInput = textFrame.locator("input[name='userName']");
    }

}
