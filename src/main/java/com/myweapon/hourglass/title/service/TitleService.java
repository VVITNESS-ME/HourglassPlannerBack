package com.myweapon.hourglass.title.service;

import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.title.dto.TitleDto;
import com.myweapon.hourglass.title.dto.TitleResponse;

import java.util.List;

public interface TitleService {

    public TitleResponse getTitleInfoByUserId(User user);
    public TitleDto achieveTitle(User user, int titleId);
    public List<TitleDto> getNewTitleList(User user);
}
