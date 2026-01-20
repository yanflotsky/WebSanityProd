package com.websanity.utils;

import com.websanity.models.UserParams;
import lombok.Getter;

/**
 * Test data class containing hardcoded users that always exist in the system
 * These users are used for testing and validation purposes
 */
public class TestUsers {

    /**
     * LFF_TELEADMIN_MANAGER - used for LFF (Load From File) tests
     */
    @Getter
    private static final UserParams LFF_TELEADMIN_MANAGER = UserParams.builder()
            .username("websanitymanlff")
            .userID("951813470")
            .build();

    /**
     * LFF Test Users - users that are imported via LFF for testing
     */
    public static class LFFUsers {
        public static final String USER_1 = "websanlffus1";
        public static final String USER_2 = "websanlffus2";
        public static final String USER_3 = "websanlffus3";
        public static final String USER_4 = "websanlffus4";
        public static final String USER_5 = "websanlffus5";

        /**
         * Get all LFF test usernames as array
         */
        public static String[] getAllUsernames() {
            return new String[]{USER_1, USER_2, USER_3, USER_4, USER_5};
        }

        /**
         * Get all LFF test usernames as list
         */
        public static java.util.List<String> usernamesForLFFList() {
            return java.util.Arrays.asList(getAllUsernames());
        }
    }

    /**
     * Sanity Test Manager - used for general sanity tests
     */
    @Getter
    private static final UserParams SANITY_MANAGER = UserParams.builder()
            .username("websanityun")
            .firstName("websanityfn")
            .lastName("websanityln")
            .password("QAautoweb12345678!!")
            .build();

    /**
     * Get LFF Manager user data
     */
    public static UserParams getLffTeleadminManager() {
        return LFF_TELEADMIN_MANAGER;
    }
    /**
     * Get LFF Manager user data
     */
    public static UserParams getBulkUploadManager() {
        return LFF_TELEADMIN_MANAGER;
    }

    /**
     * Get Sanity Manager user data
     */
    public static UserParams getAdminPortalSanityManager() {
        return SANITY_MANAGER;
    }

    /**
     * Storage Plan names used in tests
     */
    public static class StoragePlans {
        public static final String WEBSAN_PLAN_1 = "websanPlan1";
    }
}
