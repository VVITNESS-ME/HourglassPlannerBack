package com.myweapon.hourglass.title.entity;

import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.title.Title;
import com.myweapon.hourglass.title.dto.TitleDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name="user_new_title")
public class UserNewTitle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    @Enumerated(EnumType.STRING)
    private Title title;

    @Builder
    public UserNewTitle(User user, Title title) {
        this.user = user;
        this.title = title;
    }

    public TitleDto toTitleDto(){
        return TitleDto.builder()
                .id(title.getIndex())
                .name(title.getTitleName())
                .achieveCondition(title.getAchievementCondition())
                .titleColor(title.getTitleColor())
                .build();
    }

}