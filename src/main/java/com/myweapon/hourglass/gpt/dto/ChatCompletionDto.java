package com.myweapon.hourglass.gpt.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
@NoArgsConstructor
@Getter
@ToString
public class ChatCompletionDto {
    private String model;
    private List<ChatMsg> messages;

    @Builder
    public ChatCompletionDto(String model,List<ChatMsg> messages){
        this.model = model;
        this.messages = messages;
    }

}
