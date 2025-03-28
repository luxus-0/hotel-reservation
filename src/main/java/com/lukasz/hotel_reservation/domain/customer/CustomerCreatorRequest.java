package com.lukasz.hotel_reservation.domain.customer;

import com.lukasz.hotel_reservation.domain.address.AddressFinderResponse;
import com.lukasz.hotel_reservation.domain.contact.ContactFinderResponse;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

import static com.lukasz.hotel_reservation.domain.customer.CustomerConstantMessage.CUSTOMER_NAME_REGEX;
import static com.lukasz.hotel_reservation.domain.customer.CustomerConstantMessage.CUSTOMER_NAME_REGEX_MESSAGE;
import static com.lukasz.hotel_reservation.domain.customer.CustomerConstantMessage.CUSTOMER_SURNAME_REGEX;
import static com.lukasz.hotel_reservation.domain.customer.CustomerConstantMessage.CUSTOMER_SURNAME_REGEX_MESSAGE;

public record CustomerCreatorRequest(
        @NotNull @Pattern(regexp = CUSTOMER_NAME_REGEX, message = CUSTOMER_NAME_REGEX_MESSAGE) String name,
        @NotNull @Pattern(regexp = CUSTOMER_SURNAME_REGEX, message = CUSTOMER_SURNAME_REGEX_MESSAGE) String surname,
        @NotNull LocalDate birthDate,
        ContactFinderResponse contact,
        AddressFinderResponse address,
        DocumentResponse document) {
}
