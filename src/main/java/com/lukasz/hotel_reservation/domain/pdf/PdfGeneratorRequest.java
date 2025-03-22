package com.lukasz.hotel_reservation.domain.pdf;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PdfGeneratorRequest(
        @NotNull UUID reservationId,
        @NotNull String filename,
        @NotNull String fontName,
        @Min(1) int fontSize,
        @NotNull String title) {
}
