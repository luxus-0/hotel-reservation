package com.lukasz.hotel_reservation.infrastructure;

import com.lukasz.hotel_reservation.domain.reservation.exceptions.DuplicateReservationException;
import com.lukasz.hotel_reservation.domain.room.exceptions.RoomNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<String> handleRoomNotAvailable(RoomNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(DuplicateReservationException.class)
    public ResponseEntity<String> handleDuplicateReservation(DuplicateReservationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }
}

