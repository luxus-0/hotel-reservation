package com.lukasz.hotel_reservation.domain.document;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Document {
    @Id
    private UUID id;
    private DocumentType type;
    private Long number;
}
