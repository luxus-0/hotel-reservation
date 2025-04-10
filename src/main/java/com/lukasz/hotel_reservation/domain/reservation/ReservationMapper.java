package com.lukasz.hotel_reservation.domain.reservation;

import com.lukasz.hotel_reservation.domain.address.Address;
import com.lukasz.hotel_reservation.domain.customer.Customer;
import com.lukasz.hotel_reservation.domain.reservation.dto.ReservationCreatorRequest;
import com.lukasz.hotel_reservation.domain.reservation.dto.ReservationFinderResponse;
import com.lukasz.hotel_reservation.domain.room.Room;
import com.lukasz.hotel_reservation.domain.room.RoomStatus;

class ReservationMapper {
    static ReservationFinderResponse toReservationResponse(Reservation savedReservation) {
        return ReservationFinderResponse.builder()
                .status(savedReservation.getStatus())
                .checkIn(savedReservation.getCheckIn())
                .checkOut(savedReservation.getCheckOut())
                .build();
    }

    static Customer toCustomer(ReservationCreatorRequest reservationCreatorRequest) {
        return Customer.builder()
                .id(reservationCreatorRequest.customer().uuid())
                .name(reservationCreatorRequest.customer().name())
                .surname(reservationCreatorRequest.customer().surname())
                .birthDate(reservationCreatorRequest.customer().birthDate())
                .address(toAddress(reservationCreatorRequest))
                .build();
    }

    static Address toAddress(ReservationCreatorRequest reservationCreatorRequest) {
        return Address.builder()
                .city(reservationCreatorRequest.customer().address().city())
                .country(reservationCreatorRequest.customer().address().country())
                .postalCode(reservationCreatorRequest.customer().address().postalCode())
                .street(reservationCreatorRequest.customer().address().street())
                .number(reservationCreatorRequest.customer().address().number())
                .build();
    }

    static Room toRoom(ReservationCreatorRequest reservationCreatorRequest) {
        return Room.builder()
                .id(reservationCreatorRequest.room().uuid())
                .number(reservationCreatorRequest.room().number())
                .status(RoomStatus.RESERVED)
                .type(reservationCreatorRequest.room().type())
                .build();
    }
}
