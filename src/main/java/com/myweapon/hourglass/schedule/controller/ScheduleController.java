package com.myweapon.hourglass.schedule.controller;


import com.myweapon.hourglass.common.ApiResponse;
import com.myweapon.hourglass.common.ApiSuccess;
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
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")
@Slf4j
public class ScheduleController {
    private static final Boolean COMPLETED = true;
    private static final Boolean NOT_COMPLETED = false;
    private final CalendarService calendarService;
    private final TaskService taskService;
    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(LocalDateTime.class, new LocalDateTimeEditor());
    }

    @GetMapping("/calendar")
    public ResponseEntity<ApiResponse<CalendarGetResponse>> getCalendar
            (@AuthenticationPrincipal UserDetailsImpl userDetails){
        List<CalendarRemain> schedules = calendarService.getCalendar(userDetails.getUser());
        return ResponseEntity.ok(ApiResponse.success(CalendarGetResponse.of(schedules)));
    }

    @GetMapping("/calendar/{months}")
    public ResponseEntity<ApiResponse<CalendarGetResponse>> getCalenderDuring
            (@PathVariable Integer months, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return calendarService.getCalenderBy(months,userDetails.getUser());
    }

    @PostMapping("/calendar")
    public ResponseEntity<ApiResponse<CalendarPostResponse>> addCalendar
            (@RequestBody CalendarPostRequest request,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return calendarService.addCalendar(request,userDetails.getUser());
    }

    @GetMapping("/todo")
    public ResponseEntity<ApiResponse<TodoGetResponse>> getTodo(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return taskService.getTodo(userDetails.getUser(),NOT_COMPLETED);
    }


    @PostMapping("/todo")
    public ResponseEntity<ApiResponse<TodoPostResponse>> addTodo
            (@RequestBody TodoPostRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return taskService.addTodo(request,userDetails.getUser());
    }

    @GetMapping("/todo/completion")
    public ResponseEntity<ApiResponse<TodoGetResponse>> getTodoComplete(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return taskService.getTodo(userDetails.getUser(),COMPLETED);
    }

    @PostMapping("/todo/completion")
    public ResponseEntity<ApiResponse<ApiSuccess>> completeTodo(@RequestParam(name = "taskId") Long taskId){
        return taskService.completeTodo(taskId);
    }
}
