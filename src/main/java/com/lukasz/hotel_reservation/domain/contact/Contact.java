package com.lukasz.hotel_reservation.domain.contact;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    @Id
    private Long id;
    private String email;
    private String phoneNumber;
}
