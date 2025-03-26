package com.lukasz.hotel_reservation.domain.reservation.exceptions;

public class IncorrectReservationDateException extends RuntimeException {
    public IncorrectReservationDateException(String message) {
        super(message);
    }
}
