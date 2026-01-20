package com.websanity.utils;

import com.websanity.enums.Country;
import com.websanity.enums.Language;
import com.websanity.enums.TimeZone;
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

            // Create header style
            CellStyle headerStyle = createHeaderStyle(workbook);

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < HEADERS.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(HEADERS[i]);
                cell.setCellStyle(headerStyle);
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
     * Create header style
     */
    private static CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 11);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
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

            // Create header style
            CellStyle headerStyle = createHeaderStyle(workbook);

            // Create header row with only "Username" column
            Row headerRow = sheet.createRow(0);
            Cell headerCell = headerRow.createCell(0);
            headerCell.setCellValue("Username");
            headerCell.setCellStyle(headerStyle);

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
