package com.myweapon.hourglass.test;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService {
    public User findUserByOrder(Long orderId){
        return new User();
    }
    public int getScore(Long userId){
        System.out.println("점수를 확인합니다.");
        int score = 0;
        return score;
    }
    public void reduceScore(Long userId){
        System.out.println("점수를 낮춥니다.");
    }
    public void deactiveUser(Long userId){
        System.out.println("유저를 비활성화합니다.");
    }
}
