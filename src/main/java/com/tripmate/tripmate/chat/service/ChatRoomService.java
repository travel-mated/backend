package com.tripmate.tripmate.chat.service;

import com.tripmate.tripmate.chat.domain.ChatRoom;
import com.tripmate.tripmate.chat.dto.ChatRoomExistsResponse;
import com.tripmate.tripmate.chat.dto.ChatRoomGetResponse;
import com.tripmate.tripmate.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public ChatRoomGetResponse createChatRoom(Long userId, Long user2Id) {
        ChatRoom chatRoom = chatRoomRepository.save(new ChatRoom(userId, user2Id));
        return new ChatRoomGetResponse(chatRoom.getId());
    }

    public ChatRoomExistsResponse existsChatRoom(Long chatRoomId) {
        return new ChatRoomExistsResponse(chatRoomRepository.existsById(chatRoomId));
    }
    public ChatRoom getChatRoom(Long chatRoomId) {
        return chatRoomRepository.getChatRoomById(chatRoomId);
    }
}
