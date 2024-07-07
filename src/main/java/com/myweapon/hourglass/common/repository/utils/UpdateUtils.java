package com.myweapon.hourglass.common.repository.utils;

public class UpdateUtils {
    public static Boolean isNotUpdated(int updatedNum){
        if(updatedNum==0){
            return true;
        }
        return false;
    }
}
