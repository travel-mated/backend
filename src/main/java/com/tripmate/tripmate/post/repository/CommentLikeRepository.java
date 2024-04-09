package com.tripmate.tripmate.post.repository;

import com.tripmate.tripmate.post.domain.Comment;
import com.tripmate.tripmate.post.domain.CommentLike;
import com.tripmate.tripmate.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    void deletByCommentAndUser(Comment comment, User user);
}
