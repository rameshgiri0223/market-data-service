package com.marketdata.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marketdata.dto.MarketData;
import com.marketdata.service.MarketDataService;

@RestController
@RequestMapping("/api/markets")
public class MarketController {
	
	private final MarketDataService marketDataService;

	public MarketController(MarketDataService marketDataService) {
		super();
		this.marketDataService = marketDataService;
	}
	
	@GetMapping
	public List<MarketData> getMarkets()
	{
//		try {
//			return marketDataService.getMarketData();
//		} catch (Exception e) {
//			 return "Unable to Fetch Market Data From OKX";
//		}
		
		return marketDataService.getMarketData();
		
	}

}
