package com.lukasz.hotel_reservation.domain.notification;

public interface NotificationSender {
    void send(Notification notification);
}
