package com.lukasz.hotel_reservation.domain.reservation;

import com.lukasz.hotel_reservation.domain.address.AddressFinderResponse;
import com.lukasz.hotel_reservation.domain.contact.ContactFinderResponse;
import com.lukasz.hotel_reservation.domain.customer.CustomerFinderResponse;
import com.lukasz.hotel_reservation.domain.room.RoomFinderResponse;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReservationRequest(
        @NotNull UUID id,
        @NotNull ReservationStatus status,
        @NotNull LocalDateTime checkIn,
        @NotNull LocalDateTime checkOut,
        @NotNull LocalDateTime createdAt,
        RoomFinderResponse room,
        CustomerFinderResponse customer,
        AddressFinderResponse address,
        ContactFinderResponse contact) {
}
