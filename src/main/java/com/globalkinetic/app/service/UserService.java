package com.globalkinetic.app.service;

import java.util.List;

import com.globalkinetic.app.model.LoginResponse;
import com.globalkinetic.app.model.User;

public interface UserService {
	
	User saveUser(User newUser);
	List<User>getAllUser();
	LoginResponse login(String username, String password);
	User getUserByUsername(String username);
}
