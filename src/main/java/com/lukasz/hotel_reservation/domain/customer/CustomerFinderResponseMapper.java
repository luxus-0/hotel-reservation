package com.lukasz.hotel_reservation.domain.customer;

public record CustomerFinderResponseMapper() {
    public static CustomerFinderResponse mapToCustomerFinderResponse(Customer customer) {
        return CustomerFinderResponse.builder()
                .name(customer.getName())
                .surname(customer.getSurname())
                .phone(customer.getContact().getPhone())
                .email(customer.getContact().getEmail())
                .build();
    }
}
