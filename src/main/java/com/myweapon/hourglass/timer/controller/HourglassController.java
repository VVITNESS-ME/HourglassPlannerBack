package com.myweapon.hourglass.timer.controller;

import com.myweapon.hourglass.common.ApiResponse;
import com.myweapon.hourglass.security.UserDetailsImpl;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.timer.dto.HourglassEndRequest;
import com.myweapon.hourglass.timer.dto.HourglassResponse;
import com.myweapon.hourglass.timer.dto.HourglassStartRequest;
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
        return hourglassService.startHourglass(hourglassStartRequest,userDetails);
    }
    @PostMapping("/end")
    public ResponseEntity<ApiResponse<HourglassResponse>> endHourglass(@RequestBody HourglassEndRequest hourglassEndRequest
            , @AuthenticationPrincipal UserDetailsImpl userDetails){
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!"+hourglassEndRequest);
        return hourglassService.endHourglass(hourglassEndRequest,userDetails);
    }
}
