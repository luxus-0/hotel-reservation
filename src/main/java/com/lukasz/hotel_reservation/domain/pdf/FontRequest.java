package com.lukasz.hotel_reservation.domain.pdf;

import jakarta.validation.constraints.NotNull;

public record FontRequest(@NotNull String family,
                          @NotNull int size,
                          @NotNull String style,
                          @NotNull String color) {
}
