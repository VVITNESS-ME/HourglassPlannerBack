package com.myweapon.hourglass.config;

import com.myweapon.hourglass.webchat.util.SignalSocketHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@Configuration
public class WebSocketConfig implements WebSocketConfigurer {
    // 클라이언트 서버 도메인 주소
    @Value("${client.server.domain1}")
    private String clientServerDomain1;
    @Value("${client.server.domain2}")
    private String clientServerDomain2;
    @Value("${client.server.domain3}")
    private String clientServerDomain3;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(signalHandler(), "/signal")
                .setAllowedOrigins(clientServerDomain1, clientServerDomain2, clientServerDomain3)
                .withSockJS();
    }

    @Bean
    public WebSocketHandler signalHandler() {
        return new SignalSocketHandler();
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(8192);
        return container;
    }
}