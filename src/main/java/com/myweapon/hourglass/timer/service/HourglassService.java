package com.myweapon.hourglass.timer.service;

import com.myweapon.hourglass.common.exception.RestApiException;
import com.myweapon.hourglass.timer.dto.HourglassInfo;
import com.myweapon.hourglass.timer.dto.DefaultHourglassHistoryInfo;
import com.myweapon.hourglass.timer.dto.request.HourglassEndRequest;
import com.myweapon.hourglass.timer.dto.request.HourglassRunRequest;
import com.myweapon.hourglass.timer.dto.request.HourglassStopRequest;
import com.myweapon.hourglass.timer.exception.TimerRestApiException;
import com.myweapon.hourglass.timer.entity.Task;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.security.enumset.ErrorType;
import com.myweapon.hourglass.timer.dto.request.HourglassStartRequest;
import com.myweapon.hourglass.timer.entity.*;
import com.myweapon.hourglass.timer.enumset.DefaultCategory;
import com.myweapon.hourglass.timer.respository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class HourglassService {

    private final HourglassRepository hourglassRepository;
    private final HourglassHistoryService hourglassHistoryService;
    private final TaskService taskService;

    /**
     * 모래시계 시작
     * 이미 진행중인 모래시계가 있다면 시작할 수 없다.
     * @param request
     * @param user
     * @return
     */
    @Transactional
    public Long startHourglass(HourglassStartRequest request, User user){
        if(!ObjectUtils.isEmpty(findHourglassInProgress(user))){
            throw new TimerRestApiException(TimerRestApiException.HourglassInProgress);
        }

        Task otherTask = taskService.findDefaultTasksByUserIdAndCategoryName(user.getId(),DefaultCategory.OTHERS.getName());
        Hourglass hourglass = Hourglass.of(request.getTimeGoal(),otherTask);

        hourglassRepository.save(hourglass);

        hourglassHistoryService.recordStart(user,hourglass,request);
        return hourglass.getId();
    }

    @Transactional
    public Long startHourglassWithTask(User user,Long taskId,HourglassStartRequest request){
        if(!ObjectUtils.isEmpty(findHourglassInProgress(user))){
            throw new RestApiException(ErrorType.HOURGLASS_IN_PROGRESS);
        }

        Task task = taskService.findTaskByTaskId(taskId);
        Hourglass hourglass = Hourglass.of(request.getTimeGoal(),task);

        hourglassRepository.save(hourglass);

        hourglassHistoryService.recordStart(user,hourglass,request);
        return hourglass.getId();
    }

    @Transactional
    public void pauseHourglass(User user, Long hourglassId, HourglassStopRequest request){
        hourglassHistoryService.recordPause(user,findHourglassById(hourglassId),request);
    }

    @Transactional
    public void restartHourglass(User user, Long hourglassId, HourglassRunRequest request){
        hourglassHistoryService.recordRestart(user,findHourglassById(hourglassId),request);
    }

    @Transactional
    public void endHourglass(User user, Long hourglassId, HourglassEndRequest request){
        Hourglass hourglassEnding  = findHourglassById(hourglassId);
        hourglassHistoryService.recordEnd(user,hourglassEnding,request);
        hourglassEnding.setEndInfo(request.getEndInfo());
    }

    @Transactional
    public void endHourglassWithUserCategory(User user, Long userCategoryId, Long hourglassId, HourglassEndRequest request){
        Hourglass hourglassEnding = findHourglassById(hourglassId);
        Task task = taskService.findTaskByHourglass(hourglassEnding);
        taskService.changeUserCategory(task.getId(),userCategoryId);

        hourglassEnding.setTask(task);

        hourglassHistoryService.recordEnd(user,hourglassEnding,request);
        hourglassEnding.setEndInfo(request.getEndInfo());
    }


    /*
    * 진행중인 모래시계의 id를 반환한다. 없다면 -1반환
    */
    public Hourglass findHourglassInProgress(User user){
        Optional<Hourglass> hourglassesInProgress = hourglassRepository.getHourglassesInProgress(user.getId());
        return hourglassesInProgress.orElse(null);
    }

    public HourglassInfo findHourglassInfoInProgress(User user,String state){
        if(!state.equals("progress")){
            throw new TimerRestApiException(TimerRestApiException.StateOnlyProgress);
        }
        Hourglass hourglass = findHourglassInProgress(user);
        List<DefaultHourglassHistoryInfo> histories = findHistoryByHourglass(hourglass);
        return HourglassInfo.of(hourglass,histories);
    }

    public HourglassInfo findHourglassInfoInProgressAndCheck(User user,String state){
        HourglassInfo hourglassInfo = findHourglassInfoInProgress(user,state);
        if(ObjectUtils.isEmpty(hourglassInfo)){
            throw new TimerRestApiException(TimerRestApiException.NoSuchHourglassInProgress);
        }
        return hourglassInfo;
    }

    public List<DefaultHourglassHistoryInfo> findHistoryByHourglass(Hourglass hourglass){
        List<DefaultHourglassHistoryInfo> histories = hourglassHistoryService.findHourglassHistoriesBy(hourglass);
        return histories;
    }

    public Hourglass findHourglassById(Long HourglassId){
        return hourglassRepository
                .findById(HourglassId)
                .orElseThrow(()->new TimerRestApiException(TimerRestApiException.NoSuchHourglass));
    }

}
