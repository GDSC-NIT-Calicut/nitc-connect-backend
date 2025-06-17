package com.gdsc.nitcconnect.repository;

import com.gdsc.nitcconnect.model.Administrate;
import com.gdsc.nitcconnect.model.AdministrateId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdministrateRepository extends JpaRepository<Administrate, AdministrateId> {

    // Find all administrations by user ID
    List<Administrate> findByUserId(Integer userId);

    // Find all administrations by interest group ID
    List<Administrate> findByIgId(Integer igId);

    // Check if a user administrates a specific interest group
    boolean existsByUserIdAndIgId(Integer userId, Integer igId);

    // Custom query to find all users who administrate a specific interest group
    @Query("SELECT a FROM Administrate a WHERE a.igId = :igId")
    List<Administrate> findAdministratorsByIgId(@Param("igId") Integer igId);

    // Custom query to find all interest groups administered by a user
    @Query("SELECT a FROM Administrate a WHERE a.userId = :userId")
    List<Administrate> findInterestGroupsByUserId(@Param("userId") Integer userId);

    // Delete by user ID and interest group ID
    void deleteByUserIdAndIgId(Integer userId, Integer igId);

    // Count administrations by user
    long countByUserId(Integer userId);

    // Count administrators by interest group
    long countByIgId(Integer igId);
}
