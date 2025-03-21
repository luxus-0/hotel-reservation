package com.lukasz.hotel_reservation.domain.reservation;

import com.lukasz.hotel_reservation.domain.reservation.exceptions.DuplicateReservationException;
import com.lukasz.hotel_reservation.domain.reservation.exceptions.IncorrectReservationDate;
import com.lukasz.hotel_reservation.domain.reservation.exceptions.ReservationNotFoundException;
import com.lukasz.hotel_reservation.domain.room.Room;
import com.lukasz.hotel_reservation.domain.room.RoomRepository;
import com.lukasz.hotel_reservation.domain.room.RoomStatus;
import com.lukasz.hotel_reservation.domain.room.exceptions.RoomNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Log4j2
class ReservationService {
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
    }

    public void createReservation(Reservation reservation) {
        if (reservationRepository.existsByRoom_IdAndGuest_Id(
                reservation.getRoom().getId(),
                reservation.getGuest().getId())) {
            throw new DuplicateReservationException("Guest already has a reservation for this room");
        }

        Room room = getAvailableRoom(reservation);
        room.setStatus(RoomStatus.RESERVED);
        roomRepository.save(room);
        reservationRepository.save(reservation);
    }

    public ReservationResponse findReservation(UUID reservationId) {
        return reservationRepository.findById(reservationId)
                .map(this::toReservationResponse)
                .orElseThrow(() -> new ReservationNotFoundException(reservationId));
    }

    public void cancelReservation(UUID reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));

        cancelReservationWithRoomStatusAvailable(reservation);
    }

    public void cancelReservation() {
        List<Reservation> reservations = reservationRepository.findAll();
        if (reservations.isEmpty()) {
            throw new ReservationNotFoundException("Reservation not found");
        }

        reservations.stream()
                .filter(Objects::nonNull)
                .forEach(this::cancelReservationWithRoomStatusAvailable);
    }

    private void cancelReservationWithRoomStatusAvailable(Reservation reservation) {
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);

        Room room = reservation.getRoom();
        room.setStatus(RoomStatus.AVAILABLE);
        roomRepository.save(room);
    }

    public long countDays(LocalDateTime checkIn, LocalDateTime checkOut) {
        validReservationDate(checkIn, checkOut);
        LocalDateTime timeCheckIn = checkIn.withHour(14).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime timeCheckOut = checkIn.withHour(14).withMinute(0).withSecond(0).withNano(0);

        return ChronoUnit.DAYS.between(timeCheckIn, timeCheckOut);
    }

    private static void validReservationDate(LocalDateTime checkIn, LocalDateTime checkOut) {
        if (checkOut.isBefore(checkIn)) {
            throw new IncorrectReservationDate("Check-out cannot be before check-out");
        }
        if (checkOut.isEqual(checkIn)) {
            throw new IncorrectReservationDate("Check-out cannot be equal check-in");
        }
    }


    private Room getAvailableRoom(Reservation reservation) {
        return roomRepository.findById(reservation.getRoom().getId())
                .filter(room -> room.getStatus().equals(RoomStatus.AVAILABLE))
                .orElseThrow(() -> new RoomNotFoundException(reservation.getRoom().getId()));
    }

    private void validateDuplicatedReservation(Reservation reservation) {

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
