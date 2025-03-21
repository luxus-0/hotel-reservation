package com.lukasz.hotel_reservation.domain.pdf;

import com.itextpdf.text.DocumentException;

import java.io.IOException;

public interface PdfGenerator {
    PdfGeneratorResponse generate(PdfGeneratorRequest request) throws DocumentException, IOException;
}
