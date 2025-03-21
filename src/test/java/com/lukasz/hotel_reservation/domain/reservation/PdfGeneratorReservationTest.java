package com.lukasz.hotel_reservation.domain.reservation;

import com.itextpdf.text.DocumentException;
import com.lukasz.hotel_reservation.domain.guest.Guest;
import com.lukasz.hotel_reservation.domain.hotel.Hotel;
import com.lukasz.hotel_reservation.domain.hotel.HotelRepository;
import com.lukasz.hotel_reservation.domain.pdf.PdfGeneratorReservation;
import com.lukasz.hotel_reservation.domain.pdf.PdfGeneratorResponse;
import com.lukasz.hotel_reservation.domain.pdf.PdfGeneratorRequest;
import com.lukasz.hotel_reservation.domain.reservation.exceptions.ReservationNotFoundException;
import com.lukasz.hotel_reservation.domain.room.Room;
import com.lukasz.hotel_reservation.domain.room.RoomType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PdfGeneratorReservationTest {

    @Mock
    private HotelRepository hotelRepository;
    @Mock
    private ReservationRepository reservationRepository;
    @InjectMocks
    private PdfGeneratorReservation pdfGeneratorReservation;

    @Test
    void shouldGeneratePdfSuccessfully() throws DocumentException, IOException {
        // given
        UUID reservationId = UUID.randomUUID();
        Long hotelId = 123455L;

        Hotel hotel = Hotel.builder()
                .id(hotelId)
                .name("Test Hotel")
                .address("Test Address")
                .rating("5")
                .description("Test Desc")
                .build();

        Guest guest = Guest.builder()
                .name("Jan")
                .surname("Kowalski")
                .build();

        Room room = Room.builder()
                .roomNumber(101)
                .type(RoomType.SINGLE)
                .build();

        Reservation reservation = Reservation.builder()
                .id(reservationId)
                .checkIn(LocalDate.now())
                .checkOut(LocalDate.now().plusDays(2))
                .guest(guest)
                .room(room)
                .status(ReservationStatus.CONFIRMED)
                .build();

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));

        PdfGeneratorRequest request =
                new PdfGeneratorRequest(reservationId,"test.pdf", "Helvetica", 17, "Reservation");


        // when
        PdfGeneratorResponse response = pdfGeneratorReservation.generate(request);

        // then
        assertNotNull(response);
        assertNotNull(response.pdf());
        assertEquals("test.pdf", response.filename());
        assertTrue(response.pdf().length > 0);

        verify(reservationRepository).findById(reservationId);
    }

    @Test
    void shouldThrowWhenReservationNotFound() {
        // given
        UUID reservationId = UUID.randomUUID();

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

        PdfGeneratorRequest pdfRequest = new PdfGeneratorRequest(reservationId,"test.pdf", "Helvetica", 17, "Reservation");

        // then
        assertThrows(ReservationNotFoundException.class, () -> pdfGeneratorReservation.generate(pdfRequest));
    }
}