package com.websanity.models;

import com.websanity.enums.Country;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class for Contact parameters
 * Used in Admin Portal My Contacts functionality
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactParams {

    // Personal Information
    private String firstName;
    private String lastName;
    private String jobTitle;

    // Mobile Phone
    private Country mobileCountry;
    private String mobileTelephone;

    // Home Phone
    private Country homeCountry;
    private String homeTelephone;

    // Business Phone
    private Country businessCountry;
    private String businessTelephone;
    private String businessExt;

    // Fax
    private Country faxCountry;
    private String faxTelephone;

    // Contact Information
    private String communicationEmail;
    private String company;
    private String department;
}

