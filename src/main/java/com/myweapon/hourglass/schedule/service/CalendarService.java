package com.myweapon.hourglass.schedule.service;

import com.myweapon.hourglass.common.ApiResponse;
import com.myweapon.hourglass.schedule.dto.CalendarGetResponse;
import com.myweapon.hourglass.schedule.dto.CalendarPostRequest;
import com.myweapon.hourglass.schedule.dto.CalendarPostResponse;
import com.myweapon.hourglass.schedule.dto.CalendarRemain;
import com.myweapon.hourglass.schedule.entity.Calender;
import com.myweapon.hourglass.schedule.repository.CalendarRepository;
import com.myweapon.hourglass.security.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;

    public ResponseEntity<ApiResponse<CalendarGetResponse>> getCalenderBy(@PathVariable Integer months, User user){
        LocalDate now = LocalDate.now();
        LocalDate after = now.plusMonths(months);

        List<CalendarRemain> schedules = calendarRepository.findRemainCalendarDuring(user.getId(),now,after);
        schedules.sort(Comparator.comparingLong(CalendarRemain::getDDay));

        CalendarGetResponse response = CalendarGetResponse.of(schedules);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    public List<CalendarRemain> getCalendar(User user){
        LocalDate now = LocalDate.now();
        List<CalendarRemain> schedules = calendarRepository.findRemainCalendar(user.getId(),now);
        schedules.sort(Comparator.comparingLong(CalendarRemain::getDDay));
        return schedules;
    }

    public ResponseEntity<ApiResponse<CalendarPostResponse>> addCalendar(CalendarPostRequest request, User user){
        List<Calender> calenders = Calender.ListOf(request,user);
        calendarRepository.saveAll(calenders);
        CalendarPostResponse response = CalendarPostResponse.of(calenders);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
