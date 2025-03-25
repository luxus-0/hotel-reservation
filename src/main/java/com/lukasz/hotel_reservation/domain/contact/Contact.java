package com.lukasz.hotel_reservation.domain.contact;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static com.lukasz.hotel_reservation.domain.contact.ContactConstantMessage.EMAIL_REGEX;
import static com.lukasz.hotel_reservation.domain.contact.ContactConstantMessage.EMAIL_REGEX_MESSAGE;
import static com.lukasz.hotel_reservation.domain.contact.ContactConstantMessage.PHONE_REGEX;
import static com.lukasz.hotel_reservation.domain.contact.ContactConstantMessage.PHONE_REGEX_MESSAGE;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Pattern(regexp = EMAIL_REGEX, message = EMAIL_REGEX_MESSAGE)
    private String email;
    @Pattern(regexp = PHONE_REGEX, message = PHONE_REGEX_MESSAGE)
    private String phone;

    @Override
    public String toString() {
        return "Contact(email=" + email + "phone=" + phone;
    }
}

