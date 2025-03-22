package com.lukasz.hotel_reservation.domain.reservation;

import com.lukasz.hotel_reservation.domain.reservation.exceptions.IncorrectReservationDate;
import com.lukasz.hotel_reservation.domain.reservation.exceptions.ReservationExistsException;
import com.lukasz.hotel_reservation.domain.reservation.exceptions.ReservationNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest extends ReservationServiceTestConstant {
    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ReservationValidator reservationValidator;

    @InjectMocks
    private ReservationService reservationService;

    private Reservation reservation;

    @BeforeEach
    void setUp() {
        reservation = Reservation.builder()
                .id(UUID.fromString("33333333-3333-3333-3333-333333333333"))
                .checkIn(LocalDateTime.of(2025, 3, 21, 11, 0, 0))
                .checkOut(LocalDateTime.of(2025, 3, 25, 11, 0, 0))
                .status(ReservationStatus.CONFIRMED)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void shouldCreateReservationSuccessfully() {
        UUID reservationId = UUID.randomUUID();
        ReservationStatus status = ReservationStatus.CANCELLED;
        LocalDateTime checkIn = LocalDateTime.now().plusDays(1);
        LocalDateTime checkOut = LocalDateTime.now().plusDays(7);
        LocalDateTime createdAt = LocalDateTime.now();

        doNothing().when(reservationValidator).validate(checkIn, checkOut);
        when(reservationRepository.existsById(reservationId)).thenReturn(false);
        when(reservationRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ReservationRequest request = new ReservationRequest(reservationId, status, checkIn, checkOut, createdAt);

        reservationService.createReservation(request);

        verify(reservationValidator, times(1)).validate(checkIn, checkOut);
        verify(reservationRepository).save(any(Reservation.class));
    }

    @Test
    void shouldCancelReservationSuccessfully() {
        UUID reservationId = reservation.getId();

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(any())).thenReturn(reservation);

        reservationService.cancelReservation(reservationId);

        assertEquals(ReservationStatus.CANCELLED, reservation.getStatus());
        verify(reservationRepository).findById(reservationId);
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void shouldNotCancelWhenReservationNotFound() {
        UUID reservationId = UUID.randomUUID();

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

        reservationService.cancelReservation(reservationId);

        verify(reservationRepository).findById(reservationId);
        verify(reservationRepository, never()).save(any(Reservation.class));
    }

    @Test
    void shouldThrowReservationExistsException() {
        UUID reservationId = UUID.randomUUID();
        ReservationStatus status = ReservationStatus.CANCELLED;
        LocalDateTime checkIn = LocalDateTime.now().plusDays(1);
        LocalDateTime checkOut = LocalDateTime.now().plusDays(7);
        LocalDateTime createdAt = LocalDateTime.now();

        doNothing().when(reservationValidator).validate(checkIn, checkOut);
        when(reservationRepository.existsById(reservationId)).thenReturn(true);

        ReservationRequest request = new ReservationRequest(reservationId, status, checkIn, checkOut, createdAt);

        assertThrowsExactly(ReservationExistsException.class,
                () -> reservationService.createReservation(request),
                "Reservation with id " + reservationId + " already exists");

        verify(reservationRepository, never()).save(any(Reservation.class));
    }

    @Test
    void shouldThrowReservationNotFoundException() {
        UUID reservationId = UUID.randomUUID();

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

        assertThrowsExactly(ReservationNotFoundException.class,
                () -> reservationService.findReservation(reservationId),
                "Reservation id: " + reservationId + " not found");

        verify(reservationRepository, never()).save(any());
    }

    @Test
    void shouldCalculateCorrectDays() {
        LocalDateTime checkIn = LocalDateTime.of(2025, 3, 21, 10, 0);
        LocalDateTime checkOut = LocalDateTime.of(2025, 3, 25, 15, 0);

        doNothing().when(reservationValidator).validate(checkIn, checkOut);

        long days = reservationService.countDays(checkIn, checkOut);

        verify(reservationValidator, times(1)).validate(checkIn, checkOut);
        assertEquals(3, days);
    }

    @Test
    void shouldThrowIncorrectReservationDateWhenCheckOutBeforeCheckInForCountDays() {
        LocalDateTime checkIn = LocalDateTime.of(2025, 3, 25, 10, 0);
        LocalDateTime checkOut = LocalDateTime.of(2025, 3, 21, 10, 0);

        doThrow(new IncorrectReservationDate("Check-out cannot be before check-in"))
                .when(reservationValidator).validate(checkIn, checkOut);

        assertThrows(IncorrectReservationDate.class, () -> reservationService.countDays(checkIn, checkOut));
        verify(reservationValidator, times(1)).validate(checkIn, checkOut);
    }

    @Test
    void shouldThrowIncorrectReservationDateWhenDatesAreEqualForCountDays() {
        LocalDateTime checkIn = LocalDateTime.of(2025, 3, 21, 10, 0);
        LocalDateTime checkOut = checkIn;

        doThrow(new IncorrectReservationDate("Check-out cannot be equal check-in"))
                .when(reservationValidator).validate(checkIn, checkOut);

        assertThrows(IncorrectReservationDate.class, () -> reservationService.countDays(checkIn, checkOut));
        verify(reservationValidator, times(1)).validate(checkIn, checkOut);
    }

    @Test
    void shouldThrowIncorrectReservationDateWhenCheckOutBeforeCheckInForCreateReservation() {
        LocalDateTime checkIn = LocalDateTime.of(2025, 3, 25, 10, 0);
        LocalDateTime checkOut = LocalDateTime.of(2025, 3, 21, 10, 0);
        LocalDateTime createdAt = LocalDateTime.now();

        doThrow(new IncorrectReservationDate("Check-out cannot be before check-in"))
                .when(reservationValidator).validate(checkIn, checkOut);

        ReservationRequest request = new ReservationRequest(UUID.randomUUID(), ReservationStatus.PAID, checkIn, checkOut, createdAt);

        assertThrows(IncorrectReservationDate.class, () -> reservationService.createReservation(request));
        verify(reservationValidator, times(1)).validate(checkIn, checkOut);
        verify(reservationRepository, never()).save(any(Reservation.class));
    }

    @Test
    void shouldThrowIncorrectReservationDateWhenDatesAreEqualForCreateReservation() {
        LocalDateTime checkIn = LocalDateTime.of(2025, 3, 21, 10, 0);
        LocalDateTime checkOut = checkIn;
        LocalDateTime createdAt = LocalDateTime.now();

        doThrow(new IncorrectReservationDate("Check-out cannot be equal check-in"))
                .when(reservationValidator).validate(checkIn, checkOut);

        ReservationRequest request = new ReservationRequest(UUID.randomUUID(), ReservationStatus.CANCELLED, checkIn, checkOut, createdAt);

        assertThrows(IncorrectReservationDate.class, () -> reservationService.createReservation(request));
        verify(reservationValidator, times(1)).validate(checkIn, checkOut);
        verify(reservationRepository, never()).save(any(Reservation.class));
    }
}