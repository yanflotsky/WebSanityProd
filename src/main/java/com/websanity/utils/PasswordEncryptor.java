package com.websanity.utils;

/**
 * Helper utility to generate encrypted passwords
 * Run this once to encrypt your password, then use the output in pom.xml
 */
public class PasswordEncryptor {

    public static void main(String[] args) {
        // Your plain text password
        String plainPassword = "Aa123456";

        // Encrypt it
        String encryptedPassword = SecureConfig.encrypt(plainPassword);

        // Display results
        System.out.println("=".repeat(60));
        System.out.println("PASSWORD ENCRYPTION UTILITY");
        System.out.println("=".repeat(60));
        System.out.println("Machine ID: " + SecureConfig.getMachineId());
        System.out.println("Plain text: " + plainPassword);
        System.out.println("Encrypted:  " + encryptedPassword);
        System.out.println("=".repeat(60));
        System.out.println("\nAdd this to your pom.xml:");
        System.out.println("<teleadmin.password>" + encryptedPassword + "</teleadmin.password>");
        System.out.println("=".repeat(60));

        // Verify decryption works
        try {
            String decrypted = SecureConfig.decrypt(encryptedPassword);
            System.out.println("\n✓ Verification successful!");
            System.out.println("Decrypted: " + decrypted);
            System.out.println("Match: " + decrypted.equals(plainPassword));
        } catch (Exception e) {
            System.out.println("\n✗ Verification failed: " + e.getMessage());
        }
    }
}

