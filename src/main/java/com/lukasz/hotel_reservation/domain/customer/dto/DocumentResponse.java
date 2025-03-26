package com.lukasz.hotel_reservation.domain.customer.dto;

import com.lukasz.hotel_reservation.domain.customer.DocumentType;
import jakarta.validation.constraints.NotNull;

public record DocumentResponse(@NotNull
                               DocumentType type,
                               @NotNull
                               Long number) {

}
