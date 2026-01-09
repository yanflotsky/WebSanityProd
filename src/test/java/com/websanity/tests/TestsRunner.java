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
            System.out.println("Tests will run in parallel Docker containers.");
            System.out.println("Make sure Docker Desktop is running.");
            System.out.println("───────────────────────────────────────────────────────────");

            // Run Docker Compose
            runDockerTests();
        } else {
            System.out.println("Running tests locally...");
            System.out.println("Tests: AdminPortalSanityTest, TeleadminSanityTest");
            System.out.println("───────────────────────────────────────────────────────────");

            // Run tests locally via Maven
            runLocalTests();
        }
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

            ProcessBuilder pb = new ProcessBuilder("mvn", "test",
                "-Dtest=AdminPortalSanityTest,TeleadminSanityTest",
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

            // Get exit codes for both containers
            int adminExitCode = getContainerExitCode("admin-portal-tests");
            int teleadminExitCode = getContainerExitCode("teleadmin-tests");

            System.out.println("  admin-portal-tests exit code: " + adminExitCode);
            System.out.println("  teleadmin-tests exit code: " + teleadminExitCode);

            // If any container failed, return 1
            if (adminExitCode != 0 || teleadminExitCode != 0) {
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

        File[] dirsToClean = {
            new File("target/allure-results"),
            new File("target/allure-results-admin"),
            new File("target/allure-results-teleadmin")
        };

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
                    System.out.println("  Cleaned " + deletedCount + " files from " + dir.getName());
                }
            }
        }

        System.out.println("✅ Cleanup completed");
        System.out.println("───────────────────────────────────────────────────────────");
    }

    /**
     * Merges Allure results from Docker containers into a single folder.
     * Copies all JSON files from:
     * - target/allure-results-admin/
     * - target/allure-results-teleadmin/
     * Into: target/allure-results/
     */
    private static void mergeAllureResults() {
        try {
            File targetDir = new File("target/allure-results");
            File adminDir = new File("target/allure-results-admin");
            File teleadminDir = new File("target/allure-results-teleadmin");

            // Create target directory if it doesn't exist
            if (!targetDir.exists()) {
                targetDir.mkdirs();
                System.out.println("Created target/allure-results directory");
            }

            int copiedFiles = 0;

            // Copy from admin results
            if (adminDir.exists() && adminDir.isDirectory()) {
                File[] adminFiles = adminDir.listFiles((dir, name) -> name.endsWith(".json"));
                if (adminFiles != null) {
                    for (File file : adminFiles) {
                        Files.copy(file.toPath(), new File(targetDir, file.getName()).toPath(),
                                java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                        copiedFiles++;
                    }
                    System.out.println("Copied " + adminFiles.length + " files from admin-portal-tests");
                }
            }

            // Copy from teleadmin results
            if (teleadminDir.exists() && teleadminDir.isDirectory()) {
                File[] teleadminFiles = teleadminDir.listFiles((dir, name) -> name.endsWith(".json"));
                if (teleadminFiles != null) {
                    for (File file : teleadminFiles) {
                        Files.copy(file.toPath(), new File(targetDir, file.getName()).toPath(),
                                java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                        copiedFiles++;
                    }
                    System.out.println("Copied " + teleadminFiles.length + " files from teleadmin-tests");
                }
            }

            System.out.println("✅ Successfully merged " + copiedFiles + " Allure result files");

        } catch (Exception e) {
            System.err.println("⚠ Warning: Could not merge Allure results: " + e.getMessage());
            System.err.println("You can still view results separately:");
            System.err.println("  mvn allure:serve (for merged results)");
        }
    }
}

