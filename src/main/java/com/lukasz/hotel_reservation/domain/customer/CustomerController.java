package com.lukasz.hotel_reservation.domain.customer;

import com.lukasz.hotel_reservation.domain.customer.dto.CustomerCreatorRequest;
import com.lukasz.hotel_reservation.domain.customer.dto.CustomerFinderResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/{uuid}")
    public ResponseEntity<CustomerFinderResponse> find(@NotNull @PathVariable UUID uuid) {
        CustomerFinderResponse customer = customerService.find(uuid);
        return ResponseEntity.ok(customer);
    }

    @GetMapping()
    public ResponseEntity<List<CustomerFinderResponse>> find() {
        List<CustomerFinderResponse> customer = customerService.find();
        return ResponseEntity.ok(customer);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid CustomerCreatorRequest customer) {
        customerService.create(customer);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
