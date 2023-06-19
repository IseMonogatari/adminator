package com.example.Preproject.websocket;

import com.example.Preproject.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class MyWebSocket extends TextWebSocketHandler {

    @Autowired
    private RequestService requestService;

    List<WebSocketSession> webSocketSessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        if (!webSocketSessions.contains(session)) {
            webSocketSessions.add(session);
        }
        session.sendMessage(getRequestNumber());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        webSocketSessions.remove(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        for (WebSocketSession wss : webSocketSessions) {
            try {
                wss.sendMessage(getRequestNumber());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private TextMessage getRequestNumber() {
        return new TextMessage(Integer.toString(requestService.getUsersByRequest().size()));
    }
}
