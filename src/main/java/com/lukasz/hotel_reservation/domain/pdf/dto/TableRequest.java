package com.lukasz.hotel_reservation.domain.pdf.dto;

import lombok.Builder;

@Builder
public record TableRequest(int numberOfColumns,
                           int spacingBefore,
                           int spacingAfter,
                           String alignment,
                           int totalWidth,
                           int totalHeight,
                           CellRequest cell) {
}
