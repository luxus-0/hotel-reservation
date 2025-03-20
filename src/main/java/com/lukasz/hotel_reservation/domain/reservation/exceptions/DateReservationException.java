package com.lukasz.hotel_reservation.domain.reservation.exceptions;

public class DateReservationException extends RuntimeException {
    public DateReservationException(String message) {
        super(message);
    }
}
