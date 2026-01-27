package com.websanity.utils;

import com.websanity.enums.Country;
import com.websanity.models.UserParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for generating Excel files for LFF (Load From File) tests
 */
@Slf4j
public class ExcelFileGenerator {

    private static final String FILE_PREFIX_LFF = "websanlff";
    private static final String FILE_PREFIX_DFF = "websandff";
    private static final String FILE_PREFIX_BA = "websanba";
    private static final String ENV_LFF_DIRECTORY = "LFF_DIRECTORY";
    private static final String LOCAL_LFF_DIRECTORY = "src/test/resources/lff";

    /**
     * Get LFF directory path based on environment
     * - In Docker: uses LFF_DIRECTORY environment variable
     * - Locally: uses src/test/resources/lff
     */
    private static String getOutputDirectory() {
        String envDir = System.getenv(ENV_LFF_DIRECTORY);

        if (envDir != null && !envDir.trim().isEmpty()) {
            log.info("Using LFF directory from environment: {}", envDir);
            return envDir;
        }

        log.debug("Using local LFF directory: {}", LOCAL_LFF_DIRECTORY);
        return LOCAL_LFF_DIRECTORY;
    }

    // Header columns for LFF Excel file
    private static final String[] HEADERS = {
            "Mobile Phone",
            "Email Address",
            "First Name",
            "Last Name",
            "Login",
            "Password",
            "Product",
            "Assign To Plan",
            "Send welcome message to Mobile",
            "Send welcome message to Email",
            "Language",
            "Billing Type",
            "Billing Reoccurring",
            "Company",
            "Time Zone",
            "Country",
            "App Text Support",
            "Voice Call Support",
            "Enterprise Setting",
            "Enterprise Number",
            "WhatsApp API",
            "Add To Global Address Book",
            "Unique Customer Code",
            "UDID",
            "Forward Inbox To",
            "Send Outgoing Messages Via Provider"
    };

    /**
     * Generate Excel file with user data
     * @param users list of UserParams objects
     * @return filename of the generated Excel file
     */
    public static String generateExcelFileForLFF(List<UserParams> users) {
        log.info("Generating Excel file for {} users", users.size());

        // Generate filename with timestamp
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String filename = FILE_PREFIX_LFF + timestamp + ".xls";
        String outputDir = getOutputDirectory();
        String fullPath = outputDir + "/" + filename;

        try {
            // Create directory if it doesn't exist
            Path dirPath = Paths.get(outputDir);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
                log.info("Created directory: {}", outputDir);
            }

            // Create workbook and sheet
            Workbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet("Users");

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < HEADERS.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(HEADERS[i]);
            }

            // Create data rows
            int rowNum = 1;
            for (UserParams user : users) {
                Row row = sheet.createRow(rowNum++);
                fillUserRow(row, user);
            }

