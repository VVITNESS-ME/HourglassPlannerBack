package com.myweapon.hourglass.schedule.entity;

import com.myweapon.hourglass.schedule.dto.TodoPostRequest;
import com.myweapon.hourglass.category.entity.UserCategory;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.type.NumericBooleanConverter;

import java.time.LocalDateTime;

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

    @Convert(converter = NumericBooleanConverter.class)
    @Column(name="is_default")
    private Boolean isDefault;

    @Builder
    public Task(UserCategory userCategory, String title, LocalDateTime date, Boolean isCompleted,Boolean isDefault) {
        this.userCategory = userCategory;
        this.title = title;
        this.date = date;
        this.isCompleted = isCompleted;
        this.isDefault = isDefault;
    }

    /*
       task를 지정하지 않은 모래시계를 위한 task를 만든다.
    */
    public static Task defaultOf(UserCategory userCategory){
        return Task.builder()
                .userCategory(userCategory)
                .isCompleted(false)
                .title(userCategory.getCategory().getName())
                .date(LocalDateTime.now())
                .isDefault(true)
                .build();
    }

    public static Task todoOf(TodoPostRequest request,UserCategory userCategory){
        return Task.builder()
                .userCategory(userCategory)
                .isCompleted(false)
                .title(request.getTitle())
                .date(LocalDateTime.now())
                .isDefault(false)
                .build();
    }
}
