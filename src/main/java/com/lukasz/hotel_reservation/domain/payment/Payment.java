package com.lukasz.hotel_reservation.domain.payment;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Payment {
    @Id
    private UUID id;
    private PaymentStatus status;
    private PaymentMethod method;
    private LocalDate date;
    private BigDecimal amount;

    public Payment() {
    }

    public UUID getId() {
        return id;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getAmount() {
        return amount;
    }


}
