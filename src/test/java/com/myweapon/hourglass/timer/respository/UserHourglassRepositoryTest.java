package com.myweapon.hourglass.timer.respository;

import com.myweapon.hourglass.category.entity.Category;
import com.myweapon.hourglass.category.entity.UserCategory;
import com.myweapon.hourglass.common.time.TimeUtils;
import com.myweapon.hourglass.schedule.entity.Task;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.security.repository.UserRepository;
import com.myweapon.hourglass.timer.dto.HourglassEndRequest;
import com.myweapon.hourglass.timer.dto.HourglassStartRequest;
import com.myweapon.hourglass.timer.dto.StudySummeryWithCategory;
import com.myweapon.hourglass.timer.dto.StudySummeryWithCategoryName;
import com.myweapon.hourglass.timer.entity.*;
import com.myweapon.hourglass.timer.enumset.DefaultCategory;
import jakarta.persistence.EntityManager;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import(UserHourglass.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class UserHourglassRepositoryTest {

    private static final Integer HOURGLASS_SAVED_NUM = DefaultCategory.values().length*3;
    private static final String EMAIL = "shane@naver.com";
    private static final String NAME = "hkj";
    private static final String PW = "12345678";
    private static final String STUDY_START = "2023-10-17T08:45:00.000Z";
    private static final Integer STUDY_TIME = 3;
    private static final Integer STUDY_INTERVAL = 4;
    private static final Integer HOUR = 1;
    private static final Float RATING = 4f;

    @Autowired
    private EntityManager em;
    @Autowired
    private UserHourglassRepository userHourglassRepository;
    @Autowired
    private UserRepository userRepository;

    private Long userId;

    /*
    userHourglass에 대한 정보를 가져오기 위해서는 category,user,userCategory, task, hourglass 모두 들어가 있어야 한다.
     */
    @BeforeEach
    @Transactional(propagation = Propagation.REQUIRED)
    void setUp(){
        User user = saveUser();

        List<Category> categories = saveCategories();

        List<UserCategory> userCategories = saveUserCategories(categories,user);

        saveHourglass(userCategories);
        em.clear();
    }

    @Test
    @DisplayName("findAll 뷰를 제대로 가져올 수 있는지")
    public void findAll(){
        List<UserHourglass> userHourglassList = userHourglassRepository.findAll();

        assertThat(userHourglassList).size().isEqualTo(HOURGLASS_SAVED_NUM);
    }

    @Test
    @DisplayName("특정 날에 실행한 모래시계들에 대한 데이터를 잘 가져오는지")
    public void testTodaySummaryTest(){
        LocalDateTime start = TimeUtils.inputFormatString(STUDY_START).truncatedTo(ChronoUnit.DAYS).plusDays(2);
        LocalDateTime end = start.plusDays(1);

        List<StudySummeryWithCategory> totalSummery = userHourglassRepository
                .studySummeryByDay(userId,start,end);
        System.out.println(totalSummery.size());

        List<StudySummeryWithCategoryName> resultSummary = new ArrayList<>();
        for(StudySummeryWithCategory summery : totalSummery){
            UserCategory userCategory = em.find(UserCategory.class,summery.getUserCategoryId());
            Category category = em.find(Category.class,userCategory.getId());

            resultSummary.add(StudySummeryWithCategoryName.of(summery,category));
        }

        assertThat(resultSummary.size())
                .isEqualTo(5);

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

    private User saveUser(){
        User user = User.of(EMAIL,PW,NAME);
        em.persist(user);
        userId = user.getId();
        return user;
    }

    private List<Category> saveCategories(){
        List<Category> categories = new ArrayList<>();
        for(DefaultCategory defaultCategory :DefaultCategory.values()){
            Category category = Category.of(defaultCategory);
            categories.add(category);
            em.persist(Category.of(defaultCategory));
        }
        return categories;
    }


    private List<UserCategory> saveUserCategories(List<Category> categories,User user){
        List<UserCategory> userCategories = new ArrayList<>();
        for(Category category: categories){
            UserCategory userCategory = UserCategory.of(user,category,DefaultCategory.OTHERS.getColor());
            em.persist(userCategory);
            userCategories.add(userCategory);
        }
        return userCategories;
    }

    private void saveHourglass(List<UserCategory> userCategories){
        String curTime = STUDY_START;
        for(UserCategory userCategory:userCategories){
            for(int i=0;i<3;i++){
                Task task = Task.defaultOf(userCategory);
                em.persist(task);
                HourglassStartRequest hourglassStartRequest = HourglassStartRequest.builder()
                        .timeStart(curTime)
                        .timeGoal(HOUR)
                        .build();

                Hourglass hourglass = Hourglass.toStartHourglass(hourglassStartRequest);
                hourglass.setTask(task);
                hourglass.endAsDefault(task,createHourglassEndRequest(hourglass.getId(),addHour(STUDY_TIME,curTime)));
                em.persist(hourglass);
                curTime = addHour(STUDY_INTERVAL,curTime);
            }
        }
    }

    private HourglassEndRequest createHourglassEndRequest(Long hid,String timeString){
        HourglassEndRequest hourglassEndRequest = new HourglassEndRequest();
        hourglassEndRequest.setHId(hid);
        hourglassEndRequest.setTimeEnd(timeString);
        hourglassEndRequest.setRating(RATING);
        hourglassEndRequest.setTimeBurst(HOUR);
        return hourglassEndRequest;
    }

    private String addHour(int hour,String curTime){
        int timeHour = Integer.parseInt(curTime.substring(11,13));
        int nextHour = timeHour+hour;
        if(nextHour>=24){
            nextHour -= 24;
            int timeDay = Integer.parseInt(curTime.substring(8,10));
            curTime = StringUtils.overlay(curTime,String.format("%02d", timeDay + 1),8,10);
        }
        return StringUtils.overlay(curTime,String.format("%02d",nextHour),11,13);
    }
}