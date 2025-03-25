package com.lukasz.hotel_reservation.domain.contact;

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

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/contacts")
public class ContactController {

    private final ContactService contactService;

    @PostMapping
    ResponseEntity<Void> create(@RequestBody @Valid ContactCreatorRequest contact) {
        contactService.save(contact);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/{uuid}")
    ResponseEntity<ContactFinderResponse> find(@NotNull @PathVariable UUID uuid) {
        ContactFinderResponse contact = contactService.find(uuid);
        return ResponseEntity.ok(contact);
    }
}
