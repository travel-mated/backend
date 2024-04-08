package com.tripmate.tripmate.post.repository;

import com.tripmate.tripmate.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
