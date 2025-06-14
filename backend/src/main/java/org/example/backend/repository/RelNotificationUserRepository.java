package org.example.backend.repository;

import org.example.backend.model.rel_notification_user.RelNotificationUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelNotificationUserRepository extends JpaRepository<RelNotificationUser,Long> {
}
