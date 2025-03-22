package com.lukasz.hotel_reservation.domain.room;

import com.lukasz.hotel_reservation.domain.room.exceptions.RoomNotAvailableException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    private Room getAvailable(UUID roomId) {
        return roomRepository.findById(roomId)
                .filter(room -> room.getStatus() == RoomStatus.AVAILABLE)
                .orElseThrow(() -> new RoomNotAvailableException(roomId));
    }
}
