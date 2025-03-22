package com.lukasz.hotel_reservation.domain.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.lukasz.hotel_reservation.domain.reservation.ReservationResponse;
import com.lukasz.hotel_reservation.domain.reservation.ReservationService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Service
@Log4j2
@AllArgsConstructor
@Builder
public class PdfGeneratorService implements PdfGenerator {
    private final ReservationService reservationService;

    @Override
    public byte[] generate(PdfGeneratorRequest pdfRequest) throws DocumentException, IOException {
        ReservationResponse reservation = reservationService.findReservation(pdfRequest.reservationId());
        return generate(pdfRequest, reservation);
    }

    private byte[] generate(PdfGeneratorRequest pdf, ReservationResponse reservation)
            throws DocumentException, IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            Font font = FontFactory.getFont(pdf.fontName());
            document.add(new Paragraph(pdf.title(), font));
            document.add(Chunk.NEWLINE);

            addReservationDetails(document, reservation, font);

            document.close();
            log.info("PDF generated for reservation id: {}", reservation.id());
            return outputStream.toByteArray();
        }
    }

    private void addReservationDetails(Document document, ReservationResponse reservation, Font font) throws DocumentException {
        document.add(new Paragraph("Reservation id: " + reservation.id(), font));
        document.add(new Paragraph("Check-in: " + reservation.checkIn().format(DateTimeFormatter.ISO_DATE), font));
        document.add(new Paragraph("Check-out: " + reservation.checkOut().format(DateTimeFormatter.ISO_DATE), font));
        document.add(new Paragraph("Status: " + reservation.status(), font));
    }
}

