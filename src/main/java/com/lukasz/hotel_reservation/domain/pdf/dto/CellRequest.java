package com.lukasz.hotel_reservation.domain.pdf.dto;

import lombok.Builder;

@Builder
public record CellRequest(String label, String value) {
}
