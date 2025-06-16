package com.gdsc.nitcconnect.repository;
import com.gdsc.nitcconnect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // Custom query methods
    Optional<User> findByEmail(String email);
    List<User> findByName(String name);
    boolean existsByEmail(String email);
}
