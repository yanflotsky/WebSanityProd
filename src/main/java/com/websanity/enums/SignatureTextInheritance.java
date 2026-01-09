package com.websanity.enums;

public enum SignatureTextInheritance {
    DEFAULT("true", "Default"),
    MANUAL("false", "Manual");

    private final String value;
    private final String displayName;

    SignatureTextInheritance(String value, String displayName) {
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

