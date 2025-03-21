package com.lukasz.hotel_reservation.domain.room.exceptions;

import java.util.UUID;

public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException(UUID roomId) {
        super("Room ID: " + roomId + " not found");
    }
}
