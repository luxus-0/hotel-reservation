package com.lukasz.hotel_reservation.domain.reservation;

import com.lukasz.hotel_reservation.domain.reservation.exceptions.ReservationExistsException;
import com.lukasz.hotel_reservation.domain.reservation.exceptions.ReservationNotFoundException;
import com.lukasz.hotel_reservation.domain.room.Room;
import com.lukasz.hotel_reservation.domain.room.RoomRepository;
import com.lukasz.hotel_reservation.domain.room.RoomStatus;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@Log4j2
@AllArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationValidator reservationValidator;

    public void createReservation(ReservationRequest reservationRequest) {
        reservationValidator.validate(reservationRequest.checkIn(), reservationRequest.checkOut());
        if (reservationRepository.existsById(reservationRequest.id())) {
            throw new ReservationExistsException("Reservation with id " + reservationRequest.id() + " already exists");
        }

        Reservation reservation = Reservation.builder()
                .id(reservationRequest.id())
                .status(reservationRequest.status())
                .checkIn(reservationRequest.checkIn())
                .checkOut(reservationRequest.checkOut())
                .createdAt(reservationRequest.createdAt())
                .build();

        reservationRepository.save(reservation);
    }

    public ReservationResponse findReservation(UUID reservationId) {
        return reservationRepository.findById(reservationId)
                .map(this::toReservationResponse)
                .orElseThrow(() -> new ReservationNotFoundException(reservationId));
    }

    public void cancelReservation(UUID reservationId) {
        reservationRepository.findById(reservationId)
                .ifPresent(reservation -> {

                    reservation.setStatus(ReservationStatus.CANCELLED);
                    reservationRepository.save(reservation);
                    log.info("Reservation id: {} has been cancelled", reservationId);
                });
    }

    public long countDays(LocalDateTime checkIn, LocalDateTime checkOut) {
        reservationValidator.validate(checkIn, checkOut);
        LocalDateTime timeCheckIn = checkIn.withHour(14).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime timeCheckOut = checkOut.withHour(12).withMinute(0).withSecond(0).withNano(0);
        return ChronoUnit.DAYS.between(timeCheckIn, timeCheckOut);
    }

    private ReservationResponse toReservationResponse(Reservation savedReservation) {
        return ReservationResponse.builder()
                .status(savedReservation.getStatus())
                .guestId(savedReservation.getGuest().getId())
                .roomId(savedReservation.getRoom().getId())
                .checkIn(savedReservation.getCheckIn())
                .checkOut(savedReservation.getCheckOut())
                .build();
    }
}
