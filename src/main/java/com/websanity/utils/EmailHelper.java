package com.websanity.utils;

import lombok.extern.slf4j.Slf4j;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper class for working with Gmail to retrieve MFA codes from emails
 */
@Slf4j
public class EmailHelper {

    private static final String IMAP_HOST = "imap.gmail.com";
    private static final String IMAP_PORT = "993";
    private static final int MAX_RETRIES = 30;
    private static final int RETRY_DELAY_MS = 2000;

    /**
     * Deletes ALL emails from Gmail inbox (including all conversation threads)
     * This ensures completely clean inbox before test to guarantee finding only new email
     * Verifies deletion and retries if needed
     *
     * @param email       Gmail email address
     * @param appPassword Gmail App Password
     * @return Number of emails deleted
     * @throws RuntimeException if connection fails
     */
    public static int deleteAllEmails(String email, String appPassword) {
        log.info("🗑️ Deleting ALL emails from inbox (including all conversation threads)...");

        try {
            Properties props = new Properties();
            props.put("mail.store.protocol", "imaps");
            props.put("mail.imaps.host", IMAP_HOST);
            props.put("mail.imaps.port", IMAP_PORT);
            props.put("mail.imaps.ssl.enable", "true");
            props.put("mail.imaps.timeout", "10000");
            props.put("mail.imaps.connectiontimeout", "10000");

            Session session = Session.getInstance(props);
            Store store = session.getStore("imaps");

            try {
                store.connect(IMAP_HOST, email, appPassword);
                log.debug("✅ Connected to Gmail for cleanup");
            } catch (AuthenticationFailedException e) {
                log.error("❌ Authentication failed during cleanup");
                throw new RuntimeException("Gmail authentication failed during cleanup", e);
            }

            int totalDeleted = 0;
            int maxAttempts = 3; // Try up to 3 times to ensure complete deletion

            for (int attempt = 1; attempt <= maxAttempts; attempt++) {
                Folder inbox = store.getFolder("INBOX");
                inbox.open(Folder.READ_WRITE); // Open in READ_WRITE mode to delete messages

                int currentMessages = inbox.getMessageCount();

                if (attempt == 1) {
                    log.info("📬 Total messages in inbox: {}", currentMessages);
                } else {
                    log.info("📬 Attempt {}/{}: Still {} message(s) remaining", attempt, maxAttempts, currentMessages);
                }

                if (currentMessages == 0) {
                    log.info("✅ Inbox is empty");
                    inbox.close(false);
                    break; // Success!
                }

                // Get ALL messages
                Message[] allMessages = inbox.getMessages();
                log.info("📋 Marking ALL {} message(s) for deletion (attempt {}/{})...", allMessages.length, attempt, maxAttempts);

                // Mark ALL messages for deletion (no filtering)
                int markedCount = 0;
                for (int i = 0; i < allMessages.length; i++) {
                    try {
                        allMessages[i].setFlag(Flags.Flag.DELETED, true);
                        markedCount++;
                        if ((i + 1) % 10 == 0 || i == allMessages.length - 1) {
                            log.debug("🗑️ Marked {}/{} messages for deletion", i + 1, allMessages.length);
                        }
                    } catch (Exception e) {
                        log.debug("⚠️  Error marking message {} for deletion: {}", i + 1, e.getMessage());
                    }
                }

                log.info("📊 Marked {} message(s) for deletion", markedCount);

                // Close folder with expunge=true to permanently delete marked messages
                log.debug("🗑️ Executing expunge to permanently delete messages...");
                inbox.close(true);

                totalDeleted += markedCount;

                // Wait a bit for Gmail to process the deletion
                if (attempt < maxAttempts) {
                    log.debug("⏳ Waiting 1 second for Gmail to process deletion...");
                    Thread.sleep(1000);
                }
            }

            // Final verification
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            int finalCount = inbox.getMessageCount();
            inbox.close(false);
            store.close();

            if (finalCount == 0) {
                log.info("✅ Successfully deleted ALL {} email(s) - inbox is now EMPTY (verified)", totalDeleted);
            } else {
                log.warn("⚠️ Deleted {} email(s) but {} message(s) still remain in inbox", totalDeleted, finalCount);
                log.warn("⚠️ This may be due to Gmail sync delay or conversation threading");
            }

            return totalDeleted;

        } catch (Exception e) {
            log.error("❌ Error deleting emails", e);
            throw new RuntimeException("Failed to delete emails: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves MFA code from the most recent email received AFTER the given timestamp
     * Waits for the email to arrive (checks in a loop)
     * This approach doesn't require deleting emails - just finds the freshest one
     *
     * @param email          Gmail email address
     * @param appPassword    Gmail App Password
     * @param afterTimestamp Only search for emails received after this time
     * @return 6-digit MFA code
     * @throws RuntimeException if MFA code is not found or connection fails
     */
    public static String getMfaCodeAfterTimestamp(String email, String appPassword, Date afterTimestamp) {
        try {
            Properties props = new Properties();
            props.put("mail.store.protocol", "imaps");
            props.put("mail.imaps.host", IMAP_HOST);
            props.put("mail.imaps.port", IMAP_PORT);
            props.put("mail.imaps.ssl.enable", "true");
            props.put("mail.imaps.timeout", "10000");
            props.put("mail.imaps.connectiontimeout", "10000");

            Session session = Session.getInstance(props);
            Store store = session.getStore("imaps");

            try {
                store.connect(IMAP_HOST, email, appPassword);
            } catch (AuthenticationFailedException e) {
                log.error("❌ Gmail authentication failed");
                throw new RuntimeException("Gmail authentication failed", e);
            } catch (MessagingException e) {
                log.error("❌ Connection failed: {}", e.getMessage());
                throw new RuntimeException("Failed to connect to Gmail: " + e.getMessage(), e);
            }

            Folder inbox = store.getFolder("INBOX");

            // Try multiple times to find the email (it may take a few seconds to arrive)
            for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
                inbox.open(Folder.READ_ONLY);

                int totalMessages = inbox.getMessageCount();
                log.info("🔍 Attempt {}/{} - Total messages in inbox: {}", attempt, MAX_RETRIES, totalMessages);

                if (totalMessages == 0) {
                    log.debug("⏳ No messages in inbox, waiting...");
                    inbox.close(false);
                    if (attempt < MAX_RETRIES) {
                        Thread.sleep(RETRY_DELAY_MS);
                    }
                    continue;
                }

                // Get the most recent 20 messages
                int start = Math.max(1, totalMessages - 19);
                Message[] recentMessages = inbox.getMessages(start, totalMessages);
                log.debug("📨 Checking last {} message(s) (from position {} to {})", recentMessages.length, start, totalMessages);

                // Sort by received date (newest first)
                Arrays.sort(recentMessages, new Comparator<Message>() {
                    @Override
                    public int compare(Message m1, Message m2) {
                        try {
                            Date d1 = m1.getReceivedDate();
                            Date d2 = m2.getReceivedDate();
                            if (d1 == null || d2 == null) return 0;
                            return d2.compareTo(d1);
                        } catch (MessagingException e) {
                            return 0;
                        }
                    }
                });

                log.debug("🔎 Looking for emails received AFTER: {}", afterTimestamp);

                int checkedCount = 0;
                int skippedOldCount = 0;

                // Check messages starting from the newest
                for (Message message : recentMessages) {
                    try {
                        Date receivedDate = message.getReceivedDate();
                        String subject = message.getSubject();

                        checkedCount++;

                        // Skip emails received BEFORE our timestamp
                        if (receivedDate == null || receivedDate.before(afterTimestamp)) {
                            skippedOldCount++;
                            log.debug("  ⏭️  Skipping OLD email: '{}' (received: {})", subject, receivedDate);
                            continue;
                        }

                        // This email was received AFTER our timestamp
                        long secondsAfter = (receivedDate.getTime() - afterTimestamp.getTime()) / 1000;
                        log.info("  ✅ Found NEW email: '{}' (received: {} - {} seconds after timestamp)",
                                subject, receivedDate, secondsAfter);

                        String content = getTextFromMessage(message);
                        String mfaCode = extractMfaCode(content);

                        if (mfaCode != null) {
                            log.info("🎉 MFA code found: {}", mfaCode);
                            inbox.close(false);
                            store.close();
                            return mfaCode;
                        } else {
                            log.warn("  ⚠️  Email found but no MFA code extracted. Subject: {}", subject);
                            log.debug("Content preview: {}", content.substring(0, Math.min(300, content.length())));
                        }
                    } catch (Exception e) {
                        log.debug("Error processing message: {}", e.getMessage());
                    }
                }

                log.info("📊 Summary: Checked {} message(s), skipped {} old message(s), found 0 new messages with MFA code",
                        checkedCount, skippedOldCount);

                inbox.close(false);

                if (attempt < MAX_RETRIES) {
                    log.debug("⏳ Waiting {} ms before next attempt...", RETRY_DELAY_MS);
                    Thread.sleep(RETRY_DELAY_MS);
                }
            }

            store.close();
            throw new RuntimeException("MFA email not found after " + MAX_RETRIES + " attempts");

        } catch (Exception e) {
            log.error("❌ Failed to get MFA code from email");
            throw new RuntimeException("Failed to get MFA code from email: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves MFA code from the latest email in Gmail inbox
     * Searches for emails received in the last 5 minutes and returns code from the most recent one
     *
     * @param email       Gmail email address
     * @param appPassword Gmail App Password (16 characters without spaces)
     * @return 6-digit MFA code
     * @throws RuntimeException if MFA code is not found or connection fails
     */
    public static String getMfaCode(String email, String appPassword) {
        log.info("🔐 Attempting to retrieve MFA code from email: {}", email);
        log.debug("App password length: {}", appPassword != null ? appPassword.length() : 0);

        try {
            Properties props = new Properties();
            props.put("mail.store.protocol", "imaps");
            props.put("mail.imaps.host", IMAP_HOST);
            props.put("mail.imaps.port", IMAP_PORT);
            props.put("mail.imaps.ssl.enable", "true");
            props.put("mail.imaps.timeout", "10000");
            props.put("mail.imaps.connectiontimeout", "10000");

            Session session = Session.getInstance(props);
            Store store = session.getStore("imaps");

            log.debug("Connecting to Gmail IMAP server ({})...", IMAP_HOST);
            try {
                store.connect(IMAP_HOST, email, appPassword);
                log.info("✅ Successfully connected to Gmail");
            } catch (AuthenticationFailedException e) {
                log.error("❌ Authentication failed - check email and app password");
                throw new RuntimeException("Gmail authentication failed. Verify email and app password.", e);
            } catch (MessagingException e) {
                log.error("❌ Connection failed: {}", e.getMessage());
                throw new RuntimeException("Failed to connect to Gmail: " + e.getMessage(), e);
            }

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            // Inbox should be EMPTY after cleanup - waiting for FIRST message to arrive
            int initialMessageCount = inbox.getMessageCount();
            if (initialMessageCount == 0) {
                log.info("📬 Inbox is EMPTY (as expected after cleanup). Waiting for MFA email to arrive...");
            } else {
                log.warn("⚠️ Warning: Inbox not empty! Found {} message(s). Expected 0 after cleanup.", initialMessageCount);
            }

            // Wait for message to arrive (count should go from 0 to 1+)
            for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
                log.info("🔍 Attempt {}/{} - waiting for email to arrive", attempt, MAX_RETRIES);

                // Refresh inbox to get updated message count
                inbox.close(false);
                inbox.open(Folder.READ_ONLY);

                int currentMessageCount = inbox.getMessageCount();
                log.debug("📊 Current message count: {}", currentMessageCount);

                // Check if message arrived
                if (currentMessageCount > 0) {
                    log.info("✅ Message arrived! Inbox now has {} message(s)", currentMessageCount);

                    // Get the latest message (should be the MFA email we're waiting for)
                    Message latestMessage = inbox.getMessage(currentMessageCount);

                    String subject = latestMessage.getSubject();
                    Date receivedDate = latestMessage.getReceivedDate();
                    log.info("📨 Checking message - Subject: '{}', Received: {}", subject, receivedDate);

                    // Verify it's an MFA email
                    if (subject != null && !containsMfaKeywords(subject)) {
                        log.warn("⚠️ Warning: Message doesn't look like MFA email, but checking anyway...");
                    }

                    String content = getTextFromMessage(latestMessage);
                    log.debug("Email content preview (first 200 chars): {}",
                            content.length() > 200 ? content.substring(0, 200) + "..." : content);

                    String mfaCode = extractMfaCode(content);

                    if (mfaCode != null) {
                        log.info("✅ MFA code found: {} (from message received at: {})", mfaCode, receivedDate);
                        inbox.close(false);
                        store.close();
                        return mfaCode;
                    } else {
                        log.error("❌ Message arrived but contains no MFA code!");
                        log.error("Subject: {}", subject);
                        log.error("Content preview: {}", content.substring(0, Math.min(500, content.length())));
                        throw new RuntimeException("Message arrived but contains no valid MFA code");
                    }
                } else {
                    log.debug("⏳ No messages yet (attempt {}/{})", attempt, MAX_RETRIES);
                }

                if (attempt < MAX_RETRIES) {
                    log.debug("Waiting {} ms before next attempt...", RETRY_DELAY_MS);
                    Thread.sleep(RETRY_DELAY_MS);
                }
            }

            inbox.close(false);
            store.close();

            throw new RuntimeException("MFA email did not arrive after " + MAX_RETRIES + " attempts (" + (MAX_RETRIES * RETRY_DELAY_MS / 1000) + " seconds)");

        } catch (Exception e) {
            log.error("❌ Error retrieving MFA code from email", e);
            throw new RuntimeException("Failed to get MFA code from email: " + e.getMessage(), e);
        }
    }

    /**
     * Extracts text content from email message
     */
    private static String getTextFromMessage(Message message) throws MessagingException, IOException {
        String result = "";

        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("text/html")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }

        return result;
    }

    /**
     * Extracts text from multipart email content
     */
    private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
        StringBuilder result = new StringBuilder();
        int count = mimeMultipart.getCount();

        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);

            if (bodyPart.isMimeType("text/plain")) {
                result.append(bodyPart.getContent());
                break; // We prefer plain text
            } else if (bodyPart.isMimeType("text/html")) {
                result.append(bodyPart.getContent());
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                result.append(getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent()));
            }
        }

