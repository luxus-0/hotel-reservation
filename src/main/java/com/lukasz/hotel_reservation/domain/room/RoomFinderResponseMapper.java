package com.lukasz.hotel_reservation.domain.room;

class RoomFinderResponseMapper {
    public static RoomFinderResponse mapToRoomFinderResponse(Room room) {
        return RoomFinderResponse.builder()
                .id(room.getId())
                .number(room.getNumber())
                .status(room.getStatus())
                .type(room.getType())
                .build();
    }
}
