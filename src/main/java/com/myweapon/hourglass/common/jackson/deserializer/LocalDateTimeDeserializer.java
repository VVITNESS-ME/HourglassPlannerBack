package com.myweapon.hourglass.common.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.myweapon.hourglass.common.time.DateTimeUtils;

import java.io.IOException;
import java.time.LocalDateTime;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String valueString  = jsonParser.getValueAsString();

        return DateTimeUtils.stringIsoToLocalDateTime(valueString);
    }
}
