package com.myweapon.hourglass.config;

import com.myweapon.hourglass.webchat.util.SignalSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import sun.misc.SignalHandler;

@Configuration
public class WebSocketConfig implements WebSocketConfigurer {

    // WebSocketHandlerRegistry를 사용하여 WebSocket 핸들러를 등록
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(signalHandler(), "/signal") // register signalHandler for /signal path
                .setAllowedOrigins("*"); // allow all origins
    }

    // WebSocket 메시지를 처리하는 WebSocket 핸들러 빈을 생성
    @Bean
    public WebSocketHandler signalHandler() {
        return new SignalSocketHandler();
    }

    // WebSocket 설정을 위한 ServletServerContainerFactoryBean 빈을 생성
    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192); // 텍스트 메시지 버퍼 크기 설정
        container.setMaxBinaryMessageBufferSize(8192); // 바이너리 메시지 버퍼 크기 설정
        return container;
    }
}
