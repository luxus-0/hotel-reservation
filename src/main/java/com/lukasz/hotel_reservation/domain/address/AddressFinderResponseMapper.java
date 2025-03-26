package com.lukasz.hotel_reservation.domain.address;

import com.lukasz.hotel_reservation.domain.address.dto.AddressFinderResponse;

class AddressFinderResponseMapper {
    public static AddressFinderResponse toAddressFinderResponse(Address address) {
        return AddressFinderResponse.builder()
                .street(address.getStreet())
                .number(address.getNumber())
                .postalCode(address.getPostalCode())
                .city(address.getCity())
                .country(address.getCountry())
                .build();
    }
}
