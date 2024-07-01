package com.myweapon.hourglass.Exception;

import com.myweapon.hourglass.common.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
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
}
