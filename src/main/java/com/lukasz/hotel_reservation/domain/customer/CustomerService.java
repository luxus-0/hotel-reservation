package com.lukasz.hotel_reservation.domain.customer;

import com.lukasz.hotel_reservation.domain.address.Address;
import com.lukasz.hotel_reservation.domain.address.dto.AddressFinderResponse;
import com.lukasz.hotel_reservation.domain.customer.dto.CustomerCreatorRequest;
import com.lukasz.hotel_reservation.domain.customer.dto.CustomerFinderResponse;
import com.lukasz.hotel_reservation.domain.customer.exceptions.CustomerNotFoundException;
import com.lukasz.hotel_reservation.domain.customer.exceptions.DocumentIdNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.lukasz.hotel_reservation.domain.customer.CustomerMapper.getAddress;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;


    public List<CustomerFinderResponse> find() {
        return customerRepository.findAll().stream()
                .map(customer -> List.of(CustomerFinderResponse.builder()
                        .name(customer.getName())
                        .surname(customer.getSurname())
                        .email(customer.getEmail())
                        .phone(customer.getPhone())
                        .birthDate(customer.getBirthDate())
                        .address(getAddress(customer))
                        .build()))
                .findAny()
                .orElseThrow(CustomerNotFoundException::new);
    }

    public CustomerFinderResponse find(UUID uuid) {
        return customerRepository.findById(uuid).map(CustomerFinderResponseMapper::mapToCustomerFinderResponse)
                .orElseThrow(CustomerNotFoundException::new);
    }

    public Long finDocument(Long documentId) {
        return customerRepository.findAll().stream()
                .map(Customer::getDocumentId).findAny()
                .orElseThrow(() -> new DocumentIdNotFoundException(documentId));
    }

    @Transactional
    public void create(List<CustomerCreatorRequest> customersRequest) {

        Optional<CustomerCreatorRequest> customers = customersRequest.stream().findAny()

        customerRepository.saveAll(customers);
    }
}
