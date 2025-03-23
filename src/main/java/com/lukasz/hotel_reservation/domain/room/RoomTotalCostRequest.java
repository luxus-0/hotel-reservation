package com.lukasz.hotel_reservation.domain.room;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.lukasz.hotel_reservation.domain.room.RoomConstantMessage.CURRENCY_CODE_MESSAGE;
import static com.lukasz.hotel_reservation.domain.room.RoomConstantMessage.CURRENCY_CODE_REGEX;

public record RoomTotalCostRequest(
        @NotNull @Min(0) BigDecimal pricePerNight,
        @NotNull @Pattern(regexp = CURRENCY_CODE_REGEX, message = CURRENCY_CODE_MESSAGE) String currencyCode,
        @NotNull RoomType roomType,
        @NotNull LocalDateTime checkIn,
        @NotNull LocalDateTime checkOut) {
}
