package com.tripmate.tripmate.chat.domain;

import com.tripmate.tripmate.common.BaseTimeEntity;
import com.tripmate.tripmate.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@AllArgsConstructor
public class Chat extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    private ChatRoom room;

    @OneToOne(fetch = FetchType.EAGER)
    private User user;

    @Column(columnDefinition = "TEXT")
    private String message;

}