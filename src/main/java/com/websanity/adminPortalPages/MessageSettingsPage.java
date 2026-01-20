package com.websanity.adminPortalPages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.websanity.BasePage;

public class MessageSettingsPage extends BasePage {

    private final Locator loadingOverlay;
    private final Locator successMsg;

    public MessageSettingsPage(Page page) {
        super(page);
        this.loadingOverlay = page.locator("#fade");
        this.successMsg = page.locator("#successMsg");
    }

}
