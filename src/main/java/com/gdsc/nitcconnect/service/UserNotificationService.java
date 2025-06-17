package com.gdsc.nitcconnect.service;

import com.gdsc.nitcconnect.model.UserNotification;
import com.gdsc.nitcconnect.model.UserNotificationId;
import com.gdsc.nitcconnect.repository.UserNotificationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserNotificationService {

    @Autowired
    private UserNotificationRepository userNotificationRepository;


    public UserNotification createUserNotification(Integer userId, Integer notificationId, UserNotification.Status status) {
        UserNotification userNotification = new UserNotification();
        userNotification.setUserId(userId);
        userNotification.setNotificationId(notificationId);
        userNotification.setStatus(status != null ? status : UserNotification.Status.Unread);
        userNotification.setReceivedAt(LocalDateTime.now());

        return userNotificationRepository.save(userNotification);
    }


    public List<UserNotification> getUserNotifications(Integer userId) {
        return userNotificationRepository.findByUserId(userId);
    }

    public List<UserNotification> getUserNotificationsByStatus(Integer userId, UserNotification.Status status) {
        return userNotificationRepository.findByUserIdAndStatus(userId, status);
    }

    public List<UserNotification> getUnreadNotifications(Integer userId) {
        return userNotificationRepository.findByUserIdAndStatusOrderByReceivedAtDesc(userId, UserNotification.Status.Unread);
    }

    public Long getUnreadNotificationCount(Integer userId) {
        return userNotificationRepository.countByUserIdAndStatus(userId, UserNotification.Status.Unread);
    }

    public boolean markAsRead(Integer userId, Integer notificationId) {
        int updatedRows = userNotificationRepository.updateNotificationStatus(userId, notificationId, UserNotification.Status.Read);
        return updatedRows > 0;
    }

    public boolean markAsPinned(Integer userId, Integer notificationId) {
        int updatedRows = userNotificationRepository.updateNotificationStatus(userId, notificationId, UserNotification.Status.Pinned);
        return updatedRows > 0;
    }

    public boolean markAsDeleted(Integer userId, Integer notificationId) {
        int updatedRows = userNotificationRepository.updateNotificationStatus(userId, notificationId, UserNotification.Status.Deleted);
        return updatedRows > 0;
    }

    public int markAllAsRead(Integer userId) {
        return userNotificationRepository.markAllAsReadForUser(userId, UserNotification.Status.Unread, UserNotification.Status.Read);
    }

    public Optional<UserNotification> getUserNotification(Integer userId, Integer notificationId) {
        UserNotificationId id = new UserNotificationId(userId, notificationId);
        return userNotificationRepository.findById(id);
    }

    public int deleteOldNotifications(LocalDateTime cutoffDate) {
        return userNotificationRepository.deleteNotificationsOlderThan(cutoffDate);
    }

    public boolean hasUserReceivedNotification(Integer userId, Integer notificationId) {
        return userNotificationRepository.existsByUserIdAndNotificationId(userId, notificationId);
    }

    public List<UserNotification> getRecentNotifications(Integer userId, LocalDateTime since) {
        return userNotificationRepository.findByUserIdAndReceivedAtAfter(userId, since);
    }
}