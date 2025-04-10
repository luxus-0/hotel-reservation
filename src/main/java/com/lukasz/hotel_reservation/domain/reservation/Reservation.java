package com.lukasz.hotel_reservation.domain.reservation;

import com.lukasz.hotel_reservation.domain.customer.Customer;
import com.lukasz.hotel_reservation.domain.room.Room;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.format.annotation.DateTimeFormat;

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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;
    @FutureOrPresent
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "dd-MM-yyyy")
    private LocalDateTime checkIn;
    @Future
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "dd-MM-yyyy")
    private LocalDateTime checkOut;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Lob
    @JdbcTypeCode(SqlTypes.BINARY)
    @Column(name = "reservation_pdf", columnDefinition = "bytea", nullable = false)
    private byte[] pdf;
}


