package com.example.blog.repository;

import com.example.blog.entity.Post;
import com.example.blog.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test") // <-- Додає тестовий профіль
@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    public void setUp() {
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setEmail("testuser@example.com");
        testUser.setPassword("password");
        userRepository.save(testUser);
    }

    @Test
    public void testSavePost() {
        Post post = new Post();
        post.setTitle("Test Post");
        post.setContent("Test content");
        post.setAuthor(testUser);
        post.setCreatedAt(LocalDateTime.now());

        postRepository.save(post);

        assertNotNull(post.getId(), "Post ID should not be null after saving");
    }

    @Test
    public void testFindById() {
        Post post = new Post();
        post.setTitle("Test Post");
        post.setContent("Test content");
        post.setAuthor(testUser);
        post.setCreatedAt(LocalDateTime.now());

        postRepository.save(post);

        Post foundPost = postRepository.findById(post.getId()).orElseThrow();

        assertEquals(post.getTitle(), foundPost.getTitle(), "Post title should match");
        assertEquals(post.getContent(), foundPost.getContent(), "Post content should match");
    }

    @Test
    public void testDeletePost() {
        Post post = new Post();
        post.setTitle("Test Post");
        post.setContent("Test content");
        post.setAuthor(testUser);
        post.setCreatedAt(LocalDateTime.now());

        postRepository.save(post);
        Long postId = post.getId();

        postRepository.delete(post);

        assertFalse(postRepository.findById(postId).isPresent(), "Post should be deleted");
    }
}
