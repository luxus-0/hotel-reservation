package com.lukasz.hotel_reservation.domain.reservation.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ReservationCreatorResponse(
        UUID reservationId,
        String message
) {
}
