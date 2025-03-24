package com.lukasz.hotel_reservation.domain.reservation;

import com.itextpdf.text.DocumentException;
import com.lukasz.hotel_reservation.domain.address.AddressFinderResponse;
import com.lukasz.hotel_reservation.domain.contact.ContactFinderResponse;
import com.lukasz.hotel_reservation.domain.customer.CustomerFinderResponse;
import com.lukasz.hotel_reservation.domain.pdf.PdfGeneratorRequest;
import com.lukasz.hotel_reservation.domain.room.RoomFinderResponse;
import com.lukasz.hotel_reservation.domain.room.RoomStatus;
import com.lukasz.hotel_reservation.domain.room.RoomType;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Log4j2
class ReservationServiceTest {

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
    void shouldCreateSuccessfully() throws DocumentException, IOException {
        UUID reservationId = UUID.randomUUID();
        ReservationStatus status = ReservationStatus.CANCELLED;
        LocalDateTime checkIn = LocalDateTime.now().plusDays(1);
        LocalDateTime checkOut = LocalDateTime.now().plusDays(7);
        LocalDateTime createdAt = LocalDateTime.now();

        RoomFinderResponse room = RoomFinderResponse.builder()
                .id(UUID.randomUUID())
                .number(101)
                .type(RoomType.SINGLE)
                .status(RoomStatus.AVAILABLE)
                .build();

        CustomerFinderResponse customer = CustomerFinderResponse.builder()
                .name("John")
                .surname("Doe")
                .phone("123456789")
                .email("john.doe@example.com")
                .build();

        AddressFinderResponse address = AddressFinderResponse.builder()
                .city("Krakow")
                .number(10)
                .street("Main Street")
                .postalCode("30-001")
                .country("Poland")
                .build();

        ContactFinderResponse contact = new ContactFinderResponse("contact@example.com", "987654321");

        doNothing().when(reservationValidator).validate(checkIn, checkOut);
        when(reservationRepository.existsById(reservationId)).thenReturn(false);
        when(reservationRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ReservationCreatorRequest request = new ReservationCreatorRequest(
                reservationId, status, checkIn, checkOut, createdAt, room, customer, address, contact
        );

        PdfGeneratorRequest pdfRequest = PdfGeneratorRequest.builder()
                .title("Reservation Confirmation")
                .fontName("Arial")
                .build();

        reservationService.create(request, pdfRequest);

        verify(reservationValidator, times(1)).validate(checkIn, checkOut);
        verify(reservationRepository).save(any(Reservation.class));
    }

    @Test
    void shouldCancelSuccessfully() {
        UUID reservationId = reservation.getId();

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(any())).thenReturn(reservation);

        reservationService.cancel(reservationId);

        assertEquals(ReservationStatus.CANCELLED, reservation.getStatus());
        verify(reservationRepository).findById(reservationId);
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void shouldNotCancelWhenReservationNotFound() {
        UUID reservationId = UUID.randomUUID();

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

        reservationService.cancel(reservationId);

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

        RoomFinderResponse room = RoomFinderResponse.builder()
                .id(UUID.randomUUID())
                .number(101)
                .type(RoomType.SINGLE)
                .status(RoomStatus.AVAILABLE)
                .build();

        CustomerFinderResponse customer = CustomerFinderResponse.builder()
                .name("John")
                .surname("Doe")
                .phone("123456789")
                .email("john.doe@example.com")
                .build();

        AddressFinderResponse address = AddressFinderResponse.builder()
                .city("Krakow")
                .number(10)
                .street("Main Street")
                .postalCode("30-001")
                .country("Poland")
                .build();

        ContactFinderResponse contact = new ContactFinderResponse("contact@example.com", "987654321");

        doNothing().when(reservationValidator).validate(checkIn, checkOut);
        when(reservationRepository.existsById(reservationId)).thenReturn(true);

        ReservationCreatorRequest request = new ReservationCreatorRequest(
                reservationId, status, checkIn, checkOut, createdAt, room, customer, address, contact
        );

        PdfGeneratorRequest pdfRequest = PdfGeneratorRequest.builder()
                .title("Reservation Confirmation")
                .fontName("Arial")
                .build();

        assertEquals(request.id(), reservationId);
        assertEquals(status, request.status());
        assertEquals(request.checkIn(), checkIn);
        assertEquals(request.checkOut(), checkOut);
        assertEquals(request.createdAt(), createdAt);

        assertThrowsExactly(ReservationExistsException.class,
                () -> reservationService.create(request, pdfRequest), "Reservation with id " + reservationId + " already exists");
    }
}

