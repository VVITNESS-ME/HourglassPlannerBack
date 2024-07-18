package com.myweapon.hourglass.statistics.controller;

import com.influxdb.query.FluxRecord;
import com.myweapon.hourglass.security.UserDetailsImpl;
import com.myweapon.hourglass.statistics.dto.response.GardenResponse;
import com.myweapon.hourglass.statistics.service.InfluxStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class StatisticsController {
    private final InfluxStatisticsService influxStatisticsService;
    @GetMapping("/first")
    @ResponseBody
    public GardenResponse getTest(@RequestParam("start") LocalDate start, @RequestParam("end") LocalDate end, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return influxStatisticsService.getGardens(start,end,userDetails.getUser());
    }
}
