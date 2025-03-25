package com.lukasz.hotel_reservation.domain.pdf;

public interface PdfGenerator {
    byte[] generate(PdfGeneratorRequest request) throws Exception;
}
