package com.myweapon.hourglass.security.jwt;

import com.myweapon.hourglass.security.enumset.ErrorType;
import com.myweapon.hourglass.security.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.*;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        try{
            jwtFilterLogic(request,response,filterChain);
        }catch (Exception e){
            filterChain.doFilter(request,response);
        }
    }

    private void jwtFilterLogic(@NonNull HttpServletRequest request,
                                @NonNull HttpServletResponse response,
                                @NonNull FilterChain filterChain) throws ServletException, IOException{
        String authCookie = null;
        final Cookie[] cookies = request.getCookies();
        if(ObjectUtils.isEmpty(cookies)){
            filterChain.doFilter(request,response);
            return;
        }
        for(Cookie cookie:cookies){
            if(cookie.getName().equals("token")){
                System.out.println(cookie.getValue());
                authCookie = cookie.getValue();
            }
        }
        final String jwt;
        final String userEmail;
        //jwt토큰을 안 보낸 경우 그냥 return
        if(isEmpty(authCookie)||!startsWith(authCookie,"Bearer+")){
            request.setAttribute("exception", ErrorType.NOT_VALID_TOKEN);
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authCookie.substring(7);
        userEmail = jwtService.extractUserName(jwt);
        //유저 정보를 가져와서 컨텍스트에 저장
        if(StringUtils.isNotEmpty((userEmail)) && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
            if(!jwtService.validateToken(jwt,userDetails)){
                request.setAttribute("exception", ErrorType.NOT_VALID_TOKEN);
                filterChain.doFilter(request,response);
                return;
            }
            //setAuthentication
            setAuthentication(userDetails,request);
        }
        filterChain.doFilter(request,response);
    }

    private void setAuthentication(UserDetails userDetails,@NonNull HttpServletRequest request){
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,null,userDetails.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        context.setAuthentication(authToken);
        SecurityContextHolder.setContext(context);
    }
}
