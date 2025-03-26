package com.lukasz.hotel_reservation.domain.pdf;

import com.lukasz.hotel_reservation.domain.pdf.dto.PdfGeneratorRequest;

public interface PdfGenerator {
    byte[] generate(PdfGeneratorRequest request) throws Exception;
}
