package com.lukasz.hotel_reservation.domain.reservation;

import com.lukasz.hotel_reservation.domain.pdf.PdfGeneratorService;
import com.lukasz.hotel_reservation.domain.reservation.dto.ReservationCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Log4j2
public class ReservationPdfListener {

    private final PdfGeneratorService pdfGeneratorService;

    @EventListener
    void handleReservation(ReservationCreatedEvent event) {
        try {
            pdfGeneratorService.generate(event.request().pdf());
            log.info("PDF successfully generated.");
        } catch (Exception e) {
            log.error("Error generating PDF: {}", e.getMessage(), e);
        }
    }
}
