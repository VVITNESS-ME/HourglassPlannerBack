package com.myweapon.hourglass.statics.controller;

import com.myweapon.hourglass.common.ApiResponse;
import com.myweapon.hourglass.common.time.DateTimeFrame;
import com.myweapon.hourglass.common.time.DateTimeFrameConstants;
import com.myweapon.hourglass.security.UserDetailsImpl;
import com.myweapon.hourglass.statics.dto.DayOfGarden;
import com.myweapon.hourglass.statics.dto.GardenGetResponse;
import com.myweapon.hourglass.statics.service.StaticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/statics")
public class StaticsController {
    private final StaticsService staticsService;
    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    @GetMapping("/garden")
    public ResponseEntity<ApiResponse<GardenGetResponse>> getGardenOfInterval
            (@RequestParam("start") String start
                    , @RequestParam("end") String end
                    , @AuthenticationPrincipal UserDetailsImpl userDetails){
        return staticsService.getGardenOfInterval(DateTimeFrame.FROM_DAY.toLocalDateTime(start)
                ,DateTimeFrame.FROM_DAY.toLocalDateTime(end),
                userDetails.getUser());
    }
}
