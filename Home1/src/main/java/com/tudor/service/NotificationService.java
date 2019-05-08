package com.tudor.service;

import com.tudor.model.Notification;
import com.tudor.model.User;
import com.tudor.repository.UserNotification;

class NotificationService {
    private UserNotification userNotification = new UserNotification();

    void addNotification(User user, String details){
        Notification notification = new Notification(user, details);
        userNotification.addNotification(notification);
    }
}
