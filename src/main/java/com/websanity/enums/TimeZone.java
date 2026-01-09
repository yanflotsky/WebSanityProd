package com.websanity.enums;

import lombok.Getter;

@Getter
public enum TimeZone {

    // GMT-12 to GMT-10
    GMT_MINUS_12("Etc/GMT+12", "(GMT-12) "),
    GMT_MINUS_11("Etc/GMT+11", "(GMT-11) "),
    PACIFIC_MIDWAY("Pacific/Midway", "(GMT-11) Pacific/Midway"),
    PACIFIC_NIUE("Pacific/Niue", "(GMT-11) Pacific/Niue"),
    PACIFIC_PAGO_PAGO("Pacific/Pago_Pago", "(GMT-11) Pacific/Pago Pago"),
    GMT_MINUS_10("Etc/GMT+10", "(GMT-10) "),
    AMERICA_ADAK("America/Adak", "(GMT-10) America/Adak"),
    HST("HST", "(GMT-10) HST"),
    PACIFIC_HONOLULU("Pacific/Honolulu", "(GMT-10) Pacific/Honolulu"),
    PACIFIC_RAROTONGA("Pacific/Rarotonga", "(GMT-10) Pacific/Rarotonga"),
    PACIFIC_TAHITI("Pacific/Tahiti", "(GMT-10) Pacific/Tahiti"),

    // GMT-9
    PACIFIC_MARQUESAS("Pacific/Marquesas", "(GMT-9) Pacific/Marquesas"),
    GMT_MINUS_9("Etc/GMT+9", "(GMT-9) "),
    AMERICA_ANCHORAGE("America/Anchorage", "(GMT-9) America/Anchorage"),
    AMERICA_JUNEAU("America/Juneau", "(GMT-9) America/Juneau"),
    AMERICA_NOME("America/Nome", "(GMT-9) America/Nome"),
    PACIFIC_GAMBIER("Pacific/Gambier", "(GMT-9) Pacific/Gambier"),

    // GMT-8
    GMT_MINUS_8("Etc/GMT+8", "(GMT-8) "),
    AMERICA_LOS_ANGELES("America/Los_Angeles", "(GMT-8) America/Los Angeles"),
    AMERICA_TIJUANA("America/Tijuana", "(GMT-8) America/Tijuana"),
    AMERICA_VANCOUVER("America/Vancouver", "(GMT-8) America/Vancouver"),
    PST8PDT("PST8PDT", "(GMT-8) PST8PDT"),
    PACIFIC_PITCAIRN("Pacific/Pitcairn", "(GMT-8) Pacific/Pitcairn"),

    // GMT-7
    GMT_MINUS_7("Etc/GMT+7", "(GMT-7) "),
    AMERICA_DENVER("America/Denver", "(GMT-7) America/Denver"),
    AMERICA_PHOENIX("America/Phoenix", "(GMT-7) America/Phoenix"),
    MST("MST", "(GMT-7) MST"),
    MST7MDT("MST7MDT", "(GMT-7) MST7MDT"),

    // GMT-6
    GMT_MINUS_6("Etc/GMT+6", "(GMT-6) "),
    AMERICA_CHICAGO("America/Chicago", "(GMT-6) America/Chicago"),
    AMERICA_MEXICO_CITY("America/Mexico_City", "(GMT-6) America/Mexico City"),
    CST6CDT("CST6CDT", "(GMT-6) CST6CDT"),

    // GMT-5
    GMT_MINUS_5("Etc/GMT+5", "(GMT-5) "),
    AMERICA_NEW_YORK("America/New_York", "(GMT-5) America/New York"),
    AMERICA_TORONTO("America/Toronto", "(GMT-5) America/Toronto"),
    AMERICA_BOGOTA("America/Bogota", "(GMT-5) America/Bogota"),
    AMERICA_LIMA("America/Lima", "(GMT-5) America/Lima"),
    EST("EST", "(GMT-5) EST"),
    EST5EDT("EST5EDT", "(GMT-5) EST5EDT"),

    // GMT-4
    GMT_MINUS_4("Etc/GMT+4", "(GMT-4) "),
    AMERICA_CARACAS("America/Caracas", "(GMT-4) America/Caracas"),
    AMERICA_HALIFAX("America/Halifax", "(GMT-4) America/Halifax"),
    AMERICA_LA_PAZ("America/La_Paz", "(GMT-4) America/La Paz"),
    AMERICA_SANTIAGO("America/Santiago", "(GMT-4) America/Santiago"),
    ATLANTIC_BERMUDA("Atlantic/Bermuda", "(GMT-4) Atlantic/Bermuda"),

