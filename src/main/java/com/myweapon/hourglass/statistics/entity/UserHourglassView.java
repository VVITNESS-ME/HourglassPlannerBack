package com.myweapon.hourglass.statistics.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import java.time.LocalDateTime;

@Immutable
@Subselect("select h.id as hourglass_id,u.id as user_id,t.id as task_id,uc.id as user_category_id " +
        ",t.title as task_title,c.name as category_name, uc.color as color " +
        ",h.start as start,h.end as end,h.burst_time as burst_time " +
        ",h.content as hourglass_content,h.rating as rating " +
        "from user u " +
        "inner join user_category uc on u.id = uc.user_id " +
        "inner join task t on uc.id = t.user_category_id " +
        "inner join hour_glass h on t.id = h.task_id " +
        "inner join category c on c.id = uc.category_id")
@Table(name = "study_statics_view")
@Entity
@EqualsAndHashCode
@ToString
public class UserHourglassView {
    @Id
    private Long hourglassId;
    private Long userId;
    private Long taskId;
    private Long userCategoryId;
    private String taskTitle;
    private String categoryName;
    private String color;
    private LocalDateTime start;
    private LocalDateTime end;
    private Integer burstTime;
    private String hourglassContent;
    private Float rating;
}
