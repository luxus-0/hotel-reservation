package com.lukasz.hotel_reservation.domain.reservation;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReservationRequest(
        @NotNull UUID id,
        @NotNull ReservationStatus status,
        @NotNull LocalDateTime checkIn,
        @NotNull LocalDateTime checkOut,
        @NotNull LocalDateTime createdAt) {
}