    // GMT-3
    GMT_MINUS_3("Etc/GMT+3", "(GMT-3) "),
    AMERICA_SAO_PAULO("America/Sao_Paulo", "(GMT-3) America/Sao Paulo"),
    AMERICA_BUENOS_AIRES("America/Argentina/Buenos_Aires", "(GMT-3) America/Argentina/Buenos_Aires"),
    AMERICA_MONTEVIDEO("America/Montevideo", "(GMT-3) America/Montevideo"),

    // GMT-2
    GMT_MINUS_2("Etc/GMT+2", "(GMT-2) "),
    AMERICA_NORONHA("America/Noronha", "(GMT-2) America/Noronha"),

    // GMT-1
    GMT_MINUS_1("Etc/GMT+1", "(GMT-1) "),
    ATLANTIC_AZORES("Atlantic/Azores", "(GMT-1) Atlantic/Azores"),

    // GMT
    GMT("Etc/GMT", "(GMT) "),
    UTC("UTC", "(GMT) UTC"),
    ETC_UTC("Etc/UTC", "(GMT) Etc/UTC"),
    AFRICA_CASABLANCA("Africa/Casablanca", "(GMT) Africa/Casablanca"),
    EUROPE_LONDON("Europe/London", "(GMT) Europe/London"),
    EUROPE_LISBON("Europe/Lisbon", "(GMT) Europe/Lisbon"),
    WET("WET", "(GMT) WET"),

    // GMT+1
    GMT_PLUS_1("Etc/GMT-1", "(GMT+1) "),
    EUROPE_PARIS("Europe/Paris", "(GMT+1) Europe/Paris"),
    EUROPE_BERLIN("Europe/Berlin", "(GMT+1) Europe/Berlin"),
    EUROPE_ROME("Europe/Rome", "(GMT+1) Europe/Rome"),
    EUROPE_MADRID("Europe/Madrid", "(GMT+1) Europe/Madrid"),
    EUROPE_AMSTERDAM("Europe/Amsterdam", "(GMT+1) Europe/Amsterdam"),
    CET("CET", "(GMT+1) CET"),

    // GMT+2
    GMT_PLUS_2("Etc/GMT-2", "(GMT+2) "),
    EUROPE_ATHENS("Europe/Athens", "(GMT+2) Europe/Athens"),
    EUROPE_BUCHAREST("Europe/Bucharest", "(GMT+2) Europe/Bucharest"),
    EUROPE_HELSINKI("Europe/Helsinki", "(GMT+2) Europe/Helsinki"),
    ASIA_JERUSALEM("Asia/Jerusalem", "(GMT+2) Asia/Jerusalem"),
    AFRICA_CAIRO("Africa/Cairo", "(GMT+2) Africa/Cairo"),
    EET("EET", "(GMT+2) EET"),

    // GMT+3
    GMT_PLUS_3("Etc/GMT-3", "(GMT+3) "),
    EUROPE_MOSCOW("Europe/Moscow", "(GMT+3) Europe/Moscow"),
    EUROPE_ISTANBUL("Europe/Istanbul", "(GMT+3) Europe/Istanbul"),
    ASIA_RIYADH("Asia/Riyadh", "(GMT+3) Asia/Riyadh"),
    ASIA_BAGHDAD("Asia/Baghdad", "(GMT+3) Asia/Baghdad"),
    AFRICA_NAIROBI("Africa/Nairobi", "(GMT+3) Africa/Nairobi"),
    ASIA_TEHRAN("Asia/Tehran", "(GMT+3) Asia/Tehran"),

    // GMT+4
    GMT_PLUS_4("Etc/GMT-4", "(GMT+4) "),
    ASIA_DUBAI("Asia/Dubai", "(GMT+4) Asia/Dubai"),
    ASIA_BAKU("Asia/Baku", "(GMT+4) Asia/Baku"),
    ASIA_TBILISI("Asia/Tbilisi", "(GMT+4) Asia/Tbilisi"),
    ASIA_KABUL("Asia/Kabul", "(GMT+4) Asia/Kabul"),

    // GMT+5
    GMT_PLUS_5("Etc/GMT-5", "(GMT+5) "),
    ASIA_KARACHI("Asia/Karachi", "(GMT+5) Asia/Karachi"),
    ASIA_TASHKENT("Asia/Tashkent", "(GMT+5) Asia/Tashkent"),
    ASIA_KOLKATA("Asia/Kolkata", "(GMT+5) Asia/Kolkata"),
    ASIA_COLOMBO("Asia/Colombo", "(GMT+5) Asia/Colombo"),
    ASIA_KATHMANDU("Asia/Kathmandu", "(GMT+5) Asia/Kathmandu"),

