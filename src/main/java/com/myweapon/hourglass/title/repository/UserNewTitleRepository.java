package com.myweapon.hourglass.title.repository;

import com.myweapon.hourglass.title.entity.UserNewTitle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserNewTitleRepository extends JpaRepository<UserNewTitle, Long> {
    List<UserNewTitle> findAllByUserId(Long userId);
}
