package com.myweapon.hourglass.webchat.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class SignalSocketHandler extends TextWebSocketHandler {
    // 각 경로별로 세션을 관리하기 위해 Map을 사용합니다.
    private final Map<String, Set<WebSocketSession>> roomSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String path = session.getUri().getPath();
        roomSessions.putIfAbsent(path, new CopyOnWriteArraySet<>());
        roomSessions.get(path).add(session);
        System.out.println("연결됐음. 현재 연결된 세션 수: " + roomSessions.get(path).size() + " in room " + path);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String path = session.getUri().getPath();
        System.out.println("메시지 받음 in room " + path);
        System.out.println(message.getPayload());
        for (WebSocketSession ws : roomSessions.get(path)) {
            if (ws.isOpen() && !ws.getId().equals(session.getId())) {
                ws.sendMessage(message);
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String path = session.getUri().getPath();
        roomSessions.get(path).remove(session);
        System.out.println("연결이 끊어졌음. 현재 연결된 세션 수: " + roomSessions.get(path).size() + " in room " + path);
    }

    // 연결된 세션 수를 5초마다 보내는 메서드
    @Scheduled(fixedRate = 5000)
    public void sendConnectedSessionCount() {
        for (Map.Entry<String, Set<WebSocketSession>> entry : roomSessions.entrySet()) {
            String path = entry.getKey();
            int count = entry.getValue().size();
            if (count == 0) {
                continue;
            }
            System.out.println("현재 연결된 세션 수: " + count + " in room " + path);

            String jsonMessage = "{\"message\": \"현재 연결된 세션 수: " + count + "\"}";
            TextMessage message = new TextMessage(jsonMessage);

            for (WebSocketSession session : entry.getValue()) {
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
}