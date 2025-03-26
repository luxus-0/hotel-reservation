package com.lukasz.hotel_reservation.domain.pdf.exceptions;

public class FontNotFoundException extends RuntimeException {
    public FontNotFoundException(String message){
        super(message);
    }
}
