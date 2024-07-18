package com.myweapon.hourglass.title.controller;

import com.myweapon.hourglass.chatRoom.dto.ParticipantsRequest;
import com.myweapon.hourglass.common.dto.ApiResponse;
import com.myweapon.hourglass.security.UserDetailsImpl;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.title.dto.TitleDto;
import com.myweapon.hourglass.title.dto.TitleResponse;
import com.myweapon.hourglass.title.service.TitleService;
import com.myweapon.hourglass.title.service.TitleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/title")
public class TitleController {

    private final TitleService titleService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<TitleResponse>> getTitleList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        TitleResponse titleResponse = titleService.getTitleInfoByUserId(user);
        return ResponseEntity.ok(ApiResponse.success(titleResponse));
    }

    @PostMapping("/achieve/{titleId}")
    public ResponseEntity<ApiResponse<TitleDto>> achieveTitle(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                   @PathVariable String titleId) {
        User user = userDetails.getUser();
        TitleDto titleDto = titleService.achieveTitle(user, Integer.parseInt(titleId));
        return ResponseEntity.ok(ApiResponse.success(titleDto));
    }

    @GetMapping("/newTitle")
    public ResponseEntity<ApiResponse<List<TitleDto>>> getNewTitle(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        List<TitleDto> titleResponse = titleService.getNewTitleList(user);
        return ResponseEntity.ok(ApiResponse.success(titleResponse));
    }
}
