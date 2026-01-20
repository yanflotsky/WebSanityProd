package com.websanity.enums;

import lombok.Getter;

@Getter
public enum UserTypes {

    ADMINISTRATOR("Administrator", "101"),
    BANK_IGUD_AD("Bank Igud AD", "122"),
    BASIC("Basic", "191"),
    BASIC_POSTPAID("Basic Postpaid", "154"),
    BELL_SMS_2_LANDLINE("Bell SMS 2 LandLine", "137"),
    BELL_SMS_SMS2LL_PREMIUM("Bell SMS SMS2LL Premium", "138"),
    BUSINESS_MANAGER("Business Manager", "913"),
    BUSINESS_USER("Business User", "914"),
    CANADA_SHORT_CODE("Canada Short Code", "514"),
    CELLULAR_ONE_TEXT_TO_LANDLINE("Cellular One Text to Landline", "283"),
    CLALIT("Clalit", "516"),
    CORPORATE_SMS("Corporate SMS", "151"),
    CUSTOMER_ADMIN_SPRINT_EMG("Customer Admin Sprint EMG", "405"),
    DEMO_SMS2VOICE("Demo SMS2Voice", "259"),
    DEMO_UNBRANDED("Demo Unbranded", "117"),
    DERICHEBOURG_APP("Derichebourg-App", "927"),
    DEVELOPER("Developer", "915"),
    EL_AL_BASIC_POSTPAID("EL AL Basic Postpaid", "153"),
    EL_AL("El Al", "168"),
    EL_AL_BROKER("El Al Broker", "167"),
    EL_AL_2_WAY("El-AL-2-way", "158"),
    FEDEX_2_WAY("Fedex 2 way", "416"),
    FINANCIAL_US_SHORT_CODE("Financial US Short Code", "222"),
    FIRE_DEPARTMENT("Fire Department", "425"),
    FIRE_DEPARTMENT_ADMIN("Fire Department Admin", "426"),
    FLYING_CARGO("Flying Cargo", "413"),
    FLYING_CARGO_ADMIN("Flying Cargo Admin", "601"),
    FLYING_CARGO_SUB_ADMIN("Flying Cargo Sub Admin", "602"),
    IGA_ONLY_FAX("IGA - Only Fax", "421"),
    IGA_SMS_FAX("IGA - SMS_Fax", "420"),
    IGA_ADMIN("IGA Admin", "606"),
    IGA_SUB_ADMIN("IGA Sub Admin", "607"),
    IGUD("Igud", "115"),
    INTEROP_ADMIN("Interop Admin", "105"),
    ISKA_TOVA("Iska Tova", "193"),
    ISRACARD("Isracard", "184"),
    ISRACARD_ALLOWED_SMTP("Isracard (Allowed SMTP users)", "113"),
    ISRACARD_OUTLOOK_PLUGIN("Isracard (Outlook Plug-in Users)", "185"),
    ISRACARD_AD_USERS("Isracard AD-Users", "210"),
    ISRACARD_ADMIN("Isracard Admin", "200"),
    LANIADO("Laniado", "424"),
    LANIADO_HOSPITAL("Laniado Hospital", "212"),
    MOD_BASIC("MOD Basic", "430"),
    MOD_DEVELOPER("MOD Developer", "431"),
    MOD_PRO_MANAGER("MOD Pro Manager", "432"),
    MOD_PRO_USER("MOD Pro User", "433"),
    MUA_CONSUMER("MUA Consumer", "938"),
    MATE_BINYAMIN("Mate Binyamin", "119"),
    MESSAGENET("Messagenet", "224"),
    MULTIALERT("MultiAlert", "180"),
    PSDIRECT("PSDirect", "916"),
    POSTPAID("Postpaid", "172"),
    POSTPAID_SMPP_USER("Postpaid SMPP user", "261"),
    PREPAID_IL_ECONOMY("Prepaid IL Economy", "275"),
    PROMANAGER("Pro Manager", "911"),
    PROUSER("Pro User", "912"),
    RND("RND", "411"),
    READ_ONLY_ADMIN_EMG("Read Only Admin EMG", "406"),
    READ_ONLY_ADMINISTRATOR("Read Only Administrator", "103"),
    ROGERS_2_WAY("Rogers 2 way", "518"),
    SAP_AUTHENTICATION365("SAP Authentication365", "942"),
    SAP_PEOPLECONNECT365("SAP PeopleConnect365", "943"),
    SEM_IP("SEM IP", "277"),
    SEM_IP_ADMIN("SEM IP Admin", "278"),
    SEM_IP_ADMIN_SECURE("SEM IP Admin Secure", "280"),
    SEM_IP_SECURE("SEM IP Secure", "279"),
    SMPP_USER("SMPP user", "260"),
    SMS_MMS_PREPAID("SMS-MMS Prepaid", "143"),
    SHOMRON("Shomron", "120"),
    SOROKA_HOSPITAL("Soroka Hospital", "215"),
    SPRINT_ALL_US("Sprint - All US", "242"),
    SPRINT_INTERNATIONAL("Sprint - International", "276"),
    SPRINT_BROKER_ADMIN("Sprint Broker Admin", "403"),
    SPRINT_ONLY("Sprint Only", "231"),
    SUB_MANAGER("Sub Manager", "944"),
    SUB_USER("Sub User", "945"),
    TELUS_TAP_MESSAGING_GATEWAY("TELUS TAP Messaging Gateway", "613"),
    TELUS_TAP_MESSAGING_GATEWAY_ADMIN("TELUS TAP Messaging Gateway Admin", "704"),
    TELEMESSAGE_SUB_ADMIN("TeleMessage Sub Admin", "106"),
    TELUS_2_WAY("Telus 2 way", "513"),
    TICKETMASTER("Ticketmaster", "428"),
    TICKETMASTER_MANAGER("Ticketmaster Manager", "427"),
    US_ONLY_POSTPAID("US Only PostPaid", "159"),
    UNBRANDED("Unbranded", "116"),
    UNBRANDED_INTERACTIVE("Unbranded Interactive", "211"),
    VERIZON("Verizon", "199"),
    VIAERO_SMS_TO_LANDLINE("Viaero SMS to LandLine", "263"),
    VIAERO_WIRELESS_ADMIN("Viaero Wireless Admin", "707"),
    VIAERO_WIRELESS_CUSTOMER_ADMIN("Viaero Wireless Customer Admin", "708"),
    VIAERO_WIRELESS_MESSAGING_GATEWAY("Viaero Wireless Messaging Gateway", "341"),
    VIAERO_WIRELESS_RO_ADMIN("Viaero Wireless RO Admin", "709"),
    WD_PRO_USER("WD -Pro User", "922"),
    WIND_ADMIN("WIND Admin", "107"),
    WIND_SMS_TO_LANDLINE("WIND SMS to LandLine", "289"),
    WIND_SMS_TO_LANDLINE_FOR_BILLING("WIND SMS to LandLine for Billing", "290");

    private final String displayName;
    private final String value;

    UserTypes(String displayName, String value) {
        this.displayName = displayName;
        this.value = value;
    }

    /**
     * Get UserType by value
     */
    public static UserTypes getByValue(String value) {
        for (UserTypes type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown user type value: " + value);
    }

    /**
     * Get UserType by display name
     */
    public static UserTypes getByDisplayName(String displayName) {
        for (UserTypes type : values()) {
            if (type.displayName.equalsIgnoreCase(displayName.trim())) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown user type display name: " + displayName);
    }
}

