package com.myweapon.hourglass.common.embeddable;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalDateTime;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class DateAudit{
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static DateAudit createEmpty(){
        return DateAudit.builder().build();
    }

    public static DateAudit of(LocalDateTime createdAt,LocalDateTime updatedAt){
        return DateAudit.builder()
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public DateAudit withUpdatedAt(LocalDateTime updatedAt){
        return DateAudit.of(this.createdAt,updatedAt);
    }

    public DateAudit withCreatedAt(LocalDateTime createdAt){
        return DateAudit.of(createdAt,this.updatedAt);
    }
}
