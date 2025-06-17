package com.gdsc.nitcconnect.repository;

import com.gdsc.nitcconnect.model.AccessCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccessCodeRepository extends JpaRepository<AccessCode, String> {

    // Get igId if code is valid
    @Query("SELECT ac.igId FROM AccessCode ac WHERE ac.code = :code AND (ac.expiryDate IS NULL OR ac.expiryDate > :currentTime)")
    Optional<Integer> findIgIdByValidCode(@Param("code") String code, @Param("currentTime") LocalDateTime currentTime);
}