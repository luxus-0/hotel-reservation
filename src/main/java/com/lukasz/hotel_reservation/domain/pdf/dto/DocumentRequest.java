package com.lukasz.hotel_reservation.domain.pdf.dto;

import jakarta.validation.constraints.NotNull;

public record DocumentRequest(@NotNull String pageSize,
                              @NotNull float marginLeft,
                              @NotNull float marginRight,
                              @NotNull float marginTop,
                              @NotNull float marginBottom) {
}
