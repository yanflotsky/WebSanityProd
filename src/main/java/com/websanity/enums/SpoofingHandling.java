package com.websanity.enums;

import lombok.Getter;

@Getter
public enum SpoofingHandling {

    ADD_SENDER_HEADER("ADD_SENDER_HEADER", "Basic - Sender Header only"),
    CONFIG_FROM_HEADERS("CONFIG_FROM_HEADERS", "Align MIME FROM and SMTP FROM"),
    NONE("NONE", "NONE - No Sender Header");

    private final String value;
    private final String displayName;

    SpoofingHandling(String value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

}

