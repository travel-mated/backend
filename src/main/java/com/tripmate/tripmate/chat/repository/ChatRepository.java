package com.tripmate.tripmate.chat.repository;

import com.tripmate.tripmate.chat.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}
