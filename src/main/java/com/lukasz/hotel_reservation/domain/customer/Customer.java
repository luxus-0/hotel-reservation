package com.lukasz.hotel_reservation.domain.customer;

import com.lukasz.hotel_reservation.domain.address.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String surname;
    private LocalDate birthDate;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Address address;
    private String email;
    private String phone;
    private Long documentId;
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;
}
