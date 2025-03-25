package com.lukasz.hotel_reservation.domain.pdf.dto;

import jakarta.validation.constraints.NotNull;

public record ImageRequest(@NotNull String src,
                           @NotNull float width,
                           @NotNull float height,
                           @NotNull String alignment,
                           @NotNull float xPosition,
                           @NotNull float yPosition) {
}
