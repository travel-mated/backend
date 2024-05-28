package com.tripmate.tripmate.post.dto.response;

import com.tripmate.tripmate.post.domain.Comment;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        String writer,
        String contents,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static CommentResponse from(final Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getUser().getUsername(),
                comment.getContents(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }
}

