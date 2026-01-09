package com.websanity.enums;

import lombok.Getter;

@Getter
public enum Storage {

    BEHAVOX("30", "BEHAVOX"),
    BLOOMBERG("19", "BLOOMBERG"),
    CAPITAL_ONE("36", "CAPITAL ONE"),
    DEV_JPM("31", "DEV JPM"),
    ENTERPRISE_ARCHIVE("43", "ENTERPRISE ARCHIVE"),
    ERADO("7", "ERADO"),
    GENERIC_SFTP("39", "GENERIC SFTP"),
    GENERIC_SMTP_ARCHIVER("13", "GENERIC SMTP ARCHIVER"),
    GLOBAL_RELAY("3", "Global Relay"),
    INTRADYN("15", "INTRADYN"),
    JATHEON("16", "JATHEON"),
    JP_MORGAN("27", "JP MORGAN"),
    LEUMI("37", "LEUMI"),
    MICROFOCUS_DIGITAL_SAFE("11", "MICROFOCUS DIGITAL SAFE"),
    MICROFOCUS_RETAIN("17", "MICROFOCUS RETAIN"),
    MICROSOFT("20", "MICROSOFT"),
    MIMECAST("25", "MIMECAST"),
    MORGAN_STANLEY_PROD("41", "MORGAN STANLEY PROD"),
    MORGAN_STANLEY_UAT("28", "MORGAN STANLEY UAT"),
    NICE_NTR("26", "NICE NTR"),
    NICE_NTR_X_SMTP("42", "NICE NTR-X SMTP"),
    NICE_SFTP("29", "NICE_SFTP"),
    PROD_JPM("32", "PROD JPM"),
    PROOFPOINT("12", "PROOFPOINT"),
    SALESFORCE("23", "SALESFORCE"),
    SMARSH_PROARC("40", "SMARSH PROARC"),
    SMARSH_SMTP("38", "SMARSH SMTP"),
    STEELEYE("21", "STEELEYE"),
    TM_LONGTERM("2", "TM LongTerm"),
    VERINT("34", "VERINT"),
    VERITAS("6", "VERITAS"),
    VERITAS_JOURNALING("35", "VERITAS JOURNALING");

    private final String value;
    private final String displayName;

    Storage(String value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

}

