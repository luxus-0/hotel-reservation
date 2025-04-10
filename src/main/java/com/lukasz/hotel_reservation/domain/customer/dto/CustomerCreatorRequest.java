package com.lukasz.hotel_reservation.domain.customer.dto;

import com.lukasz.hotel_reservation.domain.address.dto.AddressFinderResponse;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static com.lukasz.hotel_reservation.domain.customer.CustomerConstantMessage.*;

public record CustomerCreatorRequest(
        @Pattern(regexp = CUSTOMER_NAME_REGEX, message = CUSTOMER_NAME_REGEX_MESSAGE)
        String name,

        @Pattern(regexp = CUSTOMER_SURNAME_REGEX, message = CUSTOMER_SURNAME_REGEX_MESSAGE)
        String surname,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDate birthDate,

        @Pattern(regexp = PHONE_REGEX, message = PHONE_REGEX_MESSAGE)
        String phone,

        @Pattern(regexp = EMAIL_REGEX, message = EMAIL_REGEX_MESSAGE)
        String email,

        AddressFinderResponse address,
        CustomerDocumentResponse document) {
}
