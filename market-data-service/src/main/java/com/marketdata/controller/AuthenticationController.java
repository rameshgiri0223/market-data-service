package com.marketdata.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marketdata.dto.LoginRequest;
import com.marketdata.dto.LoginResponse;
import com.marketdata.service.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
	
	private final AuthenticationService authenticationService;
	
	public AuthenticationController(AuthenticationService authenticationService)
	{
		this.authenticationService = authenticationService;
	}
	
	@PostMapping("/login")
	public LoginResponse login(@RequestBody LoginRequest loginRequest)
	{
		boolean success = authenticationService.login(loginRequest.getUserName(), loginRequest.getPassword());
		
		if(success) {
			return new LoginResponse(true, "Login Successful");
		}
		
		return new LoginResponse(false, "Invalid Username or Password");
	}
	

}
