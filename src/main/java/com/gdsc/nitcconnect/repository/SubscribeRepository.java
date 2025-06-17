package com.gdsc.nitcconnect.repository;

import com.gdsc.nitcconnect.model.Subscribe;
import com.gdsc.nitcconnect.model.SubscribeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, SubscribeId> {

    // Find all subscriptions for a specific user
    List<Subscribe> findByUserId(Integer userId);

    // Find all subscribers for a specific interest group
    List<Subscribe> findByIgId(Integer igId);

    // Find all non-muted subscriptions for a user
    List<Subscribe> findByUserIdAndIsMutedFalse(Integer userId);

    // Find all non-muted subscribers for an interest group
    List<Subscribe> findByIgIdAndIsMutedFalse(Integer igId);

    // Find all muted subscriptions for a user
    List<Subscribe> findByUserIdAndIsMutedTrue(Integer userId);

    // Check if a user is subscribed to an interest group
    boolean existsByUserIdAndIgId(Integer userId, Integer igId);

    // Find subscription by user and interest group
    Optional<Subscribe> findByUserIdAndIgId(Integer userId, Integer igId);

    // Count total subscribers for an interest group
    @Query("SELECT COUNT(s) FROM Subscribe s WHERE s.igId = :igId")
    Long countSubscribersByIgId(@Param("igId") Integer igId);

    // Count active (non-muted) subscribers for an interest group
    @Query("SELECT COUNT(s) FROM Subscribe s WHERE s.igId = :igId AND s.isMuted = false")
    Long countActiveSubscribersByIgId(@Param("igId") Integer igId);

    // Find subscriptions created after a specific date
    List<Subscribe> findBySubscribedAtAfter(LocalDateTime date);

    // Find recent subscriptions for a user
    @Query("SELECT s FROM Subscribe s WHERE s.userId = :userId ORDER BY s.subscribedAt DESC")
    List<Subscribe> findRecentSubscriptionsByUserId(@Param("userId") Integer userId);

}