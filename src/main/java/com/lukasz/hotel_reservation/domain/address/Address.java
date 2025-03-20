package com.lukasz.hotel_reservation.domain.address;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    private Long id;
    private String postalCode;
    private String city;
    private String street;
    private int number;
    private String country;
}
