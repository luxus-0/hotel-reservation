package com.lukasz.hotel_reservation.domain.customer;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import java.time.LocalDate;

import static com.lukasz.hotel_reservation.domain.contact.ContactConstantMessage.EMAIL_REGEX;
import static com.lukasz.hotel_reservation.domain.contact.ContactConstantMessage.EMAIL_REGEX_MESSAGE;
import static com.lukasz.hotel_reservation.domain.contact.ContactConstantMessage.PHONE_REGEX;
import static com.lukasz.hotel_reservation.domain.contact.ContactConstantMessage.PHONE_REGEX_MESSAGE;
import static com.lukasz.hotel_reservation.domain.customer.CustomerConstantMessage.CUSTOMER_NAME_REGEX;
import static com.lukasz.hotel_reservation.domain.customer.CustomerConstantMessage.CUSTOMER_NAME_REGEX_MESSAGE;
import static com.lukasz.hotel_reservation.domain.customer.CustomerConstantMessage.CUSTOMER_SURNAME_REGEX;
import static com.lukasz.hotel_reservation.domain.customer.CustomerConstantMessage.CUSTOMER_SURNAME_REGEX_MESSAGE;

@Builder
public record CustomerFinderResponse(
        @NotNull @Pattern(regexp = CUSTOMER_NAME_REGEX, message = CUSTOMER_NAME_REGEX_MESSAGE) String name,
        @NotNull @Pattern(regexp = CUSTOMER_SURNAME_REGEX, message = CUSTOMER_SURNAME_REGEX_MESSAGE) String surname,
        @NotNull @Past LocalDate birthDate,
        @NotNull @Pattern(regexp = PHONE_REGEX, message = PHONE_REGEX_MESSAGE) String phone,
        @NotNull @Pattern(regexp = EMAIL_REGEX, message = EMAIL_REGEX_MESSAGE) String email) {

    public String toString() {
        return ("""
                \nCustomer
                Name: %s
                Surname: %s
                Birthday: %s
                Phone: %s
                Email: %s""").formatted(name, surname, birthDate, phone, email);
    }
}


