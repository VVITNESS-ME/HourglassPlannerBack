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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        String authToken = getJwt(request);

        if(authToken==null){
            request.setAttribute("exception", ErrorType.NO_JWT);
            filterChain.doFilter(request,response);
            return;
        }

        final String jwt;
        final String userEmail;
        //jwt토큰을 안 보낸 경우 그냥 return
        if(!startsWith(authToken,"Bearer+")){
            request.setAttribute("exception", ErrorType.NOT_VALID_TOKEN);
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authToken.substring(7);
        if(!jwtService.validateToken(jwt)){
            request.setAttribute("exception", ErrorType.NOT_VALID_TOKEN);
            filterChain.doFilter(request, response);
            return;
        }


        userEmail = jwtService.extractUserName(jwt);
        if(StringUtils.isNotEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
            setAuthentication(userDetails,request);
        }
        filterChain.doFilter(request,response);
    }

    private String getJwt(@NonNull HttpServletRequest request){
        String authToken = null;
        final Cookie[] cookies = request.getCookies();
        if(ObjectUtils.isEmpty(cookies)){
            request.setAttribute("exception", ErrorType.NO_JWT);
            return null;
        }
        for(Cookie cookie:cookies){
            if(cookie.getName().equals("token")){
                authToken = cookie.getValue();
            }
        }
        return authToken;
    }

    private void setAuthentication(UserDetails userDetails,@NonNull HttpServletRequest request){
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails,null,userDetails.getAuthorities()
        );
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }
}
