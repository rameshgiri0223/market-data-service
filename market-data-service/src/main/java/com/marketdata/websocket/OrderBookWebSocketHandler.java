package com.marketdata.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.marketdata.okx.OkxWebSocketClient;

@Component
public class OrderBookWebSocketHandler extends TextWebSocketHandler {

    private final OkxWebSocketClient okxWebSocketClient;
    private final WebSocketSessionManager sessionManager;

    public OrderBookWebSocketHandler(
            OkxWebSocketClient okxWebSocketClient,
            WebSocketSessionManager sessionManager) {

        this.okxWebSocketClient = okxWebSocketClient;
        this.sessionManager = sessionManager;
    }

    @Override
    public void afterConnectionEstablished(
            WebSocketSession session) throws Exception {

        String username =
                getUsername(session);

        if (username == null) {
            try {
                session.close(CloseStatus.BAD_DATA);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }

        session.getAttributes().put("username", username);

        sessionManager.addSession(username, session);

        System.out.println(
                "Browser connected: " + username
        );
    }

    @Override
    protected void handleTextMessage(
            WebSocketSession session,
            TextMessage message) {

        String symbol = message.getPayload();

        System.out.println(
                "Requested symbol: " + symbol
        );

        okxWebSocketClient.connect(symbol);
    }

    @Override
    public void afterConnectionClosed(
            WebSocketSession session,
            CloseStatus status) {

        String username =
                (String) session.getAttributes().get("username");

        if (username != null) {
            sessionManager.removeSession(username);
        }

        System.out.println(
                "Browser disconnected: " + username
        );
    }

    private String getUsername(WebSocketSession session) {

        String query =
                session.getUri().getQuery();

        if (query == null) {
            return null;
        }

        for (String parameter : query.split("&")) {

            String[] parts = parameter.split("=");

            if (parts.length == 2 &&
                    parts[0].equals("username")) {

                return parts[1];
            }
        }

        return null;
    }
}