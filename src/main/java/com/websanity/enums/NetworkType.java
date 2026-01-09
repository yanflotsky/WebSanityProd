package com.websanity.enums;

import lombok.Getter;

@Getter
public enum NetworkType {

    THREE_HK("THREE_HK", "THREE_HK"),
    BELL("BELL", "BELL"),
    CELLCOM("CELLCOM", "CELLCOM"),
    TELUS("TELUS", "TELUS"),
    T_MOBILE("T_MOBILE", "T_MOBILE"),
    VERIZON("VERIZON", "VERIZON"),
    ROGERS("ROGERS", "ROGERS"),
    TELEFONICA_O2("TELEFONICA_O2", "TELEFONICA_O2"),
    HKCSL("HKCSL", "HKCSL"),
    SINGTEL("SINGTEL", "SINGTEL"),
    HUTCHISON("HUTCHISON", "HUTCHISON"),
    ATT("ATT", "AT&T");

    private final String value;
    private final String displayName;

    NetworkType(String value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

}

