package com.myweapon.hourglass.timer.entity;

import com.myweapon.hourglass.timer.dto.request.HourglassEndRequest;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Slf4j
@AllArgsConstructor
@Builder
public class Hourglass {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="task_id",nullable = false)
    private Task task;

    private Integer goal;

    private String content;
    private Float rating;

    public void setEndInfo(HourglassEndRequest.EndInfo endInfo){
        this.content = endInfo.getContent();
        this.rating = endInfo.getRating();
    }

    public static Hourglass of(Integer timeGoal,Task task){
        return Hourglass.builder().goal(timeGoal).task(task).build();
    }
}
