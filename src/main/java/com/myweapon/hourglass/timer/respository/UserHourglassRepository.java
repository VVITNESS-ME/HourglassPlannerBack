package com.myweapon.hourglass.timer.respository;

import com.myweapon.hourglass.timer.entity.UserCategory;
import com.myweapon.hourglass.timer.entity.UserHourglass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserHourglassRepository extends JpaRepository<UserHourglass,Long> {

    @Query("select uh from UserHourglass uh where uh.user_id=:id")
    public List<UserHourglass> findUserHourglassByUserId(@Param("id") Long id);

    @Query("select uh from UserHourglass uh where uh.user_id=:id and uh.end is null")
    public Optional<UserHourglass> findUserHourglassNotEndByUserId(@Param("id") Long id);
}
