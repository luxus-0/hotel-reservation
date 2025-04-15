package com.lukasz.hotel_reservation.domain.customer;

import com.lukasz.hotel_reservation.domain.customer.dto.CustomerCreatorRequest;
import com.lukasz.hotel_reservation.domain.customer.dto.CustomerFinderResponse;
import com.lukasz.hotel_reservation.domain.customer.exceptions.CustomerNotFoundException;
import com.lukasz.hotel_reservation.domain.customer.exceptions.DocumentIdNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.lukasz.hotel_reservation.domain.customer.CustomerMapper.toCustomers;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;


    public List<CustomerFinderResponse> find() {
        List<Customer> customers = customerRepository.findAll();
        if (customers.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found");
        }

        return toCustomers(customers);
    }

    public CustomerFinderResponse find(UUID uuid) {
        return customerRepository.findById(uuid)
                .map(CustomerMapper::toCustomer)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
    }

    public DocumentFinderResponse findDocument(Long documentId) {
        return customerRepository.findAll().stream()
                .map(document -> new DocumentFinderResponse(document.getDocumentId(), document.getDocumentType()))
                .findAny()
                .orElseThrow(() -> new DocumentIdNotFoundException(documentId));
    }

    @Transactional
    public void create(List<CustomerCreatorRequest> customersRequest) {
        List<Customer> customers = customersRequest.stream()
                .map(CustomerMapper::toCustomer)
                .toList();

        customerRepository.saveAll(customers);
    }
}
