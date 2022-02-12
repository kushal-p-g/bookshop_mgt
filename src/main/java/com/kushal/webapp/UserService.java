package com.kushal.webapp;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class UserService {

	@Autowired
	private UserRepository repo;
	
	public User login(String email, String password) {
		User user = repo.findByEmail(email);
		return user;
			
		}
			
}
