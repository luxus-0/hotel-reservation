package com.lukasz.hotel_reservation.domain.room.exceptions;

import java.util.UUID;

public class RoomNotAvailableException extends RuntimeException {
    public RoomNotAvailableException(UUID roomId) {
        super("Room ID: " + roomId + " not found");
    }
}
