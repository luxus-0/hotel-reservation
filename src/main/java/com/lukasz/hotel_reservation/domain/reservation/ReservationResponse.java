package com.lukasz.hotel_reservation.domain.reservation;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record ReservationResponse(
        @NotNull UUID id,
        @NotNull ReservationStatus status,
        @NotNull LocalDateTime checkIn,
        @NotNull LocalDateTime checkOut) {
}
