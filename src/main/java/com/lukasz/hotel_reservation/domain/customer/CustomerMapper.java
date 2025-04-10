package com.lukasz.hotel_reservation.domain.customer;

import com.lukasz.hotel_reservation.domain.address.Address;
import com.lukasz.hotel_reservation.domain.address.dto.AddressFinderResponse;
import com.lukasz.hotel_reservation.domain.customer.dto.CustomerCreatorRequest;

class CustomerMapper {
    static Address mapToAddress(CustomerCreatorRequest customerRequest) {
        return Address.builder()
                .city(customerRequest.address().city())
                .country(customerRequest.address().country())
                .postalCode(customerRequest.address().postalCode())
                .street(customerRequest.address().street())
                .number(customerRequest.address().number())
                .build();
    }

    static AddressFinderResponse mapToAddress(Customer customer) {
        return AddressFinderResponse.builder()
                .street(customer.getAddress().getStreet())
                .number(customer.getAddress().getNumber())
                .city(customer.getAddress().getCity())
                .country(customer.getAddress().getCountry())
                .postalCode(customer.getAddress().getPostalCode())
                .build();
    }

    static Customer mapToCustomer(CustomerCreatorRequest request) {
        return Customer.builder()
                .name(request.name())
                .surname(request.surname())
                .birthDate(request.birthDate())
                .phone((request.phone()))
                .email(request.email())
                .address(mapToAddress(request))
                .build();
    }
}
