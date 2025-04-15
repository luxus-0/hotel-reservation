package com.lukasz.hotel_reservation.domain.customer.exceptions;

import java.util.UUID;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(UUID customerId) {
        super("Customer ID: " + customerId + " not found");
    }

    public CustomerNotFoundException(String message){
        super(message);
    }
}
