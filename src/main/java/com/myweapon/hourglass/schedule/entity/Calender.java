package com.myweapon.hourglass.schedule.entity;

import com.myweapon.hourglass.schedule.dto.CalendarPostRequest;
import com.myweapon.hourglass.security.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Calender {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDate dueDate;

    private String description;

    @Builder
    public Calender(User user, LocalDate dueDate, String description){
        this.user = user;
        this.dueDate = dueDate;
        this.description = description;
    }
    public static Calender of(String description,LocalDate dueDate, User user){
        return Calender.builder()
                .user(user)
                .dueDate(dueDate)
                .description(description)
                .build();
    }

    public static List<Calender> ListOf(CalendarPostRequest request, User user){
        LocalDate dueDate = request.getDueDate();
        List<String> schedules = request.getSchedules();
        return schedules.stream()
                .map((schedule)->Calender.of(schedule,dueDate,user))
                .toList();
    }
}
