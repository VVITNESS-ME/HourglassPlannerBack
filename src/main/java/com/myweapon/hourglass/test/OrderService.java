package com.myweapon.hourglass.test;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class OrderService {
    private final UserService userService;
    public List<Order> findOrdersByUser(Long userId){
        return new ArrayList<>();
    }
    public void cancelOrder(Long orderId){
        System.out.println("주문을 취소합니다");
        User user = userService.findUserByOrder(orderId);
        userService.reduceScore(user.getId());
        if(userService.getScore(user.getId())<=0){
            userService.deactiveUser(userService.findUserByOrder(orderId).getId());
        }
    }
}
