package com.lukasz.hotel_reservation.domain.reservation;

import com.lukasz.hotel_reservation.domain.contact.Contact;
import com.lukasz.hotel_reservation.domain.document.Document;
import com.lukasz.hotel_reservation.domain.document.DocumentType;
import com.lukasz.hotel_reservation.domain.guest.Guest;
import com.lukasz.hotel_reservation.domain.reservation.exceptions.DuplicateReservationException;
import com.lukasz.hotel_reservation.domain.reservation.exceptions.ReservationNotFoundException;
import com.lukasz.hotel_reservation.domain.room.Room;
import com.lukasz.hotel_reservation.domain.room.RoomRepository;
import com.lukasz.hotel_reservation.domain.room.RoomStatus;
import com.lukasz.hotel_reservation.domain.room.RoomType;
import com.lukasz.hotel_reservation.domain.room.exceptions.RoomNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
    private RoomRepository roomRepository;

    @InjectMocks
    private ReservationService reservationService;

    private Room room;
    private Guest guest;
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        room = Room.builder()
                .id(UUID.fromString("11111111-1111-1111-1111-111111111111"))
                .type(RoomType.DOUBLE)
                .version(1L)
                .roomNumber(34)
                .status(RoomStatus.AVAILABLE)
                .build();

        guest = Guest.builder()
                .id(UUID.fromString("22222222-2222-2222-2222-222222222222"))
                .name("John")
                .surname("Smith")
                .birthDate(LocalDate.of(1990, 1, 10))
                .document(Document.builder()
                        .id(UUID.fromString("11111111-1111-1111-1111-111111111111"))
                        .number(123L)
                        .type(DocumentType.ID_CARD)
                        .build())
                .contact(Contact.builder()
                        .phoneNumber("12345567")
                        .email("lukasz@o2.pl")
                        .build())
                .build();

        reservation = Reservation.builder()
                .id(UUID.fromString("33333333-3333-3333-3333-333333333333"))
                .room(room)
                .guest(guest)
                .checkIn(LocalDate.of(2025, 3, 21))
                .checkOut(LocalDate.of(2025, 3, 25))
                .status(ReservationStatus.CONFIRMED)
                .createdAt(LocalDateTime.now())
                .build();

    }

    @Test
    void shouldCreateReservationSuccessfully() {
        UUID roomId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID guestId = UUID.fromString("22222222-2222-2222-2222-222222222222");

        when(reservationRepository.existsByRoom_IdAndGuest_Id(roomId, guestId)).thenReturn(false);
        when(roomRepository.findById(roomId)).thenReturn(Optional.of(room));
        when(roomRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(reservationRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ReservationResponse response = reservationService.createReservation(reservation);

        assertNotNull(response);
        assertEquals(reservation.getGuest().getId(), response.getGuestId());
        assertEquals(reservation.getRoom().getId(), response.getRoomId());
        assertEquals(reservation.getCheckIn(), response.getCheckIn());
        assertEquals(reservation.getCheckOut(), response.getCheckOut());

        verify(roomRepository, times(1)).findById(roomId);
        verify(roomRepository, times(1)).save(any(Room.class));
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    void shouldThrowDuplicateReservationExceptionWhenRoomIdAndGuestIdAlreadyExists() {
        UUID roomId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID guestId = UUID.fromString("22222222-2222-2222-2222-222222222222");

        when(reservationRepository.existsByRoom_IdAndGuest_Id(roomId, guestId)).thenReturn(true);

        assertThrows(DuplicateReservationException.class, () -> reservationService.createReservation(reservation));

        // Weryfikacja, że nie próbowało dalej rezerwować pokoju
        verify(roomRepository, never()).findById(roomId);
        verify(roomRepository, never()).save(any(Room.class));
        verify(reservationRepository, never()).save(any(Reservation.class));
        verify(roomRepository, never()).save(any(Room.class));

    }

    @Test
    void shouldThrowRoomFoundException() {
        UUID roomId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UUID guestId = UUID.fromString("22222222-2222-2222-2222-222222222222");

        when(reservationRepository.existsByRoom_IdAndGuest_Id(roomId, guestId)).thenReturn(false);
        when(roomRepository.findById(roomId)).thenReturn(Optional.empty());

        assertThrows(RoomNotFoundException.class, () -> reservationService.createReservation(reservation));

        verify(reservationRepository, never()).save(any(Reservation.class));
    }

    @Test
    void shouldCancelReservationSuccessfully() {
        UUID reservationId = UUID.fromString("33333333-3333-3333-3333-333333333333");

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(any())).thenReturn(reservation);

        reservationService.cancelReservation(reservationId);

        assertEquals(ReservationStatus.CANCELLED, reservation.getStatus());
        assertEquals(RoomStatus.AVAILABLE, room.getStatus());

        verify(reservationRepository).findById(reservationId);
        verify(reservationRepository, never()).deleteAll();
        verify(reservationRepository, times(1)).save(reservation);
        verify(reservationRepository, never()).deleteById(reservationId);
        verify(roomRepository, atMostOnce()).save(room);

    }

    @Test
    void shouldThrowExceptionWhenReservationNotFound() {
        // Given
        UUID reservationId = UUID.fromString("55555555-5555-5555-5555-555555555555");

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

        // Then
        assertThrows(ReservationNotFoundException.class, () -> reservationService.cancelReservation(reservationId));

        verify(roomRepository, never()).save(any());
        verify(reservationRepository, never()).save(any());
    }

    @Test
    void shouldCancelAllReservationsSuccessfully() {
        // Given
        List<Reservation> reservations = List.of(reservation1(), reservation2());
        when(reservationRepository.findAll()).thenReturn(reservations);

        // When
        reservationService.cancelReservation();

        // Then
        assertEquals(RoomStatus.AVAILABLE, getRoom().getStatus());
        assertEquals(RoomStatus.AVAILABLE, getRoom2().getStatus());
        assertEquals(ReservationStatus.CANCELLED, reservation1().getStatus());
        assertEquals(ReservationStatus.CANCELLED, reservation2().getStatus());

        verify(roomRepository, times(2)).save(any(Room.class));
        verify(reservationRepository, times(2)).save(any(Reservation.class));
    }

    @Test
    void shouldThrowReservationNotFoundExceptionWhenReservationNotExists() {
        // Given
        when(reservationRepository.findAll()).thenReturn(List.of());

        // When & Then
        assertThrows(ReservationNotFoundException.class, () -> reservationService.cancelReservation());

        verify(roomRepository, never()).save(any());
        verify(reservationRepository, never()).save(any());
    }
}