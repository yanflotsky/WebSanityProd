package com.websanity;

import com.microsoft.playwright.*;
import com.websanity.utils.ScreenshotOnFailureExtension;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(ScreenshotOnFailureExtension.class)
public abstract class BaseTest {
    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext context;
    protected static Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();

        // Check if running in Docker mode (isLocalRun=false means Docker)
        String isLocalRun = System.getProperty("isLocalRun", "true");
        boolean headless = isLocalRun.equals("false"); // Docker = headless, Local = headed

        System.out.println("Browser headless mode: " + headless + " (isLocalRun=" + isLocalRun + ")");

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(headless)
                .setSlowMo(headless ? 0 : 50); // No slowMo in Docker

        // Add args to disable automation detection and for Docker stability
        java.util.List<String> args = new java.util.ArrayList<>();
        args.add("--disable-blink-features=AutomationControlled"); // Mask automation
        args.add("--disable-dev-shm-usage");      // Overcome limited resource problems
        args.add("--no-sandbox");                  // Required for Docker
        args.add("--disable-setuid-sandbox");      // Additional sandbox disable
        args.add("--disable-web-security");        // Allow cross-origin requests
        args.add("--disable-features=IsolateOrigins,site-per-process"); // Disable isolation

        if (headless) {
            args.add("--disable-gpu");                 // Disable GPU hardware acceleration
            args.add("--disable-software-rasterizer"); // Prevent software rendering crashes
            args.add("--disable-features=VizDisplayCompositor"); // Stability fix
            launchOptions.setChannel("chromium"); // Use regular chromium channel
        } else {
            launchOptions.setChannel("chrome"); // Use installed Chrome for local runs
        }

        launchOptions.setArgs(args);

        browser = playwright.chromium().launch(launchOptions);

        // Create context with options to mask automation
        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(1920, 1080)
                .setIgnoreHTTPSErrors(true)
                .setBypassCSP(true)
                .setJavaScriptEnabled(true)
                .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36")
                .setLocale("en-US")
                .setTimezoneId("America/New_York"));

        // Set extra HTTP headers that a real browser would send
        context.setExtraHTTPHeaders(java.util.Map.of(
                "Accept-Language", "en-US,en;q=0.9",
                "Accept-Encoding", "gzip, deflate, br",
                "Connection", "keep-alive",
                "Upgrade-Insecure-Requests", "1"
        ));

        page = context.newPage();

        // Add init scripts to mask automation properties
        page.addInitScript("" +
                "Object.defineProperty(navigator, 'webdriver', {get: () => undefined});" +
                "Object.defineProperty(navigator, 'plugins', {get: () => [1, 2, 3, 4, 5]});" +
                "Object.defineProperty(navigator, 'languages', {get: () => ['en-US', 'en']});" +
                "window.chrome = {runtime: {}};" +
                "Object.defineProperty(navigator, 'permissions', {get: () => ({query: () => Promise.resolve({state: 'granted'})})});");
    }


    @AfterAll
    static void closeBrowser() {
        if (context != null) {
            context.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}

