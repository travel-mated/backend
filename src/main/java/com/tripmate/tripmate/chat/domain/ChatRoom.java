package com.tripmate.tripmate.chat.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@AllArgsConstructor
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatRoom_id")
    private Long id;

    private Long userId;
    private Long user2Id;

    public ChatRoom(Long userId, Long user2Id) {
        this.userId = userId;
        this.user2Id = user2Id;
    }
}
