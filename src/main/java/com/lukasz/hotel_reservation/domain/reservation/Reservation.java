package com.lukasz.hotel_reservation.domain.reservation;

import com.lukasz.hotel_reservation.domain.guest.Guest;
import com.lukasz.hotel_reservation.domain.room.Room;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import java.util.UUID;


/**
 * Represents a reservation in the hotel system, including status, dates, guest, and room.
 * Note: Currently, a {@code @ManyToOne} relationship is used for the {@code room},
 * but it could be replaced with a {@code @ManyToMany} relationship to allow multiple rooms per reservation.
 */

@Entity
public class Reservation {
    @Id
    private UUID id;
    private ReservationStatus status;
    @OneToOne
    private ReservationDate date;
    @ManyToOne
    private Guest guest;
    @ManyToOne
    private Room room;

    public Reservation() {
    }


    public UUID getId() {
        return id;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public ReservationDate getDate() {
        return date;
    }

    public Guest getGuest() {
        return guest;
    }

    public Room getRoom() {
        return room;
    }
}


