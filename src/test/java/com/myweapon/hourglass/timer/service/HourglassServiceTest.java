package com.myweapon.hourglass.timer.service;

import com.myweapon.hourglass.RestApiException;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.timer.dto.HourglassEndRequest;
import com.myweapon.hourglass.timer.dto.HourglassResponse;
import com.myweapon.hourglass.timer.respository.HourglassRepository;
import com.myweapon.hourglass.schedule.repository.TaskRepository;
import com.myweapon.hourglass.category.repository.UserCategoryRepository;
import com.myweapon.hourglass.timer.respository.UserHourglassRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class HourglassServiceTest {

    @Mock
    private HourglassRepository hourglassRepository;
    @Mock
    private UserCategoryRepository userCategoryRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserHourglassRepository userHourglassRepository;

    @InjectMocks //생성한 mock 객체들을 주입해준다.
    private HourglassService hourglassService;

//    @Test
//    @DisplayName("모래시계 시작 test")
//    void startHourglass() {
//        User user = Mockito.spy(User.of("shane@naver.com","12345678","h1"));
//        when(user.getId())
//                .thenReturn(any());
//        when(userHourglassRepository.findUserHourglassNotEndByUserId(any()))
//                .thenReturn(Optional.empty());
//        HourglassStartRequest request = new HourglassStartRequest();
//        request.setTimeStart("2023-10-17T08:45:00.000Z");
//        request.setTimeGoal(3600);
//        UserCategory userCategory = UserCategory.builder()
//                .user(user).category(new Category(DefaultCategory.OTHERS.getName())).build();
//        when(userCategoryRepository.findByUserAndCategoryName(user.getId(), DefaultCategory.OTHERS.getName()))
//                .thenReturn(Optional.of(userCategory));
//
//        Task task = Task.defaultOf(userCategory);
//
//
//        ResponseEntity<ApiResponse<HourglassResponse>> response = hourglassService.startHourglass(request,user);
//
//        assertThat(response.getStatusCode().value()).isEqualTo(200);
//    }

//    @Test
//    @DisplayName("모래시계 정상적인 종료")
//    void testEndHourglass() {
//        HourglassEndRequest request = new HourglassEndRequest();
//        request.setTimeEnd("2023-10-17T09:45:00.000Z");
//        request.setHId(1L);
//
//        when(hourglassRepository.updateToEnd(any(),any(),any(),any(),any(),any()))
//                .thenReturn(1);
//        HourglassResponse responseExpected = HourglassResponse.fromHId(1L);
//        User user = Mockito.mock(User.class);
//        when(user.getId())
//                .thenReturn(1L);
//        System.out.println(user.getId()+"!!!!!!!!!!!!!!!!!!!!!!!!!");
//
//        ResponseEntity<ApiResponse<HourglassSummaryResponse>> response = hourglassService.endHourglass(request,user);
//
////        assertThat(response.getBody().getData()).isEqualTo(responseExpected);
//    }

    @Test
    @DisplayName("동작하는 모래시계가 없을 경우")
    void testEndHourglassAbnormal(){
        HourglassEndRequest request = new HourglassEndRequest();
        request.setTimeEnd("2023-10-17T09:45:00.000Z");
        request.setHId(1L);
        when(hourglassRepository.updateToEnd(any(),any(),any(),any(),any(),any()))
                .thenReturn(0);
        HourglassResponse responseExpected = HourglassResponse.fromHId(1L);
        User user = Mockito.mock(User.class);
        when(user.getId())
                .thenReturn(1L);

        Throwable throwable = catchThrowable(()->hourglassService.endHourglass(request,user));

        assertThat(throwable)
                .isInstanceOf(RestApiException.class);
    }

    @Test
    void pauseHourglass() {
    }

    @Test
    void resumeHourglass() {
    }
}