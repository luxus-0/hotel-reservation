package com.lukasz.hotel_reservation.domain.pdf;

public record PdfGeneratorResponse(byte[] pdf, String filename) {
}
