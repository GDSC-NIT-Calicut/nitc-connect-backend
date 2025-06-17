package com.gdsc.nitcconnect.repository;

import com.gdsc.nitcconnect.model.UserNotification;
import com.gdsc.nitcconnect.model.UserNotificationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification, UserNotificationId> {

    // Find all notifications for a specific user
    List<UserNotification> findByUserId(Integer userId);

    // Find all notifications for a user with specific status
    List<UserNotification> findByUserIdAndStatus(Integer userId, UserNotification.Status status);

    // Find all unread notifications for a user
    List<UserNotification> findByUserIdAndStatusOrderByReceivedAtDesc(Integer userId, UserNotification.Status status);

    // Count unread notifications for a user
    Long countByUserIdAndStatus(Integer userId, UserNotification.Status status);

    // Find notifications received after a specific time
    List<UserNotification> findByUserIdAndReceivedAtAfter(Integer userId, LocalDateTime dateTime);

    // Find all users who received a specific notification
    List<UserNotification> findByNotificationId(Integer notificationId);

    // Check if a specific user-notification combination exists
    boolean existsByUserIdAndNotificationId(Integer userId, Integer notificationId);

    // Custom query to mark notification as read
    @Modifying
    @Transactional
    @Query("UPDATE UserNotification un SET un.status = :status WHERE un.userId = :userId AND un.notificationId = :notificationId")
    int updateNotificationStatus(@Param("userId") Integer userId,
                                 @Param("notificationId") Integer notificationId,
                                 @Param("status") UserNotification.Status status);

    // Custom query to mark all notifications as read for a user
    @Modifying
    @Query("UPDATE UserNotification un SET un.status = :newStatus WHERE un.userId = :userId AND un.status = :currentStatus")
    int markAllAsReadForUser(@Param("userId") Integer userId,
                             @Param("currentStatus") UserNotification.Status currentStatus,
                             @Param("newStatus") UserNotification.Status newStatus);

    // Delete notifications older than specified date
    @Modifying
    @Query("DELETE FROM UserNotification un WHERE un.receivedAt < :cutoffDate")
    int deleteNotificationsOlderThan(@Param("cutoffDate") LocalDateTime cutoffDate);
}