package com.myweapon.hourglass.diary.service;

import com.myweapon.hourglass.common.exception.RestApiException;
import com.myweapon.hourglass.common.dto.ApiResponse;
import com.myweapon.hourglass.common.dto.ApiSuccess;
import com.myweapon.hourglass.common.repository.utils.UpdateUtils;
import com.myweapon.hourglass.diary.dto.DayStudyRecord;
import com.myweapon.hourglass.diary.dto.HourglassPutRequest;
import com.myweapon.hourglass.statics.repository.StudyStaticsViewRepository;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.security.enumset.ErrorType;
import com.myweapon.hourglass.timer.respository.HourglassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class DiaryService {
    private final StudyStaticsViewRepository studyStaticsViewRepository;
    private final HourglassRepository hourglassRepository;

    public ResponseEntity<ApiResponse<DayStudyRecord>> getDayStudyRecord(LocalDate date, User user){
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = start.plusDays(1);

        DayStudyRecord dayStudyRecord =  DayStudyRecord.from(studyStaticsViewRepository
                .findHourglassStudyRecords(start,end,user.getId()));

        return ResponseEntity.ok(ApiResponse.success(dayStudyRecord));
    }

    public ResponseEntity<ApiSuccess> updateContentOfHourglass(Long hId, HourglassPutRequest request){
        if(UpdateUtils.isNotUpdated(hourglassRepository.updateContent(hId,request.getContent()))){
            throw new RestApiException(ErrorType.HOURGLASS_NOT_EXISTS);
        }
        return ResponseEntity.ok(ApiSuccess.body());
    }
}
