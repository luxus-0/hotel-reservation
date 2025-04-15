package com.lukasz.hotel_reservation.domain.reservation;

import com.lukasz.hotel_reservation.domain.reservation.dto.ReservationCreatorRequest;
import com.lukasz.hotel_reservation.domain.reservation.dto.ReservationCreatorResponse;
import com.lukasz.hotel_reservation.domain.reservation.dto.ReservationFinderResponse;
import com.lukasz.hotel_reservation.domain.reservation.dto.ReservationsCreatorResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/reservations")
@AllArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping("/{uuid}")
    public ResponseEntity<ReservationFinderResponse> find(@PathVariable @NotNull UUID uuid) {
        ReservationFinderResponse reservation = reservationService.find(uuid);
        return ResponseEntity.ok(reservation);
    }

    @GetMapping
    public ResponseEntity<List<ReservationFinderResponse>> find() {
        List<ReservationFinderResponse> reservation = reservationService.find();
        return ResponseEntity.status(OK).body(reservation);
    }

    @PostMapping
    public ResponseEntity<ReservationCreatorResponse> create(@RequestBody @Valid ReservationCreatorRequest reservationCreatorRequest) throws Exception {
        return ResponseEntity.status(CREATED).body(reservationService.create(reservationCreatorRequest));
    }

    @PostMapping("/all")
    public ResponseEntity<ReservationCreatorResponse> create(@RequestBody @Valid List<Reservation> reservations){
        return ResponseEntity.status(CREATED).body(reservationService.create(reservations));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull UUID uuid) {
        reservationService.cancel(uuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete() {
        reservationService.cancelAll();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/days/{fromDate}/{toDate}")
    public ResponseEntity<Long> calculateDays(@PathVariable @NotNull LocalDateTime fromDate, @PathVariable @NotNull LocalDateTime toDate) {
        Long days = reservationService.countDays(fromDate, toDate);
        return ResponseEntity.ok(days);
    }
}
