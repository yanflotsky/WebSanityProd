package com.websanity.enums;

import lombok.Getter;

@Getter
public enum UserStatus {
    ACTIVE("1", "Active"),
    SUSPENDED("2", "Suspended"),
    DELETED("4", "Deleted"),
    BLOCKED("8", "Blocked"),
    PENDING("16", "Pending");

    private final String value;
    private final String displayName;

    UserStatus(String value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}

