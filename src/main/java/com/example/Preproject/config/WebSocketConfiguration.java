package com.example.Preproject.config;

import com.example.Preproject.websocket.MyWebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    @Autowired
    public MyWebSocket myWebSocket;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myWebSocket, "/ws");
//        registry.addHandler(myWebSocket, "/ws").withSockJS();
    }
}
