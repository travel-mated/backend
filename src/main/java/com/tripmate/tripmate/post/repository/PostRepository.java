package com.tripmate.tripmate.post.repository;

import com.tripmate.tripmate.common.CustomException;
import com.tripmate.tripmate.common.ResultCode;
import com.tripmate.tripmate.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;

public interface PostRepository extends JpaRepository<Post, Long> {
    default Post getById(Long postId) {
        return findById(postId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, ResultCode.POST_NOT_FOUND));
    }
}
