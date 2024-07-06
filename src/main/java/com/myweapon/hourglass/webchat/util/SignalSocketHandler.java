package com.myweapon.hourglass.webchat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class SignalSocketHandler extends TextWebSocketHandler {
    Logger logger = LoggerFactory.getLogger(SignalSocketHandler.class);

    private final Map<String, WebSocketSession> sessions = new HashMap<>();
    private final Map<String, String> roomSessions = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 세션이 연결되면 처리
        logger.info("Connected: " + session.getId());
        System.out.println("Connection established with session id: " + session.getId());
        sessions.put(session.getId(), session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 메시지를 받으면 처리
        String payload = message.getPayload();
        logger.info("Received: " + payload);
        System.out.println("Received message: " + message.getPayload() + " from session id: " + session.getId());
        // 예: { "type": "create_room", "room": "1" }
        // 예: { "type": "join_room", "room": "1" }

        Map<String, String> msgMap = parseMessage(payload);
        String type = msgMap.get("type");
        String room = msgMap.get("room");

        if ("create_room".equals(type)) {
            roomSessions.put(session.getId(), room);
            session.sendMessage(new TextMessage("Room " + room + " created."));
            logger.info("Room " + room + " created.");
        } else if ("join_room".equals(type)) {
            roomSessions.put(session.getId(), room);
            session.sendMessage(new TextMessage("Joined room " + room));
            logger.info("Joined room " + room);
            for (Map.Entry<String, String> entry : roomSessions.entrySet()) {
                if (room.equals(entry.getValue()) && !entry.getKey().equals(session.getId())) {
                    WebSocketSession otherSession = sessions.get(entry.getKey());
                    if (otherSession != null && otherSession.isOpen()) {
                        otherSession.sendMessage(new TextMessage("New participant joined room " + room));
                        logger.info("New participant joined room " + room);
                    }
                }
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        logger.error("Transport error: " + exception.getMessage(), exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 세션이 닫히면 처리
        logger.info("Disconnected: " + session.getId() + " Status: " + status);
        sessions.remove(session.getId());
        roomSessions.remove(session.getId());
    }

    private Map<String, String> parseMessage(String message) {
        // 간단한 메시지 파싱 (JSON 파싱 등)
        Map<String, String> map = new HashMap<>();
        String[] parts = message.split(",");
        for (String part : parts) {
            String[] keyValue = part.split(":");
            map.put(keyValue[0].trim(), keyValue[1].trim());
        }
        return map;
    }
}