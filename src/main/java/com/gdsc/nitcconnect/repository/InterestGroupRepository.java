package com.gdsc.nitcconnect.repository;

import com.gdsc.nitcconnect.model.InterestGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface InterestGroupRepository extends JpaRepository<InterestGroup, Integer> {
    List<InterestGroup> findByNameContainingIgnoreCase(String name);
    List<InterestGroup> findByCreatedBy(Integer createdBy);
    Optional<InterestGroup> findByNameIgnoreCase(String name);
    List<InterestGroup> findAllByOrderByCreatedAtDesc();
    boolean existsByNameIgnoreCase(String name);
}