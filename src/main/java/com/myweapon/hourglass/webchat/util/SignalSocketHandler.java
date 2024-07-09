package com.myweapon.hourglass.webchat.util;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class SignalSocketHandler extends TextWebSocketHandler {
    private final Map<String, WebSocketSession> sessions = new HashMap<>();
    private final Map<String, String> roomSessions = new HashMap<>();
    private Timer timer;

    // 5초마다 연결된 세션의 수를 전송
    @PostConstruct
    public void startTimer() {
        timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                removeClosedSessions();
                broadcastUserCount();
            }
        }, 0, 5000); // 5초마다 실행
    }

    @PreDestroy
    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Connection established with session id: " + session.getId());
        sessions.put(session.getId(), session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("Received message: " + message.getPayload() + " from session id: " + session.getId());

        Map<String, Object> msgMap = parseMessage(payload);
        String type = (String) msgMap.get("type");
        String room = (String) msgMap.get("room");

        switch (type) {
            case "join_room":
                roomSessions.put(session.getId(), room);
                session.sendMessage(new TextMessage("Joined room " + room));
                System.out.println("Joined room " + room);
                break;
            case "offer":
            case "answer":
            case "candidate":
                relayMessageToOtherPeers(session, room, payload);
                break;
        }
    }

    private void relayMessageToOtherPeers(WebSocketSession senderSession, String room, String message) throws IOException {
        for (Map.Entry<String, String> entry : roomSessions.entrySet()) {
            if (room.equals(entry.getValue()) && !entry.getKey().equals(senderSession.getId())) {
                WebSocketSession otherSession = sessions.get(entry.getKey());
                if (otherSession != null && otherSession.isOpen()) {
                    otherSession.sendMessage(new TextMessage(message));
                    System.out.println("Relayed message to session " + otherSession.getId() + ": " + message);
                }
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("Transport error: " + exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("Disconnected: " + session.getId() + " Status: " + status);
        sessions.remove(session.getId());
        roomSessions.remove(session.getId());
    }

    private Map<String, Object> parseMessage(String message) {
        Map<String, Object> map = new HashMap<>();
        message = message.replace("{", "").replace("}", "").replace("\"", "");
        String[] parts = message.split(",");
        for (String part : parts) {
            String[] keyValue = part.split(":");
            if (keyValue.length == 2) {
                map.put(keyValue[0].trim(), keyValue[1].trim());
            }
        }
        return map;
    }

    private void removeClosedSessions() {
        Iterator<Map.Entry<String, WebSocketSession>> iterator = sessions.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, WebSocketSession> entry = iterator.next();
            if (!entry.getValue().isOpen()) {
                iterator.remove();
            }
        }
    }

    private void broadcastUserCount() {
        int userCount = sessions.size();
        String message = String.format("{\"type\": \"user_count\", \"count\": %d}", userCount);

        for (WebSocketSession session : sessions.values()) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if(userCount > 0){
            System.out.println("Broadcasted user count: " + userCount);
        }
    }
}