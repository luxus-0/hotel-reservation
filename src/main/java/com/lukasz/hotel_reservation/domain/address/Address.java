package com.lukasz.hotel_reservation.domain.address;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String postalCode;
    private String city;
    private String street;
    private String number;
    private String country;
}
