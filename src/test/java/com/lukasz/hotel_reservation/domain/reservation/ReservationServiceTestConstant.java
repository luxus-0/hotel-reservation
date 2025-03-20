package com.lukasz.hotel_reservation.domain.reservation;

import com.lukasz.hotel_reservation.domain.contact.Contact;
import com.lukasz.hotel_reservation.domain.document.Document;
import com.lukasz.hotel_reservation.domain.document.DocumentType;
import com.lukasz.hotel_reservation.domain.guest.Guest;
import com.lukasz.hotel_reservation.domain.room.Room;
import com.lukasz.hotel_reservation.domain.room.RoomStatus;
import com.lukasz.hotel_reservation.domain.room.RoomType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class ReservationServiceTestConstant {
    public static Room getRoom() {
        return Room.builder()
                .id(UUID.fromString("11111111-1111-1111-1111-111111111111"))
                .type(RoomType.DOUBLE)
                .version(1L)
                .roomNumber(34)
                .status(RoomStatus.AVAILABLE)
                .build();
    }

    public static Room getRoom2() {
        return Room.builder()
                .id(UUID.fromString("11111111-1111-1111-1111-111111111111"))
                .type(RoomType.DOUBLE)
                .version(2L)
                .roomNumber(35)
                .status(RoomStatus.AVAILABLE)
                .build();
    }

    public static Guest getGuest(){
        return Guest.builder()
                .id(UUID.fromString("22222222-2222-2222-2222-222222222222"))
                .name("John")
                .surname("Smith")
                .birthDate(LocalDate.of(1990, 1, 10))
                .document(Document.builder()
                        .id(UUID.fromString("11111111-1111-1111-1111-111111111111"))
                        .number(123L)
                        .type(DocumentType.ID_CARD)
                        .build())
                .contact(Contact.builder()
                        .phoneNumber("12345567")
                        .email("lukasz@o2.pl")
                        .build())
                .build();
    }

    public static Guest getGuest2(){
        return Guest.builder()
                .id(UUID.fromString("44444444-4444-4444-4444-444444444444"))
                .name("John")
                .surname("Smith")
                .birthDate(LocalDate.of(1990, 1, 10))
                .document(Document.builder()
                        .id(UUID.fromString("22222222-2222-2222-2222-222222222222"))
                        .number(124L)
                        .type(DocumentType.PASSPORT)
                        .build())
                .contact(Contact.builder()
                        .phoneNumber("34567890")
                        .email("nowog@o2.pl")
                        .build())
                .build();
    }

    public static Reservation reservation1() {
        return Reservation.builder()
                .id(UUID.fromString("33333333-3333-3333-3333-333333333333"))
                .room(getRoom())
                .guest(getGuest())
                .checkIn(LocalDate.of(2025, 3, 21))
                .checkOut(LocalDate.of(2025, 3, 25))
                .status(ReservationStatus.CANCELLED)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Reservation reservation2() {
        return Reservation.builder()
                .id(UUID.fromString("66666666-6666-6666-6666-666666666666"))
                .room(getRoom2())
                .guest(getGuest2())
                .checkIn(LocalDate.of(2025, 3, 21))
                .checkOut(LocalDate.of(2025, 3, 25))
                .status(ReservationStatus.CANCELLED)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
