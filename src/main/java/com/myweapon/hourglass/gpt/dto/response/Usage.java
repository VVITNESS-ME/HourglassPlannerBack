package com.myweapon.hourglass.gpt.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Usage {
    private Integer prompt_tokens;
    private Integer completion_tokens;
    private Integer total_tokens;
}
