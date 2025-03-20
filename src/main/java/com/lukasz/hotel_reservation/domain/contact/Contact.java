package com.lukasz.hotel_reservation.domain.contact;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Contact {
    @Id
    private Long id;
    private String email;
    private String phoneNumbers;


    public Contact() {
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumbers() {
        return phoneNumbers;
    }
}
