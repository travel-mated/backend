package com.tripmate.tripmate.post.api;

import com.tripmate.tripmate.common.ResponseForm;
import com.tripmate.tripmate.post.dto.response.CommentLikeResponse;
import com.tripmate.tripmate.post.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/posts/{post-id}/comments/{comment-id}/like")
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping
    public ResponseForm<Void> create(@PathVariable("comment-id") Long comentId, Long userId) {
        commentLikeService.create(comentId, userId);
        return new ResponseForm<>();
    }

    @DeleteMapping
    public ResponseForm<Void> delete(@PathVariable("comment-id") Long comentId, Long userId) {
        commentLikeService.delete(comentId, userId);
        return new ResponseForm<>();
    }

    @GetMapping
    public ResponseForm<CommentLikeResponse> get(@PathVariable("comment-id") Long comentId) {
        return new ResponseForm<>(commentLikeService.get(comentId));
    }
}
