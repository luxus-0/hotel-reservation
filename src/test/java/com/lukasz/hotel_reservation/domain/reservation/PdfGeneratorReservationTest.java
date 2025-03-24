package com.lukasz.hotel_reservation.domain.reservation;

import com.itextpdf.text.DocumentException;
import com.lukasz.hotel_reservation.domain.customer.Customer;
import com.lukasz.hotel_reservation.domain.pdf.PdfGeneratorRequest;
import com.lukasz.hotel_reservation.domain.pdf.PdfGeneratorService;
import com.lukasz.hotel_reservation.domain.room.Room;
import com.lukasz.hotel_reservation.domain.room.RoomType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PdfGeneratorReservationTest {

    @Mock
    private ReservationRepository reservationRepository;
    @InjectMocks
    private PdfGeneratorService pdfGeneratorService;

    @Test
    void shouldGeneratePdfSuccessfully() throws DocumentException, IOException {
        // given
        UUID reservationId = UUID.randomUUID();
        Long hotelId = 123455L;

        Customer customer = Customer.builder()
                .name("Jan")
                .surname("Kowalski")
                .build();

        Room room = Room.builder()
                .number(101)
                .type(RoomType.SINGLE)
                .build();

        Reservation reservation = Reservation.builder()
                .id(reservationId)
                .checkIn(LocalDateTime.now())
                .checkOut(LocalDateTime.now().plusDays(2))
                .customer(customer)
                .room(room)
                .status(ReservationStatus.CONFIRMED)
                .build();

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));

        PdfGeneratorRequest request =
                new PdfGeneratorRequest("test.content", "Helvetica", 17, "Reservation");


        // when
        byte[] response = pdfGeneratorService.generate(request);

        // then
        assertNotNull(response);
        assertEquals("test.content", request.filename());
        assertTrue(response.length > 0);

        verify(reservationRepository).findById(reservationId);
    }

    @Test
    void shouldThrowWhenReservationNotFound() {
        // given
        UUID reservationId = UUID.randomUUID();

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

        PdfGeneratorRequest pdfRequest = new PdfGeneratorRequest("test.content", "Helvetica", 17, "Reservation");

        // then
        assertThrows(ReservationNotFoundException.class, () -> pdfGeneratorService.generate(pdfRequest));
    }
}