package com.myweapon.hourglass.timer.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class HourglassEndRequest extends HourglassStopRequest{
    private EndInfo endInfo;


    @Getter
    public static class EndInfo {
        private float rating;
        private String content;
    }

}
