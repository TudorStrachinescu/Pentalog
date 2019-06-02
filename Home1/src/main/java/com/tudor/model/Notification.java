package com.tudor.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification")

public class Notification implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "user_notification_fk"))
    private User notificationUser;

    @Column(name = "details")
    private String details;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "sent_time")
    private LocalDateTime sentTime;

    public LocalDateTime getSentTime() {
        return sentTime;
    }

    public String getDetails() {
        return details;
    }

    public User getNotificationUser() {
        return notificationUser;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public Notification() {
    }

    public Notification(User notificationUser, String details) {
        this.notificationUser = notificationUser;
        this.details = details;
        createdTime = LocalDateTime.now();
    }

    public void setSentTime() {
        sentTime = LocalDateTime.now();
    }
}
