package com.myweapon.hourglass.timer.entity;

import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.timer.dto.request.HourglassRunRequest;
import com.myweapon.hourglass.timer.dto.request.HourglassStopRequest;
import com.myweapon.hourglass.timer.enumset.HourglassState;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Slf4j
@AllArgsConstructor
@Builder
public class HourglassHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "horuglass_id",nullable = false)
    private Hourglass hourglass;

    @Column(nullable = false)
    private int burstSecond;

    @Column(nullable = false)
    private LocalDateTime instant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HourglassState state;

    public static HourglassHistory of(User user,Hourglass hourglass,LocalDateTime instant){
        return HourglassHistory.builder().user(user).hourglass(hourglass).instant(instant).build();
    }

    public static HourglassHistory ofStarted(User user,Hourglass hourglass, HourglassRunRequest request){
        return HourglassHistory.builder()
                .user(user)
                .hourglass(hourglass)
                .burstSecond(0)
                .state(HourglassState.START)
                .instant(request.getInstant())
                .build();
    }

    public static HourglassHistory ofPaused(User user, Hourglass hourglass, HourglassStopRequest request){
        return HourglassHistory.builder()
                .user(user)
                .hourglass(hourglass)
                .burstSecond(request.getBurstTime())
                .state(HourglassState.PAUSE)
                .instant(request.getInstant())
                .build();
    }

    public static HourglassHistory ofRestarted(User user,Hourglass hourglass, HourglassRunRequest request){
        return HourglassHistory.builder()
                .user(user)
                .hourglass(hourglass)
                .burstSecond(0)
                .state(HourglassState.RESTART)
                .instant(request.getInstant())
                .build();
    }

    public static HourglassHistory ofEnd(User user,Hourglass hourglass,HourglassStopRequest request){
        return HourglassHistory.builder()
                .user(user)
                .hourglass(hourglass)
                .burstSecond(request.getBurstTime())
                .state(HourglassState.END)
                .instant(request.getInstant())
                .build();
    }

}
