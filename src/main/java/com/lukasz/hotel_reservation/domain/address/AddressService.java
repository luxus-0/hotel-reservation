package com.lukasz.hotel_reservation.domain.address;

import com.lukasz.hotel_reservation.domain.address.dto.AddressCreatorRequest;
import com.lukasz.hotel_reservation.domain.address.dto.AddressFinderResponse;
import com.lukasz.hotel_reservation.domain.address.exceptions.AddressNotFoundException;
import com.lukasz.hotel_reservation.domain.customer.exceptions.CustomerNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public List<AddressFinderResponse> find() {
        List<Address> addresses = addressRepository.findAll();
        if (addresses.isEmpty()) {
            throw new CustomerNotFoundException();
        }

        return addresses.stream()
                .map(address -> AddressFinderResponse.builder()
                        .street(address.getCity())
                        .number(address.getNumber())
                        .postalCode(address.getPostalCode())
                        .city(address.getCity())
                        .country(address.getCountry())
                        .build())
                .toList();
    }

public AddressFinderResponse find(UUID uuid) {
    return addressRepository.findById(uuid)
            .map(AddressFinderResponseMapper::toAddressFinderResponse)
            .orElseThrow(() -> new AddressNotFoundException("Address uuid:" + uuid + " not found"));
}

public void create(AddressCreatorRequest address) {
    Address addr = Address.builder()
            .street(address.street())
            .number(address.number())
            .postalCode(address.postalCode())
            .city(address.city())
            .country(address.country())
            .build();

    addressRepository.save(addr);
}
}
