package com.lukasz.hotel_reservation.domain.reservation;

import com.itextpdf.text.DocumentException;
import com.lukasz.hotel_reservation.domain.reservation.dto.ReservationCreatorRequest;
import com.lukasz.hotel_reservation.domain.reservation.dto.ReservationFinderResponse;
import com.lukasz.hotel_reservation.domain.reservation.exceptions.ReservationNotFoundException;
import com.lukasz.hotel_reservation.domain.room.RoomStatus;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static com.lukasz.hotel_reservation.domain.reservation.ReservationMapper.toCustomer;
import static com.lukasz.hotel_reservation.domain.reservation.ReservationMapper.toRoom;

@Service
@Log4j2
@AllArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationValidator reservationValidator;

    public List<ReservationFinderResponse> find() {
        List<Reservation> reservations = reservationRepository.findAll();
        if (reservations.isEmpty()) {
            throw new ReservationNotFoundException("Reservation not found");
        }

        return reservations.stream()
                .map(reservation -> ReservationFinderResponse.builder()
                        .checkIn(reservation.getCheckIn())
                        .checkOut(reservation.getCheckOut())
                        .status(reservation.getStatus())
                        .build())
                .toList();
    }

    public ReservationFinderResponse find(UUID reservationId) {
        return reservationRepository.findById(reservationId)
                .map(ReservationMapper::toReservationResponse)
                .orElseThrow(() -> new ReservationNotFoundException(reservationId));
    }

    public void create(ReservationCreatorRequest reservationCreatorRequest) throws DocumentException, IOException {
        reservationValidator.validate(reservationCreatorRequest.reservation().checkIn(), reservationCreatorRequest.reservation().checkOut());
        Reservation reservation = Reservation.builder()
                .status(ReservationStatus.REJECTED)
                .checkIn(reservationCreatorRequest.reservation().checkIn())
                .checkOut(reservationCreatorRequest.reservation().checkOut())
                .room(toRoom(reservationCreatorRequest))
                .customer(toCustomer(reservationCreatorRequest))
                .build();

        reservationRepository.save(reservation);
    }

    public void cancel(UUID reservationId) {
        reservationRepository.findById(reservationId)
                .ifPresent(reservation -> {

                    reservation.setStatus(ReservationStatus.CANCELLED);
                    reservation.getRoom().setStatus(RoomStatus.AVAILABLE);
                    reservationRepository.save(reservation);
                    log.info("Reservation uuid: {} has been cancelled", reservationId);
                });
    }

    public Long countDays(LocalDateTime checkIn, LocalDateTime checkOut) {
        reservationValidator.validate(checkIn, checkOut);
        LocalDateTime timeCheckIn = checkIn.withHour(14).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime timeCheckOut = checkOut.withHour(12).withMinute(0).withSecond(0).withNano(0);
        return ChronoUnit.DAYS.between(timeCheckIn, timeCheckOut);
    }
}
