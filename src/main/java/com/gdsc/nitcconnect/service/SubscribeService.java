package com.gdsc.nitcconnect.service;

import com.gdsc.nitcconnect.model.Subscribe;
import com.gdsc.nitcconnect.model.SubscribeId;
import com.gdsc.nitcconnect.repository.SubscribeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SubscribeService {

    @Autowired
    private SubscribeRepository subscribeRepository;

    // Create a new subscription
    public Subscribe subscribe(Integer userId, Integer igId) {
        if (isSubscribed(userId, igId)) {
            throw new IllegalStateException("User is already subscribed to this interest group");
        }

        Subscribe subscription = new Subscribe(userId, igId);
        return subscribeRepository.save(subscription);
    }

    // Create a new subscription with mute preference
    public Subscribe subscribe(Integer userId, Integer igId, Boolean isMuted) {
        if (isSubscribed(userId, igId)) {
            throw new IllegalStateException("User is already subscribed to this interest group");
        }

        Subscribe subscription = new Subscribe(userId, igId, isMuted);
        return subscribeRepository.save(subscription);
    }

    // Unsubscribe from an interest group
    public boolean unsubscribe(Integer userId, Integer igId) {
        SubscribeId subscribeId = new SubscribeId(userId, igId);
        if (subscribeRepository.existsById(subscribeId)) {
            subscribeRepository.deleteById(subscribeId);
            return true;
        }
        return false;
    }

    // Check if user is subscribed to an interest group
    public boolean isSubscribed(Integer userId, Integer igId) {
        return subscribeRepository.existsByUserIdAndIgId(userId, igId);
    }

    // Get subscription details
    public Optional<Subscribe> getSubscription(Integer userId, Integer igId) {
        return subscribeRepository.findByUserIdAndIgId(userId, igId);
    }

    // Get all subscriptions for a user
    public List<Subscribe> getUserSubscriptions(Integer userId) {
        return subscribeRepository.findByUserId(userId);
    }

    // Get all active (non-muted) subscriptions for a user
    public List<Subscribe> getActiveUserSubscriptions(Integer userId) {
        return subscribeRepository.findByUserIdAndIsMutedFalse(userId);
    }

    // Get all muted subscriptions for a user
    public List<Subscribe> getMutedUserSubscriptions(Integer userId) {
        return subscribeRepository.findByUserIdAndIsMutedTrue(userId);
    }

    // Get all subscribers for an interest group
    public List<Subscribe> getInterestGroupSubscribers(Integer igId) {
        return subscribeRepository.findByIgId(igId);
    }

    // Get all active subscribers for an interest group
    public List<Subscribe> getActiveInterestGroupSubscribers(Integer igId) {
        return subscribeRepository.findByIgIdAndIsMutedFalse(igId);
    }

    // Mute a subscription
    public boolean muteSubscription(Integer userId, Integer igId) {
        Optional<Subscribe> subscription = subscribeRepository.findByUserIdAndIgId(userId, igId);
        if (subscription.isPresent()) {
            subscription.get().mute();
            subscribeRepository.save(subscription.get());
            return true;
        }
        return false;
    }

    // Unmute a subscription
    public boolean unmuteSubscription(Integer userId, Integer igId) {
        Optional<Subscribe> subscription = subscribeRepository.findByUserIdAndIgId(userId, igId);
        if (subscription.isPresent()) {
            subscription.get().unmute();
            subscribeRepository.save(subscription.get());
            return true;
        }
        return false;
    }

    // Toggle mute status
    public boolean toggleMuteSubscription(Integer userId, Integer igId) {
        Optional<Subscribe> subscription = subscribeRepository.findByUserIdAndIgId(userId, igId);
        if (subscription.isPresent()) {
            Subscribe sub = subscription.get();
            if (sub.isCurrentlyMuted()) {
                sub.unmute();
            } else {
                sub.mute();
            }
            subscribeRepository.save(sub);
            return true;
        }
        return false;
    }

    // Statistics
    public Long getTotalSubscriberCount(Integer igId) {
        return subscribeRepository.countSubscribersByIgId(igId);
    }

    public Long getActiveSubscriberCount(Integer igId) {
        return subscribeRepository.countActiveSubscribersByIgId(igId);
    }

    public int getUserSubscriptionCount(Integer userId) {
        return subscribeRepository.findByUserId(userId).size();
    }

    public int getUserActiveSubscriptionCount(Integer userId) {
        return subscribeRepository.findByUserIdAndIsMutedFalse(userId).size();
    }

    // Get recent subscriptions
    public List<Subscribe> getRecentSubscriptions(Integer userId) {
        return subscribeRepository.findRecentSubscriptionsByUserId(userId);
    }

    public List<Subscribe> getSubscriptionsAfterDate(LocalDateTime date) {
        return subscribeRepository.findBySubscribedAtAfter(date);
    }


    // Batch subscribe
    public List<Subscribe> batchSubscribe(Integer userId, List<Integer> igIds) {
        return igIds.stream()
                .filter(igId -> !isSubscribed(userId, igId))
                .map(igId -> subscribe(userId, igId))
                .toList();
    }

    // Batch unsubscribe
    public long batchUnsubscribe(Integer userId, List<Integer> igIds) {
        return igIds.stream()
                .mapToLong(igId -> unsubscribe(userId, igId) ? 1 : 0)
                .sum();
    }

    // Update subscription
    public Optional<Subscribe> updateSubscription(Integer userId, Integer igId, Boolean isMuted) {
        Optional<Subscribe> subscription = subscribeRepository.findByUserIdAndIgId(userId, igId);
        if (subscription.isPresent()) {
            subscription.get().setIsMuted(isMuted);
            return Optional.of(subscribeRepository.save(subscription.get()));
        }
        return Optional.empty();
    }
}