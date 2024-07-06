package com.myweapon.hourglass.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    // 클라이언트 서버 도메인 주소
    @Value("${client.server.domain1}")
    private String clientServerDomain1;
    @Value("${client.server.domain2}")
    private String clientServerDomain2;
    @Value("${client.server.domain3}")
    private String clientServerDomain3;

    // 전역 CORS 설정
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(clientServerDomain1, clientServerDomain2, clientServerDomain3) // 필요한 도메인 추가
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}