package com.lukasz.hotel_reservation.domain.room;

import com.lukasz.hotel_reservation.domain.room.dto.RoomFinderResponse;

public class RoomMapper {
    public static Room toRoom(RoomFinderResponse room) {
        return Room.builder()
                .id(room.uuid())
                .type(room.type())
                .number(room.number())
                .status(room.status())
                .build();
    }
}
