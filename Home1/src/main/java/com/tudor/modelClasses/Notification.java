package com.tudor.modelClasses;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notification")

public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
                        CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User notificationUser;

    @Column(name = "details")
    private String details;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "sent_time")
    private LocalDateTime sentTime;

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
