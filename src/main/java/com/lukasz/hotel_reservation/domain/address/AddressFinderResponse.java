package com.lukasz.hotel_reservation.domain.address;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import static com.lukasz.hotel_reservation.domain.address.AddressConstantMessage.*;

@Builder
public record AddressFinderResponse(
        @NotNull
        @Pattern(regexp = CITY_REGEX, message = "Incorrect city name!")
        String city,

        @Min(1) @Pattern(regexp = STREET_NUMBER_REGEX, message = "Street number must be positive!")
        int number,

        @NotNull
        @Pattern(regexp = CITY_REGEX, message = "Incorrect street name!")
        String street,

        @NotNull
        @Pattern(regexp = POSTAL_CODE_REGEX, message = "Incorrect postal code!")
        String postalCode,

        @NotNull
        @Pattern(regexp = CITY_REGEX, message = "Incorrect country name!")
        String country
) {
}
