package com.lukasz.hotel_reservation.domain.reservation.dto;

import com.lukasz.hotel_reservation.domain.address.dto.AddressFinderResponse;
import com.lukasz.hotel_reservation.domain.customer.dto.CustomerFinderResponse;
import com.lukasz.hotel_reservation.domain.room.dto.RoomFinderResponse;

public record ReservationCreatorRequest(
        ReservationFinderResponse reservation,
        RoomFinderResponse room,
        CustomerFinderResponse customer) {
}
