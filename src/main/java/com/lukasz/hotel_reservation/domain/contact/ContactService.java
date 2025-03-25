package com.lukasz.hotel_reservation.domain.contact;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;

    public ContactFinderResponse find(UUID uuid) {
        return contactRepository.findById(uuid)
                .map(contact -> new ContactFinderResponse(contact.getEmail(), contact.getPhone()))
                .orElseThrow(() -> new ContactNotFoundException("Contact not found"));
    }

    public void save(ContactCreatorRequest contact) {
        contactRepository.save(Contact.builder()
                .email(contact.email())
                .phone(contact.phone())
                .build());
    }
}
