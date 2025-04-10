package com.lukasz.hotel_reservation.domain.address;

import com.lukasz.hotel_reservation.domain.address.dto.AddressCreatorRequest;
import com.lukasz.hotel_reservation.domain.address.dto.AddressFinderResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    @GetMapping
    public ResponseEntity<List<AddressFinderResponse>> find() {
        return ResponseEntity.ok(addressService.find());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<AddressFinderResponse> find(@NotNull @PathVariable UUID uuid) {
        return ResponseEntity.ok(addressService.find(uuid));
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid AddressCreatorRequest address) {
        addressService.create(address);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }
}
