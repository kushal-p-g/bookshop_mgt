package com.kushal.webapp.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kushal.webapp.entity.User;
import com.kushal.webapp.userRpository.UserRepository;
@Service
public class UserService {

	@Autowired
	private UserRepository repo;
	
	public User login(String email, String password) {
		User user = repo.findByEmail(email);
		return user;
			
		}
			
}

