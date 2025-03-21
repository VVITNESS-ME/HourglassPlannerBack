package com.myweapon.hourglass.timer.enumset;

import com.myweapon.hourglass.timer.entity.HourglassHistory;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum HourglassState {
    START("start"),
    PAUSE("pause"),
    RESTART("restart"),
    END("end");

    private String state;

    private HourglassState(String state){
        this.state = state;
    }

    public static Optional<HourglassState> getHourglassState(String stateString){
        return Arrays.stream(HourglassState.values())
                .filter(state->state.getState().equals(stateString))
                .findFirst();
    }

    public String toString(){
        return state;
    }
}
