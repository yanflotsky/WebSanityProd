package com.websanity.enums;

import lombok.Getter;

@Getter
public enum SourceType {

    IMESSAGE_CAPTURE("IMESSAGE_CAPTURE", "iMessage Capture"),
    WHATSAPP_ARCHIVER("WHATSAPP_ARCHIVER", "WhatsApp Phone Capture"),
    SIGNAL("SIGNAL", "Signal Capture"),
    WIRELESS_COPY("WIRELESS_COPY", "Android Capture"),
    WHATSAPP_CLOUD_ARCHIVER("WHATSAPP_CLOUD_ARCHIVER", "WhatsApp Cloud Capture"),
    TEAMS_CAPTURE("TEAMS_CAPTURE", "Teams Capture"),
    WEBEX("WEBEX", "Webex Capture"),
    WECHAT("WECHAT", "WeChat Capture"),
    VIRTUAL_NUMBER("VIRTUAL_NUMBER", "Enterprise Number Capture"),
    NETWORK_ARCHIVING("NETWORK_ARCHIVING", "Carrier Capture"),
    TELEGRAM("TELEGRAM", "Telegram Capture");

    private final String value;
    private final String displayName;

    SourceType(String value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

}

