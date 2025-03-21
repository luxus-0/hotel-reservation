package com.lukasz.hotel_reservation.domain.reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Getter
public class ReservationResponse {
    private ReservationStatus status;
    private UUID guestId;
    private UUID roomId;
    private LocalDate checkIn;
    private LocalDate checkOut;
}
