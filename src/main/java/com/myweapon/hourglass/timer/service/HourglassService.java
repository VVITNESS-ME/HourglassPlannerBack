package com.myweapon.hourglass.timer.service;

import com.myweapon.hourglass.common.exception.RestApiException;
import com.myweapon.hourglass.category.entity.Category;
import com.myweapon.hourglass.category.entity.UserCategory;
import com.myweapon.hourglass.category.repository.UserCategoryRepository;
import com.myweapon.hourglass.common.dto.ApiResponse;
import com.myweapon.hourglass.schedule.entity.Task;
import com.myweapon.hourglass.schedule.repository.TaskRepository;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.security.enumset.ErrorType;
import com.myweapon.hourglass.statistics.service.InfluxStatisticsService;
import com.myweapon.hourglass.timer.dto.*;
import com.myweapon.hourglass.timer.entity.*;
import com.myweapon.hourglass.timer.enumset.DefaultCategory;
import com.myweapon.hourglass.timer.respository.*;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class HourglassService {

    private final HourglassRepository hourglassRepository;
    private final UserCategoryRepository userCategoryRepository;
    private final TaskRepository taskRepository;
    private final UserHourglassRepository userHourglassRepository;

    private final InfluxStatisticsService influxStatisticsService;
    private final EntityManager em;

    public Long startHourglass(HourglassStartRequest request, User user){
        if(isHourglassInProgress(user)){
            throw new RestApiException(ErrorType.HOURGLASS_IN_PROGRESS);
        }

        Task task = getOthersTaskOf(user);

        Hourglass hourglass = createAndSaveHourglass(task,request);
        influxStatisticsService.recordStartLog(hourglass,user);

        return hourglass.getId();
    }

    public Long startHourglassWithTask(Long tId,HourglassStartRequest request,User user){
        if(isHourglassInProgress(user)){
            throw new RestApiException(ErrorType.HOURGLASS_IN_PROGRESS);
        }

        Task task = taskRepository.findById(tId).orElseThrow(()->new RestApiException(ErrorType.TASK_NOT_EXISTS));

        Hourglass hourglass = createAndSaveHourglass(task,request);
        influxStatisticsService.recordStartLog(hourglass,user);

        return hourglass.getId();
    }

    /*
    통계 데이터 : 카테고리 별로 공부한 시간, 오늘 공부한 시간
     */
    public HourglassSummaryResponse endHourglass(HourglassEndRequest request ,User user){
        Hourglass hourglass = hourglassRepository.findById(request.getHId())
                .orElseThrow(()->new RestApiException(ErrorType.HOURGLASS_NOT_EXISTS));
        Task task = taskRepository.findDefaultTaskByCategoryName(request.getCategoryName(),user)
                .orElseThrow(()->new RestApiException((ErrorType.USER_CATEGORY_NOT_EXISTS)));

        hourglass.end(task,request);
        em.flush();

        influxStatisticsService.recordEndLog(hourglass,user);

        List<StudySummeryWithCategoryName> resultSummary = getResultSummery(user);

        return HourglassSummaryResponse.of(hourglass.getId(), resultSummary);
    }

    public HourglassSummaryResponse endHourglassWithTask(Long taskId, HourglassEndRequest request ,User user){
        Hourglass hourglass = hourglassRepository.findById(request.getHId())
                .orElseThrow(()->new RestApiException(ErrorType.HOURGLASS_NOT_EXISTS));
        Task task = taskRepository.findTodoTaskNotCompletedByTaskId(taskId)
                .orElseThrow(()->new RestApiException(ErrorType.TASK_NOT_EXISTS));

        hourglass.end(task,request);
        em.flush();

        influxStatisticsService.recordEndLog(hourglass,user);

        List<StudySummeryWithCategoryName> resultSummary = getResultSummery(user);

        return HourglassSummaryResponse.of(hourglass.getId(), resultSummary);
    }


    public ResponseEntity<ApiResponse<HourglassResponse>> pauseHourglass(HourglassPauseRequest request, User user){
        Hourglass hourglass = hourglassRepository.findById(request.getHId())
                .orElseThrow(()->new RestApiException(ErrorType.HOURGLASS_NOT_EXISTS));

        if(!hourglass.pause()){
            throw new RestApiException(ErrorType.HOURGLASS_ALREADY_PAUSE);
        }

        influxStatisticsService.recordPauseLog(hourglass,user);

        return ResponseEntity.ok(ApiResponse.success(HourglassResponse.fromHId(hourglass.getId())));
    }


    public ResponseEntity<ApiResponse<HourglassResponse>> resumeHourglass(HourglassResumeRequest request,User user){
        Hourglass hourglass = hourglassRepository.findById(request.getHId())
                .orElseThrow(()->new RestApiException(ErrorType.HOURGLASS_NOT_EXISTS));

        if(!hourglass.resume()){
            throw new RestApiException(ErrorType.HOURGLASS_NOT_PAUSE);
        }

        influxStatisticsService.recordResumeLog(hourglass,user);

        return ResponseEntity.ok(ApiResponse.success(HourglassResponse.fromHId(hourglass.getId())));
    }





    public Optional<HourglassInfoInProgress> getHourglassInProgressInfo(User user){
        return userHourglassRepository.findHourglassInProgress(user.getId());
    }

    public List<String> getContentOf(LocalDate localDate,User user){
        return userHourglassRepository.getContentsOf(localDate,user.getId());
    }

    private Task getOthersTaskOf(User user){
        return taskRepository.findDefaultTaskByCategoryName(DefaultCategory.OTHERS.getName(),user)
                .orElseThrow();
    }

    private Hourglass createAndSaveHourglass(Task task, HourglassStartRequest request){
        Hourglass hourglass = Hourglass.toStartHourglass(request);
        hourglass.setTask(task);

        hourglassRepository.save(hourglass);
        return hourglass;
    }


    private boolean isHourglassInProgress(User user){
        Optional<HourglassInfoInProgress> progressInfo = userHourglassRepository.findHourglassInProgress(user.getId());
        return progressInfo.isPresent();
    }

    private List<StudySummeryWithCategoryName> getResultSummery(User user){
        LocalDateTime start = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        LocalDateTime end = start.plusDays(1);

        List<StudySummeryWithCategory> userCategorySummery
                = userHourglassRepository.studySummeryByDay(user.getId(), start, end);

        List<StudySummeryWithCategoryName> resultSummary = new ArrayList<>();
        for(StudySummeryWithCategory summery : userCategorySummery){
            UserCategory userCategory = userCategoryRepository.findById(summery.getUserCategoryId())
                    .orElseThrow(()->new RestApiException(ErrorType.USER_CATEGORY_NOT_EXISTS));
            Category category = userCategory.getCategory();
            resultSummary.add(StudySummeryWithCategoryName.of(summery,category));
        }

        return resultSummary;
    }


}
