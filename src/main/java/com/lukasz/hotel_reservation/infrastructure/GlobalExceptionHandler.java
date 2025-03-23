package com.lukasz.hotel_reservation.infrastructure;

import com.lukasz.hotel_reservation.domain.reservation.exceptions.IncorrectReservationDate;
import com.lukasz.hotel_reservation.domain.reservation.exceptions.ReservationExistsException;
import com.lukasz.hotel_reservation.domain.room.exceptions.RoomNotAvailableException;
import com.lukasz.hotel_reservation.domain.room.exceptions.RoomNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RoomNotAvailableException.class)
    public ResponseEntity<String> handleRoomNotAvailable(RoomNotAvailableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<String> handleRoomNotFound(RoomNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(ReservationExistsException.class)
    public ResponseEntity<String> handleReservationExists(ReservationExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }

    @ExceptionHandler(IncorrectReservationDate.class)
    public ResponseEntity<String> handleIncorrectReservationDate(IncorrectReservationDate ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
}

