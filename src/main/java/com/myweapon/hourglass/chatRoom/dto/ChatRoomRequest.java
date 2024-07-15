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
    private String name;
    private int limitPeople;
    private Boolean isSecretRoom;
    private String passWord;
}