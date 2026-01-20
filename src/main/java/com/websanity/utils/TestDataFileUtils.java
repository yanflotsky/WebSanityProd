package com.websanity.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Utility class for handling test data file paths
 */
@Slf4j
public class TestDataFileUtils {

    private static final String TEST_RESOURCES_PATH = "src/test/resources";
    private static final String LFF_FOLDER = "lff";

    /**
     * Get absolute path to LFF test data file
     * Works both locally and in Docker
     *
     * @param fileName name of the file (e.g., "signup-users.xlsx")
     * @return absolute path to the file
     */
    public static String getLFFFilePath(String fileName) {
        String relativePath = String.format("%s/%s/%s", TEST_RESOURCES_PATH, LFF_FOLDER, fileName);
        Path path = Paths.get(relativePath);

        if (!Files.exists(path)) {
            log.warn("File not found at relative path: {}", relativePath);

            // Try absolute path from project root
            String projectRoot = System.getProperty("user.dir");
            String absolutePath = projectRoot + File.separator + relativePath.replace("/", File.separator);
            path = Paths.get(absolutePath);

            if (!Files.exists(path)) {
                String errorMsg = String.format("File not found: %s (tried both relative and absolute paths)", fileName);
                log.error(errorMsg);
                throw new RuntimeException(errorMsg);
            }
        }

        String absolutePath = path.toAbsolutePath().toString();
        log.info("Using LFF file: {}", absolutePath);
        return absolutePath;
    }

    /**
     * Get path to signup users file
     *
     * @return path to signup-users.xlsx
     */
    public static String getSignUpUsersFile() {
        return getLFFFilePath("websanlff.xls");
    }

    /**
     * Get path to update users file
     *
     * @return path to update-users.xlsx
     */
    public static String getUpdateUsersFile() {
        return getLFFFilePath("update-users.xlsx");
    }

    /**
     * Get path to delete users file
     *
     * @return path to delete-users.xlsx
     */
    public static String getDeleteUsersFile() {
        return getLFFFilePath("websandff.xls");
    }

    /**
     * Check if LFF file exists
     *
     * @param fileName name of the file
     * @return true if file exists
     */
    public static boolean isLFFFileExists(String fileName) {
        try {
            String relativePath = String.format("%s/%s/%s", TEST_RESOURCES_PATH, LFF_FOLDER, fileName);
            return Files.exists(Paths.get(relativePath));
        } catch (Exception e) {
            log.error("Error checking file existence: {}", fileName, e);
            return false;
        }
    }

    /**
     * Get all available LFF files
     *
     * @return array of file names
     */
    public static String[] getAvailableLFFFiles() {
        String dirPath = String.format("%s/%s", TEST_RESOURCES_PATH, LFF_FOLDER);
        File dir = new File(dirPath);

        if (!dir.exists() || !dir.isDirectory()) {
            log.warn("LFF directory not found: {}", dirPath);
            return new String[0];
        }

        File[] files = dir.listFiles((d, name) ->
            name.endsWith(".xlsx") || name.endsWith(".xls") || name.endsWith(".csv"));

        if (files == null || files.length == 0) {
            log.warn("No LFF files found in: {}", dirPath);
            return new String[0];
        }

        String[] fileNames = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            fileNames[i] = files[i].getName();
        }

        return fileNames;
    }
}
