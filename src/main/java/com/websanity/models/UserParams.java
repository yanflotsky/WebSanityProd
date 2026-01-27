package com.websanity.models;

import com.websanity.enums.Country;
import com.websanity.enums.Language;
import com.websanity.enums.UserTypes;
import com.websanity.enums.TimeZone;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class UserParams {

    // Service Level & Admin
    private UserTypes userType;
    private String customerAdministrator;
    private String prepaidFactor;
    private String monthlyCharge;

    // User Information
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String platformAccountId;
    private String userID;

    // Location & Language
    private TimeZone timeZone;
    private Language language;
    private Country country;

    // Phone Numbers
    private Country homeCountryCode;
    private String homeArea;
    private String homePhone;

    private Country mobileCountryCode;
    private String mobileArea;
    private String mobilePhone;

    private Country businessCountryCode;
    private String businessArea;
    private String businessPhone;
    private String businessExt;

    private Country faxCountryCode;
    private String faxArea;
    private String faxPhone;

    // Additional Information
    private String email;
    private String company;
    private Boolean addToGlobalAddressBook;
    private String allowedManagedUsers;
    private String uniqueCustomerCode;
    private String udid;
    private Boolean sendWelcomeMessageEmail;
    private Boolean sendWelcomeMessageMobile;
    private String assignToPlan;
    private String billingType;
    private String billingReoccurring;
    private String appTextSupport;
    private String voiceCallSupport;
    private String enterpriseSetting;
    private String enterpriseNumber;
    private String whatsAppApi;
    private String forwardInboxTo;
    private String sendOutgoingMessagesViaProvider;

    /**
     * Create minimal user with only required fields
     */
    public static UserParams createMinimal(
            String firstName,
            String lastName,
            String username,
            String password,
            String email
    ) {
        return UserParams.builder()
                .userType(UserTypes.PROMANAGER)
                .firstName(firstName)
                .lastName(lastName)
                .username(username)
                .password(password)
                .email(email)
                .country(Country.UNITED_STATES)
                .timeZone(TimeZone.EST)
                .language(Language.ENGLISH_US)
                .build();
    }

    /**
     * Create standard user with common fields
     */
    public static UserParams createStandard(
            UserTypes userTypes,
            String firstName,
            String lastName,
            String username,
            String password,
            String email,
            Country country,
            TimeZone timeZone,
            Language language
    ) {
        return UserParams.builder()
                .userType(userTypes)
                .firstName(firstName)
                .lastName(lastName)
                .username(username)
                .password(password)
                .email(email)
                .country(country)
                .timeZone(timeZone)
                .language(language)
                .build();
    }
}

