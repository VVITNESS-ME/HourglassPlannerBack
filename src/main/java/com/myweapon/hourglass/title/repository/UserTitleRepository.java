package com.myweapon.hourglass.title.repository;

import com.myweapon.hourglass.title.Title;
import com.myweapon.hourglass.title.entity.UserTitle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTitleRepository extends JpaRepository<UserTitle, Long> {
    Optional<UserTitle> findByUserId(Long userId);
}
