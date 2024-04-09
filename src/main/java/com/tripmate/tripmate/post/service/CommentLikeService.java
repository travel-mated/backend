package com.tripmate.tripmate.post.service;

import com.tripmate.tripmate.post.domain.Comment;
import com.tripmate.tripmate.post.domain.CommentLike;
import com.tripmate.tripmate.post.repository.CommentLikeRepository;
import com.tripmate.tripmate.post.repository.CommentRepository;
import com.tripmate.tripmate.user.domain.User;
import com.tripmate.tripmate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public void create(Long comentId, Long userId) {
        Comment comment = commentRepository.getById(comentId);
        User user = userRepository.getById(userId);

        commentLikeRepository.save(CommentLike.builder()
                .comment(comment)
                .user(user)
                .build());
    }

    @Transactional
    public void delete(Long commentId, Long userId) {
        commentLikeRepository.deleteByCommentIdAndUserId(commentId, userId);
    }
}
