package com.marketdata.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.marketdata.websocket.OrderBookWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final OrderBookWebSocketHandler orderBookWebSocketHandler;

    public WebSocketConfig(OrderBookWebSocketHandler orderBookWebSocketHandler) {
        this.orderBookWebSocketHandler = orderBookWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(
            WebSocketHandlerRegistry registry) {

        registry.addHandler(
                orderBookWebSocketHandler,
                "/ws/orderbook"
        ).setAllowedOrigins("*");
    }
}
