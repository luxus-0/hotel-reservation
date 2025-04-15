package com.lukasz.hotel_reservation.domain.reservation;

import com.lukasz.hotel_reservation.domain.reservation.dto.ReservationFinderResponse;

class ReservationMapper {
    static ReservationFinderResponse toReservation(Reservation savedReservation) {
        return ReservationFinderResponse.builder()
                .status(savedReservation.getStatus())
                .checkIn(savedReservation.getCheckIn())
                .checkOut(savedReservation.getCheckOut())
                .build();
    }

}
