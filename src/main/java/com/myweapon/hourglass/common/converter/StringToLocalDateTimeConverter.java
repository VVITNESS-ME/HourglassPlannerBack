package com.myweapon.hourglass.common.converter;

import com.myweapon.hourglass.common.time.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(String source) {
        return DateTimeUtils.stringIsoToLocalDateTime(source);
    }
}
