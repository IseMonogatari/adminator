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
            System.out.println("Сессия не существует " + session);
            webSocketSessions.add(session);
        }
        System.out.println("Сессия добавлена " + session);
        System.out.println("\n Число подключений в начале: " + webSocketSessions.size() + "\n");
        session.sendMessage(getRequestNumber());
    }
//    WebUtils

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        webSocketSessions.remove(session);
        System.out.println("Сессия типо закрыта: " + session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("\nЧисло сессий открытых = " + webSocketSessions.size() + "\n");

        for (WebSocketSession wss : webSocketSessions) {
            try {
                System.out.println("\ngetRequestNumber() = " + getRequestNumber() + "\n");
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
