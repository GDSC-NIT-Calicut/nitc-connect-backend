package com.gdsc.nitcconnect.service;

import com.gdsc.nitcconnect.model.Notification;
import com.gdsc.nitcconnect.repository.NotificationRepository;
//import com.gdsc.nitcconnect.exception.NotificationNotFoundException;
import com.gdsc.nitcconnect.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private PostRepository postService;

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAllByOrderByCreatedAtDesc();
    }

    public Notification getNotificationById(Integer notificationId) {
        return notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Notification not found with id: " + notificationId
                ));
    }

    public Notification createNotification(Notification notification) {
        notification.setCreatedAt(LocalDateTime.now());
        return notificationRepository.save(notification);
    }

    public Notification updateNotification(Integer notificationId, Notification updatedNotification) {
        Notification existingNotification = getNotificationById(notificationId);
        existingNotification.setMessage(updatedNotification.getMessage());
        existingNotification.setPostId(updatedNotification.getPostId());
        return notificationRepository.save(existingNotification);
    }

    public void deleteNotification(Integer notificationId) {
        if (!notificationRepository.existsById(notificationId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Notification not found with id: " + notificationId
            );
        }
        notificationRepository.deleteById(notificationId);
    }

    public List<Notification> getNotificationsByPost(Integer postId) {
        return notificationRepository.findByPostId(postId);
    }

    public List<Notification> searchNotificationsByMessage(String message) {
        return notificationRepository.findByMessageContainingIgnoreCase(message);
    }

    // Business method - create notification when post is created
    public Notification createPostNotification(Integer postId, String postTitle) {
        Notification notification = new Notification();
        notification.setPostId(postId);
        notification.setMessage("New post created: " + postTitle);
        notification.setCreatedAt(LocalDateTime.now());
        return notificationRepository.save(notification);
    }
}