package com.myweapon.hourglass.title.service;

import com.myweapon.hourglass.common.exception.RestApiException;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.security.enumset.ErrorType;
import com.myweapon.hourglass.title.Title;
import com.myweapon.hourglass.title.dto.TitleDto;
import com.myweapon.hourglass.title.dto.TitleResponse;
import com.myweapon.hourglass.title.dto.UserTitleDto;
import com.myweapon.hourglass.title.entity.UserNewTitle;
import com.myweapon.hourglass.title.entity.UserTitle;
import com.myweapon.hourglass.title.repository.UserNewTitleRepository;
import com.myweapon.hourglass.title.repository.UserTitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TitleServiceImpl implements TitleService {
    private final UserTitleRepository userTitleRepository;
    private final UserNewTitleRepository userNewTitleRepository;
    @Override
    public TitleResponse getTitleInfoByUserId(User user) {
        UserTitleDto userTitleDto = getTitleByUserId(user.getId());
        return userTitleDto.toTitleResponse();
    }

    @Override
    public TitleDto achieveTitle(User user, int titleId) {
        return turnTrueTitle(user, titleId);
    }

    @Override
    public List<TitleDto> getNewTitleList(User user) {
        return getNewTitleListByUserId(user.getId());
    }

    private TitleDto turnTrueTitle(User user, int titleId) {
        Optional<UserTitle> userTitleOptional = userTitleRepository.findByUserId(user.getId());
        if(userTitleOptional.isPresent()){
            UserTitle userTitle = userTitleOptional.get();
            Title targetTitle = Title.getTitleOfNumber(titleId);
            userTitle.addTitle(targetTitle);

            UserNewTitle userNewTitle = UserNewTitle.builder()
                    .user(user)
                    .title(targetTitle)
                    .build();

            userTitleRepository.save(userTitle);
            userNewTitleRepository.save(userNewTitle);
            return userNewTitle.toTitleDto();
        }else {
            throw new RestApiException(ErrorType.NOT_KNOWN_ERROR);
        }
    }

    private UserTitleDto getTitleByUserId(Long userId){
        Optional<UserTitle> userTitleOptional = userTitleRepository.findByUserId(userId);
        if(userTitleOptional.isPresent()){
            UserTitle userTitle = userTitleOptional.get();
            return userTitle.ToUserTitleDto();
        }else {
            throw new RestApiException(ErrorType.NOT_KNOWN_ERROR);
        }
    }

    private List<TitleDto> getNewTitleListByUserId(Long userId){
        List<UserNewTitle> userNewTitleList  = userNewTitleRepository.findByUserId(userId);

        List<TitleDto> titleDtoList = new ArrayList<>();

        for (UserNewTitle userNewTitle : userNewTitleList) {
            titleDtoList.add(userNewTitle.toTitleDto());
            userNewTitleRepository.delete(userNewTitle);
        }
        return titleDtoList;
    }
}
