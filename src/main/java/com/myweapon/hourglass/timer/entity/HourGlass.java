package com.myweapon.hourglass.timer.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Type;
import org.hibernate.type.NumericBooleanConverter;

import java.sql.Time;
import java.util.Date;

@Entity
@Table(name="hour_glass")
public class HourGlass {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="task_id")
    private Task task;

    @Temporal(TemporalType.TIMESTAMP)
    private Date start;

    @Temporal(TemporalType.TIMESTAMP)
    private Date end;

    @Column(name="burst_time")
    private Time burstTime;

    @Convert(converter = NumericBooleanConverter.class)
    @Column(name="is_stopped_forcibly")
    private Boolean isStoppedForcibly;
}
