package com.myweapon.hourglass.schedule.controller;


import com.myweapon.hourglass.common.ApiResponse;
import com.myweapon.hourglass.common.binder.LocalDateTimeEditor;
import com.myweapon.hourglass.schedule.dto.*;
import com.myweapon.hourglass.schedule.service.CalendarService;
import com.myweapon.hourglass.schedule.service.TaskService;
import com.myweapon.hourglass.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
@Slf4j
public class ScheduleController {
    private final CalendarService calendarService;
    private final TaskService taskService;
    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(LocalDateTime.class, new LocalDateTimeEditor());
    }

    @GetMapping("/calendar/{months}")
    public ResponseEntity<ApiResponse<CalendarGetResponse>> getCalender
            (@PathVariable Integer months, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return calendarService.getCalender(months,userDetails.getUser());
    }

    @PostMapping("/calendar")
    public ResponseEntity<ApiResponse<CalendarPostResponse>> addCalendar
            (@RequestBody CalendarPostRequest request,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return calendarService.addCalendar(request,userDetails.getUser());
    }

    @GetMapping("/todo")
    public ResponseEntity<ApiResponse<TodoGetResponse>> getTodo(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return taskService.getTodo(userDetails.getUser());
    }


    @PostMapping("/todo")
    public ResponseEntity<ApiResponse<TodoPostResponse>> addTodo
            (@RequestBody TodoPostRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return taskService.addTodo(request,userDetails.getUser());
    }


}
