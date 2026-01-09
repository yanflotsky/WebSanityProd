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
     * @param encryptedText Base64 encoded encrypted string (or path to secret file in Docker)
     * @return Decrypted plain text
     */
    public static String decrypt(String encryptedText) {
        return decryptSecret(encryptedText, "teleadmin_password");
    }

    /**
     * Decrypt a secret with a specific secret name
     * @param encryptedText Base64 encoded encrypted string
     * @param secretName Name of the Docker secret file (e.g., "gmail_password")
     * @return Decrypted plain text
     */
    public static String decryptSecret(String encryptedText, String secretName) {
        // Check if running in Docker with secrets
        String useDockerSecrets = System.getenv("USE_DOCKER_SECRETS");
        if ("true".equals(useDockerSecrets)) {
            // Read password from Docker secret file
            try {
                String secretPath = "/run/secrets/" + secretName;
                java.nio.file.Path path = java.nio.file.Paths.get(secretPath);
                if (java.nio.file.Files.exists(path)) {
                    String password = new String(java.nio.file.Files.readAllBytes(path), StandardCharsets.UTF_8).trim();
                    System.out.println("Docker Secrets mode: " + secretName + " loaded from " + secretPath);
                    return password;
                } else {
                    System.err.println("Docker secret file not found: " + secretPath);
                }
            } catch (Exception e) {
                System.err.println("Failed to read Docker secret: " + e.getMessage());
            }
        }

        // Check if running in Docker without secrets (fallback)
        String computername = System.getenv("COMPUTERNAME");
        if (computername == null && !"true".equals(useDockerSecrets)) {
            // Running in Docker/Linux without secrets - try to use password as-is
            System.out.println("Docker mode detected (no secrets) - using password as-is");
            return encryptedText;
        }

        // Windows mode - decrypt as usual
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

