package com.tripmate.tripmate.post.service;

import com.tripmate.tripmate.post.domain.Post;
import com.tripmate.tripmate.post.domain.Scrap;
import com.tripmate.tripmate.post.repository.PostRepository;
import com.tripmate.tripmate.post.repository.ScrapRepository;
import com.tripmate.tripmate.user.domain.User;
import com.tripmate.tripmate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScrapService {

    private final ScrapRepository scrapRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void create(Long postId, Long userId) {
        Post post = postRepository.getPostById(postId);
        User user = userRepository.getUserById(userId);

        scrapRepository.save(Scrap.builder()
                .post(post)
                .user(user)
                .build());
    }

    @Transactional
    public void delete(Long postId, Long userId) {
        Post post = postRepository.getPostById(postId);
        User user = userRepository.getUserById(userId);

        scrapRepository.deletByPostAndUser(post, user);
    }
}

