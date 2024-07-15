package com.myweapon.hourglass.today.i.learned.dto;

import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@Getter
public class DocumentContentDto {
    private String title;
    private String content;
}
