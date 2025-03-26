package com.lukasz.hotel_reservation.domain.customer;

import com.lukasz.hotel_reservation.domain.customer.dto.CustomerFinderResponse;

public record CustomerFinderResponseMapper() {
    public static CustomerFinderResponse mapToCustomerFinderResponse(Customer customer) {
        return CustomerFinderResponse.builder()
                .name(customer.getName())
                .surname(customer.getSurname())
                .birthDate(customer.getBirthDate())
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .build();
    }
}
