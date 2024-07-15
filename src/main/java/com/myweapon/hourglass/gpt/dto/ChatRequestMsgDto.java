package com.myweapon.hourglass.gpt.dto;

import lombok.*;


@NoArgsConstructor
@Getter
@ToString
public class ChatRequestMsgDto {
    private String role;
    private String content;

    @Builder
    public ChatRequestMsgDto(String role,String content){
        this.role = role;
        this.content = content;
    }
}
