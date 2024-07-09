package com.myweapon.hourglass.diary.controller;

import com.myweapon.hourglass.common.ApiResponse;
import com.myweapon.hourglass.common.ApiSuccess;
import com.myweapon.hourglass.diary.dto.DayStudyRecord;
import com.myweapon.hourglass.diary.dto.HourglassPutRequest;
import com.myweapon.hourglass.diary.service.DiaryService;
import com.myweapon.hourglass.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/diary")
public class DiaryController {
    private final DiaryService diaryService;
    @GetMapping("/calendar")
    public ResponseEntity<ApiResponse<DayStudyRecord>> getDayStudyRecord
            (@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate date, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return diaryService.getDayStudyRecord(date,userDetails.getUser());
    }

    @PutMapping("/hourglass/{hId}")
    public ResponseEntity<ApiSuccess> updateHourglass(@PathVariable Long hId, @RequestBody HourglassPutRequest request){
        return diaryService.updateContentOfHourglass(hId,request);
    }
}
