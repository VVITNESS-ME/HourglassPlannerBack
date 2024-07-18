package com.myweapon.hourglass.title.entity;

import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.title.Title;
import com.myweapon.hourglass.title.dto.UserTitleDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name="user_title")
public class UserTitle {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;

    private Boolean title1;
    private Boolean title2;
    private Boolean title3;
    private Boolean title4;
    private Boolean title5;
    private Boolean title6;
    private Boolean title7;
    private Boolean title8;
    private Boolean title9;
    private Boolean title10;
    private Boolean title11;
    private Boolean title12;
    private Boolean title13;
    private int main_title;

    @Builder
    public UserTitle(User user, Boolean title1, Boolean title2, Boolean title3, Boolean title4, Boolean title5, Boolean title6, Boolean title7, Boolean title8, Boolean title9, Boolean title10, Boolean title11, Boolean title12, Boolean title13, int main_title) {
        this.user = user;
        this.title1 = title1;
        this.title2 = title2;
        this.title3 = title3;
        this.title4 = title4;
        this.title5 = title5;
        this.title6 = title6;
        this.title7 = title7;
        this.title8 = title8;
        this.title9 = title9;
        this.title10 = title10;
        this.title11 = title11;
        this.title12 = title12;
        this.title13 = title13;
        this.main_title = main_title;
    }

    public UserTitleDto toUserTitleDto(){
        return UserTitleDto.builder()
                .main_title(this.main_title)
                .title1(this.title1)
                .title2(this.title2)
                .title3(this.title3)
                .title4(this.title4)
                .title5(this.title5)
                .title6(this.title6)
                .title7(this.title7)
                .title8(this.title8)
                .title9(this.title9)
                .title10(this.title10)
                .title11(this.title11)
                .title12(this.title12)
                .title13(this.title13)
                .build();
    }

    public void addTitle(Title title) {
        switch (title) {
            case TITLE1 -> this.title1 = true;
            case TITLE2 -> this.title2 = true;
            case TITLE3 -> this.title3 = true;
            case TITLE4 -> this.title4 = true;
            case TITLE5 -> this.title5 = true;
            case TITLE6 -> this.title6 = true;
            case TITLE7 -> this.title7 = true;
            case TITLE8 -> this.title8 = true;
            case TITLE9 -> this.title9 = true;
            case TITLE10 -> this.title10 = true;
            case TITLE11 -> this.title11 = true;
            case TITLE12 -> this.title12 = true;
            case TITLE13 -> this.title13 = true;
        }
    }

    public void changeMainTitle(int titleId){
        this.main_title = titleId;
    }
}
