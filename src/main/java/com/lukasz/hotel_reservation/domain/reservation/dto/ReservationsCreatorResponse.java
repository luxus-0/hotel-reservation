package com.lukasz.hotel_reservation.domain.reservation.dto;

import com.lukasz.hotel_reservation.domain.reservation.Reservation;

import java.util.List;

public record ReservationsCreatorResponse(List<Reservation> reservations, String message) {
}
