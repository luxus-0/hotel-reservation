package com.lukasz.hotel_reservation.domain.address;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    public ResponseEntity<AddressFinderResponse> find(@NotNull @PathVariable UUID uuid) {
        AddressFinderResponse address = addressService.find(uuid);
        return ResponseEntity.ok(address);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid AddressCreatorRequest address) {
        addressService.create(address);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }
}
