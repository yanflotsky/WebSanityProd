package com.websanity.enums;

import lombok.Getter;

@Getter
public enum Provider {

    NONE("", "None"),
    CELLCOM("1", "1 (Cellcom)"),
    HOT_MOBILE("2", "2 (Hot Mobile)"),
    VERIZON("3", "3 (Verizon)"),
    HOT_MOBILE_INTERNATIONAL("4", "4 (Hot Mobile International)"),
    PROVIDER_019("6", "6 (019)"),
    TMOBILE_EMG("8", "8 (TMobile EMG)"),
    TMOBILE_EMG_US("9", "9 (TMobile EMG US)"),
    VIAHUB_FOR_PS("10", "10 (ViaHub for PS)"),
    VIAHUB_FOR_PS_DIRECT("11", "11 (ViaHub for PS DIRECT)"),
    BANDWIDTH_CHARLIE_USA("13", "13 (Bandwidth-CHARLIE-USA)"),
    CELLCOM_FOR_PS("14", "14 (Cellcom for PS)"),
    PROVIDER_019_FIRST_PRIORITY("15", "15 (019 as first priority)"),
    BANDWIDTH_ARTHUR_USA("16", "16 (Bandwidth-ARTHUR-USA)"),
    QUANTIC_VISION_PANAMA("17", "17 (Quantic Vision - for Panama)"),
    MITO_FOR_PARTNER("19", "19 (Mito for Partner)"),
    INFOBIP_FOR_HOT("20", "20 (InfoBip for HOT)"),
    MMD_SMART("21", "21 (MMD smart)"),
    TELUS("31", "31 (Telus)"),
    VIAEOR_VIA_SYBASE("39", "39 (Viaeor via Sybase)"),
    VIAERO_WIRELESS_MESSAGING_GATEWAY("41", "41 (Viaero Wireless Messaging Gateway)"),
    MBLOX_USA_TRX("53", "53 (Mblox USA TRX)"),
    MOBILE365_VERIZON("62", "62 (Mobile365 - Verizon)"),
    TELEMESSAGE_PROD("69", "69 (TeleMessage Prod)"),
    TELEMESSAGE_PROD_IL("71", "71 (TeleMessage Prod-IL)"),
    US_INTERCARRIER("89", "89 (US Intercarrier)"),
    BELL("99", "99 (BELL)"),
    CELLCOM_106("106", "106 (Cellcom)"),
    SPRINT_WCTP_REPLY("129", "129 (Sprint WCTP Reply)"),
    INTEROP_TECHNOLOGIES("178", "178 (Interop Technologies)"),
    SMS_DEMO("200", "200 (SMS_DEMO)"),
    RND_FOR_SMPP("201", "201 (RND For SMPP)"),
    WIND_SMS2LL("289", "289 (Wind SMS2LL)"),
    VONAGE_TR_NEXMO_TR_CHARLIE("310", "310 (VonageTR - NexmoTR-Charlie)"),
    AMD("311", "311 (AMD)"),
    STOUR_MARINE_COMMERCIALS("312", "312 (Stour Marine Commercials)"),
    TWILIO_TRX("313", "313 (Twilio TRX)"),
    BOOST_NEXTEL("317", "317 (Boost Nextel)"),
    TELUS_TAP("320", "320 (TelusTap)"),
    NEXMO("322", "322 (nexmo)"),
    CAPITAL_ONE_INCOMING("372", "372 (Capital One for Incoming)"),
    CLX_FOR_INCOMING("373", "373 (CLX_FOR_INCOMING)"),
    BEEPSEND_FOR_INCOMING("374", "374 (BeepSend for Incoming)"),
    TWILIO("376", "376 (Twilio)"),
    CLX_ARTHUR_USA_10DLC("377", "377 (CLX-ARTHUR-USA-10DLC)"),
    CLX_ARTHUR_WORLDWIDE("378", "378 (CLX-ARTHUR-Worldwide)"),
    CLX_CHARLIE_USA("379", "379 (CLX-CHARLIE-USA)"),
    CLX_CHARLIE_WORLDWIDE("380", "380 (CLX-CHARLIE-Worldwide)"),
    INFOBIP_FOR_INCOMING("387", "387 (infoBip for incoming)"),
    PLIVO_FOR_INCOMING("388", "388 (Plivo for incoming)"),
    IMS_SMPP_EL_AL_1("392", "392 (IMS SMPP (El-AL) - 1)"),
    MESSAGEBIRD_FOR_INCOMING("395", "395 (MessageBird for incoming)"),
    CELLCOM_FOR_EL_AL("397", "397 (Cellcom For EL-AL)"),
    TELUS_2WAY("398", "398 (TELUS 2way)"),
    MESSAGEBIRD_MT("399", "399 (MessageBird MT)"),
    AMD_FOR_INCOMING("401", "401 (AMD for incoming)"),
    IMS_SMPP_EL_AL_2("403", "403 (IMS SMPP (El-AL) - 2 )"),
    IMS_SMPP_EL_AL_3("404", "404 (IMS SMPP (El-AL) - 3 )"),
    ROGERS_CA("444", "444 (Rogers-CA)"),
    BENNY_SMS2LL_SYNC("2000", "2000 (BENNY SMS2LL SYNC)");

    private final String value;
    private final String displayName;

    Provider(String value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }
}

