package com.lukasz.hotel_reservation.domain.room.dto;

import com.lukasz.hotel_reservation.domain.room.RoomStatus;
import com.lukasz.hotel_reservation.domain.room.RoomType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateRoomRequest(
        @NotNull @Min(1) int number,
        @NotNull @Enumerated(EnumType.STRING) RoomType type,
        @NotNull @Enumerated(EnumType.STRING) RoomStatus status,
        @NotNull @Min(1) Long version
) {
}
