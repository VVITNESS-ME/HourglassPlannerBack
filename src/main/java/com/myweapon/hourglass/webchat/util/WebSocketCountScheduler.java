package com.myweapon.hourglass.webchat.util;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
@RequiredArgsConstructor
public class WebSocketCountScheduler {
    private final SignalSocketHandler signalSocketHandler;

    @Scheduled(fixedRate = 5000)
    public void sendConnectedSessionCount() {
        int count = signalSocketHandler.getSessions().size();
        System.out.println("Current connections: " + count);

        TextMessage message = new TextMessage(String.valueOf(count));

        for (WebSocketSession session: signalSocketHandler.getSessions()) {
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
