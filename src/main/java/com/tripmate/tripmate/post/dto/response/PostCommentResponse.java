package com.tripmate.tripmate.post.dto.response;

import com.tripmate.tripmate.post.domain.Comment;

import java.time.LocalDateTime;

public record PostCommentResponse(
        Long id,
        String writer,
        String contents,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PostCommentResponse from(final Comment comment) {
        return new PostCommentResponse(
                comment.getId(),
                comment.getUser().getName(),
                comment.getContents(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}

