package com.myweapon.hourglass.timer.respository;

import com.myweapon.hourglass.UserData;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.security.repository.UserRepository;
import com.myweapon.hourglass.timer.entity.UserHourglass;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DataJpaTest
@Import(UserHourglass.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.REQUIRED)
class UserHourglassRepositoryTest {

    @Autowired
    private EntityManager em;
    @Autowired
    private UserHourglassRepository userHourglassRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    @Transactional(propagation = Propagation.REQUIRED)
    void setUp(){


    }

    @Test
    public void findAll(){
        List<UserHourglass> userHourglassList = userHourglassRepository.findAll();
        System.out.println(userHourglassList);
    }

    @Test
    public void findByUserId(){
        List<UserHourglass> userHourglass = userHourglassRepository.findUserHourglassByUserId(1L);
        System.out.println(userHourglass);
    }

    @Test
    public void findNotEndByUserId(){
        List<UserHourglass> userHourglass = userHourglassRepository.findUserHourglassByUserId(1L);
        System.out.println(userHourglass);
    }

}