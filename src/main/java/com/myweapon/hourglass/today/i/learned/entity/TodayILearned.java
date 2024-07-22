package com.myweapon.hourglass.today.i.learned.entity;

import com.myweapon.hourglass.common.embeddable.DateAudit;
import com.myweapon.hourglass.common.jpa.listener.Auditable;
import com.myweapon.hourglass.common.jpa.listener.DateAuditEntityListener;
import com.myweapon.hourglass.security.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Service
@Table(name = "today_i_learned")
@EntityListeners(value = DateAuditEntityListener.class)
@ToString
public class TodayILearned implements Auditable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private LocalDate dateTodo;

    @Embedded
    private DateAudit dateAudit;

    @Embedded
    private DocumentContent documentContent;

    public static TodayILearned of(DocumentContent documentContent, LocalDate dateTodo , User user){
        return TodayILearned.builder()
                .dateTodo(dateTodo)
                .documentContent(documentContent)
                .user(user)
                .build();
    }

    @Override
    public Optional<DateAudit> getDateAudit(){
        return Optional.ofNullable(dateAudit);
    }
    @Override
    public Optional<LocalDateTime> getCreateAt() {
        return Optional.ofNullable(dateAudit.getCreatedAt());
    }

    @Override
    public Optional<LocalDateTime> getUpdatedAt() {
        return Optional.ofNullable(dateAudit.getUpdatedAt());
    }

    @Override
    public void setDateAudit(DateAudit dateAudit) {
//        System.out.println(dateAudit);
        this.dateAudit = dateAudit;
    }

    @Override
    public void setCreatedAt(LocalDateTime createAt) {
//        System.out.println(createAt);
        dateAudit = dateAudit.withCreatedAt(createAt);
    }

    @Override
    public void setUpdatedAt(LocalDateTime updatedAt) {
//        System.out.println(updatedAt);
        dateAudit = dateAudit.withUpdatedAt(updatedAt);
    }

    public void setTitle(String title){
        this.documentContent = documentContent.withTitleAt(title);
    }

    public void setContent(String content){
        this.documentContent = documentContent.withContentAt(content);
    }

}
