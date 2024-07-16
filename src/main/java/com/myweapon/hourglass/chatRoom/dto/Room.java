package com.myweapon.hourglass.chatRoom.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class Room {
    private long roomId;
    private String title;
    private boolean isSecretRoom;
    private int limit;
    private int participants;
}