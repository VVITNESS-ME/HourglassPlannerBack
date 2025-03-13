package com.myweapon.hourglass.timer.controller;

import com.myweapon.hourglass.common.dto.ApiResponse;
import com.myweapon.hourglass.common.dto.ApiSuccess;
import com.myweapon.hourglass.security.UserDetailsImpl;
import com.myweapon.hourglass.timer.dto.DateTimeRange;
import com.myweapon.hourglass.timer.dto.HourglassHistoryInfoWithCategory;
import com.myweapon.hourglass.timer.dto.response.BurstTimesByDate;
import com.myweapon.hourglass.timer.enumset.DateRangeType;
import com.myweapon.hourglass.timer.service.HourglassHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/statistics")
public class StatisticsController {
    private final HourglassHistoryService hourglassHistoryService;
    @GetMapping("/burst_time/sum")
    public ApiResponse<BurstTimesByDate> getBurstTimesDuring(@RequestParam("standard_date") LocalDate standardDate
            ,@RequestParam("interval")DateRangeType intervalType
            ,@RequestParam("range")DateRangeType rangeType
            ,@AuthenticationPrincipal UserDetailsImpl userDetails){
        DateTimeRange range = DateTimeRange.range(rangeType,standardDate);
        BurstTimesByDate burstTimesByDate = hourglassHistoryService.sumBurstTimeByDate(userDetails.getUser(),intervalType,range);
        return ApiResponse.success(burstTimesByDate);
    }

    @GetMapping("/burst_time/point")
    public ApiResponse<List<HourglassHistoryInfoWithCategory>> getHourglassRunningPoint(@RequestParam("start")LocalDateTime start
            ,@RequestParam("end")LocalDateTime end
            ,@AuthenticationPrincipal UserDetailsImpl userDetails){
        List<HourglassHistoryInfoWithCategory> hourglassHistoryInfoWithCategories = hourglassHistoryService
                .findHourglassHistoriesByUserAndDateTimeRange(userDetails.getUser(),DateTimeRange.of(start,end));
        return ApiResponse.success(hourglassHistoryInfoWithCategories);
    }


}
