package com.lukasz.hotel_reservation.domain.contact;

public class ContactNotFoundException extends RuntimeException {
    public ContactNotFoundException(String message) {
        super(message);
    }
}
