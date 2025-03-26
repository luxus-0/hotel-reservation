package com.lukasz.hotel_reservation.domain.pdf.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record FontRequest(@NotNull String family,
                          @NotNull int size,
                          @NotNull String style,
                          ColorRequest color) {
}
