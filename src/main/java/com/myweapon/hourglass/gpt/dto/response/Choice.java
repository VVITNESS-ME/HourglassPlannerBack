package com.myweapon.hourglass.gpt.dto.response;

import com.myweapon.hourglass.gpt.dto.ChatMsg;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@Getter
@ToString
public class Choice {
    private Integer index;
    private ChatMsg message;
    private String logprobs;
    private String finish_reason;
}
