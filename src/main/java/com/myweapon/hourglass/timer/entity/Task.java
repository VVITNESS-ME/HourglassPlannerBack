package com.myweapon.hourglass.timer.entity;

import com.myweapon.hourglass.security.entity.User;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.Type;
import org.hibernate.type.NumericBooleanConverter;

import java.util.Date;

@Entity
@NoArgsConstructor
public class Task {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_category")
    private UserCategory userCategory;

    private String title;
    private Float rating;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Convert(converter = NumericBooleanConverter.class)
    @Column(name="is_completed")
    private Integer isCompleted;

    @Lob
    private String content;

}
