package com.tripmate.tripmate.post.api;

import com.tripmate.tripmate.common.ResponseForm;
import com.tripmate.tripmate.post.dto.request.PostCommentRequest;
import com.tripmate.tripmate.post.dto.response.PostCommentResponse;
import com.tripmate.tripmate.post.service.PostCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/posts/{post-id}/coments")
public class PostCommentController {

    private final PostCommentService postCommentService;

    @PostMapping
    public ResponseForm<PostCommentResponse> createPostComment(@PathVariable("post-id") Long postId,
                                                               Long userId,
                                                               @Validated @RequestBody PostCommentRequest request) {
        return new ResponseForm<>(postCommentService.createPostComment(postId, userId, request));
    }
}

