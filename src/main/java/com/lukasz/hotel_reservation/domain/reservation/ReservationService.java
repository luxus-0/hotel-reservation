package com.lukasz.hotel_reservation.domain.reservation;

import com.lukasz.hotel_reservation.domain.reservation.exceptions.DuplicateReservationException;
import com.lukasz.hotel_reservation.domain.reservation.exceptions.ReservationNotFoundException;
import com.lukasz.hotel_reservation.domain.room.Room;
import com.lukasz.hotel_reservation.domain.room.RoomRepository;
import com.lukasz.hotel_reservation.domain.room.RoomStatus;
import com.lukasz.hotel_reservation.domain.room.exceptions.RoomNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

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

    public ReservationResponse createReservation(Reservation reservation) {
        validateDuplicatedReservation(reservation);
        Room room = getAvailableRoom(reservation);
        updateRoomStatus(room);
        return saveReservation(reservation);

    }

    public ReservationResponse findReservation(UUID reservationId) {
        return reservationRepository.findById(reservationId)
                .map(this::toReservationResponse)
                .orElseThrow(() -> new ReservationNotFoundException(reservationId));
    }

    public void cancelReservation(UUID reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException(reservationId));

        Room room = reservation.getRoom();
        room.setStatus(RoomStatus.AVAILABLE);
        roomRepository.save(room);

        reservation.setStatus(ReservationStatus.CANCELLED);
        Reservation reservationSaved = reservationRepository.save(reservation);

        log.info("Reservation id: {} cancelled, room id: {} is now available", reservationSaved.getId(), room.getId());
    }

    public void cancelReservation() {
        List<Reservation> reservations = reservationRepository.findAll();
        if (reservations.isEmpty()) {
            throw new ReservationNotFoundException("Reservation not found");
        }

        savedCancelReservation(reservations);
    }

    private void savedCancelReservation(List<Reservation> reservations) {
        reservations.stream()
                .filter(Objects::nonNull)
                .forEach(reservation -> {
                    Room room = savedAvailableRoom(reservation);
                    savedCanceledReservation(reservation, room);
                });
    }

    private void savedCanceledReservation(Reservation reservation, Room room) {
        reservation.setStatus(ReservationStatus.CANCELLED);
        Reservation reservationSaved = reservationRepository.save(reservation);

        log.info("Reservation: {} cancelled, room: {}", reservationSaved, room);
    }

    private Room savedAvailableRoom(Reservation reservation) {
        Room room = reservation.getRoom();
        room.setStatus(RoomStatus.AVAILABLE);
        roomRepository.save(room);
        return room;
    }

    private Room getAvailableRoom(Reservation reservation) {
        return roomRepository.findById(reservation.getRoom().getId())
                .filter(room -> room.getStatus().equals(RoomStatus.AVAILABLE))
                .orElseThrow(() -> new RoomNotFoundException(reservation.getRoom().getId()));
    }

    private void updateRoomStatus(Room room) {
        room.setStatus(RoomStatus.RESERVED);
        roomRepository.save(room);
    }

    private void validateDuplicatedReservation(Reservation reservation) {
        if (reservationRepository.existsByRoom_IdAndGuest_Id(
                reservation.getRoom().getId(),
                reservation.getGuest().getId())) {
            throw new DuplicateReservationException("Guest already has a reservation for this room");
        }
    }

    private ReservationResponse saveReservation(Reservation reservation) {
        reservation.setRoom(reservation.getRoom());
        Reservation reservationSaved = reservationRepository.save(reservation);
        log.info("Reservation saved: {}", reservationSaved);
        return toReservationResponse(reservation);
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
