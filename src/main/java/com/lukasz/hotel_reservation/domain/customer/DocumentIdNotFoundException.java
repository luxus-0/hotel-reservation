package com.lukasz.hotel_reservation.domain.customer;

public class DocumentIdNotFoundException extends RuntimeException{
    public DocumentIdNotFoundException(Long id){
        super("Document id: " + id + " not found");
    }
}
