package com.myweapon.hourglass.schedule.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Builder
public class TodoPostResponse {
    private Long tId;
    public TodoPostResponse(Long tId){
        this.tId = tId;
    }
    public static TodoPostResponse of(Long tid){
        return TodoPostResponse.builder()
                .tId(tid)
                .build();
    }
}
