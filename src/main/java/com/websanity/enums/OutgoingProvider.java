package com.websanity.enums;

import lombok.Getter;

@Getter
public enum OutgoingProvider {

    NONE("", "None"),
    BANDWIDTH_CHARLIE_USA("13", "13 (Bandwidth-CHARLIE-USA)"),
    VONAGE_TR_NEXMO_TR_CHARLIE("310", "310 (VonageTR - NexmoTR-Charlie)"),
    STOUR_MARINE_COMMERCIALS("312", "312 (Stour Marine Commercials)"),
    TWILIO_TRX("313", "313 (Twilio TRX)"),
    CLX_CHARLIE_USA("379", "379 (CLX-CHARLIE-USA)"),
    MESSAGEBIRD_MT("399", "399 (MessageBird MT)");

    private final String value;
    private final String displayName;

    OutgoingProvider(String value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }
}

