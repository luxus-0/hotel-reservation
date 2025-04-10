package com.lukasz.hotel_reservation.domain.pdf;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.lukasz.hotel_reservation.domain.customer.dto.CustomerFinderResponse;
import com.lukasz.hotel_reservation.domain.customer.CustomerService;
import com.lukasz.hotel_reservation.domain.pdf.dto.TableRequest;
import com.lukasz.hotel_reservation.domain.pdf.exceptions.TableAlignmentNotFoundException;
import com.lukasz.hotel_reservation.domain.reservation.Reservation;
import com.lukasz.hotel_reservation.domain.reservation.dto.ReservationFinderResponse;
import com.lukasz.hotel_reservation.domain.reservation.ReservationService;
import com.lukasz.hotel_reservation.domain.room.dto.RoomFinderResponse;
import com.lukasz.hotel_reservation.domain.room.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TableService {
    private final CustomerService customerService;
    private final RoomService roomService;
    private final ReservationService reservationService;

    public PdfPTable getTable(TableRequest tableRequest) throws Exception {
        PdfPTable table = new PdfPTable(tableRequest.numberOfColumns());
        table.setSpacingBefore(tableRequest.spacingBefore());
        int alignment = getAlignment(tableRequest.alignment());
        table.setHorizontalAlignment(alignment);
        table.setSpacingAfter(tableRequest.spacingAfter());
        table.setComplete(true);
        table.setTotalWidth(tableRequest.totalWidth());
        table.setRole(PdfName.TABLE);

        PdfPCell cell = new PdfPCell();
        cell.setColspan(tableRequest.numberOfColumns());
        cell.setBorder(Rectangle.BOTTOM);
        table.completeRow();

        List<CustomerFinderResponse> customer = customerService.find();
        List<ReservationFinderResponse> reservation = reservationService.find();
        RoomFinderResponse room = roomService.find();

        addCell(table,
                tableRequest.cell().label(),
                "%s\n%s\n%s".formatted(
                        customer.toString(),
                        reservation.toString(),
                        room.toString()));

        return table;
    }

    private void addCell(PdfPTable table, String label, String value) throws Exception {
        table.addCell(label);
        table.addCell(value);
    }

    private int getAlignment(String tableAlignment) {
        switch (tableAlignment.toLowerCase()) {
            case "center" -> {
                return PdfPTable.ALIGN_CENTER;
            }
            case "left" -> {
                return PdfPTable.ALIGN_LEFT;
            }
            case "right" -> {
                return PdfPTable.ALIGN_RIGHT;
            }
            case "bottom" -> {
                return PdfPTable.ALIGN_BOTTOM;
            }
            case "top" -> {
                return PdfPTable.ALIGN_TOP;
            }
            case "justified" -> {
                return PdfPTable.ALIGN_JUSTIFIED;
            }
            case "justified all" -> {
                return PdfPTable.ALIGN_JUSTIFIED_ALL;
            }
            case "title" -> {
                return PdfPTable.TITLE;
            }
            default -> throw new TableAlignmentNotFoundException("Table alignment not found");
        }
    }
}
