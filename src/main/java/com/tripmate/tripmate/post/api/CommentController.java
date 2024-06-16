package com.tripmate.tripmate.post.api;

import com.tripmate.tripmate.common.ResponseForm;
import com.tripmate.tripmate.post.dto.request.CommentRequest;
import com.tripmate.tripmate.post.dto.response.CommentResponse;
import com.tripmate.tripmate.post.dto.response.CommentPageResponse;
import com.tripmate.tripmate.post.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/posts/{post-id}/coments")
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseForm<CommentPageResponse> get(@PathVariable("post-id") Long postId) {
        return new ResponseForm<>(commentService.get(postId));
    }

    @PostMapping
    public ResponseForm<CommentResponse> create(@PathVariable("post-id") Long postId,
                                                Long userId,
                                                @Valid @RequestBody CommentRequest request) {
        return new ResponseForm<>(commentService.create(postId, userId, request));
    }


    @PatchMapping("/{comment-id}")
    public ResponseForm<CommentResponse> update(
            Long userId,
            @PathVariable("comment-id") Long commentId,
            @Valid @RequestBody CommentRequest request
    ) {
        return new ResponseForm<>(commentService.update(userId, commentId, request));
    }


    @DeleteMapping("/{comment-id}")
    public ResponseForm<CommentResponse> delete(
            Long userId,
            @PathVariable("comment-id") Long commentId
    ) {
        commentService.delete(userId, commentId);
        return new ResponseForm<>();
    }
}

