package com.lukasz.hotel_reservation.domain.reservation.dto;

import com.lukasz.hotel_reservation.domain.reservation.ReservationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ReservationFinderResponse(
        @NotNull ReservationStatus status,
        @NotNull LocalDateTime checkIn,
        @NotNull LocalDateTime checkOut) {

    public String toString() {
        return ("""
                \nReservation
                Status: %s
                Check in: %s
                Check out: %s""").formatted(status, checkIn, checkOut);
    }
}
