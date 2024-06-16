package com.tripmate.tripmate.chat.repository;

import com.tripmate.tripmate.chat.domain.ChatRoom;
import com.tripmate.tripmate.common.CustomException;
import com.tripmate.tripmate.common.ResultCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    default ChatRoom getChatRoomById(Long chatRoomId) {
        return findById(chatRoomId).orElseThrow(() ->
                new CustomException(HttpStatus.NOT_FOUND, ResultCode.CHATROOM_NOT_FOUND));
    }
}
