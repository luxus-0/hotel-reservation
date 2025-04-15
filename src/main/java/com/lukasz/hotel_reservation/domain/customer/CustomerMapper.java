package com.lukasz.hotel_reservation.domain.customer;

import com.lukasz.hotel_reservation.domain.address.Address;
import com.lukasz.hotel_reservation.domain.address.dto.AddressFinderResponse;
import com.lukasz.hotel_reservation.domain.customer.dto.CustomerCreatorRequest;
import com.lukasz.hotel_reservation.domain.customer.dto.CustomerFinderResponse;

import java.util.List;

public class CustomerMapper {
    static Address toAddress(CustomerCreatorRequest customerRequest) {
        return Address.builder()
                .city(customerRequest.address().city())
                .country(customerRequest.address().country())
                .postalCode(customerRequest.address().postalCode())
                .street(customerRequest.address().street())
                .number(customerRequest.address().number())
                .build();
    }

    static AddressFinderResponse toAddress(Customer customer) {
        return AddressFinderResponse.builder()
                .street(customer.getAddress().getStreet())
                .number(customer.getAddress().getNumber())
                .city(customer.getAddress().getCity())
                .country(customer.getAddress().getCountry())
                .postalCode(customer.getAddress().getPostalCode())
                .build();
    }

    static Customer toCustomer(CustomerCreatorRequest request) {
        return Customer.builder()
                .name(request.name())
                .surname(request.surname())
                .birthDate(request.birthDate())
                .phone((request.phone()))
                .email(request.email())
                .address(toAddress(request))
                .build();
    }

    public static Customer toCustomer(CustomerFinderResponse customer) {
        return Customer.builder()
                .id(customer.uuid())
                .name(customer.name())
                .surname(customer.surname())
                .phone(customer.phone())
                .email(customer.email())
                .birthDate(customer.birthDate())
                .build();
    }

    public static CustomerFinderResponse toCustomer(Customer customer) {
        return CustomerFinderResponse.builder()
                .uuid(customer.getId())
                .name(customer.getName())
                .address(toAddress(customer))
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .birthDate(customer.getBirthDate())
                .build();
    }

    static List<CustomerFinderResponse> toCustomers(List<Customer> customers) {
        return customers.stream()
                .map(customer -> CustomerFinderResponse.builder()
                        .name(customer.getName())
                        .surname(customer.getSurname())
                        .email(customer.getEmail())
                        .phone(customer.getPhone())
                        .birthDate(customer.getBirthDate())
                        .address(toAddress(customer))
                        .build())
                .toList();
    }
}
