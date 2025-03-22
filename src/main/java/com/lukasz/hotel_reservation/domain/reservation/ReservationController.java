package com.lukasz.hotel_reservation.domain.reservation;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservation")
@AllArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    public void saveReservation(@RequestBody @Valid ReservationRequest reservationRequest){
        reservationService.createReservation(reservationRequest);
    }
}
