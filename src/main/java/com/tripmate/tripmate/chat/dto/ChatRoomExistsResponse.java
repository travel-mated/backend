package com.tripmate.tripmate.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatRoomExistsResponse {
    private boolean isExist;
}
