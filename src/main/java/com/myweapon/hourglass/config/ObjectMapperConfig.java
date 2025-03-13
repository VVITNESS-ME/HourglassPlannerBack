package com.myweapon.hourglass.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.myweapon.hourglass.common.jackson.deserializer.LocalDateTimeDeserializer;
import com.myweapon.hourglass.common.jackson.serializer.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDateTime;

@Configuration
public class ObjectMapperConfig {
    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder){
        ObjectMapper objectMapper = builder.build();
        JavaTimeModule customJavaTimeModule = new JavaTimeModule();
        customJavaTimeModule.addSerializer(LocalDateTime.class,new LocalDateTimeSerializer());
        customJavaTimeModule.addDeserializer(LocalDateTime.class,new LocalDateTimeDeserializer());
        objectMapper.registerModule(customJavaTimeModule);
        return objectMapper;
    }
}