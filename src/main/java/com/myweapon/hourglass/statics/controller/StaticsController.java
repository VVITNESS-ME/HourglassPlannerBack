package com.myweapon.hourglass.statics.controller;

import com.myweapon.hourglass.common.ApiResponse;
import com.myweapon.hourglass.common.time.DateStartEnd;
import com.myweapon.hourglass.common.time.DateTimeFrame;
import com.myweapon.hourglass.common.time.Week;
import com.myweapon.hourglass.security.UserDetailsImpl;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.statics.dto.*;
import com.myweapon.hourglass.statics.service.StaticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/statics")
public class StaticsController {

    private final StaticsService staticsService;

    @GetMapping("/garden")
    public ResponseEntity<ApiResponse<GardenGetResponse>> getGardenOfInterval
            (@RequestParam("start") String start
                    , @RequestParam("end") String end
                    , @AuthenticationPrincipal UserDetailsImpl userDetails){
        List<TotalBurstByDay> totalBurstByDays = staticsService.calculateTotalBurstByDaysOf
                (DateTimeFrame.FROM_DAY.toLocalDateTime(start)
                        ,DateTimeFrame.FROM_DAY.toLocalDateTime(end)
                        ,userDetails.getUser());

        GardenGetResponse response = GardenGetResponse.of(totalBurstByDays);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/daily-statistics")
    public ResponseEntity<ApiResponse<DailyStatisticsResponse>> weekStatistics
            (@RequestParam("date") String dateString
                    ,@RequestParam("day") Week week
                    ,@AuthenticationPrincipal UserDetailsImpl userDetails){
        LocalDate date = DateTimeFrame.FROM_DAY.toLocalDate(dateString);

        DateStartEnd dateStartEnd = week.getWeekInterval(date);
        LocalDateTime monday = dateStartEnd.getStartWithTime();
        LocalDateTime sunday = dateStartEnd.getEndWithTime();

        User user = userDetails.getUser();

        List<TotalBurstByWeekDay> byDays = staticsService.calculateTotalBurstByWeekDays
                (dateStartEnd.getStartWithTime(),user);

        List<BurstRatioByCategories> byCategories = staticsService.calculateBurstRatioByCategoryOf(monday,sunday,user);

        return ResponseEntity.ok(ApiResponse.success(DailyStatisticsResponse.of(byDays,byCategories)));
    }
}
