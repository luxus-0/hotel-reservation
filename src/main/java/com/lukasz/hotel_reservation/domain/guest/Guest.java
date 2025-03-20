package com.lukasz.hotel_reservation.domain.guest;

import com.lukasz.hotel_reservation.document.Document;
import com.lukasz.hotel_reservation.domain.address.Address;
import com.lukasz.hotel_reservation.domain.contact.Contact;
import com.lukasz.hotel_reservation.domain.reservation.Reservation;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
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
    @OneToMany
    private List<Reservation> reservations;

    public Guest() {
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Address getAddress() {
        return address;
    }

    public Contact getContact() {
        return contact;
    }

    public Document getDocument() {
        return document;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}
