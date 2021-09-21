package com.globalkinetic.app.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.globalkinetic.app.model.LoginResponse;
import com.globalkinetic.app.model.User;
import com.globalkinetic.app.repo.UserRepository;
import com.globalkinetic.app.service.UserService;

@Service
public class UserImpl implements UserService, UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public User saveUser(User newUser) {

		newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
		User user = userRepo.save(newUser);
		return user;
	}

	@Override
	public List<User> getAllUser() {
		return userRepo.findAll();
	}

	@Override
	public LoginResponse login(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid Username or password");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				getAuthority());
	}

	List<GrantedAuthority> getAuthority() {
		GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN");
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(authority);

		return authorities;
	}

	@Override
	public User getUserByUsername(String username) {
		User user = userRepo.findByUsername(username);
		return user;
	}
}
