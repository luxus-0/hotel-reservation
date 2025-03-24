package com.lukasz.hotel_reservation.domain.reservation;

import java.util.UUID;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException(UUID reservationId) {
        super("Reservation Id:" + reservationId + "not found");
    }

    public ReservationNotFoundException(String message){
        super(message);
    }
}
