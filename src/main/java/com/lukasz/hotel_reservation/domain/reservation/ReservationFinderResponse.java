package com.lukasz.hotel_reservation.domain.reservation;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record ReservationFinderResponse(
        @NotNull UUID id,
        @NotNull ReservationStatus status,
        @NotNull LocalDateTime checkIn,
        @NotNull LocalDateTime checkOut) {

    public String toString() {
        return ("""
                \nReservation
                Id: %s
                Status: %s
                Check in: %s
                Check out: %s""").formatted(id, status, checkIn, checkOut);
    }
}
