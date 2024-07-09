package com.myweapon.hourglass.schedule.service;

import com.myweapon.hourglass.common.ApiResponse;
import com.myweapon.hourglass.schedule.dto.CalendarGetResponse;
import com.myweapon.hourglass.schedule.dto.CalendarPostRequest;
import com.myweapon.hourglass.schedule.dto.CalendarPostResponse;
import com.myweapon.hourglass.schedule.dto.CalendarRemain;
import com.myweapon.hourglass.schedule.entity.Calender;
import com.myweapon.hourglass.schedule.repository.CalenderRepository;
import com.myweapon.hourglass.security.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalenderRepository calenderRepository;

    public ResponseEntity<ApiResponse<CalendarGetResponse>> getCalender (@PathVariable Integer months, User user){
        LocalDate now = LocalDate.now();
        LocalDate after = now.plusMonths(months);

        List<CalendarRemain> schedules = calenderRepository.findRemainCalender(user.getId(),now,after);
        schedules.sort(Comparator.comparingInt(CalendarRemain::getDDay));

        CalendarGetResponse response = CalendarGetResponse.of(schedules);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    public ResponseEntity<ApiResponse<CalendarPostResponse>> addCalendar(CalendarPostRequest request, User user){
        Calender calender = Calender.of(request,user);
        calenderRepository.save(calender);
        CalendarPostResponse response = CalendarPostResponse.of(calender.getId());
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
