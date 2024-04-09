package com.tripmate.tripmate.post.api;

import com.tripmate.tripmate.common.ResponseForm;
import com.tripmate.tripmate.post.dto.request.CommentRequest;
import com.tripmate.tripmate.post.dto.response.PostCommentResponse;
import com.tripmate.tripmate.post.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/posts/{post-id}/coments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseForm<PostCommentResponse> create(@PathVariable("post-id") Long postId,
                                                    Long userId,
                                                    @Valid @RequestBody CommentRequest request) {
        return new ResponseForm<>(commentService.create(postId, userId, request));
    }


    @PatchMapping("/{comment-id}")
    public ResponseForm<PostCommentResponse> update(
            Long userId,
            @PathVariable("comment-id") Long commentId,
            @Valid @RequestBody CommentRequest request
    ) {
        return new ResponseForm<>(commentService.update(userId, commentId, request));
    }


    @DeleteMapping("/{comment-id}")
    public ResponseForm<PostCommentResponse> delete(
            Long userId,
            @PathVariable("comment-id") Long commentId
    ) {
        commentService.delete(userId, commentId);
        return new ResponseForm<>();
    }
}

