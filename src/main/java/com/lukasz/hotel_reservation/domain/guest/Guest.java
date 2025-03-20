package com.lukasz.hotel_reservation.domain.guest;

import com.lukasz.hotel_reservation.domain.document.Document;
import com.lukasz.hotel_reservation.domain.address.Address;
import com.lukasz.hotel_reservation.domain.contact.Contact;
import com.lukasz.hotel_reservation.domain.reservation.Reservation;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Guest {
    @Id
    private UUID id;
    private String name;
    private String surname;
    private LocalDate birthDate;
    @OneToOne
    private Address address;
    @OneToOne
    private Contact contact;
    @OneToOne
    private Document document;
}
