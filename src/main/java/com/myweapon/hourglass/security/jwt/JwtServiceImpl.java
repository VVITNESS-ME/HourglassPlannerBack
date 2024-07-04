package com.myweapon.hourglass.security.jwt;


import com.myweapon.hourglass.security.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService{
    private static final long TOKEN_TIME = 1000*60*60*24*180L;

    @Value("${token.signing.key}")
    private String jwtSigningKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init(){
        byte[] bytes = Base64.getDecoder().decode(jwtSigningKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    @Override
    public String extractUserName(String token) {
        return extractClaim(token,Claims::getSubject);
    }

    @Override
    public String generateToken(User user) {
        return generateToken(new HashMap<>(),user);
    }

    @Override
    public boolean validateToken(String token) {
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }
        catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }

        return false;
    }
    private <T> T extractClaim(String token, Function<Claims,T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }
    private String generateToken(Map<String, Objects> extraClaims,User user){
        return Jwts.builder()
                .setClaims(extraClaims).setSubject(user.getName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+TOKEN_TIME))
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
                .getBody();
    }
}
