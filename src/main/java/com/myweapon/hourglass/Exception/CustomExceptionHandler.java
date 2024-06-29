package com.myweapon.hourglass.Exception;

import com.myweapon.hourglass.common.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(RestApiException.class)
    protected ResponseEntity<ErrorResponse> handleRestApiException(RestApiException e){
        System.out.println(e.getErrorType().getMessage());
        return ResponseEntity
                .status(e.getErrorType().getCode())
                .body(ErrorResponse.of(e.getErrorType()));
    }
}
