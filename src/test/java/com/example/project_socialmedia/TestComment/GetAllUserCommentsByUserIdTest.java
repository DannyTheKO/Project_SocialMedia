package com.example.project_socialmedia.TestComment;

import com.example.project_socialmedia.application.Exception.ResourceNotFound;
import com.example.project_socialmedia.application.Service.CommentService;
import com.example.project_socialmedia.domain.Model.Comment;
import com.example.project_socialmedia.domain.Model.User;
import com.example.project_socialmedia.domain.Repository.CommentRepository;
import com.example.project_socialmedia.domain.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class GetAllUserCommentsByUserIdTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CommentRepository commentRepository;


    @Autowired
    private CommentService commentService;

    @Test
    void getAllUserCommentsByUserId_UserFound() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setUserId(userId);

        Comment comment1 = new Comment();
        comment1.setContent("Test Comment 1");
        comment1.setCreatedAt(LocalDateTime.now());

        Comment comment2 = new Comment();
        comment2.setContent("Test Comment 2");
        comment2.setCreatedAt(LocalDateTime.now());

        user.setComments(Arrays.asList(comment1, comment2));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        List<Comment> comments = commentService.getAllCommentsByUserId(userId);

        // Assert
        assertNotNull(comments);
        assertEquals(2, comments.size());
        assertEquals("Test Comment 1", comments.get(0).getContent());
        assertEquals("Test Comment 2", comments.get(1).getContent());
    }

    @Test
    void getAllUserCommentsByUserId_UserNotFound() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFound.class, () -> commentService.getAllCommentsByUserId(userId));
    }
}
