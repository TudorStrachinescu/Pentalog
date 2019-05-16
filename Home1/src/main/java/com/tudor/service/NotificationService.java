package com.tudor.service;

import com.tudor.model.Notification;
import com.tudor.model.User;
import com.tudor.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class NotificationService {
    @Autowired
    private NotificationRepository userNotification;

    void addNotification(User user, String details){
        Notification notification = new Notification(user, details);
        userNotification.save(notification);
    }
}
