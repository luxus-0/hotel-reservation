package com.lukasz.hotel_reservation.domain.hotel.exceptions;

public class HotelNotFoundException extends RuntimeException {
    public HotelNotFoundException() {
        super("Hotel not found");
    }
}
