package com.marketdata.okx;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class OkxWebSocketClient {

	private static final String OKX_WS_URL = "wss://ws.okx.com:8443/ws/v5/public";

	public void connect(String symbol) {

		System.out.println("Connecting to OKX for: " + symbol);

		StandardWebSocketClient client = new StandardWebSocketClient();

		client.execute(new TextWebSocketHandler() {

			@Override
			public void afterConnectionEstablished(WebSocketSession session) throws Exception {

				System.out.println("Connected to OKX");

				String message = """
						{
						  "op": "subscribe",
						  "args": [
						    {
						      "channel": "books",
						      "instId": "%s"
						    }
						  ]
						}
						""".formatted(symbol);

				session.sendMessage(new TextMessage(message));
			}

			@Override
			protected void handleTextMessage(WebSocketSession session, TextMessage message) {

				System.out.println("OKX Message: " + message.getPayload());
			}

			@Override
			public void handleTransportError(WebSocketSession session, Throwable exception) {

				System.out.println("OKX WebSocket Error: " + exception.getMessage());
			}
		}, OKX_WS_URL).whenComplete((session, error) -> {

			if (error != null) {
				System.out.println("OKX Connection Failed");
				error.printStackTrace();
			} else {
				System.out.println("OKX Connection Completed");
			}
		});
	}
}