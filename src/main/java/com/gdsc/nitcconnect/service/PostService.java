package com.gdsc.nitcconnect.service;

import com.gdsc.nitcconnect.model.Post;
import com.gdsc.nitcconnect.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Integer postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Post not found with id: " + postId
                ));
    }

    public Post createPost(Post post) {
        post.setCreatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    public Post updatePost(Integer postId, Post updatedPost) {
        Post existingPost = getPostById(postId);
        existingPost.setTitle(updatedPost.getTitle());
        existingPost.setContent(updatedPost.getContent());
        return postRepository.save(existingPost);
    }

    public void deletePost(Integer postId) {
        if (!postRepository.existsById(postId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Post not found with id: " + postId
            );
        }
        postRepository.deleteById(postId);
    }

    public List<Post> getPostsByInterestGroup(Integer igId) {
        return postRepository.findByIgId(igId);
    }

    public List<Post> getPostsByAuthor(Integer authorId) {
        return postRepository.findByAuthorId(authorId);
    }

    public List<Post> searchPostsByTitle(String title) {
        return postRepository.findByTitleContainingIgnoreCase(title);
    }
}