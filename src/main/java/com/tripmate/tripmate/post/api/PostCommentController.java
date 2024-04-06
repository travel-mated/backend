package com.tripmate.tripmate.post.api;

import com.tripmate.tripmate.common.ResponseForm;
import com.tripmate.tripmate.post.dto.request.PostCommentRequest;
import com.tripmate.tripmate.post.dto.response.PostCommentResponse;
import com.tripmate.tripmate.post.service.PostCommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/posts/{post-id}/coments")
public class PostCommentController {

    private final PostCommentService postCommentService;

    @PostMapping
    public ResponseForm<PostCommentResponse> create(@PathVariable("post-id") Long postId,
                                                    Long userId,
                                                    @Valid @RequestBody PostCommentRequest request) {
        return new ResponseForm<>(postCommentService.create(postId, userId, request));
    }


    @PatchMapping("/{comment-id}")
    public ResponseForm<PostCommentResponse> update(
            Long userId,
            @PathVariable("comment-id") Long commentId,
            @Valid @RequestBody PostCommentRequest request
    ) {
        return new ResponseForm<>(postCommentService.update(userId, commentId, request));
    }


    @DeleteMapping("/{comment-id}")
    public ResponseForm<PostCommentResponse> delete(
            Long userId,
            @PathVariable("comment-id") Long commentId
    ) {
        postCommentService.delete(userId, commentId);
        return new ResponseForm<>();
    }
}

