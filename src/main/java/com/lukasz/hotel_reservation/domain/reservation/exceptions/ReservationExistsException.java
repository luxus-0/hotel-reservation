package com.lukasz.hotel_reservation.domain.reservation.exceptions;

public class ReservationExistsException extends RuntimeException {
    public ReservationExistsException(String message) {
        super(message);
    }
}
