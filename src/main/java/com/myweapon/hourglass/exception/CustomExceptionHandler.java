package com.myweapon.hourglass.exception;

import com.myweapon.hourglass.RestApiException;
import com.myweapon.hourglass.common.ErrorResponse;
import com.myweapon.hourglass.security.enumset.ErrorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(RestApiException.class)
    @ResponseBody
    protected ResponseEntity<ErrorResponse> handleRestApiException(RestApiException e){
        log.error(e.getErrorType().getMessage());
        return ResponseEntity
                .status(e.getErrorType().getCode())
                .body(ErrorResponse.of(e.getErrorType()));
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    protected ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException e){
        log.error("email is not exists");
        ErrorType error = ErrorType.NO_EMAIL_OR_PASSWORD;
        return ResponseEntity
                .status(error.getCode())
                .body(ErrorResponse.of(error));
    }
}
