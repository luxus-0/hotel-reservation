package com.lukasz.hotel_reservation.domain.pdf.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PdfGeneratorRequest(
        @NotNull FontRequest font,
        @NotNull DocumentRequest document,
        @NotNull ParagraphRequest paragraph,
        @NotNull TableRequest table,
        @NotNull ImageRequest image,
        @NotNull String title
) {
}
