package com.gdsc.nitcconnect.routes;

import com.gdsc.nitcconnect.model.Notification;
import com.gdsc.nitcconnect.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/test/notifications")
public class NotificationTest {

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Integer id) {
        return ResponseEntity.ok(notificationService.getNotificationById(id));
    }

    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(notificationService.createNotification(notification));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notification> updateNotification(@PathVariable Integer id, @RequestBody Notification notification) {
        return ResponseEntity.ok(notificationService.updateNotification(id, notification));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Integer id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Notification>> getNotificationsByPost(@PathVariable Integer postId) {
        return ResponseEntity.ok(notificationService.getNotificationsByPost(postId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Notification>> searchNotifications(@RequestParam String message) {
        return ResponseEntity.ok(notificationService.searchNotificationsByMessage(message));
    }
}