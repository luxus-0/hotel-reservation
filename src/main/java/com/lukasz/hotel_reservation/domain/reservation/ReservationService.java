package com.lukasz.hotel_reservation.domain.reservation;

import com.lukasz.hotel_reservation.domain.customer.CustomerMapper;
import com.lukasz.hotel_reservation.domain.customer.CustomerService;
import com.lukasz.hotel_reservation.domain.customer.dto.CustomerFinderResponse;
import com.lukasz.hotel_reservation.domain.reservation.dto.ReservationCreatedEvent;
import com.lukasz.hotel_reservation.domain.reservation.dto.ReservationCreatorRequest;
import com.lukasz.hotel_reservation.domain.reservation.dto.ReservationCreatorResponse;
import com.lukasz.hotel_reservation.domain.reservation.dto.ReservationFinderResponse;
import com.lukasz.hotel_reservation.domain.reservation.exceptions.ReservationNotFoundException;
import com.lukasz.hotel_reservation.domain.room.RoomMapper;
import com.lukasz.hotel_reservation.domain.room.RoomService;
import com.lukasz.hotel_reservation.domain.room.RoomStatus;
import com.lukasz.hotel_reservation.domain.room.dto.RoomFinderResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Log4j2
@AllArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final ReservationValidator reservationValidator;
    private final CustomerService customerService;
    private final RoomService roomService;

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
                .map(ReservationMapper::toReservation)
                .orElseThrow(() -> new ReservationNotFoundException(reservationId));
    }

    public ReservationCreatorResponse create(ReservationCreatorRequest reservationRequest) throws Exception {
        reservationValidator.validate(
                reservationRequest.reservation().checkIn(),
                reservationRequest.reservation().checkOut());

        UUID customerId = reservationRequest.customer().uuid();
        CustomerFinderResponse customer = customerService.find(customerId);

        UUID roomId = reservationRequest.room().uuid();
        RoomFinderResponse room = roomService.find(roomId);

        Reservation reservation = getReservation(reservationRequest, room, customer);

        eventPublisher.publishEvent(new ReservationCreatedEvent(reservationRequest));

        Reservation reservationSaved = reservationRepository.save(reservation);
        return ReservationCreatorResponse.builder()
                .reservationId(reservationSaved.getUuid())
                .message("Reservation saved successfully")
                .build();
    }

    public ReservationCreatorResponse create(List<Reservation> reservations) {
        if (reservations.isEmpty()) {
            throw new ReservationNotFoundException("Reservation list is empty");
        }
        List<Reservation> savedReservations = reservationRepository.saveAll(reservations);
        log.info("Saved {} reservations", savedReservations.size());

        UUID id = savedReservations.stream()
                .map(Reservation::getUuid)
                .findAny()
                .orElseThrow();

        return ReservationCreatorResponse.builder()
                .reservationId(id)
                .message("Reservations saved successfully")
                .build();
    }

    private Reservation getReservation(ReservationCreatorRequest reservationRequest, RoomFinderResponse room, CustomerFinderResponse customer) throws Exception {
        ReservationFinderResponse reservation = reservationRequest.reservation();
        return Reservation.builder()
                .uuid(reservationRequest.uuid() != null ? reservationRequest.uuid() : UUID.randomUUID())
                .createdAt(LocalDateTime.now())
                .status(ReservationStatus.CONFIRMED)
                .checkIn(reservation.checkIn())
                .checkOut(reservation.checkOut())
                .room(RoomMapper.toRoom(room))
                .customer(CustomerMapper.toCustomer(customer))
                .build();
    }

    public void cancel(UUID reservationId) {
        reservationRepository.findById(reservationId)
                .ifPresent(reservation -> {
                    reservation.setStatus(ReservationStatus.CANCELLED);
                    if (reservation.getRoom() != null) {
                        reservation.getRoom().setStatus(RoomStatus.AVAILABLE);
                    }
                    Reservation saved = reservationRepository.save(reservation);
                    log.info("Reservation canceled: {}", saved.getUuid());
                });
    }

    public void cancelAll() {
        reservationRepository.deleteAll();
        log.info("All reservations deleted.");
    }

    public Long countDays(LocalDateTime checkIn, LocalDateTime checkOut) {
        reservationValidator.validate(checkIn, checkOut);
        LocalDateTime timeCheckIn = checkIn.withHour(14).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime timeCheckOut = checkOut.withHour(12).withMinute(0).withSecond(0).withNano(0);
        return ChronoUnit.DAYS.between(timeCheckIn, timeCheckOut);
    }
}