package com.lukasz.hotel_reservation.domain.customer;

import com.lukasz.hotel_reservation.domain.address.Address;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

import static com.lukasz.hotel_reservation.domain.customer.CustomerConstantMessage.*;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Pattern(regexp = CUSTOMER_NAME_REGEX, message = CUSTOMER_NAME_REGEX_MESSAGE)
    private String name;
    @Pattern(regexp = CUSTOMER_SURNAME_REGEX, message = CUSTOMER_SURNAME_REGEX_MESSAGE)
    private String surname;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    @Pattern(regexp = EMAIL_REGEX, message = EMAIL_REGEX_MESSAGE)
    private String email;
    @Pattern(regexp = PHONE_REGEX, message = PHONE_REGEX_MESSAGE)
    private String phone;
    private Long documentId;
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;
}
