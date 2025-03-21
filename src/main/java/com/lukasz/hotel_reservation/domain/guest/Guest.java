package com.lukasz.hotel_reservation.domain.guest;

import com.lukasz.hotel_reservation.domain.address.Address;
import com.lukasz.hotel_reservation.domain.contact.Contact;
import com.lukasz.hotel_reservation.domain.document.Document;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
