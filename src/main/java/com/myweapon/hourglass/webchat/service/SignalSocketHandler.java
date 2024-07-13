package com.myweapon.hourglass.webchat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class SignalSocketHandler extends TextWebSocketHandler {

    private final Map<String, Set<WebSocketSession>> rooms = new ConcurrentHashMap<>();
    private final Map<WebSocketSession, String> clients = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String path = session.getUri().getPath();
        rooms.putIfAbsent(path, new CopyOnWriteArraySet<>());
        rooms.get(path).add(session);

        String peerId = UUID.randomUUID().toString();
        clients.put(session, peerId);
        System.out.println("연결됐음. peerID: " + peerId);

        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(Map.of("type", "peerId", "peerId", peerId))));

        for (WebSocketSession ws : rooms.get(path)) {
            if (ws.isOpen() && !ws.equals(session)) {
                ws.sendMessage(new TextMessage(objectMapper.writeValueAsString(Map.of("type", "join", "peerId", peerId))));
            }
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String path = session.getUri().getPath();
        Map<String, Object> data = objectMapper.readValue(message.getPayload(), Map.class);
        data.put("peerId", clients.get(session));
        String updatedMessage = objectMapper.writeValueAsString(data);

        for (WebSocketSession ws : rooms.get(path)) {
            if (ws.isOpen() && !ws.equals(session)) {
                ws.sendMessage(new TextMessage(updatedMessage));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String path = session.getUri().getPath();
        rooms.get(path).remove(session);
        if (rooms.get(path).isEmpty()) {
            rooms.remove(path);
        }
        clients.remove(session);
        System.out.println("연결이 끊어졌음: " + session.getUri().toString());
    }

    // 연결된 세션 수를 5초마다 보내는 메서드
    @Scheduled(fixedRate = 5000)
    public void sendConnectedSessionCount() {
        for (Map.Entry<String, Set<WebSocketSession>> entry : rooms.entrySet()) {
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