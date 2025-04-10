package com.lukasz.hotel_reservation.domain.notification;

import com.lukasz.hotel_reservation.domain.notification.exceptions.EmailSendingException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Instant;
import java.util.Date;

@Log4j2
@AllArgsConstructor
@Service
public class EmailNotificationService implements NotificationService {

    private final JavaMailSender mailSender;

    @Override
    public void send(String to, String subject, String text, String attachmentPath) throws MessagingException, FileNotFoundException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);
        helper.setSentDate(Date.from(Instant.now()));
        FileSystemResource file = new FileSystemResource(new File(attachmentPath));

        if (!file.exists()) {
            throw new FileNotFoundException("File not exists in path: " + attachmentPath);
        }

        helper.addAttachment(file.getFilename(), file);

        mailSender.send(message);
        log.info("Email send successfully to: {}", to);
    }

    @Override
    public void send(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            message.setSentDate(Date.from(Instant.now()));
            mailSender.send(message);

            log.info("Email sent to: {} successfully", to);
        } catch (MailException ex) {
            throw new EmailSendingException("Invalid send email to: " + to, ex);
        }
    }
}
