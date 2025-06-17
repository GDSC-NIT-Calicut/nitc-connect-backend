package com.gdsc.nitcconnect.repository;
import com.gdsc.nitcconnect.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByIgId(Integer igId);
    List<Post> findByAuthorId(Integer authorId);
    List<Post> findByTitleContainingIgnoreCase(String title);
}
