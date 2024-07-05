package com.myweapon.hourglass.webchat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SignalingSocketHandler extends TextWebSocketHandler {
    private static final Logger logger = LoggerFactory.getLogger(SignalingSocketHandler.class);
    private final Map<String, WebSocketSession> sessions = new HashMap<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        logger.info("Received message from session {}: {}", session.getId(), message.getPayload());
        // 메시지를 다른 클라이언트로 브로드캐스트
        for (WebSocketSession webSocketSession : sessions.values()) {
            if (webSocketSession.isOpen() && !webSocketSession.getId().equals(session.getId())) {
                logger.info("Sending message to session {}", webSocketSession.getId());
                webSocketSession.sendMessage(new TextMessage(payload));
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.put(session.getId(), session);
        logger.info("Session {} connected", session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session.getId());
        logger.info("Session {} disconnected", session.getId());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        logger.error("WebSocket error in session {}: {}", session.getId(), exception.getMessage());
    }
}