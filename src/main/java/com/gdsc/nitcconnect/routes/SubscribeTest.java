package com.gdsc.nitcconnect.routes;

import com.gdsc.nitcconnect.model.Subscribe;
import com.gdsc.nitcconnect.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/test/subscriptions")
@CrossOrigin(origins = "*")
public class SubscribeTest {

    @Autowired
    private SubscribeService subscribeService;

    // Subscribe to an interest group
    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@RequestParam Integer userId,
                                       @RequestParam Integer igId,
                                       @RequestParam(required = false, defaultValue = "false") Boolean isMuted) {
        try {
            Subscribe subscription = subscribeService.subscribe(userId, igId, isMuted);
            return ResponseEntity.ok(subscription);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Unsubscribe from an interest group
    @DeleteMapping("/unsubscribe")
    public ResponseEntity<?> unsubscribe(@RequestParam Integer userId,
                                         @RequestParam Integer igId) {
        boolean success = subscribeService.unsubscribe(userId, igId);
        if (success) {
            return ResponseEntity.ok("Successfully unsubscribed");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Check subscription status
    @GetMapping("/check")
    public ResponseEntity<Boolean> isSubscribed(@RequestParam Integer userId,
                                                @RequestParam Integer igId) {
        boolean isSubscribed = subscribeService.isSubscribed(userId, igId);
        return ResponseEntity.ok(isSubscribed);
    }

    // Get subscription details
    @GetMapping("/details")
    public ResponseEntity<Subscribe> getSubscription(@RequestParam Integer userId,
                                                     @RequestParam Integer igId) {
        Optional<Subscribe> subscription = subscribeService.getSubscription(userId, igId);
        return subscription.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get all subscriptions for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Subscribe>> getUserSubscriptions(@PathVariable Integer userId) {
        List<Subscribe> subscriptions = subscribeService.getUserSubscriptions(userId);
        return ResponseEntity.ok(subscriptions);
    }

    // Get active subscriptions for a user
    @GetMapping("/user/{userId}/active")
    public ResponseEntity<List<Subscribe>> getActiveUserSubscriptions(@PathVariable Integer userId) {
        List<Subscribe> subscriptions = subscribeService.getActiveUserSubscriptions(userId);
        return ResponseEntity.ok(subscriptions);
    }

    // Get muted subscriptions for a user
    @GetMapping("/user/{userId}/muted")
    public ResponseEntity<List<Subscribe>> getMutedUserSubscriptions(@PathVariable Integer userId) {
        List<Subscribe> subscriptions = subscribeService.getMutedUserSubscriptions(userId);
        return ResponseEntity.ok(subscriptions);
    }

    // Get subscribers for an interest group
    @GetMapping("/interest-group/{igId}/subscribers")
    public ResponseEntity<List<Subscribe>> getInterestGroupSubscribers(@PathVariable Integer igId) {
        List<Subscribe> subscribers = subscribeService.getInterestGroupSubscribers(igId);
        return ResponseEntity.ok(subscribers);
    }

    // Get active subscribers for an interest group
    @GetMapping("/interest-group/{igId}/active-subscribers")
    public ResponseEntity<List<Subscribe>> getActiveInterestGroupSubscribers(@PathVariable Integer igId) {
        List<Subscribe> subscribers = subscribeService.getActiveInterestGroupSubscribers(igId);
        return ResponseEntity.ok(subscribers);
    }

    // Mute subscription
    @PutMapping("/mute")
    public ResponseEntity<?> muteSubscription(@RequestParam Integer userId,
                                              @RequestParam Integer igId) {
        boolean success = subscribeService.muteSubscription(userId, igId);
        if (success) {
            return ResponseEntity.ok("Subscription muted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Unmute subscription
    @PutMapping("/unmute")
    public ResponseEntity<?> unmuteSubscription(@RequestParam Integer userId,
                                                @RequestParam Integer igId) {
        boolean success = subscribeService.unmuteSubscription(userId, igId);
        if (success) {
            return ResponseEntity.ok("Subscription unmuted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Toggle mute status
    @PutMapping("/toggle-mute")
    public ResponseEntity<?> toggleMuteSubscription(@RequestParam Integer userId,
                                                    @RequestParam Integer igId) {
        boolean success = subscribeService.toggleMuteSubscription(userId, igId);
        if (success) {
            return ResponseEntity.ok("Subscription mute status toggled successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get subscription statistics
    @GetMapping("/stats/interest-group/{igId}/total-count")
    public ResponseEntity<Long> getTotalSubscriberCount(@PathVariable Integer igId) {
        Long count = subscribeService.getTotalSubscriberCount(igId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/stats/interest-group/{igId}/active-count")
    public ResponseEntity<Long> getActiveSubscriberCount(@PathVariable Integer igId) {
        Long count = subscribeService.getActiveSubscriberCount(igId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/stats/user/{userId}/subscription-count")
    public ResponseEntity<Integer> getUserSubscriptionCount(@PathVariable Integer userId) {
        int count = subscribeService.getUserSubscriptionCount(userId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/stats/user/{userId}/active-subscription-count")
    public ResponseEntity<Integer> getUserActiveSubscriptionCount(@PathVariable Integer userId) {
        int count = subscribeService.getUserActiveSubscriptionCount(userId);
        return ResponseEntity.ok(count);
    }

    // Get recent subscriptions
    @GetMapping("/user/{userId}/recent")
    public ResponseEntity<List<Subscribe>> getRecentSubscriptions(@PathVariable Integer userId) {
        List<Subscribe> subscriptions = subscribeService.getRecentSubscriptions(userId);
        return ResponseEntity.ok(subscriptions);
    }

    // Batch operations
    @PostMapping("/batch-subscribe")
    public ResponseEntity<List<Subscribe>> batchSubscribe(@RequestParam Integer userId,
                                                          @RequestBody List<Integer> igIds) {
        List<Subscribe> subscriptions = subscribeService.batchSubscribe(userId, igIds);
        return ResponseEntity.ok(subscriptions);
    }

    @DeleteMapping("/batch-unsubscribe")
    public ResponseEntity<Long> batchUnsubscribe(@RequestParam Integer userId,
                                                 @RequestBody List<Integer> igIds) {
        long unsubscribedCount = subscribeService.batchUnsubscribe(userId, igIds);
        return ResponseEntity.ok(unsubscribedCount);
    }

    // Update subscription
    @PutMapping("/update")
    public ResponseEntity<Subscribe> updateSubscription(@RequestParam Integer userId,
                                                        @RequestParam Integer igId,
                                                        @RequestParam Boolean isMuted) {
        Optional<Subscribe> subscription = subscribeService.updateSubscription(userId, igId, isMuted);
        return subscription.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}