package com.myweapon.hourglass.profile.controller;

import com.myweapon.hourglass.common.dto.ApiSuccess;
import com.myweapon.hourglass.profile.dto.NewPasswordRequest;
import com.myweapon.hourglass.profile.dto.NickNameChangeRequest;
import com.myweapon.hourglass.profile.service.ProfileService;
import com.myweapon.hourglass.security.UserDetailsImpl;
import com.myweapon.hourglass.security.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService profileService;
    @Transactional
    @PutMapping("/nickname")
    public ResponseEntity<ApiSuccess> changeNickName(@RequestBody NickNameChangeRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        profileService.changeNickName(user,request);

        return ResponseEntity.ok(ApiSuccess.body());
    }

    @Transactional
    @PutMapping("/password")
    public ResponseEntity<ApiSuccess> changePassword(@RequestBody NewPasswordRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        profileService.changePassword(user,request);

        return ResponseEntity.ok(ApiSuccess.body());
    }

    @PutMapping("/delete")
    public ResponseEntity<ApiSuccess> deleteUser(@AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        profileService.deleteUser(user);
//        System.out.println(user);

        return ResponseEntity.ok(ApiSuccess.body());
    }
}
