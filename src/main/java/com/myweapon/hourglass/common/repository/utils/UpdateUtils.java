package com.myweapon.hourglass.common.repository.utils;

public class UpdateUtils {
    /**
     * update가 되었는지 판단하는 메서드
     * @param updatedNum
     * @return
     */
    public static Boolean isNotUpdated(int updatedNum){
        if(updatedNum==0){
            return true;
        }
        return false;
    }
}
