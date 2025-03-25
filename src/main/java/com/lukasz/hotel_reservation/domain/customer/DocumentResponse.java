package com.lukasz.hotel_reservation.domain.customer;

import jakarta.validation.constraints.NotNull;

public record DocumentResponse(@NotNull
                               DocumentType type,
                               @NotNull
                               Long number){

}
