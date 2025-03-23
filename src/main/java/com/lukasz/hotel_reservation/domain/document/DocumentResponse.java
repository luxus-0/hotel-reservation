package com.lukasz.hotel_reservation.domain.document;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DocumentResponse(@NotNull
                               DocumentType type,
                               @NotNull
                               UUID number){

}
