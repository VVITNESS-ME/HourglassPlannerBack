package com.myweapon.hourglass.common.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String valueString  = jsonParser.getValueAsString();

        ZonedDateTime zonedDateTime = OffsetDateTime.parse(valueString)
                .atZoneSameInstant(ZoneId.of("Asia/Seoul"));

        return zonedDateTime.toLocalDateTime();
    }
}
