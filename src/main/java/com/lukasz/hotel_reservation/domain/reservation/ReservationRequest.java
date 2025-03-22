package com.lukasz.hotel_reservation.domain.reservation;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReservationRequest(UUID id, ReservationStatus status, LocalDateTime checkIn, LocalDateTime checkOut, LocalDateTime createdAt) {

}
