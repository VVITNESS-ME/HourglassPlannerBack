package com.myweapon.hourglass.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myweapon.hourglass.common.ErrorResponse;
import com.myweapon.hourglass.security.enumset.ErrorType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

//    @Autowired
//    @Qualifier("handlerExceptionResolver")
//    private HandlerExceptionResolver resolver;
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorType errorType = (ErrorType)request.getAttribute("exception");
        if(errorType==null){
            exceptionHandler(response, ErrorType.UNKNOWN_ERROR);
            return;
        }

        if(errorType.equals(ErrorType.NOT_VALID_TOKEN)){
            exceptionHandler(response, ErrorType.NOT_VALID_TOKEN);
        }
    }
    public void exceptionHandler(HttpServletResponse response,ErrorType errorType){
        response.setStatus(errorType.getCode());
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
