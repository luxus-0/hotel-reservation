package com.lukasz.hotel_reservation.domain.reservation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    byte findReservationByPdf(byte[] pdf);
}
