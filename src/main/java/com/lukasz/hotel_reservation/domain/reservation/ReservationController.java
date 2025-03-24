package com.lukasz.hotel_reservation.domain.reservation;

import com.itextpdf.text.DocumentException;
import com.lukasz.hotel_reservation.domain.pdf.PdfGeneratorRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public void create(@RequestBody @Valid ReservationCreatorRequest reservationCreatorRequest,@RequestBody @Valid PdfGeneratorRequest pdfRequest) throws DocumentException, IOException {
        reservationService.create(reservationCreatorRequest, pdfRequest);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@NotNull @PathVariable UUID uuid) {
        reservationService.cancel(uuid);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/days/{from}/{to}")
    public void calculateDays(@PathVariable LocalDateTime from, @PathVariable LocalDateTime to){
        reservationService.countDays(from, to);
    }
}
