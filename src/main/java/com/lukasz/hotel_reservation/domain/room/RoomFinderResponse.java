package com.lukasz.hotel_reservation.domain.room;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record RoomFinderResponse(
        @NotNull UUID id,
        @Min(1) @NotNull int number,
        @NotNull RoomType type,
        @NotNull RoomStatus status) {

    public String toString() {
        return ("""
                \nRoom
                id: %s
                Number: %s
                Type: %s
                Status: %s""").formatted(id, number, type, status);
    }
}

