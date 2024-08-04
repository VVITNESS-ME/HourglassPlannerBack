package com.myweapon.hourglass.timer.controller;

import com.myweapon.hourglass.common.dto.ApiResponse;
import com.myweapon.hourglass.security.UserDetailsImpl;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.timer.dto.*;
import com.myweapon.hourglass.timer.service.HourglassService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @GetMapping("/progress")
    public ResponseEntity<ApiResponse<HourglassInfoInProgress>> getHourglassInProgress(@AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        Optional<HourglassInfoInProgress> hourglassInfoInProgress = hourglassService.getHourglassInProgressInfo(userDetails.getUser());
        return hourglassInfoInProgress.map(infoInProgress -> ResponseEntity.ok(ApiResponse.success(infoInProgress)))
                .orElseGet(() -> ResponseEntity.ok(ApiResponse.successWithMessage(null, "hourglass is not in progress")));
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
    @ResponseBody
    public ApiResponse<HourglassSummaryResponse> endHourglass(@RequestBody HourglassEndRequest hourglassEndRequest
            , @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ApiResponse.success(hourglassService.endHourglass(hourglassEndRequest,userDetails.getUser()));
    }

    @PostMapping(value = "/end/{taskId}")
    @ResponseBody
    public ApiResponse<HourglassSummaryResponse>endHourglassWithTask(@PathVariable Long taskId,
                                                                                      @RequestBody HourglassEndRequest request,
                                                                                      @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ApiResponse.success(hourglassService.endHourglassWithTask(taskId,request,userDetails.getUser()));
    }

    @PostMapping("/pause")
    public ResponseEntity<ApiResponse<HourglassResponse>> pauseHourglass(@RequestBody HourglassPauseRequest hourglassPauseRequest
            ,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return hourglassService.pauseHourglass(hourglassPauseRequest,userDetails.getUser());
    }

    @PostMapping("/restart")
    public ResponseEntity<ApiResponse<HourglassResponse>> resumeHourglass(@RequestBody HourglassResumeRequest hourglassPauseRequest
            ,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return hourglassService.resumeHourglass(hourglassPauseRequest,userDetails.getUser());
    }
}
