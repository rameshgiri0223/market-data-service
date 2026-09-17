package com.marketdata.okx;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;


import org.springframework.stereotype.Component;

@Component
public class OkxRestClinet {
	
	private static final String OKX_URL = "https://openapi.okx.com/api/v5/market/tickers?instType=SPOT";
	
	private final HttpClient httpClient;
	
	public OkxRestClinet()
	{
		this.httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(15)).build();
	}
	
	public String marketData()
	{
		try {
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(OKX_URL)).timeout(Duration.ofSeconds(20)).header("Accept", "application/json").GET().build();
			
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			
			if(response.statusCode() != 200) {
				throw new RuntimeException("OKX API Returned Status: " + response.body());
			}
			
			return response.body();
		} catch (Exception e) {
			
			throw new RuntimeException("Failed to Fetch Data From OKX", e);
		}
	}

}
