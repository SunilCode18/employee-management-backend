package com.springboot.app.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.app.Logger.MyLogger;
import com.springboot.app.model.User;
import com.springboot.app.service.JwtService;
import com.springboot.app.service.UserService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
public class UserController {
	
	private static final Logger logger =
            LogManager.getLogger(UserController.class);
	
	@Autowired
	private UserService service;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
	

	
	@PostMapping("/register")
	public User register(@RequestBody User user) {
		logger.info("User registration API called");
		user.setPassword(encoder.encode(user.getPassword()));
//		System.out.println(user.getPassword());
		service.saveUser(user);
		 logger.info("User registered successfully: {}",
	                user.getUsername());
		return user;
	}
	
	@PostMapping("/login")
	public String login(@RequestBody User user) {
		logger.info("Login API called for username: {}",
                user.getUsername());
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		System.out.println("Login API Called....");
		if(authentication.isAuthenticated()) {
			  logger.info("User authenticated successfully: {}",
	                    user.getUsername());
			return jwtService.generateToken(user.getUsername());
		}else {
			  logger.warn("Authentication failed for username: {}",
	                    user.getUsername());
			return "Login Failed";
		}
		
	}

}
