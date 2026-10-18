package com.springboot.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.app.model.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	
	User findByUsername(String username);

}
