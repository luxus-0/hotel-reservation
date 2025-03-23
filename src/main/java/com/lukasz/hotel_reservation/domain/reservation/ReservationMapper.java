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

    static Customer toCustomer(ReservationRequest reservationRequest) {
        return Customer.builder()
                .name(reservationRequest.customer().name())
                .surname(reservationRequest.customer().surname())
                .birthDate(reservationRequest.customer().birthDate())
                .address(toAddress(reservationRequest))
                .contact(toContact(reservationRequest))
                .build();
    }

    static Contact toContact(ReservationRequest reservationRequest) {
        return Contact.builder()
                .phone(reservationRequest.contact().phone())
                .email(reservationRequest.contact().email())
                .build();
    }

    static Address toAddress(ReservationRequest reservationRequest) {
        return Address.builder()
                .city(reservationRequest.address().city())
                .country(reservationRequest.address().country())
                .postalCode(reservationRequest.address().postalCode())
                .street(reservationRequest.address().street())
                .number(reservationRequest.address().number())
                .build();
    }

    static Room toRoom(ReservationRequest reservationRequest) {
        return Room.builder()
                .id(reservationRequest.room().id())
                .number(reservationRequest.room().number())
                .status(reservationRequest.room().status())
                .type(reservationRequest.room().type())
                .build();
    }
}
