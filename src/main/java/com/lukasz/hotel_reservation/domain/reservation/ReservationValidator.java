package com.lukasz.hotel_reservation.domain.reservation;

import com.lukasz.hotel_reservation.domain.reservation.exceptions.IncorrectReservationDate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
class ReservationValidator {
    public void validate(LocalDateTime checkIn, LocalDateTime checkOut) {
        if (checkOut.isBefore(checkIn)) {
            throw new IncorrectReservationDate("Check-out cannot be before check-out");
        }
        if (checkOut.isEqual(checkIn)) {
            throw new IncorrectReservationDate("Check-out cannot be equal check-in");
        }
    }
}
