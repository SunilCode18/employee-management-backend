package com.springboot.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.app.dao.UserRepo;
import com.springboot.app.model.User;
import com.springboot.app.model.UserPrinciple;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepo repo;
	
	public UserDetails loadUserByUsername(String username) {
		User user = repo.findByUsername(username);
		if(user==null) {
			System.out.println("User Not Found");
			throw new UsernameNotFoundException("Nor User Found");
		}
		return new UserPrinciple(user);
		
	}

}
