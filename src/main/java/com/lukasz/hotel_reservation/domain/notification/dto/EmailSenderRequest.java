package com.lukasz.hotel_reservation.domain.notification.dto;

import jakarta.validation.constraints.NotNull;

public record EmailSenderRequest(@NotNull String to, @NotNull String subject, @NotNull String body, String attachmentPath) {
}
