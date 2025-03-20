package com.lukasz.hotel_reservation.document;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Document {
    @Id
    private UUID id;
    private DocumentType type;
    private int number;

    public UUID getId() {
        return id;
    }

    public DocumentType getType() {
        return type;
    }

    public int getNumber() {
        return number;
    }
}
