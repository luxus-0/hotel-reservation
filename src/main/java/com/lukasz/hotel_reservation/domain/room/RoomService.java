package com.lukasz.hotel_reservation.domain.room;

import com.lukasz.hotel_reservation.domain.room.dto.CreateRoomRequest;
import com.lukasz.hotel_reservation.domain.room.dto.RoomFinderResponse;
import com.lukasz.hotel_reservation.domain.room.dto.RoomTotalCostRequest;
import com.lukasz.hotel_reservation.domain.room.dto.RoomTotalCostResponse;
import com.lukasz.hotel_reservation.domain.room.exceptions.RoomNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.Currency;
import java.util.UUID;

@Service
@AllArgsConstructor
@Log4j2
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomFinderResponse find() {
        return roomRepository.findAll().stream()
                .map(RoomFinderResponseMapper::mapToRoomFinderResponse).findAny()
                .orElseThrow(RoomNotFoundException::new);
    }

    public RoomFinderResponse find(UUID roomId) {
        return roomRepository.findById(roomId)
                .map(RoomFinderResponseMapper::mapToRoomFinderResponse)
                .orElseThrow(() -> new RoomNotFoundException(roomId));
    }

    public void create(CreateRoomRequest createRoomRequest) {
        Room room = Room.builder()
                .status(RoomStatus.AVAILABLE)
                .type(createRoomRequest.type())
                .number(createRoomRequest.number())
                .version(createRoomRequest.version())
                .build();

        Room roomSaved = roomRepository.save(room);
        log.info("Room saved to database: {}", roomSaved);
    }

    public RoomTotalCostResponse calculateTotalCost(RoomTotalCostRequest request) {
        long days = ChronoUnit.DAYS.between(request.checkIn(), request.checkOut());
        String currency = Currency.getInstance(request.currencyCode()).toString();
        BigDecimal roomTotalCost = request.pricePerNight().multiply(BigDecimal.valueOf(days));
        return new RoomTotalCostResponse(roomTotalCost, currency);
    }
}
