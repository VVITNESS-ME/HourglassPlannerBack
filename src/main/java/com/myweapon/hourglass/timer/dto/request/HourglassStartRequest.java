package com.myweapon.hourglass.timer.dto.request;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class HourglassStartRequest extends HourglassRunRequest{
    private Integer timeGoal;
}
