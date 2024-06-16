package com.tripmate.tripmate.chat.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatMessage {
    private Long roomId;
    private Long senderId;
    private String message;
}
