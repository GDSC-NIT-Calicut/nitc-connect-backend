package com.gdsc.nitcconnect.repository;
import com.gdsc.nitcconnect.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByPostId(Integer postId);
    List<Notification> findByMessageContainingIgnoreCase(String message);
    List<Notification> findAllByOrderByCreatedAtDesc();
}
