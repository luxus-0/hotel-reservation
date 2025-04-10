package com.lukasz.hotel_reservation.domain.room.dto;

import com.lukasz.hotel_reservation.domain.room.RoomStatus;
import com.lukasz.hotel_reservation.domain.room.RoomType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record RoomFinderResponse(
        @NotNull UUID uuid,
        @Min(1) @NotNull Integer number,
        @NotNull RoomType type,
        @NotNull RoomStatus status) {

    public String toString() {
        return ("""
                \nRoom
                id: %s
                Number: %s
                Type: %s
                Status: %s""").formatted(uuid, number, type, status);
    }
}

