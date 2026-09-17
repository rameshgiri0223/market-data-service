package com.marketdata.service;

import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
	
	private static final String USERNAME = "admin";
	private static final String PASSWORD = "admin@123";
	
	public boolean login(String userName, String password)
	{
		return USERNAME.equals(userName) && PASSWORD.equals(password);
	}

}
