package com.lukasz.hotel_reservation.domain.guest;

import java.util.UUID;

public class GuestNotFoundException extends RuntimeException {
    public GuestNotFoundException(UUID guestId){
        super("Guest id: " + guestId + " not found");
    }
}
