package com.tripmate.tripmate.chat.controller;


import com.tripmate.tripmate.chat.dto.ChatRoomExistsResponse;
import com.tripmate.tripmate.chat.dto.ChatRoomGetResponse;
import com.tripmate.tripmate.chat.service.ChatRoomService;
import com.tripmate.tripmate.common.ResponseForm;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/chat/room")
public class ChatRoomController {

    private ChatRoomService chatRoomService;

    @PostMapping
    public ResponseForm<ChatRoomGetResponse> createChatRoom(Long userId, Long user2Id) {
        return new ResponseForm<>(chatRoomService.createChatRoom(userId, user2Id));
    }
    @GetMapping
    public ResponseForm<ChatRoomExistsResponse> createChatRoom(Long chatRoomId) {
        return new ResponseForm<>(chatRoomService.existsChatRoom(chatRoomId));
    }
}
