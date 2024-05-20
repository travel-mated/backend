package com.tripmate.tripmate.post.service;

import com.tripmate.tripmate.common.CustomException;
import com.tripmate.tripmate.common.ResultCode;
import com.tripmate.tripmate.post.domain.Comment;
import com.tripmate.tripmate.post.dto.request.CommentRequest;
import com.tripmate.tripmate.post.dto.response.CommentResponse;
import com.tripmate.tripmate.post.repository.CommentRepository;
import com.tripmate.tripmate.post.repository.PostRepository;
import com.tripmate.tripmate.user.domain.User;
import com.tripmate.tripmate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentResponse create(final Long userId,
                                  final Long postId,
                                  final CommentRequest request) {
        validateExistPost(postId);
        User user = userRepository.getUserById(userId);
        Comment comment = new Comment(postId, user, request.contents());

        if (hasParentComment(request.parentCommentId())) {
            Comment parentComment = commentRepository.getCommentById(request.parentCommentId());
            parentComment.addCommentReply(comment);
        }

        return CommentResponse.from(commentRepository.save(comment));
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
        User user = userRepository.getUserById(userId);
        Comment comment = commentRepository.getCommentById(postCommentId);

        validateSameOwner(user, comment);
        commentRepository.delete(comment);
    }

    private void validateSameOwner(final User user, final Comment comment) {
        if (!comment.isOwner(user)) {
            throw new CustomException(HttpStatus.BAD_REQUEST, ResultCode.POST_COMMENT_NOT_WRITER);
        }
    }

    @Transactional
    public CommentResponse update(final Long userId,
                                  final Long postCommentId,
                                  final CommentRequest request) {
        User user = userRepository.getUserById(userId);
        Comment comment = commentRepository.getCommentById(postCommentId);
        comment.updateContents(request.contents(), user);
        return CommentResponse.from(comment);
    }
}