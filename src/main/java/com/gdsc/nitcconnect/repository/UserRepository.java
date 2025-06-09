package com.gdsc.nitcconnect.repository;
import com.gdsc.nitcconnect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
