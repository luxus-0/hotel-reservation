package com.lukasz.hotel_reservation.domain.address;

import com.lukasz.hotel_reservation.domain.address.dto.AddressCreatorRequest;
import com.lukasz.hotel_reservation.domain.address.dto.AddressFinderResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public List<Address> findAll(){
        return addressRepository.findAll();
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
