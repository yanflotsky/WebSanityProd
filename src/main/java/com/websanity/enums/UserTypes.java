package com.websanity.enums;

import lombok.Getter;

@Getter
public enum UserTypes {

    PROMANAGER("Pro Manager", "911"),
    PROUSER("Pro User", "912");

    private final String displayName;
    private final String value;

    UserTypes(String displayName, String value) {
        this.displayName = displayName;
        this.value = value;
    }

    /**
     * Get ServiceLevel by value
     */
    public static UserTypes getByValue(String value) {
        for (UserTypes level : values()) {
            if (level.value.equals(value)) {
                return level;
            }
        }
        throw new IllegalArgumentException("Unknown service level value: " + value);
    }

    /**
     * Get ServiceLevel by display name
     */
    public static UserTypes getByDisplayName(String displayName) {
        for (UserTypes level : values()) {
            if (level.displayName.equalsIgnoreCase(displayName)) {
                return level;
            }
        }
        throw new IllegalArgumentException("Unknown service level display name: " + displayName);
    }
}

