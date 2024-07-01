package com.myweapon.hourglass.security;

import com.myweapon.hourglass.Exception.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        try{
            filterChain.doFilter(request,response);
        }catch(JwtException e){

        }
    }

    public void setErrorResponse(HttpStatus httpStatus,HttpServletRequest request,
                                 HttpServletResponse response,Throwable ex) throws IOException{

    }

}
