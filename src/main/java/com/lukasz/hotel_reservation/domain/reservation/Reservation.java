package com.lukasz.hotel_reservation.domain.reservation;

import com.lukasz.hotel_reservation.domain.guest.Guest;
import com.lukasz.hotel_reservation.domain.room.Room;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


/**
 * Represents a reservation in the hotel system, including status, dates, guest, and room.
 * Note: Currently, a {@code @ManyToOne} relationship is used for the {@code room},
 * but it could be replaced with a {@code @ManyToMany} relationship to allow multiple rooms per reservation.
 */

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    private UUID id;
    private ReservationStatus status;
    @ManyToOne
    private Guest guest;
    @ManyToOne
    private Room room;
    @FutureOrPresent
    private LocalDate checkIn;
    @Future
    private LocalDate checkOut;
    @CreationTimestamp
    private LocalDateTime createdAt;
}


