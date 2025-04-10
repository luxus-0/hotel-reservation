package com.lukasz.hotel_reservation.domain.address.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import static com.lukasz.hotel_reservation.domain.address.AddressConstantMessage.*;

@Builder
public record AddressFinderResponse(
        @Pattern(regexp = STREET_REGEX, message = STREET_REGEX_MESSAGE)
        String street,

        @Min(1) @Pattern(regexp = STREET_NUMBER_REGEX, message = STREET_NUMBER_REGEX_MESSAGE)
        String number,

        @Pattern(regexp = POSTAL_CODE_REGEX, message = POSTAL_CODE_REGEX_MESSAGE)
        String postalCode,

        @Pattern(regexp = CITY_REGEX, message = CITY_REGEX_MESSAGE)
        String city,

        @Pattern(regexp = COUNTRY_REGEX, message = COUNTRY_REGEX_MESSAGE)
        String country
) {
}
