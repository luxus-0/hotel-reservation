package com.lukasz.hotel_reservation.domain.customer;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static com.lukasz.hotel_reservation.domain.contact.ContactConstantMessage.*;
import static com.lukasz.hotel_reservation.domain.customer.CustomerConstantMessage.*;
import static com.lukasz.hotel_reservation.domain.customer.CustomerConstantMessage.CUSTOMER_SURNAME_REGEX_MESSAGE;

@Builder
public record CustomerFinderResponse(
        @NotNull @Pattern(regexp = CUSTOMER_NAME_REGEX, message = CUSTOMER_NAME_REGEX_MESSAGE) String name,
        @NotNull @Pattern(regexp = CUSTOMER_SURNAME_REGEX, message = CUSTOMER_SURNAME_REGEX_MESSAGE) String surname,
        @NotNull @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate birthDate,
        @NotNull @Pattern(regexp = PHONE_REGEX, message = PHONE_REGEX_MESSAGE) String phone,
        @NotNull @Pattern(regexp = EMAIL_REGEX, message = EMAIL_REGEX_MESSAGE) String email) {
}
