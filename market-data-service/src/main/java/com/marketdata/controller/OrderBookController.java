package com.marketdata.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marketdata.okx.OkxWebSocketClient;

@RestController
@RequestMapping("/api/orderbook")
public class OrderBookController {

    private final OkxWebSocketClient okxWebSocketClient;

    public OrderBookController(OkxWebSocketClient okxWebSocketClient) {
        this.okxWebSocketClient = okxWebSocketClient;
    }

    @GetMapping("/connect")
    public String connect(@RequestParam String symbol) {

        okxWebSocketClient.connect(symbol);

        return "Order book connection started for " + symbol;
    }
}