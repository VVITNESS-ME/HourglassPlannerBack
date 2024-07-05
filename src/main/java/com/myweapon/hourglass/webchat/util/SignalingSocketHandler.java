package com.myweapon.hourglass.webchat.util;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SignalingSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new HashMap<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("Received message from session " + session.getId() + ": " + payload);
        // 메시지를 다른 클라이언트로 브로드캐스트
        for (WebSocketSession webSocketSession : sessions.values()) {
            if (webSocketSession.isOpen() && !webSocketSession.getId().equals(session.getId())) {
                System.out.println("Sending message to session " + webSocketSession.getId());
                webSocketSession.sendMessage(new TextMessage(payload));
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.put(session.getId(), session);
        System.out.println("Session " + session.getId() + " connected");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session.getId());
        System.out.println("Session " + session.getId() + " disconnected");
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.err.println("WebSocket error in session " + session.getId() + ": " + exception.getMessage());
    }
}