package com.myweapon.hourglass.today.i.learned.repository;

import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.today.i.learned.entity.DocumentContent;
import com.myweapon.hourglass.today.i.learned.entity.TodayILearned;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface TodayILearnedRepository extends JpaRepository<TodayILearned,Long> {

    @Query("select t.documentContent from TodayILearned t where t.dateTodo =:dateTodo and t.user =:user")
    public Optional<DocumentContent> findDocumentContentByDateTodo(LocalDate dateTodo, User user);

    @Query("select t from TodayILearned t where t.dateTodo =:dateTodo and t.user =:user")
    public Optional<TodayILearned> findByDateTodo(LocalDate dateTodo, User user);

//    @Query("")
}
