package com.myweapon.hourglass.chatRoom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class ChatRoomRequest {
    private String title;
    private int limit;
    private Boolean isSecretRoom;
    private String password;
}