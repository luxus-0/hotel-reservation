package com.lukasz.hotel_reservation.domain.pdf;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ParagraphRequest(@NotNull UUID reservationId,
                               @NotNull UUID roomId,
                               @NotNull UUID customerId,
                               @NotNull String alignment,
                               @NotNull float leading,
                               @NotNull float spacingBefore,
                               @NotNull float spacingAfter,
                               @NotNull FontRequest font) {
}
