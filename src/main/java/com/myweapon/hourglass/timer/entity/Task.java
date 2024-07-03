package com.myweapon.hourglass.timer.entity;

import com.myweapon.hourglass.security.UserDetailsImpl;
import com.myweapon.hourglass.security.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.Type;
import org.hibernate.type.NumericBooleanConverter;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
public class Task {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_category_id")
    private UserCategory userCategory;

    private String title;
    private Float rating;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime date;

    @Convert(converter = NumericBooleanConverter.class)
    @Column(name="is_completed")
    private Boolean isCompleted;

    @Lob
    private String content;

    @Builder
    public Task(UserCategory userCategory, String title, Float rating, LocalDateTime date, Boolean isCompleted, String content) {
        this.userCategory = userCategory;
        this.title = title;
        this.rating = rating;
        this.date = date;
        this.isCompleted = isCompleted;
        this.content = content;
    }

    public static Task defaultOf(UserCategory userCategory){
        return Task.builder()
                .userCategory(userCategory)
                .isCompleted(false)
                .title(userCategory.getCategory().getName())
                .build();
    }
}
