package com.lukasz.hotel_reservation.domain.pdf;

import com.itextpdf.text.DocumentException;

import java.io.IOException;

public interface PdfGenerator {
    byte[] generate(PdfGeneratorRequest request) throws DocumentException, IOException;
}
