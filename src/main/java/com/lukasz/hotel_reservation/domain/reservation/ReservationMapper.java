package com.lukasz.hotel_reservation.domain.reservation;

import com.lukasz.hotel_reservation.domain.address.Address;
import com.lukasz.hotel_reservation.domain.contact.Contact;
import com.lukasz.hotel_reservation.domain.customer.Customer;
import com.lukasz.hotel_reservation.domain.room.Room;

class ReservationMapper {
    static ReservationFinderResponse toReservationResponse(Reservation savedReservation) {
        return ReservationFinderResponse.builder()
                .id(savedReservation.getId())
                .status(savedReservation.getStatus())
                .checkIn(savedReservation.getCheckIn())
                .checkOut(savedReservation.getCheckOut())
                .build();
    }

    static Customer toCustomer(ReservationCreatorRequest reservationCreatorRequest) {
        return Customer.builder()
                .name(reservationCreatorRequest.customer().name())
                .surname(reservationCreatorRequest.customer().surname())
                .birthDate(reservationCreatorRequest.customer().birthDate())
                .address(toAddress(reservationCreatorRequest))
                .contact(toContact(reservationCreatorRequest))
                .build();
    }

    static Contact toContact(ReservationCreatorRequest reservationCreatorRequest) {
        return Contact.builder()
                .phone(reservationCreatorRequest.contact().phone())
                .email(reservationCreatorRequest.contact().email())
                .build();
    }

    static Address toAddress(ReservationCreatorRequest reservationCreatorRequest) {
        return Address.builder()
                .city(reservationCreatorRequest.address().city())
                .country(reservationCreatorRequest.address().country())
                .postalCode(reservationCreatorRequest.address().postalCode())
                .street(reservationCreatorRequest.address().street())
                .number(reservationCreatorRequest.address().number())
                .build();
    }

    static Room toRoom(ReservationCreatorRequest reservationCreatorRequest) {
        return Room.builder()
                .id(reservationCreatorRequest.room().id())
                .number(reservationCreatorRequest.room().number())
                .status(reservationCreatorRequest.room().status())
                .type(reservationCreatorRequest.room().type())
                .build();
    }
}
