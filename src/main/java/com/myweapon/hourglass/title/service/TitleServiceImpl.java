package com.myweapon.hourglass.title.service;

import com.myweapon.hourglass.common.exception.RestApiException;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.security.enumset.ErrorType;
import com.myweapon.hourglass.title.dto.TitleResponse;
import com.myweapon.hourglass.title.dto.UserTitleDto;
import com.myweapon.hourglass.title.entity.UserTitle;
import com.myweapon.hourglass.title.repository.UserTitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TitleServiceImpl implements TitleService {
    private final UserTitleRepository userTitleRepository;

    @Override
    public TitleResponse getTitleInfoByUserId(User user) {
        UserTitleDto userTitleDto = getTitleByUserId(user.getId());
        return userTitleDto.toTitleResponse();
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
}
