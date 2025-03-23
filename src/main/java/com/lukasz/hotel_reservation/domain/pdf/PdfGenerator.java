package com.lukasz.hotel_reservation.domain.pdf;

import com.itextpdf.text.DocumentException;
import com.lukasz.hotel_reservation.domain.reservation.ReservationFinderResponse;

import java.io.IOException;

public interface PdfGenerator {
    byte[] generate(PdfGeneratorRequest request) throws DocumentException, IOException;
}
