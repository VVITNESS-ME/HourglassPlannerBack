package com.myweapon.hourglass.repository;

import com.myweapon.hourglass.UserData;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.security.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;


import static org.assertj.core.api.Assertions.*;

//@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(User.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    private EntityManager em;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveTest(){
        User user = User.builder().email(UserData.EMAIL).name(UserData.NAME).password(UserData.PASSWORD).build();
        userRepository.save(user);

        assertThat(user.getName()).isEqualTo(UserData.NAME);
        System.out.println(em);
        System.out.println("=====================================");
        em.flush();
        em.clear();
        em.close();
        System.out.println("-------------------------------------");
        em = null;
        System.out.println(em);
    }
    @Test
    public void findTest(){
        User user = User.builder().email(UserData.EMAIL).name(UserData.NAME).password(UserData.PASSWORD).build();
        userRepository.save(user);

        assertThat(user.getName()).isEqualTo(UserData.NAME);
        System.out.println("=====================================");
        System.out.println(em);
        em.flush();
        em.clear();
        em.close();
        em = null;
        User findUser = userRepository.findUserByEmail(UserData.EMAIL).get();

        assertThat(findUser.getEmail()).isEqualTo(UserData.EMAIL);
    }

//    @AfterEach
//    public void terminateTransaction(){
//        em.close();
//        em = null;
//    }
}