package com.myweapon.hourglass.profile.service;

import com.myweapon.hourglass.RestApiException;
import com.myweapon.hourglass.profile.dto.NewPasswordRequest;
import com.myweapon.hourglass.profile.dto.NickNameChangeRequest;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.security.enumset.ErrorType;
import com.myweapon.hourglass.security.repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EntityManager entityManager;

    public void changeNickName (User user, NickNameChangeRequest request){
        user = entityManager.merge(user);
        String newNickname = request.getNewNickname();

        if(userRepository.findUserByName(newNickname).isPresent()){
            throw new RestApiException(ErrorType.DUPLICATED_NAME);
        }
        user.setName(newNickname);
        System.out.println(user.getName());
    }

    public void changePassword(User user, NewPasswordRequest request){
        user = entityManager.merge(user);
        if(!passwordEncoder.matches(request.getCurrentPassword(),user.getPassword())){
            throw new RestApiException(ErrorType.PASSWORD_NOT_CORRESPOND);
        }

        String newPassword = passwordEncoder.encode(request.getNewPassword());
        user.setPassword(newPassword);
    }

    @Transactional
    public void deleteUser(User user){
        user = entityManager.merge(user);
        user.setIsDeleted(true);
        user.setEmail(UUID.randomUUID().toString());
        user.setName(UUID.randomUUID().toString());
    }
}
