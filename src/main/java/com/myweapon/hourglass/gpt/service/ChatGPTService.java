package com.myweapon.hourglass.gpt.service;

import com.myweapon.hourglass.gpt.dto.ChatCompletionDto;
import com.myweapon.hourglass.gpt.dto.ChatMsg;
import com.myweapon.hourglass.gpt.dto.response.ChatResponseDto;
import com.myweapon.hourglass.gpt.enumset.GPTRole;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ChatGPTService {
    public ChatResponseDto prompt(ChatCompletionDto chatCompletionDto) ;
    public List<Map<String, Object>> modelList();

    public static ChatCompletionDto createChatCompletionDtoFrom(String content){
        return ChatCompletionDto.builder()
                .model("gpt-3.5-turbo")
                .messages(createChatRequestMsgDtoListFrom(content))
                .build();
    }

//    public static ChatCompletionDto createChatCompletionDtoFrom(List<String> content){
//        return ChatCompletionDto.builder()
//                .model("gpt-3.5-turbo")
//                .messages(createChatRequestMsgDtoListFrom(content))
//                .build();
//    }

    public static ChatCompletionDto createChatCompletionDtoFrom(List<ChatMsg> content){
        return ChatCompletionDto.builder()
                .model("gpt-3.5-turbo")
                .messages(content)
                .build();
    }

    public static List<ChatMsg> createChatRequestMsgDtoListFrom(String content){
        List<ChatMsg> chatMsgList = new ArrayList<>();
        chatMsgList.add(createChatRequestMsgDtoFrom(content));
        return chatMsgList;
    }

    public static List<ChatMsg> createChatRequestMsgDtoListFrom(List<String> content){
        List<ChatMsg> chatMsgList = new ArrayList<>();
        for(String c:content){
            chatMsgList.add(createChatRequestMsgDtoFrom(c));
        }
        return chatMsgList;
    }

    public static ChatMsg createChatRequestMsgDtoFrom(String content){
        return ChatMsg.builder()
                .role(GPTRole.USER.getRoleName())
                .content(content)
                .build();
    }

    public static ChatMsg createSystemChatRequestMsgDtoFrom(String content){
        return ChatMsg.builder()
                .role(GPTRole.SYSTEM.getRoleName())
                .content(content)
                .build();
    }
}
