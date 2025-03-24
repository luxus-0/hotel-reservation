package com.lukasz.hotel_reservation.domain.pdf;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PdfGeneratorRequest(
        @NotNull String filename,
        @NotNull String fontName,
        @Min(1) int fontSize,
        @NotNull String title) {
}
