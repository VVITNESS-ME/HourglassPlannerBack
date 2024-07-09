package com.myweapon.hourglass.statics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class DayOfGarden {
    private LocalDate date;
    private Long totalBurst;
    public DayOfGarden(LocalDateTime dateString,Long totalBurst){
        this.date = dateString.toLocalDate();
        this.totalBurst = totalBurst;
    }
}
