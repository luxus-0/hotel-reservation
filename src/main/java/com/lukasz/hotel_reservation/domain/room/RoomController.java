package com.lukasz.hotel_reservation.domain.room;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/rooms")
public class RoomController {
    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<RoomFinderResponse> find(){
        RoomFinderResponse room = roomService.find();
        return ResponseEntity.ok(room);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<RoomFinderResponse> find(@PathVariable @NotNull UUID uuid){
        RoomFinderResponse room = roomService.find(uuid);
        return ResponseEntity.ok(room);
    }



    @PostMapping
    ResponseEntity<Void> create(@RequestBody @Valid CreateRoomRequest room){
        roomService.create(room);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @GetMapping("/cost")
    ResponseEntity<RoomTotalCostResponse> calculateTotalCost(@RequestBody @Valid RoomTotalCostRequest request){
        RoomTotalCostResponse totalCost = roomService.calculateTotalCost(request);
        return ResponseEntity.ok(totalCost);
    }
}
