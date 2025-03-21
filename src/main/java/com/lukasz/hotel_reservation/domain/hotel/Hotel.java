package com.lukasz.hotel_reservation.domain.hotel;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {
    @Id
    private Long id;
    private String name;
    private String address;
    private String description;
    private String image;
    private String rating;
}
