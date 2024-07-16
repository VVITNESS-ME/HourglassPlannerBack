package com.myweapon.hourglass.gpt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myweapon.hourglass.common.exception.RestApiException;
import com.myweapon.hourglass.gpt.dto.ChatCompletionDto;
import com.myweapon.hourglass.gpt.dto.response.ChatResponseDto;
import com.myweapon.hourglass.security.enumset.ErrorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class SyncChatGPTService implements ChatGPTService
{
    private final String promptEntryPoint = "/v1/chat/completions";
    private final String modelAvailableEntryPoint = "v1/models";

    private final RestTemplate restTemplate;
    private final HttpHeaders headers;

    @Value("${gpt.baseURL}")
    private String baseUrl;


    @Override
    public ChatResponseDto prompt(ChatCompletionDto chatCompletionDto) {
        System.out.println(baseUrl);

        ChatResponseDto chatResponseDto = null;

        URI gptUrl = GPTURIFrom(promptEntryPoint);
        HttpEntity<ChatCompletionDto> requestEntity = new HttpEntity<>(chatCompletionDto, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(gptUrl,requestEntity,String.class);

        try {
            ObjectMapper om = new ObjectMapper();
            chatResponseDto = om.readValue(response.getBody(), ChatResponseDto.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RestApiException(ErrorType.GPT_ERROR);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RestApiException(ErrorType.GPT_ERROR);
        }
        return chatResponseDto;
    }

    @Override
    public List<Map<String, Object>> modelList() {
        List<Map<String, Object>> resultList = null;

        ResponseEntity<String> response = restTemplate.exchange(
                GPTURIFrom(modelAvailableEntryPoint)
                , HttpMethod.GET
                , new HttpEntity<>(headers)
                , String.class);

        try {
            ObjectMapper om = new ObjectMapper();
            Map<String, Object> data = om.readValue(response.getBody(), new TypeReference<>() {
            });

            resultList = (List<Map<String, Object>>) data.get("data");
            for (Map<String, Object> object : resultList) {
                log.debug("ID: " + object.get("id"));
                log.debug("Object: " + object.get("object"));
                log.debug("Created: " + object.get("created"));
                log.debug("Owned By: " + object.get("owned_by"));
            }
        } catch (JsonMappingException e) {
            log.debug("JsonMappingException :: " + e.getMessage());
        } catch (JsonProcessingException e) {
            log.debug("JsonProcessingException :: " + e.getMessage());
        } catch (RuntimeException e) {
            log.debug("RuntimeException :: " + e.getMessage());
        }
        return resultList;
    }

    private URI GPTURIFrom(String endPoint){
        return UriComponentsBuilder.fromUriString(baseUrl)
                .path(endPoint)
                .encode()
                .build()
                .toUri();
    }

}
