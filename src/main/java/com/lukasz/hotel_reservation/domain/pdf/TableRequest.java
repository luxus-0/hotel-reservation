package com.lukasz.hotel_reservation.domain.pdf;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record TableRequest(
        @NotNull int numberOfColumns,
        @NotNull List<List<String>> rows,
        @NotNull int spacingBefore) {
}
