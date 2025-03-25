package com.lukasz.hotel_reservation.domain.pdf;

import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.lukasz.hotel_reservation.domain.customer.CustomerFinderResponse;
import com.lukasz.hotel_reservation.domain.customer.CustomerService;
import com.lukasz.hotel_reservation.domain.pdf.dto.ParagraphRequest;
import com.lukasz.hotel_reservation.domain.reservation.ReservationFinderResponse;
import com.lukasz.hotel_reservation.domain.reservation.ReservationService;
import com.lukasz.hotel_reservation.domain.room.RoomFinderResponse;
import com.lukasz.hotel_reservation.domain.room.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ParagraphService {
    private final CustomerService customerService;
    private final ReservationService reservationService;
    private final RoomService roomService;
    private final FontService fontService;
    
    public Paragraph getParagraph(ParagraphRequest paragraphRequest) {
        return convertParagraph(paragraphRequest);
    }

    private Paragraph convertParagraph(ParagraphRequest paragraphRequest) {
        CustomerFinderResponse customer = customerService.find();
        ReservationFinderResponse reservation = reservationService.find();
        RoomFinderResponse room = roomService.find();

        Font font = fontService.convertFont(paragraphRequest.font());
        Paragraph paragraph = new Paragraph("%s\n%s\n%s".formatted(customer.toString(), reservation.toString(), room.toString()), font);
        int alignment = getAlignment(paragraphRequest.alignment());
        paragraph.setAlignment(alignment);
        paragraph.setSpacingBefore(paragraphRequest.spacingBefore());
        paragraph.setSpacingAfter(paragraphRequest.spacingAfter());
        paragraph.setLeading(paragraphRequest.leading());
        return paragraph;
    }

    private int getAlignment(String alignmentParagraph) {
        switch (alignmentParagraph.toLowerCase()) {
            case "center" -> { return Paragraph.ALIGN_CENTER; }
            case "right" -> { return Paragraph.ALIGN_RIGHT; }
            case "left" -> { return Paragraph.ALIGN_LEFT;}
            case "bottom" -> { return Paragraph.ALIGN_BOTTOM; }
            case "top" -> { return Paragraph.ALIGN_TOP; }
            default -> throw new ParagraphAlignmentNotFoundException("Invalid paragraph alignment");
        }
    }
}
