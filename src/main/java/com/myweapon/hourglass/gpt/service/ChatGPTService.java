package com.myweapon.hourglass.gpt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myweapon.hourglass.gpt.dto.ChatCompletionDto;
import com.myweapon.hourglass.gpt.dto.ChatRequestMsgDto;
import com.myweapon.hourglass.gpt.enumset.GPTRole;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ChatGPTService {
    public Map<String, Object> prompt(ChatCompletionDto chatCompletionDto) ;
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

    public static ChatCompletionDto createChatCompletionDtoFrom(List<ChatRequestMsgDto> content){
        return ChatCompletionDto.builder()
                .model("gpt-3.5-turbo")
                .messages(content)
                .build();
    }

    public static List<ChatRequestMsgDto> createChatRequestMsgDtoListFrom(String content){
        List<ChatRequestMsgDto> chatRequestMsgDtoList = new ArrayList<>();
        chatRequestMsgDtoList.add(createChatRequestMsgDtoFrom(content));
        return chatRequestMsgDtoList;
    }

    public static List<ChatRequestMsgDto> createChatRequestMsgDtoListFrom(List<String> content){
        List<ChatRequestMsgDto> chatRequestMsgDtoList = new ArrayList<>();
        for(String c:content){
            chatRequestMsgDtoList.add(createChatRequestMsgDtoFrom(c));
        }
        return chatRequestMsgDtoList;
    }

    public static ChatRequestMsgDto createChatRequestMsgDtoFrom(String content){
        return ChatRequestMsgDto.builder()
                .role(GPTRole.USER.getRoleName())
                .content(content)
                .build();
    }

    public static ChatRequestMsgDto createSystemChatRequestMsgDtoFrom(String content){
        return ChatRequestMsgDto.builder()
                .role(GPTRole.SYSTEM.getRoleName())
                .content(content)
                .build();
    }
}
