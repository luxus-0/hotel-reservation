package com.lukasz.hotel_reservation.domain.pdf;

import com.lukasz.hotel_reservation.domain.pdf.dto.CellRequest;

import java.util.List;

public record TableRequest(int numberOfColumns,
                           List<List<String>> rows,
                           int spacingBefore,
                           int spacingAfter,
                           String alignment,
                           int totalWidth,
                           int totalHeight,
                           CellRequest cell) {
}
