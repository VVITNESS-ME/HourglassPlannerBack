package com.myweapon.hourglass.today.i.learned.controller;

import com.myweapon.hourglass.common.dto.ApiResponse;
import com.myweapon.hourglass.common.dto.ApiSuccess;
import com.myweapon.hourglass.gpt.dto.response.ChatResponseDto;
import com.myweapon.hourglass.security.UserDetailsImpl;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.today.i.learned.dto.DocumentContentDto;
import com.myweapon.hourglass.today.i.learned.service.ChatGPTEnhancedTodayILearnedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/today-i-learned")
public class TodayILearnedController {
    private final ChatGPTEnhancedTodayILearnedService chatGPTEnhancedTodayILearnedService;
    @GetMapping("/{dateTodo}/original")
    public ResponseEntity<ApiResponse<DocumentContentDto>> getTodayILearnedAt
            (@PathVariable("dateTodo")LocalDate dateTodo, @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        DocumentContentDto documentContentDto = chatGPTEnhancedTodayILearnedService.getDocumentContent(dateTodo,user);
        return ResponseEntity.ok(ApiResponse.success(documentContentDto));
    }

    @GetMapping("/{dateTodo}/modified")
    @ResponseBody
    public ApiResponse<DocumentContentDto> getTodayILearnedFromGpt(@PathVariable("dateTodo")LocalDate dateTodo, @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        DocumentContentDto documentContentDto = chatGPTEnhancedTodayILearnedService.getDocumentContentByChatGPT(dateTodo,user);
        return ApiResponse.success(documentContentDto);
    }
    @PostMapping("/modify")
    @ResponseBody
    public ApiResponse<DocumentContentDto> convertTil(@RequestBody DocumentContentDto requestContentDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        DocumentContentDto documentContentDto = chatGPTEnhancedTodayILearnedService.convertTilWithChatGPT(requestContentDto, user);
        return ApiResponse.success(documentContentDto);
    }

    @PostMapping("/{dateTodo}/modified")
    public ResponseEntity<ApiSuccess> postTodayILearned
            (@PathVariable("dateTodo")LocalDate dateTodo
                    , @RequestBody DocumentContentDto documentContentDto
                    , @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        chatGPTEnhancedTodayILearnedService.updateDocumentContent(documentContentDto,dateTodo,user);
        return ResponseEntity.ok(ApiSuccess.body());
    }


}
