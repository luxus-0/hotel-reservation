package com.lukasz.hotel_reservation.domain.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.lukasz.hotel_reservation.domain.customer.CustomerFinderResponse;
import com.lukasz.hotel_reservation.domain.customer.CustomerService;
import com.lukasz.hotel_reservation.domain.reservation.ReservationFinderResponse;
import com.lukasz.hotel_reservation.domain.reservation.ReservationService;
import com.lukasz.hotel_reservation.domain.room.RoomFinderResponse;
import com.lukasz.hotel_reservation.domain.room.RoomService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Service
@Log4j2
@AllArgsConstructor
public class PdfGeneratorService implements PdfGenerator {
    private final ReservationService reservationService;
    private final RoomService roomService;
    private final CustomerService customerService;

    @Override
    public byte[] generate(PdfGeneratorRequest pdf)
            throws DocumentException, IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            Font font = FontFactory.getFont(pdf.fontName());
            addDocument(pdf, document, font);

            document.close();
            return outputStream.toByteArray();
        }
    }

    private void addDocument(PdfGeneratorRequest pdf, Document document, Font font) throws DocumentException {
        ReservationFinderResponse reservation = reservationService.find();
        RoomFinderResponse room = roomService.find();
        CustomerFinderResponse customer = customerService.find();


        document.add(new Paragraph(pdf.title(), font));
        document.add(Chunk.NEWLINE);

        document.add(new Paragraph("Customer: " + customer.name() + " " + customer.surname(), font));
        document.add(new Paragraph("Email: " + customer.email(), font));
        document.add(new Paragraph("Phone: " + customer.phone(), font));

        document.add(new Paragraph("Reservation id: " + reservation.id(), font));
        document.add(new Paragraph("Check-in: " + reservation.checkIn().format(DateTimeFormatter.ISO_DATE), font));
        document.add(new Paragraph("Check-out: " + reservation.checkOut().format(DateTimeFormatter.ISO_DATE), font));
        document.add(new Paragraph("Status: " + reservation.status(), font));

        document.add(new Paragraph("Room id: " + room.id(), font));
        document.add(new Paragraph("Room type: " + room.type(), font));
        document.add(new Paragraph("Room number: " + room.number(), font));
        document.add(new Paragraph("Room status: " + room.status(), font));
    }
}

