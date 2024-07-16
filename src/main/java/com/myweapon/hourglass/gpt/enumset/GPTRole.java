package com.myweapon.hourglass.gpt.enumset;

import lombok.Getter;

@Getter
public enum GPTRole {
    SYSTEM("system"),
    USER("user");

    private final String roleName;

    GPTRole(String roleName){
        this.roleName = roleName;
    }
}
