package com.myweapon.hourglass.today.i.learned.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.*;

@Embeddable
@NoArgsConstructor
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class DocumentContent {

    @Lob
    private String title;
    @Lob
    private String content;

    public DocumentContent withTitleAt(String title){
        return of(title,this.content);
    }

    public DocumentContent withContentAt(String content){
        return of(this.title,content);
    }

    public static DocumentContent of(String title,String content){
        return DocumentContent.builder()
                .title(title)
                .content(content)
                .build();
    }
}
