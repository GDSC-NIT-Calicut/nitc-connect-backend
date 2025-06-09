package com.gdsc.nitcconnect.repository;
import com.gdsc.nitcconnect.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
