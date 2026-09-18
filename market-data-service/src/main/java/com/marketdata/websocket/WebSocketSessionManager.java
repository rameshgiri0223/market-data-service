package com.marketdata.websocket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.WebSocketSession;

import org.springframework.stereotype.Component;

@Component
public class WebSocketSessionManager {

    private final Map<String, WebSocketSession> sessions =
            new ConcurrentHashMap<>();

    public void addSession(String username, WebSocketSession session)
            throws Exception {

        WebSocketSession oldSession = sessions.put(username, session);

        if (oldSession != null && oldSession.isOpen()) {
            oldSession.close();
        }
    }

    public void removeSession(String username) {
        sessions.remove(username);
    }

    public WebSocketSession getSession(String username) {
        return sessions.get(username);
    }
}
