package com.myweapon.hourglass.title;

import lombok.Getter;

@Getter
public enum Title {
    TITLE1(1, "시작이 반", "모든 타이머 첫번째 종료시"),
    TITLE2(2, "망부석", "3시간 동안 자리이탈/졸음 없음"),
    TITLE3(3, "원펀맨", "운동 카테고리 100시간 달성 (누적)"),
    TITLE4(4, "전집중 호흡", "1시간 동안 자리이탈/졸음 없음"),
    TITLE5(5, "그건 제 잔상입니다만", "30분이내 10회 이상 자리이탈/졸음"),
    TITLE6(6, "몰입의 경지", "일주일 이내 100시간이상 달성"),
    TITLE7(7, "작심삼분", "타이머 시작후 3분이내 종료"),
    TITLE8(8, "친구와 함께", "같이하기 모드 처음 참가한 사용자"),
    TITLE9(9, "도전 정신", "카테고리를 10개이상 생성한 사용자"),
    TITLE10(10, "무아지경을 경험한 자", "최초 지정한 타이머를 30분이상 지나서 완료"),
    TITLE11(11, "무아지경의 경지", "최초 지정한 타이머를 30분이상 지나서 완료를 7회이상 완료"),
    TITLE12(12, "포기하면 편해", "최초 지정한 타이머가 완료되기 전에 종료한 사용자"),
    TITLE13(13, "포기하면 편해", "새로 생성한 카테고리 작업 완료시");

    private final int index;
    private final String titleName;
    private final String achievementCondition;

    Title(int index, String titleName, String achievementCondition) {
        this.index = index;
        this.titleName = titleName;
        this.achievementCondition = achievementCondition;
    }
}
