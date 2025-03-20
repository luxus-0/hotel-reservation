package com.lukasz.hotel_reservation.domain.reservation;

import com.lukasz.hotel_reservation.domain.reservation.exceptions.DuplicateReservationException;
import com.lukasz.hotel_reservation.domain.room.Room;
import com.lukasz.hotel_reservation.domain.room.RoomRepository;
import com.lukasz.hotel_reservation.domain.room.RoomStatus;
import com.lukasz.hotel_reservation.domain.room.exceptions.RoomNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

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

    private Room getAvailableRoom(Reservation reservation){
        return roomRepository.findById(reservation.getRoom().getId())
                .filter(room -> room.getStatus().equals(RoomStatus.AVAILABLE))
                .orElseThrow(RoomNotFoundException::new);
    }

    private void updateRoomStatus(Room room){
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

    private ReservationResponse saveReservation(Reservation reservation){
        reservation.setRoom(reservation.getRoom());
        Reservation reservationSaved = reservationRepository.save(reservation);
        log.info("Reservation saved: {}", reservationSaved);
        return getReservation(reservation);
    }

    private ReservationResponse getReservation(Reservation savedReservation) {
        return ReservationResponse.builder()
                .status(savedReservation.getStatus())
                .guestId(savedReservation.getGuest().getId())
                .roomId(savedReservation.getRoom().getId())
                .checkIn(savedReservation.getCheckIn())
                .checkOut(savedReservation.getCheckOut())
                .build();
    }
}
