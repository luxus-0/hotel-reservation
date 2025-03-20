package com.lukasz.hotel_reservation.domain.room;

import com.lukasz.hotel_reservation.domain.reservation.Reservation;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;
import java.util.UUID;

@Entity
public class Room {
    @Id
    private UUID id;
    private int roomNumber;
    private RoomType type;

    public UUID getId() {
        return id;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public RoomType getType() {
        return type;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    @OneToMany
    private List<Reservation> reservations;
    
    
}
