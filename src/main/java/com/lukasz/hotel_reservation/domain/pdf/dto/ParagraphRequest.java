package com.lukasz.hotel_reservation.domain.pdf.dto;

import jakarta.validation.constraints.NotNull;

public record ParagraphRequest(@NotNull String alignment,
                               @NotNull float leading,
                               @NotNull float spacingBefore,
                               @NotNull float spacingAfter,
                               @NotNull FontRequest font) {
}
