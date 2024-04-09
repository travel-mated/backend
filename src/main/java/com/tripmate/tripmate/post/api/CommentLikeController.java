package com.tripmate.tripmate.post.api;

import com.tripmate.tripmate.common.ResponseForm;
import com.tripmate.tripmate.post.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
