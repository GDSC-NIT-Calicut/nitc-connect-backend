package com.gdsc.nitcconnect.repository;
import com.gdsc.nitcconnect.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}
