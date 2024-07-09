package com.myweapon.hourglass.webchat.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class SignalSocketHandler extends TextWebSocketHandler {
    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("연결됐음. 현재 연결된 세션 수: " + sessions.size());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("메시지 받음!");
        System.out.println(message.getPayload());
        for (WebSocketSession ws : sessions) {
            if (ws.isOpen() && !ws.getId().equals(session.getId())) {
                ws.sendMessage(message);
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("연결이 끊어졌음. 현재 연결된 세션 수: " + sessions.size());
    }

    // 연결된 세션 수를 5초마다 보내는 메서드
    @Scheduled(fixedRate = 5000)
    public void sendConnectedSessionCount() {
        int count = sessions.size();
        // 세션 수가 0이면 메시지를 보내지 않음
        if(count == 0) {
            return;
        }
        System.out.println("현재 연결된 세션 수: " + count);

        String jsonMessage = "{\"message\": \"현재 연결된 세션 수: " + count + "\"}";
        TextMessage message = new TextMessage(jsonMessage);

        for (WebSocketSession session: sessions) {
            try {
                if (session.isOpen()) {
                    session.sendMessage(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}