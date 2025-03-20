package com.lukasz.hotel_reservation.domain.reservation.exceptions;

public class ReservationNotFoundException extends RuntimeException{
    public ReservationNotFoundException(){
        super("Reservation not found");
    }
}
