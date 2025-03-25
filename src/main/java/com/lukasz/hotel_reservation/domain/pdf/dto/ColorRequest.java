package com.lukasz.hotel_reservation.domain.pdf.dto;

import jakarta.validation.constraints.NotNull;

public record ColorRequest(@NotNull int red, @NotNull int green, @NotNull int blue) {
}
