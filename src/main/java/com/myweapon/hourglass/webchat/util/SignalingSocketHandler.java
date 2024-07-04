package com.myweapon.hourglass.webchat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.TextMessage;

import java.util.concurrent.CopyOnWriteArrayList;

public class SignalingSocketHandler extends TextWebSocketHandler {
    private static final Logger log = LoggerFactory.getLogger(SignalingSocketHandler.class);
    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    // 새로 연결된 WebSocket 세션을 저장
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("New WebSocket session: " + session.getId());
        sessions.add(session);
    }

    // WebSocket 세션을 닫을 때 세션을 제거
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("Session closed: " + session.getId());
        sessions.remove(session);
    }

    // 메시지를 받았을 때 모든 WebSocket 세션에 메시지를 전송
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        // 메시지를 JSON 형식으로 파싱
        // 예: {"type": "offer", "sdp": "...", "clientId": "client1"}
        for (WebSocketSession s : sessions) {
            if (s.isOpen() && !s.getId().equals(session.getId())) {
                // 뭐 보내니
                log.info("Sending message: " + payload);

                s.sendMessage(new TextMessage(payload));
            }
        }
    }
}