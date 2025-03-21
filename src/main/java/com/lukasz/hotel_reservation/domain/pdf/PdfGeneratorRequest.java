package com.lukasz.hotel_reservation.domain.pdf;

import java.util.UUID;

public record PdfGeneratorRequest(UUID reservationId,
                                  String filename,
                                  String fontName,
                                  int fontSize,
                                  String title) {
}
