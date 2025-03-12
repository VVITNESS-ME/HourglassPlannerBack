package com.myweapon.hourglass.test;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserOrderManager {
    private final OrderService orderService;
    private final UserService userService;
    public void deactiveUserAndCancelOrder(Long userId){
        userService.deactiveUser(userId);
        for(Order order:orderService.findOrdersByUser(userId)){
            orderService.cancelOrder(order.getId());
        }
    }
}
