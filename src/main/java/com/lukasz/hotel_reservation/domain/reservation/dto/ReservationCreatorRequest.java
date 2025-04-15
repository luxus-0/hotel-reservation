package com.lukasz.hotel_reservation.domain.reservation.dto;

import com.lukasz.hotel_reservation.domain.customer.dto.CustomerFinderResponse;
import com.lukasz.hotel_reservation.domain.pdf.dto.PdfGeneratorRequest;
import com.lukasz.hotel_reservation.domain.room.dto.RoomFinderResponse;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ReservationCreatorRequest(
        @NotNull
        UUID uuid,
        ReservationFinderResponse reservation,
        RoomFinderResponse room,
        CustomerFinderResponse customer,
        PdfGeneratorRequest pdf) {
}
