package com.tripmate.tripmate.post.service;

import com.tripmate.tripmate.common.CustomException;
import com.tripmate.tripmate.common.ResultCode;
import com.tripmate.tripmate.post.domain.PostComment;
import com.tripmate.tripmate.post.dto.request.PostCommentRequest;
import com.tripmate.tripmate.post.dto.response.PostCommentResponse;
import com.tripmate.tripmate.post.repository.PostCommentRepository;
import com.tripmate.tripmate.post.repository.PostRepository;
import com.tripmate.tripmate.user.domain.User;
import com.tripmate.tripmate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostCommentService {

    private final PostCommentRepository postCommentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public PostCommentResponse create(final Long userId,
                                      final Long postId,
                                      final PostCommentRequest request) {
        validateExistPost(postId);
        User user = userRepository.getById(userId);
        PostComment postComment = new PostComment(postId, user, request.contents());

        if (hasParentComment(request.parentCommentId())) {
            PostComment parentComment = postCommentRepository.getById(request.parentCommentId());
            parentComment.addCommentReply(postComment);
        }

        return PostCommentResponse.from(postCommentRepository.save(postComment));
    }

    public void validateExistPost(final Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new CustomException(HttpStatus.NOT_FOUND, ResultCode.POST_NOT_FOUND);
        }
    }

    public boolean hasParentComment(Long parentId) {
        return parentId != null;
    }

    @Transactional
    public void delete(final Long userId, final Long postCommentId) {
        User user = userRepository.getById(userId);
        PostComment comment = postCommentRepository.getById(postCommentId);

        validateSameOwner(user, comment);
        postCommentRepository.delete(comment);
    }

    private void validateSameOwner(final User user, final PostComment comment) {
        if (!comment.isOwner(user)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, ResultCode.POST_COMMENT_NOT_WRITER);
        }
    }

    @Transactional
    public PostCommentResponse update(final Long userId,
                                      final Long postCommentId,
                                      final PostCommentRequest request) {
        User user = userRepository.getById(userId);
        PostComment comment = postCommentRepository.getById(postCommentId);
        comment.updateContent(request.contents(), user);
        return PostCommentResponse.from(comment);
    }
}