            // Auto-size columns
            for (int i = 0; i < HEADERS.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write to file
            try (FileOutputStream fileOut = new FileOutputStream(fullPath)) {
                workbook.write(fileOut);
            }

            workbook.close();

            log.info("Excel file generated successfully: {}", filename);
            return filename;

        } catch (IOException e) {
            log.error("Error generating Excel file", e);
            throw new RuntimeException("Failed to generate Excel file: " + e.getMessage(), e);
        }
    }

    /**
     * Fill row with user data
     */
    private static void fillUserRow(Row row, UserParams user) {
        int colNum = 0;

        // Mobile Phone
        createCell(row, colNum++, formatMobilePhone(user));

        // Email Address
        createCell(row, colNum++, user.getEmail());

        // First Name
        createCell(row, colNum++, user.getFirstName());

        // Last Name
        createCell(row, colNum++, user.getLastName());

        // Login
        createCell(row, colNum++, user.getUsername());

        // Password
        createCell(row, colNum++, user.getPassword());

        // Product
        createCell(row, colNum++, user.getUserType() != null ? user.getUserType().getDisplayName() : "");

        // Assign To Plan
        createCell(row, colNum++, ""); // Empty by default

        // Send welcome message to Mobile
        createCell(row, colNum++, ""); // Empty by default

        // Send welcome message to Email
        createCell(row, colNum++, ""); // Empty by default

        // Language
        createCell(row, colNum++, user.getLanguage() != null ? user.getLanguage().name() : "");

        // Billing Type
        createCell(row, colNum++, ""); // Empty by default

        // Billing Reoccurring
        createCell(row, colNum++, ""); // Empty by default

        // Company
        createCell(row, colNum++, user.getCompany());

        // Time Zone
        createCell(row, colNum++, user.getTimeZone() != null ? user.getTimeZone().name() : "");

        // Country
        createCell(row, colNum++, user.getCountry() != null ? user.getCountry().getDisplayName() : "");

        // App Text Support
        createCell(row, colNum++, ""); // Empty by default

        // Voice Call Support
        createCell(row, colNum++, ""); // Empty by default

        // Enterprise Setting
        createCell(row, colNum++, ""); // Empty by default

        // Enterprise Number
        createCell(row, colNum++, ""); // Empty by default

        // WhatsApp API
        createCell(row, colNum++, ""); // Empty by default

        // Add To Global Address Book
        createCell(row, colNum++, ""); // Empty by default

        // Unique Customer Code
        createCell(row, colNum++, ""); // Empty by default

        // UDID
        createCell(row, colNum++, ""); // Empty by default

        // Forward Inbox To
        createCell(row, colNum++, ""); // Empty by default

        // Send Outgoing Messages Via Provider
        createCell(row, colNum++, ""); // Empty by default
    }

    /**
     * Format mobile phone with country code, area, and number (digits only, no dashes)
     */
    private static String formatMobilePhone(UserParams user) {
        if (user.getMobileCountryCode() == null || user.getMobileArea() == null || user.getMobilePhone() == null) {
            return "";
        }
        // Remove + and any other non-digit characters from dialing code, keep only digits
        String dialingCode = user.getMobileCountryCode().getDialingCode().replaceAll("[^0-9]", "");
        return dialingCode + user.getMobileArea() + user.getMobilePhone();
    }

    /**
     * Create cell with value
     */
    private static void createCell(Row row, int column, String value) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value != null ? value : "");
    }

    /**
     * Get full path to generated file
     */
    public static String getFullPath(String filename) {
        return getOutputDirectory() + "/" + filename;
    }

    /**
     * Clean up LFF directory - delete all .xls and .xlsx files
     * Use this in @BeforeAll to clean up old test files before generating new ones
     */
    public static void cleanupLFFDirectory() {
        String outputDir = getOutputDirectory();
        log.info("Cleaning up LFF directory: {}", outputDir);

        try {
            Path dirPath = Paths.get(outputDir);

            // Check if directory exists
            if (!Files.exists(dirPath)) {
                log.info("LFF directory does not exist, nothing to clean up");
                return;
            }

            // Delete all .xls and .xlsx files
            try (var stream = Files.list(dirPath)) {
                stream.filter(Files::isRegularFile)
                        .filter(path -> {
                            String fileName = path.getFileName().toString().toLowerCase();
                            return fileName.endsWith(".xls") || fileName.endsWith(".xlsx");
                        })
                        .forEach(path -> {
                            try {
                                Files.delete(path);
                                log.info("Deleted file: {}", path.getFileName());
                            } catch (IOException e) {
                                log.error("Failed to delete file: {}", path.getFileName(), e);
                            }
                        });
            }

            log.info("LFF directory cleanup completed");

        } catch (IOException e) {
            log.error("Error during LFF directory cleanup", e);
            throw new RuntimeException("Failed to cleanup LFF directory: " + e.getMessage(), e);
        }
    }

    /**
     * Generate Excel file with only Username column for DFF (Delete From File)
     * @param users list of UserParams objects (only username will be used)
     * @return filename of the generated Excel file
     */
    public static String generateExcelFileForDFF(List<UserParams> users) {
        log.info("Generating DFF Excel file for {} users (Username only)", users.size());

        // Generate filename with timestamp
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String filename = FILE_PREFIX_DFF + timestamp + ".xls";
        String outputDir = getOutputDirectory();
        String fullPath = outputDir + "/" + filename;

        try {
            // Create directory if it doesn't exist
            Path dirPath = Paths.get(outputDir);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
                log.info("Created directory: {}", outputDir);
            }

            // Create workbook and sheet
            Workbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet("Users");

            // Create header row with only "Username" column
            Row headerRow = sheet.createRow(0);
            Cell headerCell = headerRow.createCell(0);
            headerCell.setCellValue("Username");

            // Create data rows
            int rowNum = 1;
            for (UserParams user : users) {
                Row row = sheet.createRow(rowNum++);
                Cell cell = row.createCell(0);
                cell.setCellValue(user.getUsername());
            }

            // Auto-size column
            sheet.autoSizeColumn(0);

            // Write to file
            try (FileOutputStream fileOut = new FileOutputStream(fullPath)) {
                workbook.write(fileOut);
            }

            workbook.close();

            log.info("Delete From File Excel file generated successfully: {}", filename);
            return filename;

        } catch (IOException e) {
            log.error("Error generating Delete From File Excel file", e);
            throw new RuntimeException("Failed to generate DFF Excel file: " + e.getMessage(), e);
        }
    }

    /**
     * Generate Excel file for UFF (Update From File) with simplified headers
     * Headers: First Name, Last Name, Login, Password, Mobile Phone, Email Address, Unique Customer Code, UDID
     * @param users list of UserParams objects
     * @return filename of the generated Excel file
     */
    public static String generateExcelFileForBulkUpload(List<UserParams> users) {
        log.info("Generating UFF Excel file for {} users", users.size());

        // Headers for Update From File
        String[] uffHeaders = {
            "First Name",
            "Last Name",
            "Login",
            "Password",
            "Mobile Phone",
            "Email Address",
            "Unique Customer Code",
            "UDID"
        };

        // Generate filename with timestamp
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String filename = FILE_PREFIX_BA + timestamp + ".xls";
        String outputDir = getOutputDirectory();
        String fullPath = outputDir + "/" + filename;

        try {
            // Create directory if it doesn't exist
            Path dirPath = Paths.get(outputDir);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
                log.info("Created directory: {}", outputDir);
            }

            // Create workbook and sheet
            Workbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet("Users");

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < uffHeaders.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(uffHeaders[i]);
            }

            // Create data rows
            int rowNum = 1;
            for (UserParams user : users) {
                Row row = sheet.createRow(rowNum++);
                int colNum = 0;

                // First Name
                createCell(row, colNum++, user.getFirstName());

                // Last Name
                createCell(row, colNum++, user.getLastName());

                // Login
                createCell(row, colNum++, user.getUsername());

                // Password
                createCell(row, colNum++, user.getPassword());

                // Mobile Phone
                createCell(row, colNum++, formatMobilePhone(user));

                // Email Address
                createCell(row, colNum++, user.getEmail());

                // Unique Customer Code
                createCell(row, colNum++, user.getUniqueCustomerCode() != null ? user.getUniqueCustomerCode() : "");

                // UDID
                createCell(row, colNum++, user.getUdid() != null ? user.getUdid() : "");
            }

            // Auto-size columns
            for (int i = 0; i < uffHeaders.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write to file
            try (FileOutputStream fileOut = new FileOutputStream(fullPath)) {
                workbook.write(fileOut);
            }

            workbook.close();

            log.info("Update From File Excel file generated successfully: {}", filename);
            return filename;

        } catch (IOException e) {
            log.error("Error generating Update From File Excel file", e);
            throw new RuntimeException("Failed to generate UFF Excel file: " + e.getMessage(), e);
        }
    }

    /**
     * Generate Excel file for updating multiple users with all update parameters
     * @param usersToUpdate List of UserParams containing the usernames of the users to update
     * @param paramsToUpdate List of UserParams containing the new values to update (must match size of usersToUpdate)
     * @return filename of the generated Excel file
     */
    public static String generateExcelFileForUFF(List<UserParams> usersToUpdate, List<UserParams> paramsToUpdate) {
        if (usersToUpdate.size() != paramsToUpdate.size()) {
            throw new IllegalArgumentException("usersToUpdate and paramsToUpdate lists must have the same size");
        }

        log.info("Generating Excel file for updating {} users", usersToUpdate.size());

        // Headers for user update
        String[] updateHeaders = {
                "Login",
                "Mobile Phone",
                "Email Address",
                "First Name",
                "Last Name",
                "Password",
                "Product",
                "Assign To Plan",
                "Language",
                "Billing Type",
                "Billing Reoccurring",
                "Company",
                "Time Zone",
                "Country",
                "App Text Support",
                "Voice Call Support",
                "Enterprise Setting",
                "Enterprise Number",
                "WhatsApp API",
                "Add To Global Address Book",
                "Unique Customer Code",
                "UDID",
                "Forward Inbox To",
                "Send Outgoing Messages Via Provider"
        };

        // Generate filename with timestamp
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String filename = "websanupdate" + timestamp + ".xls";
        String outputDir = getOutputDirectory();
        String fullPath = outputDir + "/" + filename;

        try {
            // Create directory if it doesn't exist
            Path dirPath = Paths.get(outputDir);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
                log.info("Created directory: {}", outputDir);
            }

            // Create workbook and sheet
            Workbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet("Users");

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < updateHeaders.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(updateHeaders[i]);
            }

            // Create data rows for each user
            int rowNum = 1;
            for (int userIndex = 0; userIndex < usersToUpdate.size(); userIndex++) {
                UserParams userToUpdate = usersToUpdate.get(userIndex);
                UserParams paramsToUpdateForUser = paramsToUpdate.get(userIndex);

                Row row = sheet.createRow(rowNum++);
                int colNum = 0;

                // Login - from userToUpdate
                createCell(row, colNum++, userToUpdate.getUsername());

                // Mobile Phone - from paramsToUpdate
                createCell(row, colNum++, formatMobilePhone(paramsToUpdateForUser));

                // Email Address - from paramsToUpdate
                createCell(row, colNum++, paramsToUpdateForUser.getEmail());

                // First Name - from paramsToUpdate
                createCell(row, colNum++, paramsToUpdateForUser.getFirstName());

                // Last Name - from paramsToUpdate
                createCell(row, colNum++, paramsToUpdateForUser.getLastName());

                // Password - from paramsToUpdate
                createCell(row, colNum++, paramsToUpdateForUser.getPassword());

                // Product - from paramsToUpdate
                createCell(row, colNum++, paramsToUpdateForUser.getUserType() != null ? paramsToUpdateForUser.getUserType().getDisplayName() : "");

                // Assign To Plan - from paramsToUpdate
                createCell(row, colNum++, paramsToUpdateForUser.getAssignToPlan() != null ? paramsToUpdateForUser.getAssignToPlan() : "");

                // Language - from paramsToUpdate
                createCell(row, colNum++, paramsToUpdateForUser.getLanguage() != null ? paramsToUpdateForUser.getLanguage().name() : "");

                // Billing Type - from paramsToUpdate
                createCell(row, colNum++, paramsToUpdateForUser.getBillingType() != null ? paramsToUpdateForUser.getBillingType() : "");

                // Billing Reoccurring - from paramsToUpdate
                createCell(row, colNum++, paramsToUpdateForUser.getBillingReoccurring() != null ? paramsToUpdateForUser.getBillingReoccurring() : "");

                // Company - from paramsToUpdate
                createCell(row, colNum++, paramsToUpdateForUser.getCompany());

                // Time Zone - from paramsToUpdate
                createCell(row, colNum++, paramsToUpdateForUser.getTimeZone() != null ? paramsToUpdateForUser.getTimeZone().name() : "");

                // Country - from paramsToUpdate
                createCell(row, colNum++, paramsToUpdateForUser.getCountry() != null ? paramsToUpdateForUser.getCountry().getDisplayName() : "");

                // App Text Support - from paramsToUpdate
                createCell(row, colNum++, paramsToUpdateForUser.getAppTextSupport() != null ? paramsToUpdateForUser.getAppTextSupport() : "");

                // Voice Call Support - from paramsToUpdate
                createCell(row, colNum++, paramsToUpdateForUser.getVoiceCallSupport() != null ? paramsToUpdateForUser.getVoiceCallSupport() : "");

                // Enterprise Setting - from paramsToUpdate
                createCell(row, colNum++, paramsToUpdateForUser.getEnterpriseSetting() != null ? paramsToUpdateForUser.getEnterpriseSetting() : "");

                // Enterprise Number - from paramsToUpdate
                createCell(row, colNum++, paramsToUpdateForUser.getEnterpriseNumber() != null ? paramsToUpdateForUser.getEnterpriseNumber() : "");

                // WhatsApp API - from paramsToUpdate
                createCell(row, colNum++, paramsToUpdateForUser.getWhatsAppApi() != null ? paramsToUpdateForUser.getWhatsAppApi() : "");

                // Add To Global Address Book - from paramsToUpdate
                createCell(row, colNum++, paramsToUpdateForUser.getAddToGlobalAddressBook() != null ? paramsToUpdateForUser.getAddToGlobalAddressBook().toString() : "");

                // Unique Customer Code - from paramsToUpdate
                createCell(row, colNum++, paramsToUpdateForUser.getUniqueCustomerCode() != null ? paramsToUpdateForUser.getUniqueCustomerCode() : "");

                // UDID - from paramsToUpdate
                createCell(row, colNum++, paramsToUpdateForUser.getUdid() != null ? paramsToUpdateForUser.getUdid() : "");

                // Forward Inbox To - from paramsToUpdate
                createCell(row, colNum++, paramsToUpdateForUser.getForwardInboxTo() != null ? paramsToUpdateForUser.getForwardInboxTo() : "");

                // Send Outgoing Messages Via Provider - from paramsToUpdate
                createCell(row, colNum++, paramsToUpdateForUser.getSendOutgoingMessagesViaProvider() != null ? paramsToUpdateForUser.getSendOutgoingMessagesViaProvider() : "");
            }

            // Auto-size columns
            for (int i = 0; i < updateHeaders.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Write to file
            try (FileOutputStream fileOut = new FileOutputStream(fullPath)) {
                workbook.write(fileOut);
            }

            workbook.close();

            log.info("User update Excel file generated successfully: {}", filename);
            return filename;

        } catch (IOException e) {
            log.error("Error generating user update Excel file", e);
            throw new RuntimeException("Failed to generate user update Excel file: " + e.getMessage(), e);
        }
    }

    /**
     * Create sample users for testing
     */
    public static List<UserParams> createSampleUsers(int count) {
        List<UserParams> users = new ArrayList<>();
        String timestamp = String.valueOf(System.currentTimeMillis() % 10000000);

        for (int i = 1; i <= count; i++) {
            UserParams user = UserParams.builder()
                    .username("websanlffus" + timestamp + i)
                    .password("Aa123123123123123")
                    .email("websanlffus" + timestamp + i + "@gmail.com")
                    .firstName("websanlffusfn" + i)
                    .lastName("websanlffusln" + i)
                    .mobileCountryCode(Country.ISRAEL)
                    .mobileArea("58")
                    .mobilePhone(String.format("5%06d", Integer.parseInt(timestamp.substring(1)) + i))
                    .build();

            users.add(user);
            log.info("Created sample user #{}: {}", i, user.getUsername());
        }

        return users;
    }
}
