package com.websanity.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration loader for test properties
 */
@Slf4j
public class ConfigLoader {

    private static final String CONFIG_FILE = "config.properties";
    private static Properties properties;

    static {
        properties = new Properties();
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                log.error("Unable to find {}", CONFIG_FILE);
                throw new RuntimeException("Configuration file not found: " + CONFIG_FILE);
            }
            properties.load(input);
            log.debug("Configuration loaded from {}", CONFIG_FILE);
        } catch (IOException e) {
            log.error("Error loading configuration", e);
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    /**
     * Get property value by key
     */
    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            log.warn("Property '{}' not found in configuration", key);
        }
        return value;
    }

    /**
     * Get property value with default fallback
     */
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Get test email address
     */
    public static String getTestEmail() {
        return getProperty("test.email");
    }

    /**
     * Get Gmail App Password (automatically decrypts if encrypted)
     * Store encrypted password in config.properties for security
     */
    public static String getEmailAppPassword() {
        String encryptedPassword = getProperty("test.email.app.password");
        if (encryptedPassword == null) {
            throw new RuntimeException("Email app password not configured in config.properties");
        }

        try {
            // Try to decrypt - if it's encrypted
            String decrypted = SecureConfig.decrypt(encryptedPassword);
            log.debug("Email app password decrypted successfully");
            return decrypted;
        } catch (Exception e) {
            // If decryption fails, assume it's plain text (for backward compatibility)
            log.warn("Email app password appears to be in plain text. Consider encrypting it.");
            return encryptedPassword;
        }
    }

    /**
     * Get base URL
     */
    public static String getBaseUrl() {
        return getProperty("base.url", "https://www.google.com");
    }

    /**
     * Check if browser should run in headless mode
     */
    public static boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("browser.headless", "false"));
    }

    /**
     * Get default timeout in milliseconds
     */
    public static int getDefaultTimeout() {
        return Integer.parseInt(getProperty("default.timeout", "30000"));
    }
}

