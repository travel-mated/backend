package com.tripmate.tripmate.post.repository;

import com.tripmate.tripmate.post.domain.Post;
import com.tripmate.tripmate.post.domain.Scrap;
import com.tripmate.tripmate.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    void deletByPostAndUser(Post post, User user);
}
