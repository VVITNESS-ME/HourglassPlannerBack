package com.myweapon.hourglass.timer.controller;

import com.myweapon.hourglass.common.ApiResponse;
import com.myweapon.hourglass.security.UserDetailsImpl;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.timer.dto.*;
import com.myweapon.hourglass.timer.service.HourglassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/timer")
public class HourglassController {
    private final HourglassService hourglassService;

    @PostMapping("/start")
    public ResponseEntity<ApiResponse<HourglassResponse>> startHourglass(@RequestBody HourglassStartRequest request
            , @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        Long hId = hourglassService.startHourglass(request,user);
        return ResponseEntity.ok(ApiResponse.success(HourglassResponse.fromHId(hId)));
    }

    @PostMapping("/start/{tId}")
    public ResponseEntity<ApiResponse<HourglassResponse>> startHourglassWithTask
            (@PathVariable Long tId, @RequestBody HourglassStartRequest request
                    ,@AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        Long hId = hourglassService.startHourglassWithTask(tId,request,user);
        return ResponseEntity.ok(ApiResponse.success(HourglassResponse.fromHId(hId)));
    }

    @PostMapping("/end")
    public ResponseEntity<ApiResponse<HourglassSummaryResponse>> endHourglass(@RequestBody HourglassEndRequest hourglassEndRequest
            , @AuthenticationPrincipal UserDetailsImpl userDetails){
        return hourglassService.endHourglass(hourglassEndRequest,userDetails.getUser());
    }
    @PostMapping(value = "/end/{taskId}")
    public ResponseEntity<ApiResponse<HourglassSummaryResponse>> endHourglassWithTask(@PathVariable Long taskId,
                                                                                      @RequestBody HourglassEndWithTaskRequest request,
                                                                                      @AuthenticationPrincipal UserDetailsImpl userDetails){
        return hourglassService.endHourglassWithTask(taskId,request,userDetails.getUser());
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
