package com.myweapon.hourglass.timer.entity;

import com.myweapon.hourglass.RestApiException;
import com.myweapon.hourglass.common.TimeUtils;
import com.myweapon.hourglass.security.enumset.ErrorType;
import com.myweapon.hourglass.timer.dto.HourglassEndRequest;
import com.myweapon.hourglass.timer.dto.HourglassStartRequest;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name="hour_glass")
public class Hourglass {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="task_id")
    private Task task;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime start;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime end;

    @Column(name="burst_time")
    private Integer burstTime;

    private Integer goal;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime last_pause;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime last_resume;

    private String content;
    private Float rating;

    @Builder
    public Hourglass(Task task, LocalDateTime start, LocalDateTime end
            , Integer burstTime, Integer goal, LocalDateTime last_pause
            , LocalDateTime last_resume, String content, Float rating) {
        this.task = task;
        this.start = start;
        this.end = end;
        this.burstTime = burstTime;
        this.goal = goal;
        this.last_pause = last_pause;
        this.last_resume = last_resume;
        this.content = content;
        this.rating = rating;
    }

    public static Hourglass toStartHourglass(HourglassStartRequest request){
        LocalDateTime localDateTime = TimeUtils.formatString(request.getTimeStart());
        return Hourglass.builder()
                .start(localDateTime)
                .goal(request.getTimeGoal())
                .build();
    }

    public Boolean end(UserCategory userCategory, HourglassEndRequest request){
        LocalDateTime now = LocalDateTime.now();

        task.setUserCategory(userCategory);
        end = now;
        burstTime = request.getTimeBurst();
        rating = request.getRating();
        content = request.getContent();

        return true;
    }

    public Boolean pause(){
        LocalDateTime now = LocalDateTime.now();

        if(last_pause==null){
            last_pause = now;
            return true;
        }
        else if(last_resume==null){
            return false;
        }
        else if(last_pause.isBefore(last_resume)) {
            last_pause = now;
            return true;
        }
        return false;
    }

    public Boolean resume(){
        LocalDateTime now = LocalDateTime.now();

        if(last_pause == null){
            return false;
        }
        else if (last_resume == null || last_resume.isBefore(last_pause)){
            last_resume = now;
            return true;
        }
        return false;
    }

}
