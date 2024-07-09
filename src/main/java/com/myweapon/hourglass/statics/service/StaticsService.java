package com.myweapon.hourglass.statics.service;

import com.myweapon.hourglass.common.ApiResponse;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.statics.dto.DayOfGarden;
import com.myweapon.hourglass.statics.dto.GardenGetResponse;
import com.myweapon.hourglass.statics.repository.StudyStaticsViewRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StaticsService {
    private final StudyStaticsViewRepository studyStaticsViewRepository;
    public ResponseEntity<ApiResponse<GardenGetResponse>> getGardenOfInterval(LocalDateTime start, LocalDateTime end, User user){
        List<DayOfGarden> entries = studyStaticsViewRepository.findGardenOfInterval(start,end,user.getId());
        GardenGetResponse response = GardenGetResponse.of(entries);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
