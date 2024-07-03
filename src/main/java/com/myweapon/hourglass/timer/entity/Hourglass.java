package com.myweapon.hourglass.timer.entity;

import com.myweapon.hourglass.common.TimeUtils;
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
    private LocalDateTime pause_time;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime resume_time;

    @Builder
    public Hourglass(Task task, LocalDateTime start, LocalDateTime end, Integer burstTime, Integer goal, LocalDateTime pause_time, LocalDateTime resume_time) {
        this.task = task;
        this.start = start;
        this.end = end;
        this.burstTime = burstTime;
        this.goal = goal;
        this.pause_time = pause_time;
        this.resume_time = resume_time;
    }

    public static Hourglass toStartHourglass(HourglassStartRequest request){
        LocalDateTime localDateTime = TimeUtils.formatString(request.getTimeStart());
        System.out.println("!!");
        return Hourglass.builder()
                .start(localDateTime)
                .goal(request.getTimeGoal())
                .build();
    }
}
