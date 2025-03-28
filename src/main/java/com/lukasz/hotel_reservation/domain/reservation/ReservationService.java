package com.lukasz.hotel_reservation.domain.reservation;

import com.itextpdf.text.DocumentException;
import com.lukasz.hotel_reservation.domain.pdf.PdfGeneratorRequest;
import com.lukasz.hotel_reservation.domain.room.RoomStatus;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static com.lukasz.hotel_reservation.domain.reservation.ReservationMapper.toCustomer;
import static com.lukasz.hotel_reservation.domain.reservation.ReservationMapper.toRoom;

@Service
@Log4j2
@AllArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationValidator reservationValidator;

    public void create(ReservationCreatorRequest reservationCreatorRequest, PdfGeneratorRequest pdf) throws DocumentException, IOException {
        reservationValidator.validate(reservationCreatorRequest.checkIn(), reservationCreatorRequest.checkOut());
        Reservation reservation = Reservation.builder()
                .id(reservationCreatorRequest.id())
                .status(ReservationStatus.REJECTED)
                .checkIn(reservationCreatorRequest.checkIn())
                .checkOut(reservationCreatorRequest.checkOut())
                .room(toRoom(reservationCreatorRequest))
                .customer(toCustomer(reservationCreatorRequest))
                .build();

        reservationRepository.save(reservation);
    }

    public ReservationFinderResponse find() {
        return reservationRepository.findAll()
                .stream()
                .map(ReservationMapper::toReservationResponse)
                .findAny()
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));
    }

    public ReservationFinderResponse find(UUID reservationId) {
        return reservationRepository.findById(reservationId)
                .map(ReservationMapper::toReservationResponse)
                .orElseThrow(() -> new ReservationNotFoundException(reservationId));
    }

    public void cancel(UUID reservationId) {
        reservationRepository.findById(reservationId)
                .ifPresent(reservation -> {

                    reservation.setStatus(ReservationStatus.CANCELLED);
                    reservation.getRoom().setStatus(RoomStatus.AVAILABLE);
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
}
