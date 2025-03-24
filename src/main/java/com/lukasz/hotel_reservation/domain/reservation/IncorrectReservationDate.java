package com.lukasz.hotel_reservation.domain.reservation;

public class IncorrectReservationDate extends RuntimeException {
    public IncorrectReservationDate(String message) {
        super(message);
    }
}
