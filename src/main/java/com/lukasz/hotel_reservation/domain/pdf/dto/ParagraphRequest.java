package com.lukasz.hotel_reservation.domain.pdf.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ParagraphRequest(@NotNull String alignment,
                               @NotNull float leading,
                               @NotNull float spacingBefore,
                               @NotNull float spacingAfter,
                               @NotNull FontRequest font) {
}
