package com.myweapon.hourglass.security.jwt;

import com.myweapon.hourglass.security.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUserName(String token);
    String generateToken(User user);
    boolean validateToken(String token, UserDetails userDetails);
}
