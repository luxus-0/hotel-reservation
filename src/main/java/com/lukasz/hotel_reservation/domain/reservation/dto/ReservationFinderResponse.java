package com.lukasz.hotel_reservation.domain.reservation.dto;

import com.lukasz.hotel_reservation.domain.reservation.ReservationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Builder
public record ReservationFinderResponse(
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @NotNull
        ReservationStatus status,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @NotNull @NotNull
        LocalDateTime checkIn,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @NotNull
        LocalDateTime checkOut,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @NotNull
        LocalDateTime createdAt) {

    public String toString() {
        return ("""
                \nReservation
                Status: %s
                Check in: %s
                Check out: %s
                Created at: %s""").formatted(status, checkIn, checkOut, createdAt);
    }
}
