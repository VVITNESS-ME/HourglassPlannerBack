package com.myweapon.hourglass.statistics.controller;

import com.myweapon.hourglass.common.dto.ApiResponse;
import com.myweapon.hourglass.common.dto.ApiSuccess;
import com.myweapon.hourglass.common.time.Week;
import com.myweapon.hourglass.security.UserDetailsImpl;
import com.myweapon.hourglass.statistics.dto.response.*;
import com.myweapon.hourglass.statistics.service.ComprehensiveStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/v2/statistics")
@RequiredArgsConstructor
public class StatisticsController {
    private final ComprehensiveStatisticsService comprehensiveStatisticsService;
    @GetMapping("/garden")
    @ResponseBody
    public ApiResponse<GardenResponse> getGardens(@RequestParam("start") LocalDate start, @RequestParam("end") LocalDate end, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ApiResponse.success(comprehensiveStatisticsService.getGardensDuring(start,end,userDetails.getUser()));
    }

    @GetMapping("/day")
    @ResponseBody
    public ApiResponse<DayStatisticsResponse> getDayStatisticsResponse(@RequestParam("date") LocalDate date, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ApiResponse.success(comprehensiveStatisticsService.getDayStatisticsResponse(date,userDetails.getUser()));
    }

    @GetMapping("/week")
    @ResponseBody
    public ApiResponse<WeekStatisticsResponse> getWeekStatisticsResponse(@RequestParam("date") LocalDate date, @RequestParam("day") Week week, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return ApiResponse.success(comprehensiveStatisticsService.getWeekStatisticsResponse(week.getWeekDuration(date),userDetails.getUser()));
    }

    @GetMapping("/month")
    @ResponseBody
    public ApiResponse<MonthStatisticsResponse> getMonthStatisticsResponse(@RequestParam("date") LocalDate date,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return ApiResponse.success(comprehensiveStatisticsService.getMonthStatisticsResponse(date,userDetails.getUser()));
    }

    @GetMapping("/year")
    @ResponseBody
    public ApiResponse<YearStatisticsResponse> getYearStatisticsResponse(@RequestParam("date") LocalDate date,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return ApiResponse.success(comprehensiveStatisticsService.getYearStatisticsResponse(date,userDetails.getUser()));
    }
}
