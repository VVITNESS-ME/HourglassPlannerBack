package com.myweapon.hourglass.timer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

import java.time.LocalDateTime;

@Immutable
@Subselect("select h.id, u.id as user_id,uc.id as user_category_id,uc.color as color,h.start,h.end,h.goal,h.task_id,h.burst_time,h.last_pause,h.last_resume,h.content,h.rating from user u\r\n"
+"inner join user_category uc on u.id = uc.user_id\r\n"
+"inner join task t on uc.id = t.user_category_id\r\n"
+"inner join hour_glass h on t.id = h.task_id")
@Table(name = "user_hourglass_v")
@Entity
@EqualsAndHashCode
@ToString
public class UserHourglass {
    @Id
    private Long id;
    private Long user_id;
    private Long user_category_id;
    private String color;
    private LocalDateTime end;
    private LocalDateTime start;
    private Integer goal;
    private Long task_id;
    private Integer burst_time;
    private LocalDateTime last_pause;
    private LocalDateTime last_resume;
    private String content;
    private Float rating;
}
