package com.websanity.enums;

import lombok.Getter;

@Getter
public enum ParticipantHeaderFormat {

    FIRST_NAME_LAST_NAME_MOBILE_EMAIL("FIRST_NAME_LAST_NAME_MOBILE_EMAIL", "First  Last Mobile <email> | Sam Jones 17819988776 <sam@telemessage.com>"),
    FIRST_NAME_LAST_NAME_MOBILE_EMAIL_QUOTES("FIRST_NAME_LAST_NAME_MOBILE_EMAIL_QUOTES", "\"First  Last Mobile\" <email> | \"Sam Jones 17819988776\" <sam@telemessage.com>"),
    LAST_NAME_FIRST_NAME_MOBILE_EMAIL("LAST_NAME_FIRST_NAME_MOBILE_EMAIL", "Last  First Mobile <email> | Jones Sam 17819988776 <sam@telemessage.com>"),
    LAST_NAME_COMMA_FIRST_NAME_MOBILE_EMAIL("LAST_NAME_COMMA_FIRST_NAME_MOBILE_EMAIL", "Last, First Mobile <email> | Jones, Sam 17819988776 <sam@telemessage.com>"),
    MOBILE_EMAIL("MOBILE_EMAIL", "Mobile <email> | 17819988776 <sam@telemessage.com>"),
    FIRST_NAME_LAST_NAME_EMAIL("FIRST_NAME_LAST_NAME_EMAIL", "First Last <email> | Sam Jones <sam@telemessage.com>"),
    LAST_NAME_FIRST_NAME_EMAIL("LAST_NAME_FIRST_NAME_EMAIL", "Last  First <email> | Jones Sam <sam@telemessage.com>"),
    LAST_NAME_COMMA_FIRST_NAME_EMAIL("LAST_NAME_COMMA_FIRST_NAME_EMAIL", "Last, First <email> | Jones, Sam <sam@telemessage.com>");

    private final String value;
    private final String displayName;

    ParticipantHeaderFormat(String value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

}

