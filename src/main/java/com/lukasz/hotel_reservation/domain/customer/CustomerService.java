package com.lukasz.hotel_reservation.domain.customer;

import com.lukasz.hotel_reservation.domain.address.Address;
import com.lukasz.hotel_reservation.domain.contact.Contact;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerFinderResponse find() {
        return customerRepository.findAll().stream()
                .map(customer -> CustomerFinderResponse.builder()
                        .name(customer.getName())
                        .surname(customer.getSurname())
                        .email(customer.getContact().getEmail())
                        .phone(customer.getContact().getPhone())
                        .birthDate(customer.getBirthDate())
                        .build())
                .findAny()
                .orElseThrow(CustomerNotFoundException::new);
    }

    public CustomerFinderResponse find(UUID uuid) {
        return customerRepository.findById(uuid).map(CustomerFinderResponseMapper::mapToCustomerFinderResponse)
                .orElseThrow(CustomerNotFoundException::new);
    }

    public Long finDocument(Long documentId){
        return customerRepository.findAll().stream()
                .map(Customer::getDocumentId).findAny()
                .orElseThrow(() -> new DocumentIdNotFoundException(documentId));
    }

    @Transactional
    public void create(CustomerCreatorRequest customerRequest) {

        Customer customer = Customer.builder()
                .name(customerRequest.name())
                .surname(customerRequest.surname())
                .contact(getContact(customerRequest))
                .address(getAddress(customerRequest))
                .documentType(customerRequest.document().type())
                .build();

        customerRepository.save(customer);
    }

    private static Address getAddress(CustomerCreatorRequest customerRequest) {
        return Address.builder()
                .city(customerRequest.address().city())
                .country(customerRequest.address().country())
                .postalCode(customerRequest.address().postalCode())
                .street(customerRequest.address().street())
                .number(customerRequest.address().number())
                .build();
    }

    private static Contact getContact(CustomerCreatorRequest customerRequest) {
        return Contact.builder()
                .phone(customerRequest.contact().phone())
                .email(customerRequest.contact().email())
                .build();
    }
}
