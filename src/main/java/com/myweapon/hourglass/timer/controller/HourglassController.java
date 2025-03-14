package com.myweapon.hourglass.timer.controller;


import com.myweapon.hourglass.common.dto.ApiResponse;
import com.myweapon.hourglass.common.dto.ApiSuccess;
import com.myweapon.hourglass.security.UserDetailsImpl;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.timer.dto.HourglassInfo;
import com.myweapon.hourglass.timer.dto.request.HourglassEndRequest;
import com.myweapon.hourglass.timer.dto.request.HourglassRunRequest;
import com.myweapon.hourglass.timer.dto.request.HourglassStartRequest;
import com.myweapon.hourglass.timer.dto.request.HourglassStopRequest;
import com.myweapon.hourglass.timer.service.HourglassService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hourglass")
@Slf4j
public class HourglassController {
    private final HourglassService hourglassService;

    //태스크 없이 타이머를 돌릴 때
    @PostMapping(path = "")
    public ApiResponse<Long> startHourglass(@RequestBody HourglassStartRequest request
            , @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        Long hourglassId = hourglassService.startHourglass(request,user);
        return ApiResponse.success(hourglassId);
    }


    @PostMapping(path="",params = "task")
    public ApiResponse<Long> startHourglassWithTask
            (@RequestParam(name="task") Long taskId, @RequestBody HourglassStartRequest request
                    ,@AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        Long hourglassId = hourglassService.startHourglassWithTask(user,taskId,request);
        return ApiResponse.success(hourglassId);
    }

    @GetMapping(path="",params="state")
    public ApiResponse<HourglassInfo> getHourglassInfoInProgress(
            @RequestParam(name="state") String state,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        return ApiResponse.success(hourglassService.findHourglassInfoInProgress(user,state));
    }


    //태스크 없이 실행된 타이머를 종료할 때 호출하는 API, default task에서 선택해서 종료한다.
    @PostMapping(path="/{hourglassId}/history/end")
    @ResponseBody
    public ApiSuccess endHourglass(
            @PathVariable(name="hourglassId") Long hourglassId
            , @RequestBody HourglassEndRequest request
            , @AuthenticationPrincipal UserDetailsImpl userDetails){
        hourglassService.endHourglass(userDetails.getUser(),hourglassId,request);
        return ApiSuccess.body();
    }

    @PostMapping(value = "/{hourglassId}/history/end",params="user-category")
    @ResponseBody
    public ApiSuccess endHourglassWithUserCategory(
            @PathVariable(name="hourglassId") Long hourglassId
            ,@RequestParam(name="user-category") Long userCategoryId
            ,@RequestBody HourglassEndRequest request
            ,@AuthenticationPrincipal UserDetailsImpl userDetails){
        hourglassService.endHourglassWithUserCategory(userDetails.getUser(),userCategoryId,hourglassId,request);
        return ApiSuccess.body();
    }

    @PostMapping("/{hourglassId}/history/pause")
    public ApiSuccess pauseHourglass(
            @PathVariable(name="hourglassId") Long hourglassId
            ,@RequestBody HourglassStopRequest request
            ,@AuthenticationPrincipal UserDetailsImpl userDetails){
        hourglassService.pauseHourglass(userDetails.getUser(),hourglassId,request);
        return ApiSuccess.body();
    }

    @PostMapping("/{hourglassId}/history/restart")
    public ApiSuccess restartHourglass(
            @PathVariable(name="hourglassId") Long hourglassId
            ,@RequestBody HourglassRunRequest request
            ,@AuthenticationPrincipal UserDetailsImpl userDetails){
        hourglassService.restartHourglass(userDetails.getUser(),hourglassId,request);
        return ApiSuccess.body();
    }
}
