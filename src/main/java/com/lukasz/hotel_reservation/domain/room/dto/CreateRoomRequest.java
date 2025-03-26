package com.lukasz.hotel_reservation.domain.room.dto;

import com.lukasz.hotel_reservation.domain.room.RoomStatus;
import com.lukasz.hotel_reservation.domain.room.RoomType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateRoomRequest(
        @NotNull @GeneratedValue(strategy = GenerationType.UUID) UUID uuid,
        @NotNull @Min(1) int number,
        @NotNull @Enumerated(EnumType.STRING) RoomType type,
        @NotNull @Enumerated(EnumType.STRING) RoomStatus status,
        @NotNull @Min(1) Long version
) {
}
