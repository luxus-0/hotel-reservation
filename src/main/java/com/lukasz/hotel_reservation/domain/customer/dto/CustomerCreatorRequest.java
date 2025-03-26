package com.lukasz.hotel_reservation.domain.customer.dto;

import com.lukasz.hotel_reservation.domain.address.dto.AddressFinderResponse;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

import static com.lukasz.hotel_reservation.domain.customer.CustomerConstantMessage.*;

public record CustomerCreatorRequest(
        @NotNull
        @Pattern(regexp = CUSTOMER_NAME_REGEX, message = CUSTOMER_NAME_REGEX_MESSAGE)
        String name,

        @NotNull
        @Pattern(regexp = CUSTOMER_SURNAME_REGEX, message = CUSTOMER_SURNAME_REGEX_MESSAGE)
        String surname,

        @NotNull
        LocalDate birthDate,

        @NotNull
        @Pattern(regexp = PHONE_REGEX, message = PHONE_REGEX_MESSAGE)
        String phone,

        @NotNull
        @Pattern(regexp = EMAIL_REGEX, message = PHONE_REGEX_MESSAGE)
        String email,

        AddressFinderResponse address,
        DocumentResponse document) {
}
