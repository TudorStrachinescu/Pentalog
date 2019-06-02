package com.tudor.service;

import com.tudor.dto.converter.UserConverter;
import com.tudor.model.Notification;
import com.tudor.model.Person;
import com.tudor.model.User;
import com.tudor.repository.NotificationRepository;
import com.tudor.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired

    private PersonRepository personRepository;

    @Autowired
    private UserConverter userConverter;

    void addNotification(User user, String details){
        Notification notification = new Notification(user, details);
        notificationRepository.save(notification);
    }

    @Scheduled(cron = "0 0 * * * ?")
    public void sendNotifications(){
        for(Notification n : notificationRepository.findAll()){
            if(n.getSentTime() == null){
                Optional<Person> email =  personRepository.findByUser(n.getNotificationUser());
                if(email.isPresent()) {
                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setSubject("transaction notification from: " + n.getCreatedTime());
                    message.setFrom("from_placeholder");
                    message.setTo(email.get().getEmail());
                    message.setText(n.getDetails());
                    n.setSentTime();
                    notificationRepository.save(n);
                }
            }
        }
    }
}
