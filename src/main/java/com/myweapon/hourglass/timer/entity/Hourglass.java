package com.myweapon.hourglass.timer.entity;

import com.myweapon.hourglass.schedule.entity.Task;
import com.myweapon.hourglass.timer.dto.HourglassEndRequest;
import com.myweapon.hourglass.timer.dto.HourglassStartRequest;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Slf4j
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
        return Hourglass.builder()
                .start(request.getTimeStart())
                .goal(request.getTimeGoal())
                .build();
    }

    /*
    !주의 : userCategory는 항상 영속 상태의 엔티티만 넣어야 함.
     */
    public Boolean end(Task task, HourglassEndRequest request){
        this.task = task;
        end = request.getTimeEnd();
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
