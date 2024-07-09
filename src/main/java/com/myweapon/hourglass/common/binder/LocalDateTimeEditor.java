package com.myweapon.hourglass.common.binder;

import com.myweapon.hourglass.RestApiException;
import com.myweapon.hourglass.security.enumset.ErrorType;

import java.beans.PropertyEditorSupport;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateTimeEditor extends PropertyEditorSupport {
    private final DateTimeFormatter formatter;

    public LocalDateTimeEditor(){
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        try{
            setValue(LocalDateTime.parse(text,formatter));
        } catch (DateTimeParseException e){
            throw new IllegalArgumentException("Invalid date format.");
        }
    }

    @Override
    public String getAsText() {
        LocalDateTime value = (LocalDateTime) getValue();
        return (value != null ? value.format(formatter) : "");
    }
}
