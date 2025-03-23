package com.lukasz.hotel_reservation.domain.reservation;

import com.lukasz.hotel_reservation.domain.customer.CustomerFinderResponse;
import com.lukasz.hotel_reservation.domain.document.DocumentResponse;
import com.lukasz.hotel_reservation.domain.room.RoomFinderResponse;
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
}
