package com.myweapon.hourglass.timer.entity;

import com.myweapon.hourglass.security.UserDetailsImpl;
import com.myweapon.hourglass.security.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.Type;
import org.hibernate.type.NumericBooleanConverter;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Task {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_category_id")
    private UserCategory userCategory;

    private String title;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime date;

    @Convert(converter = NumericBooleanConverter.class)
    @Column(name="is_completed")
    private Boolean isCompleted;

    @Builder
    public Task(UserCategory userCategory, String title, LocalDateTime date, Boolean isCompleted) {
        this.userCategory = userCategory;
        this.title = title;
        this.date = date;
        this.isCompleted = isCompleted;
    }

    /*
       task를 지정하지 않은 모래시계를 위한 task를 만든다.
    */
    public static Task defaultOf(UserCategory userCategory){
        return Task.builder()
                .userCategory(userCategory)
                .isCompleted(false)
                .title(userCategory.getCategory().getName())
                .build();
    }
}
