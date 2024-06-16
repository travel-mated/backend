package com.tripmate.tripmate.post.repository;

import com.tripmate.tripmate.post.domain.Comment;
import com.tripmate.tripmate.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    void testFindAllByPostIdAndParentCommentIdIsNull() {
        // Setup data
        Long postId = 1L;
        User user = User.builder().name("test name").build(); // Assume User class is set up appropriately
        entityManager.persist(user);

        // Create a parent comment
        Comment parentComment = new Comment(postId, user, "Parent comment");
        entityManager.persist(parentComment);

        // Create child comments
        Comment childComment1 = new Comment(postId, user, "Child comment 1");
        childComment1.setParentComment(parentComment);
        entityManager.persist(childComment1);

        // Create a top-level comment
        Comment topLevelComment = new Comment(postId, user, "Top-level comment");
        entityManager.persist(topLevelComment);

        entityManager.flush();

        // Test the method
        List<Comment> results = commentRepository.findAllByPostIdAndParentCommentIdIsNull(postId);

        // Verification
        assertEquals(2, results.size()); // Should only find the top-level comment
        assertEquals("Top-level comment", results.get(0).getContents());
    }
}
