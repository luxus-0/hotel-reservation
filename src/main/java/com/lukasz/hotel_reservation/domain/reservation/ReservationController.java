package com.lukasz.hotel_reservation.domain.reservation;

import com.itextpdf.text.DocumentException;
import com.lukasz.hotel_reservation.domain.reservation.dto.ReservationCreatorRequest;
import com.lukasz.hotel_reservation.domain.reservation.dto.ReservationFinderResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
    public void create(@RequestBody @Valid ReservationCreatorRequest reservationCreatorRequest) throws DocumentException, IOException {
        reservationService.create(reservationCreatorRequest);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull UUID uuid) {
        reservationService.cancel(uuid);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/days/{fromDate}/{toDate}")
    public ResponseEntity<Long> calculateDays(@PathVariable @NotNull LocalDateTime fromDate, @PathVariable @NotNull LocalDateTime toDate) {
        Long days = reservationService.countDays(fromDate, toDate);
        return ResponseEntity.ok(days);
    }
}
