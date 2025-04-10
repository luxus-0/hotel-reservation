package com.lukasz.hotel_reservation.domain.room.exceptions;

import java.util.UUID;

public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException(UUID id) {
        super("Room uuid: " + id + " not found");
    }

    public RoomNotFoundException() {
        super("Room not found");
    }
}
