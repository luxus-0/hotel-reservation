package com.lukasz.hotel_reservation.domain.customer.dto;

import com.lukasz.hotel_reservation.domain.address.dto.AddressFinderResponse;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

import static com.lukasz.hotel_reservation.domain.customer.CustomerConstantMessage.*;

@Builder
public record CustomerFinderResponse(
        @NotNull
        UUID uuid,
        @NotNull @Pattern(regexp = CUSTOMER_NAME_REGEX, message = CUSTOMER_NAME_REGEX_MESSAGE)
        String name,

        @NotNull @Pattern(regexp = CUSTOMER_SURNAME_REGEX, message = CUSTOMER_SURNAME_REGEX_MESSAGE)
        String surname,

        @NotNull @Past LocalDate birthDate,
        @NotNull @Pattern(regexp = PHONE_REGEX, message = PHONE_REGEX_MESSAGE)
        String phone,

        @NotNull @Pattern(regexp = EMAIL_REGEX, message = EMAIL_REGEX_MESSAGE)
        String email,

        AddressFinderResponse address) {

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


