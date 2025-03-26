package com.lukasz.hotel_reservation.domain.reservation;

import com.itextpdf.text.DocumentException;
import com.lukasz.hotel_reservation.domain.pdf.dto.PdfGeneratorRequest;
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
import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/reservations")
@AllArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping("/{uuid}")
    public ResponseEntity<ReservationFinderResponse> find(@NotNull @PathVariable UUID uuid) {
        ReservationFinderResponse reservation = reservationService.find(uuid);
        return ResponseEntity.ok(reservation);
    }

    @GetMapping
    public ResponseEntity<ReservationFinderResponse> find() {
        ReservationFinderResponse reservation = reservationService.find();
        return ResponseEntity.status(OK).body(reservation);
    }

    @PostMapping
    public void create(@RequestBody @Valid ReservationCreatorRequest reservationCreatorRequest, @RequestBody @Valid PdfGeneratorRequest pdfRequest) throws DocumentException, IOException {
        reservationService.create(reservationCreatorRequest, pdfRequest);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@NotNull @PathVariable UUID uuid) {
        reservationService.cancel(uuid);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/days/{from}/{to}")
    public void calculateDays(@PathVariable LocalDateTime from, @PathVariable LocalDateTime to) {
        reservationService.countDays(from, to);
    }
}
