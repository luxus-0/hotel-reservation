package com.lukasz.hotel_reservation.domain.address;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import static com.lukasz.hotel_reservation.domain.address.AddressConstantMessage.CITY_REGEX;
import static com.lukasz.hotel_reservation.domain.address.AddressConstantMessage.CITY_REGEX_MESSAGE;
import static com.lukasz.hotel_reservation.domain.address.AddressConstantMessage.COUNTRY_REGEX;
import static com.lukasz.hotel_reservation.domain.address.AddressConstantMessage.COUNTRY_REGEX_MESSAGE;
import static com.lukasz.hotel_reservation.domain.address.AddressConstantMessage.POSTAL_CODE_REGEX;
import static com.lukasz.hotel_reservation.domain.address.AddressConstantMessage.POSTAL_CODE_REGEX_MESSAGE;
import static com.lukasz.hotel_reservation.domain.address.AddressConstantMessage.STREET_NUMBER_REGEX;
import static com.lukasz.hotel_reservation.domain.address.AddressConstantMessage.STREET_NUMBER_REGEX_MESSAGE;
import static com.lukasz.hotel_reservation.domain.address.AddressConstantMessage.STREET_REGEX;
import static com.lukasz.hotel_reservation.domain.address.AddressConstantMessage.STREET_REGEX_MESSAGE;

public record AddressCreatorRequest(
        @NotNull @Pattern(regexp = STREET_REGEX, message = STREET_REGEX_MESSAGE)
        String street,

        @NotNull @Pattern(regexp = STREET_NUMBER_REGEX, message = STREET_NUMBER_REGEX_MESSAGE)
        int streetNumber,

        @NotNull @Pattern(regexp = POSTAL_CODE_REGEX, message = POSTAL_CODE_REGEX_MESSAGE)
        String postalCode,

        @NotNull @Pattern(regexp = CITY_REGEX, message = CITY_REGEX_MESSAGE)
        String city,

        @NotNull @Pattern(regexp = COUNTRY_REGEX, message = COUNTRY_REGEX_MESSAGE)
        String country) {
}
