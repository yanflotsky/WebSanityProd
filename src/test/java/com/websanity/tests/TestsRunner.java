package com.websanity.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;

/**
 * Test Suite Runner for parallel execution in Docker or local mode.
 *
 * Execution mode is controlled by isLocalRun property in pom.xml:
 * - isLocalRun=true  → Tests run locally (sequential)
 * - isLocalRun=false → Tests run in Docker (parallel containers)
 *
 * Usage: Run this class as Java Application (not as JUnit test!)
 *        Right-click → Run 'TestsRunner.main()'
 */
public class TestsRunner {

    // ════════════════════════════════════════════════════════════════════════════
    // 📝 CENTRALIZED TEST CONFIGURATION - Add your test classes here!
    // ════════════════════════════════════════════════════════════════════════════
    // Format: "TestClassName:docker-service-name:allure-results-folder"
    private static final String[] TEST_CLASSES = {
        "AdminPortalSanityTest:admin-portal-tests:allure-results-admin",
        "TeleadminSanityTest:teleadmin-tests:allure-results-teleadmin"
        // Add more test classes here:
        // "NewTestClass:new-service-name:allure-results-new"
    };
    // ════════════════════════════════════════════════════════════════════════════

    /**
     * Get comma-separated list of test class names for Maven
     * Example: "AdminPortalSanityTest,TeleadminSanityTest"
     */
    private static String getTestClassNames() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < TEST_CLASSES.length; i++) {
            String className = TEST_CLASSES[i].split(":")[0];
            if (i > 0) sb.append(",");
            sb.append(className);
        }
        return sb.toString();
    }

    /**
     * Get array of Docker service names
     * Example: ["admin-portal-tests", "teleadmin-tests"]
     */
    private static String[] getServiceNames() {
        String[] services = new String[TEST_CLASSES.length];
        for (int i = 0; i < TEST_CLASSES.length; i++) {
            services[i] = TEST_CLASSES[i].split(":")[1];
        }
        return services;
    }

    /**
     * Get array of Allure results folder names
     * Example: ["allure-results-admin", "allure-results-teleadmin"]
     */
    private static String[] getAllureResultsFolders() {
        String[] folders = new String[TEST_CLASSES.length];
        for (int i = 0; i < TEST_CLASSES.length; i++) {
            folders[i] = TEST_CLASSES[i].split(":")[2];
        }
        return folders;
    }

    public static void main(String[] args) {
        // Try to read from system property first (passed by Maven)
        String isLocalRun = System.getProperty("isLocalRun");

        // If not set, try to read from pom.xml
        if (isLocalRun == null) {
            isLocalRun = readIsLocalRunFromPom();
        }

        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("   WebSanity Test Suite Runner");
        System.out.println("═══════════════════════════════════════════════════════════");
        System.out.println("isLocalRun = " + isLocalRun);
        System.out.println("───────────────────────────────────────────────────────────");

        // If property is not set or is "true", run locally
        boolean runLocally = isLocalRun == null || !isLocalRun.equals("false");

        System.out.println("Execution Mode: " + (runLocally ? "LOCAL" : "DOCKER"));
        System.out.println("───────────────────────────────────────────────────────────");

        // Clean previous results before running tests
        cleanAllureResults();

        if (!runLocally) {
            System.out.println("⚠ Docker mode detected!");
            System.out.println("Test classes will run in parallel Docker containers.");
            System.out.println("Make sure Docker Desktop is running.");
            System.out.println("───────────────────────────────────────────────────────────");

            // Run Docker Compose
            runDockerTests();
        } else {
            System.out.println("Running tests locally...");
            System.out.println("Tests: " + getTestClassNames());
            System.out.println("───────────────────────────────────────────────────────────");

            // Run tests locally via Maven
            runLocalTests();
        }
    }

    /**
     * Finds Maven command in the system.
     * Tries multiple locations:
     * 1. mvn.cmd in PATH
     * 2. Common installation paths
     * 3. Maven wrapper (mvnw.cmd)
     */
    private static String findMavenCommand() {
        // Try standard mvn.cmd (might work if PATH is set)
        try {
            Process testProcess = new ProcessBuilder("mvn.cmd", "--version").start();
            testProcess.waitFor();
            if (testProcess.exitValue() == 0) {
                return "mvn.cmd";
            }
        } catch (Exception ignored) {}

        // Try common Maven installation paths on Windows
        String[] possiblePaths = {
            "C:\\Program Files\\Apache\\Maven\\bin\\mvn.cmd",
            "C:\\Program Files\\Maven\\bin\\mvn.cmd",
            "C:\\Maven\\bin\\mvn.cmd",
            System.getenv("MAVEN_HOME") + "\\bin\\mvn.cmd",
            System.getenv("M2_HOME") + "\\bin\\mvn.cmd"
        };

        for (String path : possiblePaths) {
            if (path != null && new File(path).exists()) {
                return path;
            }
        }

        // Try Maven wrapper in project root
        File mvnw = new File("mvnw.cmd");
        if (mvnw.exists()) {
            return "mvnw.cmd";
        }

        // Fallback to mvn and hope it works
        return "mvn";
    }

    private static String readIsLocalRunFromPom() {
        try {
            // Find pom.xml (go up from current directory)
            File pomFile = new File("pom.xml");
            if (!pomFile.exists()) {
                pomFile = new File("../pom.xml");
            }
            if (!pomFile.exists()) {
                pomFile = new File("../../pom.xml");
            }

            if (pomFile.exists()) {
                String content = new String(Files.readAllBytes(pomFile.toPath()));

                // Simple regex to find <isLocalRun>VALUE</isLocalRun>
                int startTag = content.indexOf("<isLocalRun>");
                if (startTag != -1) {
                    int endTag = content.indexOf("</isLocalRun>", startTag);
                    if (endTag != -1) {
                        String value = content.substring(startTag + 12, endTag).trim();
                        System.out.println("Read from pom.xml: isLocalRun = " + value);
                        return value;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Warning: Could not read isLocalRun from pom.xml: " + e.getMessage());
        }
        return null;
    }

    private static void runLocalTests() {
        try {
            System.out.println("Starting local test execution...");

            // Find Maven executable
            String mvnCommand = findMavenCommand();
            System.out.println("Using Maven: " + mvnCommand);

            ProcessBuilder pb = new ProcessBuilder(mvnCommand, "test",
                "-Dtest=" + getTestClassNames(),
                "-DisLocalRun=true");
            pb.redirectErrorStream(true);

            Process process = pb.start();

            // Read output
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            int exitCode = process.waitFor();

            System.out.println("───────────────────────────────────────────────────────────");
            System.out.println("Local execution completed with exit code: " + exitCode);
            System.out.println("═══════════════════════════════════════════════════════════");

            System.exit(exitCode);

        } catch (IOException | InterruptedException e) {
            System.err.println("Error running local tests: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void runDockerTests() {
        try {
            System.out.println("Starting Docker containers...");
            System.out.println("Both containers will run until completion...");

            // Remove --abort-on-container-exit to allow both containers to finish
            ProcessBuilder pb = new ProcessBuilder("docker", "compose", "up");
            pb.redirectErrorStream(true);

            Process process = pb.start();

            // Read output
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            int exitCode = process.waitFor();

            System.out.println("───────────────────────────────────────────────────────────");
            System.out.println("Docker compose completed with exit code: " + exitCode);

            // Check individual container exit codes
            int finalExitCode = checkContainerExitCodes();

            // Cleanup
            System.out.println("Cleaning up Docker containers...");
            new ProcessBuilder("docker", "compose", "down").start().waitFor();

            // Merge Allure results from both containers
            System.out.println("───────────────────────────────────────────────────────────");
            System.out.println("Merging Allure results from Docker containers...");
            mergeAllureResults();

            System.out.println("═══════════════════════════════════════════════════════════");
            System.out.println("To view Allure report run:");
            System.out.println("mvn allure:serve");
            System.out.println("═══════════════════════════════════════════════════════════");

            // Exit the JVM with the actual test result status
            System.exit(finalExitCode);

        } catch (IOException | InterruptedException e) {
            System.err.println("Error running Docker tests: " + e.getMessage());
            System.err.println("Make sure Docker Desktop is running!");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Checks the exit codes of individual Docker containers.
     * Returns 0 if all containers exited successfully, 1 otherwise.
     */
    private static int checkContainerExitCodes() {
        try {
            System.out.println("Checking container exit codes...");

            String[] serviceNames = getServiceNames();
            boolean allSuccess = true;

            // Get exit codes for all containers
            for (String serviceName : serviceNames) {
                int exitCode = getContainerExitCode(serviceName);
                System.out.println("  " + serviceName + " exit code: " + exitCode);

                if (exitCode != 0) {
                    allSuccess = false;
                }
            }

            // If any container failed, return 1
            if (!allSuccess) {
                System.out.println("⚠ One or more test containers failed!");
                return 1;
            }

            System.out.println("✅ All test containers completed successfully");
            return 0;

        } catch (Exception e) {
            System.err.println("⚠ Warning: Could not check container exit codes: " + e.getMessage());
            return 1;
        }
    }

    /**
     * Gets the exit code of a Docker container.
     */
    private static int getContainerExitCode(String containerName) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("docker", "inspect", containerName,
            "--format={{.State.ExitCode}}");
        pb.redirectErrorStream(true);

        Process process = pb.start();

        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line.trim());
            }
        }

        process.waitFor();

        try {
            return Integer.parseInt(output.toString());
        } catch (NumberFormatException e) {
            System.err.println("Warning: Could not parse exit code for " + containerName + ": " + output);
            return 1;
        }
    }

    /**
     * Cleans previous Allure results from all result directories.
     */
    private static void cleanAllureResults() {
        System.out.println("Cleaning previous Allure results...");

        // Build dynamic list of directories to clean
        String[] allureFolders = getAllureResultsFolders();
        File[] dirsToClean = new File[allureFolders.length + 2]; // +2 for main allure-results and screenshots

        dirsToClean[0] = new File("target/allure-results");
        for (int i = 0; i < allureFolders.length; i++) {
            dirsToClean[i + 1] = new File("target/" + allureFolders[i]);
        }
        dirsToClean[dirsToClean.length - 1] = new File("target/screenshots");

        for (File dir : dirsToClean) {
            if (dir.exists() && dir.isDirectory()) {
                File[] files = dir.listFiles();
                if (files != null) {
                    int deletedCount = 0;
                    for (File file : files) {
                        if (file.isFile() && file.delete()) {
                            deletedCount++;
                        }
                    }
                    if (deletedCount > 0) {
                        System.out.println("  Cleaned " + deletedCount + " files from " + dir.getName());
                    }
                }
            } else if (!dir.exists()) {
                // Create directory if it doesn't exist
                dir.mkdirs();
            }
        }

        System.out.println("✅ Cleanup completed");
        System.out.println("───────────────────────────────────────────────────────────");
    }

    /**
     * Merges Allure results from Docker containers into a single folder.
     * Dynamically copies all JSON files from configured allure-results folders
     * into: target/allure-results/
     */
    private static void mergeAllureResults() {
        try {
            File targetDir = new File("target/allure-results");

            // Create target directory if it doesn't exist
            if (!targetDir.exists()) {
                targetDir.mkdirs();
                System.out.println("Created target/allure-results directory");
            }

            int totalCopiedFiles = 0;
            String[] allureFolders = getAllureResultsFolders();
            String[] serviceNames = getServiceNames();

            // Copy from all configured result directories
            for (int i = 0; i < allureFolders.length; i++) {
                File sourceDir = new File("target/" + allureFolders[i]);

                if (sourceDir.exists() && sourceDir.isDirectory()) {
                    File[] files = sourceDir.listFiles((dir, name) -> name.endsWith(".json"));
                    if (files != null && files.length > 0) {
                        for (File file : files) {
                            Files.copy(file.toPath(), new File(targetDir, file.getName()).toPath(),
                                    java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                        }
                        totalCopiedFiles += files.length;
                        System.out.println("Copied " + files.length + " files from " + serviceNames[i]);
                    }
                }
            }

            System.out.println("✅ Successfully merged " + totalCopiedFiles + " Allure result files");

        } catch (Exception e) {
            System.err.println("⚠ Warning: Could not merge Allure results: " + e.getMessage());
            System.err.println("You can still view results separately:");
            System.err.println("  mvn allure:serve (for merged results)");
        }
    }
}

