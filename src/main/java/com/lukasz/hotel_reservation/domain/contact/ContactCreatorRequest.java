package com.lukasz.hotel_reservation.domain.contact;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import static com.lukasz.hotel_reservation.domain.contact.ContactConstantMessage.*;

public record ContactCreatorRequest(
        @NotNull @Pattern(regexp = EMAIL_REGEX, message = EMAIL_REGEX_MESSAGE)
        String email,

        @NotNull @Pattern(regexp = PHONE_REGEX, message = PHONE_REGEX_MESSAGE)
        String phone) {
}
