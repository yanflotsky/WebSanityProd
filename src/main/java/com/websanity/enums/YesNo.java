package com.websanity.enums;

import lombok.Getter;

@Getter
public enum YesNo {

    YES("yes", "Yes"),
    NO("no", "No");

    private final String value;
    private final String displayName;

    YesNo(String value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

}

