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
        if (reservationRepository.existsByRoom_IdAndGuest_Id(
                reservation.getRoom().getId(),
                reservation.getGuest().getId())) {
            throw new DuplicateReservationException("Guest already has a reservation for this room");
        }

        return roomRepository.findById(reservation.getRoom().getId())
                .filter(room -> room.getStatus().equals(RoomStatus.AVAILABLE))
                .map(room -> {

                    room.setStatus(RoomStatus.RESERVED);
                    Room savedRoom = roomRepository.save(room);
                    log.info("Room {} has been reserved", savedRoom);

                    reservation.setRoom(savedRoom);
                    Reservation savedReservation = reservationRepository.save(reservation);
                    log.info("Reservation {} has been created", savedReservation);

                    return toReservationResponse(savedReservation);
                })
                .orElseThrow(() -> new RoomNotFoundException("Room not found"));
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
