package com.myweapon.hourglass.security;

import com.myweapon.hourglass.entity.User;
import com.myweapon.hourglass.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


//인증이 완료되면 유저에 대한 정보를 database에서
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User findUser = userRepository.findUserByEmail(username).orElseThrow(()-> new UsernameNotFoundException("이메일이 존재하지 않습니다."));

        return new UserDetailsImpl(findUser, findUser.getEmail());
    }
}
