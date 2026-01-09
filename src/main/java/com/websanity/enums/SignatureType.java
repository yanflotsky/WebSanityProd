package com.websanity.enums;

public enum SignatureType {
    INHERIT("INHERIT", "Default"),
    OFF("OFF", "Off"),
    ALWAYS("ALWAYS", "Always"),
    FIRST_MESSAGE_THREAD("FIRST_MESSAGE_THREAD", "First Message in Thread"),
    DAILY("DAILY", "Daily");

    private final String value;
    private final String displayName;

    SignatureType(String value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    public String getValue() {
        return value;
    }

    public String getDisplayName() {
        return displayName;
    }
}

