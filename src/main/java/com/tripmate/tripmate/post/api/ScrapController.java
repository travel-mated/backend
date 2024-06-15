package com.tripmate.tripmate.post.api;

import com.tripmate.tripmate.common.ResponseForm;
import com.tripmate.tripmate.post.service.ScrapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/posts/{post-id}")
@RequiredArgsConstructor
public class ScrapController {

    private ScrapService scrapService;

    @PostMapping
    public ResponseForm<Void> create(@PathVariable("post-id") Long postId, Long userId) {
        scrapService.create(postId, userId);
        return new ResponseForm<>();
    }

    @DeleteMapping
    public ResponseForm<Void> delete(@PathVariable("post-id") Long postId, Long userId) {
        scrapService.delete(postId, userId);
        return new ResponseForm<>();
    }
}
