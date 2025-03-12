package com.myweapon.hourglass.config;

import com.myweapon.hourglass.common.time.Week;
import org.springframework.core.convert.converter.Converter;

public class StringToWeekConverter implements Converter<String, Week> {
    @Override
    public Week convert(String source) {
        return Week.fromWeekCode(Integer.parseInt(source));
    }
}
