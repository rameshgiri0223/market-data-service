package com.marketdata.dto;

public class MarketData {
	
	private String symbol;
	private String lastPrice;
	private String change24h;
	private String volume24h;
	
	

	public MarketData(String symbol, String lastPrice, String change24h, String volume24h) {
		super();
		this.symbol = symbol;
		this.lastPrice = lastPrice;
		this.change24h = change24h;
		this.volume24h = volume24h;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getLastPrice() {
		return lastPrice;
	}
	public void setLastPrice(String lastPrice) {
		this.lastPrice = lastPrice;
	}
	public String getChange24h() {
		return change24h;
	}
	public void setChange24h(String change24h) {
		this.change24h = change24h;
	}
	public String getVolume24h() {
		return volume24h;
	}
	public void setVolume24h(String volume24h) {
		this.volume24h = volume24h;
	}
	
	
	

}
