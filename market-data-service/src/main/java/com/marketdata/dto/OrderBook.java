package com.marketdata.dto;

import java.util.List;

public class OrderBook {

    private String symbol;
    private List<List<String>> bids;
    private List<List<String>> asks;

    public OrderBook(String symbol, List<List<String>> bids, List<List<String>> asks) {
        this.symbol = symbol;
        this.bids = bids;
        this.asks = asks;
    }

    public String getSymbol() {
        return symbol;
    }

    public List<List<String>> getBids() {
        return bids;
    }

    public List<List<String>> getAsks() {
        return asks;
    }
}
