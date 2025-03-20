package com.lukasz.hotel_reservation.domain.room.exceptions;

public class RoomNotFoundException extends RuntimeException{
    public RoomNotFoundException(String message){
        super(message);
    }
}