        return result.toString();
    }

    /**
     * Extracts 6-digit MFA code from email text content
     *
     * @param text Email content
     * @return 6-digit code or null if not found
     */
    private static String extractMfaCode(String text) {
        if (text == null || text.isEmpty()) {
            log.debug("Email content is empty or null");
            return null;
        }

        // Try multiple patterns to find the code

        // Pattern 1: Standard 6-digit code with word boundaries
        Pattern pattern1 = Pattern.compile("\\b\\d{6}\\b");
        Matcher matcher1 = pattern1.matcher(text);
        if (matcher1.find()) {
            String code = matcher1.group();
            log.debug("✓ Found 6-digit code (pattern 1): {}", code);
            return code;
        }

        // Pattern 2: Code with spaces (e.g., "1 2 3 4 5 6" or "12 34 56")
        Pattern pattern2 = Pattern.compile("(\\d\\s*){6}");
        Matcher matcher2 = pattern2.matcher(text);
        if (matcher2.find()) {
            String codeWithSpaces = matcher2.group();
            String code = codeWithSpaces.replaceAll("\\s+", "");
            if (code.length() == 6) {
                log.debug("✓ Found 6-digit code with spaces (pattern 2): {}", code);
                return code;
            }
        }

        // Pattern 3: Code after common phrases
        Pattern pattern3 = Pattern.compile("(?:code|verification|OTP|token)[:\\s-]*([\\d\\s]{6,})", Pattern.CASE_INSENSITIVE);
        Matcher matcher3 = pattern3.matcher(text);
        if (matcher3.find()) {
            String codeWithSpaces = matcher3.group(1);
            String code = codeWithSpaces.replaceAll("\\s+", "").substring(0, Math.min(6, codeWithSpaces.replaceAll("\\s+", "").length()));
            if (code.length() == 6) {
                log.debug("✓ Found 6-digit code after keyword (pattern 3): {}", code);
                return code;
            }
        }

        log.debug("❌ No 6-digit code found in email content (length: {} chars)", text.length());
        log.debug("Email content sample: {}", text.substring(0, Math.min(500, text.length())));
        return null;
    }

    /**
     * Waits for MFA code to arrive in email with timeout
     *
     * @param email       Gmail email address
     * @param appPassword Gmail App Password
     * @param timeoutSec  Timeout in seconds
     * @return 6-digit MFA code
     */
    public static String waitForMfaCode(String email, String appPassword, int timeoutSec) {
        log.info("Waiting for MFA code (timeout: {} seconds)", timeoutSec);
        long startTime = System.currentTimeMillis();
        long timeoutMs = timeoutSec * 1000L;

        while ((System.currentTimeMillis() - startTime) < timeoutMs) {
            try {
                return getMfaCode(email, appPassword);
            } catch (Exception e) {
                log.debug("MFA code not found yet, retrying...");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Interrupted while waiting for MFA code", ie);
                }
            }
        }

        throw new RuntimeException("Timeout: MFA code not received within " + timeoutSec + " seconds");
    }

    /**
     * Retrieves MFA code from the most recent emails, regardless of time
     * Useful for testing or when you know an email exists but might be older
     *
     * @param email       Gmail email address
     * @param appPassword Gmail App Password
     * @param maxEmails   Maximum number of recent emails to check
     * @return 6-digit MFA code
     * @throws RuntimeException if MFA code is not found
     */
    public static String getMfaCodeFromRecentEmails(String email, String appPassword, int maxEmails) {
        log.info("🔐 Attempting to retrieve MFA code from last {} emails", maxEmails);

        try {
            Properties props = new Properties();
            props.put("mail.store.protocol", "imaps");
            props.put("mail.imaps.host", IMAP_HOST);
            props.put("mail.imaps.port", IMAP_PORT);
            props.put("mail.imaps.ssl.enable", "true");
            props.put("mail.imaps.timeout", "10000");
            props.put("mail.imaps.connectiontimeout", "10000");

            Session session = Session.getInstance(props);
            Store store = session.getStore("imaps");

            log.debug("Connecting to Gmail IMAP server ({})...", IMAP_HOST);
            try {
                store.connect(IMAP_HOST, email, appPassword);
                log.info("✅ Successfully connected to Gmail");
            } catch (AuthenticationFailedException e) {
                log.error("❌ Authentication failed - check email and app password");
                throw new RuntimeException("Gmail authentication failed. Verify email and app password.", e);
            } catch (MessagingException e) {
                log.error("❌ Connection failed: {}", e.getMessage());
                throw new RuntimeException("Failed to connect to Gmail: " + e.getMessage(), e);
            }

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            int totalMessages = inbox.getMessageCount();
            log.info("📬 Total messages in inbox: {}", totalMessages);

            if (totalMessages == 0) {
                inbox.close(false);
                store.close();
                throw new RuntimeException("No messages found in inbox");
            }

            // Get the most recent N messages
            int start = Math.max(1, totalMessages - maxEmails + 1);
            Message[] recentMessages = inbox.getMessages(start, totalMessages);
            log.info("📧 Checking {} most recent message(s)", recentMessages.length);

            // Sort messages by received date (newest first)
            Arrays.sort(recentMessages, new Comparator<Message>() {
                @Override
                public int compare(Message m1, Message m2) {
                    try {
                        Date d1 = m1.getReceivedDate();
                        Date d2 = m2.getReceivedDate();
                        if (d1 == null || d2 == null) return 0;
                        return d2.compareTo(d1); // Descending order (newest first)
                    } catch (MessagingException e) {
                        return 0;
                    }
                }
            });

            // Check each message for MFA code
            for (int i = 0; i < recentMessages.length; i++) {
                Message message = recentMessages[i];
                try {
                    String subject = message.getSubject();
                    Date receivedDate = message.getReceivedDate();

                    log.info("📨 [{}] Checking message - Subject: '{}', Received: {}", i + 1, subject, receivedDate);

                    // Filter by subject keywords (optional)
                    if (subject != null && !containsMfaKeywords(subject)) {
                        log.debug("⏭️  Skipping - subject doesn't contain MFA keywords");
                        continue;
                    }

                    String content = getTextFromMessage(message);
                    log.debug("Email content preview (first 200 chars): {}",
                            content.length() > 200 ? content.substring(0, 200) + "..." : content);

                    String mfaCode = extractMfaCode(content);

                    if (mfaCode != null) {
                        log.info("✅ MFA code found: {} (from message received at: {})", mfaCode, receivedDate);
                        inbox.close(false);
                        store.close();
                        return mfaCode;
                    } else {
                        log.warn("⚠️ No MFA code found in this message");
                    }
                } catch (Exception e) {
                    log.debug("Error processing message: {}", e.getMessage());
                }
            }

            inbox.close(false);
            store.close();

            throw new RuntimeException("MFA code not found in the last " + maxEmails + " emails");

        } catch (Exception e) {
            log.error("❌ Error retrieving MFA code from recent emails", e);
            throw new RuntimeException("Failed to get MFA code from recent emails: " + e.getMessage(), e);
        }
    }

    /**
     * Check if email subject contains MFA-related keywords
     * This helps filter out irrelevant emails
     */
    private static boolean containsMfaKeywords(String subject) {
        if (subject == null) {
            return false;
        }

        String subjectLower = subject.toLowerCase();
        String[] keywords = {
            "verification", "verify", "code", "otp", "authenticate",
            "security", "confirm", "two-factor", "2fa", "mfa",
            "login", "sign in", "signin", "access"
        };

        for (String keyword : keywords) {
            if (subjectLower.contains(keyword)) {
                log.debug("✓ Subject contains MFA keyword: '{}'", keyword);
                return true;
            }
        }

        return false;
    }
}


