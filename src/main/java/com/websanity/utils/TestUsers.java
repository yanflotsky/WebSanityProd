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
     * Values are loaded from Maven profiles via System properties
     */
    public static UserParams getLffTeleadminManager() {
        return UserParams.builder()
                .username(System.getProperty("lff.manager.username"))
                .userID(System.getProperty("lff.manager.userid"))
                .build();
    }
    /**
     * Get Bulk Upload Manager user data (same as LFF Manager)
     */
    public static UserParams getBulkUploadManager() {
        return getLffTeleadminManager();
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
