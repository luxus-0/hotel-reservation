package com.lukasz.hotel_reservation.domain.reservation.exceptions;

public class DuplicateReservationException extends RuntimeException {
    public DuplicateReservationException(String message) {
        super(message);
    }
}
