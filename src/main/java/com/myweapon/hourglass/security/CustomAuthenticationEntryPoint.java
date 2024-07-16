package com.myweapon.hourglass.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myweapon.hourglass.common.dto.ErrorResponse;
import com.myweapon.hourglass.security.enumset.ErrorType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorType errorType = (ErrorType)request.getAttribute("exception");
        if(errorType==null){
            exceptionHandler(response, ErrorType.UNKNOWN_ERROR_AUTHENTICATION_FAILED);
            return;
        }
        exceptionHandler(response,errorType);
    }
    public void exceptionHandler(HttpServletResponse response,ErrorType errorType){
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            String json = new ObjectMapper().writeValueAsString(ErrorResponse.of(errorType));
            response.getWriter().write(json);
            log.error(errorType.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
