package com.lukasz.hotel_reservation.domain.room;

import com.lukasz.hotel_reservation.domain.room.dto.RoomFinderResponse;

class RoomFinderResponseMapper {
    public static RoomFinderResponse mapToRoomFinderResponse(Room room) {
        return RoomFinderResponse.builder()
                .number(room.getNumber())
                .status(room.getStatus())
                .type(room.getType())
                .build();
    }
}
