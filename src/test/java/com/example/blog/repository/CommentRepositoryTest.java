package com.example.blog.repository;

import com.example.blog.entity.Comment;
import com.example.blog.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    public void setUp() {
        // Створюємо користувача для тестів
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setEmail("testuser@example.com");
        testUser.setPassword("password");
        userRepository.save(testUser);
    }

    @Test
    public void testSaveComment() {
        // Створюємо новий коментар
        Comment comment = new Comment();
        comment.setContent("This is a test comment");
        comment.setAuthor(testUser);

        // Зберігаємо коментар в базу
        commentRepository.save(comment);

        // Перевіряємо, чи коментар збережений
        assertNotNull(comment.getId(), "Comment ID should not be null after saving");
    }

    @Test
    public void testFindById() {
        // Створюємо новий коментар
        Comment comment = new Comment();
        comment.setContent("This is a test comment");
        comment.setAuthor(testUser);

        // Зберігаємо коментар в базу
        commentRepository.save(comment);

        // Знаходимо коментар за ID
        Comment foundComment = commentRepository.findById(comment.getId()).orElseThrow();

        // Перевіряємо правильність знаходження
        assertEquals(comment.getContent(), foundComment.getContent(), "Comment content should match");
    }

    @Test
    public void testDeleteComment() {
        // Створюємо новий коментар
        Comment comment = new Comment();
        comment.setContent("This is a test comment");
        comment.setAuthor(testUser);

        // Зберігаємо коментар
        commentRepository.save(comment);
        Long commentId = comment.getId();

        // Видаляємо коментар
        commentRepository.delete(comment);

        // Перевіряємо, що коментар видалений
        assertFalse(commentRepository.findById(commentId).isPresent(), "Comment should be deleted");
    }
}
