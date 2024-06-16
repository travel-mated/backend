package com.tripmate.tripmate.chat.service;

import com.tripmate.tripmate.chat.domain.Chat;
import com.tripmate.tripmate.chat.dto.ChatMessage;
import com.tripmate.tripmate.chat.repository.ChatRepository;
import com.tripmate.tripmate.chat.repository.ChatRoomRepository;
import com.tripmate.tripmate.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    public void createChat(Long roomId, ChatMessage message) {
        chatRepository.save(Chat.builder()
                .room(chatRoomRepository.getChatRoomById(message.getRoomId()))
                .user(userRepository.getUserById(message.getSenderId()))
                .message(message.getMessage())
                .build());
    }

}
