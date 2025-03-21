package com.lukasz.hotel_reservation.domain.room;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Room {
    @Id
    private UUID id;
    private int roomNumber;
    private RoomType type;
    private RoomStatus status = RoomStatus.AVAILABLE;
    @Version
    private Long version;
}
