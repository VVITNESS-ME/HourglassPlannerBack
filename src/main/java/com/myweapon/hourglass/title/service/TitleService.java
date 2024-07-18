package com.myweapon.hourglass.title.service;

import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.title.dto.TitleResponse;

public interface TitleService {

    public TitleResponse getTitleInfoByUserId(User user);
    public TitleResponse achieveTitle(User user, int titleId);
}
