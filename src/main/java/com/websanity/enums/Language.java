package com.websanity.enums;

import lombok.Getter;

@Getter
public enum Language {

    EMPTY("", ""),
    SPANISH_SPAIN("esES", "español (España)"),
    JAPANESE("jaJP", "日本語"),
    SPANISH_MEXICO("esMX", "español (México)"),
    RUSSIAN("ruRU", "русский"),
    HEBREW("iwIL", "עברית"),
    PORTUGUESE_BRAZIL("ptBR", "português"),
    GERMAN("deDE", "Deutsch"),
    ITALIAN("itIT", "italiano"),
    FRENCH("frFR", "français"),
    ENGLISH_US("enUS", "English (United States)"),
    ENGLISH_UK("enGB", "English (United Kingdom)");

    private final String value;
    private final String displayName;

    Language(String value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    /**
     * Get Language by value
     */
    public static Language getByValue(String value) {
        for (Language language : values()) {
            if (language.value.equals(value)) {
                return language;
            }
        }
        throw new IllegalArgumentException("Unknown language value: " + value);
    }

    /**
     * Get Language by display name
     */
    public static Language getByDisplayName(String displayName) {
        for (Language language : values()) {
            if (language.displayName.equalsIgnoreCase(displayName)) {
                return language;
            }
        }
        throw new IllegalArgumentException("Unknown language display name: " + displayName);
    }
}