    // GMT+6
    GMT_PLUS_6("Etc/GMT-6", "(GMT+6) "),
    ASIA_DHAKA("Asia/Dhaka", "(GMT+6) Asia/Dhaka"),
    ASIA_ALMATY("Asia/Almaty", "(GMT+6) Asia/Almaty"),
    ASIA_YANGON("Asia/Yangon", "(GMT+6) Asia/Yangon"),

    // GMT+7
    GMT_PLUS_7("Etc/GMT-7", "(GMT+7) "),
    ASIA_BANGKOK("Asia/Bangkok", "(GMT+7) Asia/Bangkok"),
    ASIA_JAKARTA("Asia/Jakarta", "(GMT+7) Asia/Jakarta"),
    ASIA_HO_CHI_MINH("Asia/Ho_Chi_Minh", "(GMT+7) Asia/Ho_Chi_Minh"),

    // GMT+8
    GMT_PLUS_8("Etc/GMT-8", "(GMT+8) "),
    ASIA_SHANGHAI("Asia/Shanghai", "(GMT+8) Asia/Shanghai"),
    ASIA_HONG_KONG("Asia/Hong_Kong", "(GMT+8) Asia/Hong Kong"),
    ASIA_SINGAPORE("Asia/Singapore", "(GMT+8) Asia/Singapore"),
    ASIA_TAIPEI("Asia/Taipei", "(GMT+8) Asia/Taipei"),
    ASIA_MANILA("Asia/Manila", "(GMT+8) Asia/Manila"),
    AUSTRALIA_PERTH("Australia/Perth", "(GMT+8) Australia/Perth"),

    // GMT+9
    GMT_PLUS_9("Etc/GMT-9", "(GMT+9) "),
    ASIA_TOKYO("Asia/Tokyo", "(GMT+9) Asia/Tokyo"),
    ASIA_SEOUL("Asia/Seoul", "(GMT+9) Asia/Seoul"),
    AUSTRALIA_DARWIN("Australia/Darwin", "(GMT+9) Australia/Darwin"),
    AUSTRALIA_ADELAIDE("Australia/Adelaide", "(GMT+9) Australia/Adelaide"),

    // GMT+10
    GMT_PLUS_10("Etc/GMT-10", "(GMT+10) "),
    AUSTRALIA_SYDNEY("Australia/Sydney", "(GMT+10) Australia/Sydney"),
    AUSTRALIA_MELBOURNE("Australia/Melbourne", "(GMT+10) Australia/Melbourne"),
    AUSTRALIA_BRISBANE("Australia/Brisbane", "(GMT+10) Australia/Brisbane"),
    AUSTRALIA_HOBART("Australia/Hobart", "(GMT+10) Australia/Hobart"),
    PACIFIC_GUAM("Pacific/Guam", "(GMT+10) Pacific/Guam"),

    // GMT+11
    GMT_PLUS_11("Etc/GMT-11", "(GMT+11) "),
    PACIFIC_NOUMEA("Pacific/Noumea", "(GMT+11) Pacific/Noumea"),

    // GMT+12
    GMT_PLUS_12("Etc/GMT-12", "(GMT+12) "),
    PACIFIC_AUCKLAND("Pacific/Auckland", "(GMT+12) Pacific/Auckland"),
    PACIFIC_FIJI("Pacific/Fiji", "(GMT+12) Pacific/Fiji"),

    // GMT+13
    GMT_PLUS_13("Etc/GMT-13", "(GMT+13) "),
    PACIFIC_APIA("Pacific/Apia", "(GMT+13) Pacific/Apia"),
    PACIFIC_TONGATAPU("Pacific/Tongatapu", "(GMT+13) Pacific/Tongatapu"),

    // GMT+14
    GMT_PLUS_14("Etc/GMT-14", "(GMT+14) "),
    PACIFIC_KIRITIMATI("Pacific/Kiritimati", "(GMT+14) Pacific/Kiritimati");

    private final String value;
    private final String displayName;

    TimeZone(String value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    /**
     * Get TimeZone by value
     */
    public static TimeZone getByValue(String value) {
        for (TimeZone tz : values()) {
            if (tz.value.equals(value)) {
                return tz;
            }
        }
        throw new IllegalArgumentException("Unknown timezone value: " + value);
    }

    /**
     * Get TimeZone by display name
     */
    public static TimeZone getByDisplayName(String displayName) {
        for (TimeZone tz : values()) {
            if (tz.displayName.equalsIgnoreCase(displayName)) {
                return tz;
            }
        }
        throw new IllegalArgumentException("Unknown timezone display name: " + displayName);
    }
}

