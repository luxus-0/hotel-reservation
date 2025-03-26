package com.lukasz.hotel_reservation.domain.pdf;

import com.lukasz.hotel_reservation.domain.pdf.dto.CellRequest;
import lombok.Builder;

import java.util.List;

@Builder
public record TableRequest(int numberOfColumns,
                           int spacingBefore,
                           int spacingAfter,
                           String alignment,
                           int totalWidth,
                           int totalHeight,
                           CellRequest cell) {
}
