package com.lukasz.hotel_reservation.domain.contact;

import jakarta.validation.constraints.Pattern;

import static com.lukasz.hotel_reservation.domain.contact.ContactConstantMessage.EMAIL_REGEX;
import static com.lukasz.hotel_reservation.domain.contact.ContactConstantMessage.EMAIL_REGEX_MESSAGE;
import static com.lukasz.hotel_reservation.domain.contact.ContactConstantMessage.PHONE_REGEX;
import static com.lukasz.hotel_reservation.domain.contact.ContactConstantMessage.PHONE_REGEX_MESSAGE;

public record ContactFinderResponse(
        @Pattern(regexp = EMAIL_REGEX, message = EMAIL_REGEX_MESSAGE)
        String email,
        @Pattern(regexp = PHONE_REGEX, message = PHONE_REGEX_MESSAGE)
        String phone) {
}
