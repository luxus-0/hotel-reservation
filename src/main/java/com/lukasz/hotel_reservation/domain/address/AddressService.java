package com.lukasz.hotel_reservation.domain.address;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public AddressFinderResponse find(UUID uuid) {
        return addressRepository.findById(uuid)
                .map(AddressFinderResponseMapper::toAddressFinderResponse)
                .orElseThrow(() -> new Address.AddressNotFoundException("Address id:" + uuid + " not found"));
    }

    public void create(AddressCreatorRequest address) {
        Address addr = Address.builder()
                .street(address.street())
                .number(address.streetNumber())
                .postalCode(address.postalCode())
                .city(address.city())
                .country(address.country())
                .build();

        addressRepository.save(addr);
    }
}
