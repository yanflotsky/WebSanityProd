package com.websanity.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * Secure configuration utility for encrypting/decrypting sensitive data
 * Uses AES-256 encryption based on machine-specific identifier
 */
public class SecureConfig {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

    /**
     * Generate encryption key based on machine-specific identifiers
     * This ensures decryption works only on the same machine
     */
    private static SecretKey generateKey() {
        try {
            String username = System.getProperty("user.name");
            String computername = System.getenv("COMPUTERNAME");

            if (username == null || computername == null) {
                throw new IllegalStateException("Cannot determine machine identity");
            }

            String machineId = username + "@" + computername;

            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] keyBytes = sha.digest(machineId.getBytes(StandardCharsets.UTF_8));

            return new SecretKeySpec(keyBytes, ALGORITHM);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate encryption key", e);
        }
    }

    /**
     * Encrypt a plain text string
     * @param plainText The text to encrypt
     * @return Base64 encoded encrypted string
     */
    public static String encrypt(String plainText) {
        try {
            SecretKey key = generateKey();
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed", e);
        }
    }

    /**
     * Decrypt an encrypted string
     * @param encryptedText Base64 encoded encrypted string
     * @return Decrypted plain text
     */
    public static String decrypt(String encryptedText) {
        try {
            SecretKey key = generateKey();
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Decryption failed - wrong machine or corrupted data", e);
        }
    }

    /**
     * Get current machine identifier (for debugging)
     */
    public static String getMachineId() {
        String username = System.getProperty("user.name");
        String computername = System.getenv("COMPUTERNAME");
        return username + "@" + computername;
    }
}

