package com.tripmate.tripmate.post.dto.response;

import com.tripmate.tripmate.post.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@Data
@AllArgsConstructor
public class CommentResponse {
    private Long commentId;
    private Long userId;
    private Long parentCommentId;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<CommentResponse> childComments;

    public static CommentResponse from(Comment comment) {
        Comment parentComment = comment.getParentComment();

        Set<CommentResponse> childComments = new TreeSet<>(Comparator
                .comparing(CommentResponse::getCreatedAt)
                .thenComparing(CommentResponse::getCommentId));

        return new CommentResponse(
                comment.getId(),
                comment.getUser().getId(),
                parentComment != null ? parentComment.getId() : null,
                comment.getContents(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                childComments
        );
    }

    public boolean hasParentComment() {
        return getParentCommentId() != null;
    }
}


