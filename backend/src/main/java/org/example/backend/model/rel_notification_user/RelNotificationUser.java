package org.example.backend.model.rel_notification_user;


import jakarta.persistence.*;
import lombok.Data;
import org.example.backend.model.notification.Notification;
import org.example.backend.model.user.User;

@Data
@Entity
@Table(name = "rel_notification_user",uniqueConstraints = @UniqueConstraint(columnNames = {"notification_id","user_id"}))
public class RelNotificationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "notification_id",nullable = false)
    private Notification notification;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
}
