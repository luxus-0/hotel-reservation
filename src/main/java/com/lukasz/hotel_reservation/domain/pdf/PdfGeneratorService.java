package com.lukasz.hotel_reservation.domain.pdf;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lukasz.hotel_reservation.domain.customer.CustomerFinderResponse;
import com.lukasz.hotel_reservation.domain.customer.CustomerService;
import com.lukasz.hotel_reservation.domain.reservation.ReservationFinderResponse;
import com.lukasz.hotel_reservation.domain.reservation.ReservationService;
import com.lukasz.hotel_reservation.domain.room.RoomFinderResponse;
import com.lukasz.hotel_reservation.domain.room.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@Service
public class PdfGeneratorService implements PdfGenerator {

    private final ReservationService reservationService;
    private final RoomService roomService;
    private final CustomerService customerService;

    @Override
    public byte[] generate(PdfGeneratorRequest pdf) throws IOException, DocumentException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            Image image = convertImage(pdf.image());
            Paragraph paragraph = convertParagraph(pdf.paragraph());
            PdfPTable table = convertTable(pdf.table());

            document.add(image);
            document.add(Chunk.NEWLINE);
            document.add(table);
            document.addTitle(pdf.title());
            document.add(Chunk.NEWLINE);
            document.add(paragraph);

            document.close();
            return outputStream.toByteArray();
        }
    }

    private Font convertFont(FontRequest font) {
        return FontFactory.getFont(font.family());
    }

    private Image convertImage(ImageRequest imageRequest) throws BadElementException, IOException {
        Image image = Image.getInstance(imageRequest.src());
        image.scaleAbsolute(imageRequest.width(), imageRequest.height());
        image.setAbsolutePosition(imageRequest.xPosition(), imageRequest.yPosition());

        switch (imageRequest.alignment().toLowerCase()) {
            case "center":
                image.setAlignment(Image.ALIGN_CENTER);
                break;
            case "right":
                image.setAlignment(Image.ALIGN_RIGHT);
                break;
            case "left":
                image.setAlignment(Image.ALIGN_LEFT);
                break;
            default:
                throw new IllegalArgumentException("Invalid alignment: " + imageRequest.alignment());
        }

        image.setAbsolutePosition(imageRequest.xPosition(), imageRequest.yPosition());

        return image;
    }

    private Paragraph convertParagraph(ParagraphRequest paragraphRequest) {
        CustomerFinderResponse customer = customerService.find(paragraphRequest.customerId());
        ReservationFinderResponse reservation = reservationService.find(paragraphRequest.reservationId());
        RoomFinderResponse room = roomService.find(paragraphRequest.roomId());

        Font font = convertFont(paragraphRequest.font());
        String reservation_ = showReservation(customer, reservation, room);
        return getParagraph(paragraphRequest, reservation_, font);
    }

    private PdfPTable convertTable(TableRequest tableRequest) {
        PdfPTable table = new PdfPTable(tableRequest.numberOfColumns());
        table.setSpacingBefore(tableRequest.spacingBefore());
        for (List<String> row : tableRequest.rows()) {
            for (String cell : row) {
                table.addCell(cell);
            }

            ReservationFinderResponse reservation = reservationService.find();
            RoomFinderResponse room = roomService.find();
            CustomerFinderResponse customer = customerService.find();


            getCustomerTable(table, customer);
            getReservationTable(table, reservation);
            getRoomTable(table, room);

        }
        return table;
    }

    private static void getRoomTable(PdfPTable table, RoomFinderResponse room) {
        table.addCell("Room ID");
        table.addCell(room.id().toString());
        table.addCell("Room Number");
        table.addCell(String.valueOf(room.number()));
        table.addCell("Room Type");
        table.addCell(String.valueOf(room.type()));
        table.addCell("Room Status");
        table.addCell(String.valueOf(room.status()));
    }

    private static void getReservationTable(PdfPTable table, ReservationFinderResponse reservation) {
        table.addCell("Reservation ID");
        table.addCell(reservation.id().toString());
        table.addCell("Check-in");
        table.addCell(reservation.checkIn().toString());
        table.addCell("Check-out");
        table.addCell(reservation.checkOut().toString());
        table.addCell("Status");
        table.addCell(reservation.status().toString());
    }

    private static void getCustomerTable(PdfPTable table, CustomerFinderResponse customer) {
        table.addCell("Name");
        table.addCell(customer.name() + " " + customer.surname());
        table.addCell("Email");
        table.addCell(customer.email());
        table.addCell("Phone");
        table.addCell(customer.phone());
    }

    private static Paragraph getParagraph(ParagraphRequest paragraphRequest, String reservation_, Font font) {
        Paragraph paragraph = new Paragraph(reservation_, font);

        switch (paragraphRequest.alignment().toLowerCase()) {
            case "center":
                paragraph.setAlignment(Paragraph.ALIGN_CENTER);
                break;
            case "right":
                paragraph.setAlignment(Paragraph.ALIGN_RIGHT);
                break;
            case "justify":
                paragraph.setAlignment(Paragraph.ALIGN_JUSTIFIED);
                break;
            default:
                paragraph.setAlignment(Paragraph.ALIGN_LEFT);
                break;
        }
        paragraph.setLeading(paragraphRequest.leading());
        return paragraph;
    }

    private static String showReservation(CustomerFinderResponse customer, ReservationFinderResponse reservation, RoomFinderResponse room) {
        return printCustomer(customer) +
                printReservation(reservation) +
                printRoom(room);
    }

    private static String printRoom(RoomFinderResponse room) {
        return "Room" + "\n" +
                "ID: " + room.id() + "\n" +
                "Number: " + room.number() + "\n" +
                "Type: " + room.type() + "\n" +
                "Status: " + room.status();
    }

    private static String printReservation(ReservationFinderResponse reservation) {
        return "Reservation" + "\n" +
                "ID: " + reservation.id() + "\n" +
                "Check in: " + reservation.checkIn() + "\n" +
                "Check out: " + reservation.checkOut() + "\n" +
                "Status: " + reservation.status() + "\n\n";
    }

    private static String printCustomer(CustomerFinderResponse customer) {
        return "Customer" + "\n" +
                "Name: " + customer.name() + " " + customer.surname() + "\n" +
                "Email: " + customer.email() + "\n" + "\n" +
                "Phone: " + customer.phone() + "\n" +
                "Birthday: " + customer.birthDate() + "\n\n";
    }
}