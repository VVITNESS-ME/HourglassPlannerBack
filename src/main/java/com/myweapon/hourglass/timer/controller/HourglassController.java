package com.myweapon.hourglass.timer.controller;

import com.myweapon.hourglass.common.ApiResponse;
import com.myweapon.hourglass.security.UserDetailsImpl;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.timer.dto.*;
import com.myweapon.hourglass.timer.service.HourglassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/timer")
public class HourglassController {
    private final HourglassService hourglassService;

    @PostMapping("/start")
    public ResponseEntity<ApiResponse<HourglassResponse>> startHourglass(@RequestBody HourglassStartRequest hourglassStartRequest
            , @AuthenticationPrincipal UserDetailsImpl userDetails){
        return hourglassService.startHourglass(hourglassStartRequest,userDetails.getUser());
    }
    @PostMapping("/end")
    public ResponseEntity<ApiResponse<HourglassSummaryResponse>> endHourglass(@RequestBody HourglassEndRequest hourglassEndRequest
            , @AuthenticationPrincipal UserDetailsImpl userDetails){
        return hourglassService.endHourglass(hourglassEndRequest,userDetails.getUser());
    }
    @PostMapping("/pause")
    public ResponseEntity<ApiResponse<HourglassResponse>> pauseHourglass(@RequestBody HourglassPauseRequest hourglassPauseRequest){
        return hourglassService.pauseHourglass(hourglassPauseRequest);
    }

    @PostMapping("/restart")
    public ResponseEntity<ApiResponse<HourglassResponse>> resumeHourglass(@RequestBody HourglassResumeRequest hourglassPauseRequest){
        return hourglassService.resumeHourglass(hourglassPauseRequest);
    }
}
