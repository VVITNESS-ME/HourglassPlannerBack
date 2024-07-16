package com.myweapon.hourglass.gpt.dto;

import lombok.*;


@NoArgsConstructor
@Getter
@ToString
public class ChatMsg {
    private String role;
    private String content;

    @Builder
    public ChatMsg(String role, String content){
        this.role = role;
        this.content = content;
    }
}
