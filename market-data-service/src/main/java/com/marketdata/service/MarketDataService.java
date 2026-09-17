package com.marketdata.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.marketdata.dto.MarketData;
import com.marketdata.okx.OkxRestClinet;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
public class MarketDataService {

	private final OkxRestClinet okxRestClinet;

	public MarketDataService(OkxRestClinet okxRestClinet) {
		super();
		this.okxRestClinet = okxRestClinet;
	}

	public List<MarketData> getMarketData()
	{
		try {
			String response = okxRestClinet.marketData();
			
			ObjectMapper mapper = new ObjectMapper();
			
			JsonNode data = mapper.readTree(response).get("data");
			
			List<MarketData> markets = new ArrayList<MarketData>();
			
			for(JsonNode item: data) {
				
				String symbol = item.get("instId").asText();
				String price = item.get("last").asText();
				String volume = item.get("open24h").asText();
				double last = item.get("last").asDouble();
				double open = item.get("open24h").asDouble();
				
				double change = ((last-open) / open) * 100;
				
				markets.add(new MarketData(symbol, price, String.format("%.2f", change), volume));
			}
			
			return markets.stream().sorted((a,b) -> Double.compare(Double.parseDouble(b.getVolume24h()), Double.parseDouble(a.getVolume24h()))).limit(20).toList();
			
			

		} catch (Exception e) {
			throw new RuntimeException("Failed to proccess market data", e);
		}
		
		
	}

}
