package com.tripmate.tripmate.post.repository;

import com.tripmate.tripmate.common.CustomException;
import com.tripmate.tripmate.common.ResultCode;
import com.tripmate.tripmate.post.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    default Comment getById(Long postCommentId) {
        return findById(postCommentId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ResultCode.POST_COMMENT_NOT_FOUND));
    }

    List<Comment> findAllByPostId(Long postId);

    List<Comment> findAllByPostIdAndParentCommentIdIsNull(Long postId);
}
