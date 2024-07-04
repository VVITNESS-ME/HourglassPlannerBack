package com.myweapon.hourglass.timer.service;

import com.myweapon.hourglass.RestApiException;
import com.myweapon.hourglass.common.ApiResponse;
import com.myweapon.hourglass.security.UserDetailsImpl;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.security.enumset.ErrorType;
import com.myweapon.hourglass.timer.dto.HourglassEndRequest;
import com.myweapon.hourglass.timer.dto.HourglassResponse;
import com.myweapon.hourglass.timer.respository.HourglassRepository;
import com.myweapon.hourglass.timer.respository.TaskRepository;
import com.myweapon.hourglass.timer.respository.UserCategoryRepository;
import com.myweapon.hourglass.timer.respository.UserHourglassRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Member;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
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
//        Mockito.sp
//        User user = User.of("shane@naver.com","123","h");
//        UserDetailsImpl testUserDetailsImpl =  new UserDetailsImpl(user,user.getName());
//        when(userHourglassRepository.findUserHourglassNotEndByUserId())
//
//    }

//    @Test
//    @DisplayName("모래시계 정상적인 종료")
//    void testEndHourglass() {
//        HourglassEndRequest request = new HourglassEndRequest();
//        request.setTimeEnd("2023-10-17T09:45:00.000Z");
//        request.setHId(1L);
//        when(hourglassRepository.updateToEnd(any(),any(),any(),any(),any()))
//                .thenReturn(1);
//        HourglassResponse responseExpected = HourglassResponse.fromHId(1L);
//
//        ResponseEntity<ApiResponse<HourglassResponse>> response = hourglassService.endHourglass(request);
//
//        assertThat(response.getBody().getData()).isEqualTo(responseExpected);
//    }

//    @Test
//    @DisplayName("동작하는 모래시계가 없을 경우")
//    void testEndHourglassAbnormal(){
//        HourglassEndRequest request = new HourglassEndRequest();
//        request.setTimeEnd("2023-10-17T09:45:00.000Z");
//        request.setHId(1L);
//        when(hourglassRepository.updateToEnd(any(),any(),any(),any(),any()))
//                .thenReturn(0);
//        HourglassResponse responseExpected = HourglassResponse.fromHId(1L);
//
//        Throwable throwable = catchThrowable(()->hourglassService.endHourglass(request));
//
//        assertThat(throwable)
//                .isInstanceOf(RestApiException.class);
//    }

    @Test
    void pauseHourglass() {
    }

    @Test
    void resumeHourglass() {
    }
}