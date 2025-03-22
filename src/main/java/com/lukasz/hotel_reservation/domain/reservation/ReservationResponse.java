package com.lukasz.hotel_reservation.domain.reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Getter
public class ReservationResponse {
    private UUID id;
    private ReservationStatus status;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
}
