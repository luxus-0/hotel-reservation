package com.lukasz.hotel_reservation.domain.notification;

import jakarta.mail.MessagingException;

import java.io.FileNotFoundException;

public interface NotificationSender {

    void send(String to, String subject, String text, String attachmentPath) throws MessagingException, FileNotFoundException;
    void send(String to, String subject, String text);
}
