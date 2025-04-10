package com.lukasz.hotel_reservation.domain.notification;

import com.lukasz.hotel_reservation.domain.notification.dto.EmailSenderRequest;
import com.lukasz.hotel_reservation.domain.notification.dto.SmsSenderRequest;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

    private final SmsNotificationService smsSender;
    private final EmailNotificationService emailSender;

    @PostMapping("/email")
    ResponseEntity<Void> sendEmail(EmailSenderRequest emailRequest) throws MessagingException, FileNotFoundException {
        emailSender.send(emailRequest.to(), emailRequest.subject(), emailRequest.body(), emailRequest.attachmentPath());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/sms")
    ResponseEntity<Void> sendSms(SmsSenderRequest smsRequest) {
        smsSender.send(smsRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
