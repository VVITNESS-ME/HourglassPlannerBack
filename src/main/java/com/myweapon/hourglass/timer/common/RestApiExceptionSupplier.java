package com.myweapon.hourglass.timer.common;

import com.myweapon.hourglass.common.exception.RestApiException;
import com.myweapon.hourglass.security.enumset.ErrorType;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

@Slf4j
public class RestApiExceptionSupplier implements Supplier<RestApiException> {
    private ErrorType errorType;

    @Builder
    public RestApiExceptionSupplier(ErrorType errorType){
        this.errorType = errorType;
    }
    @Override
    public RestApiException get() {
        log.error(errorType.getMessage());
        return new RestApiException(errorType);
    }

    public static RestApiExceptionSupplier of(ErrorType errorType){
        return RestApiExceptionSupplier.builder()
                .errorType(errorType)
                .build();
    }
}
