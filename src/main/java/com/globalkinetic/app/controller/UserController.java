package com.globalkinetic.app.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.globalkinetic.app.model.LoginRequest;
import com.globalkinetic.app.model.LoginResponse;
import com.globalkinetic.app.model.User;
import com.globalkinetic.app.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/users")
	public User saveUser(@RequestBody User user) {

		return userService.saveUser(user);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest login, HttpServletRequest request) {
		final Authentication auth = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(login.getUsername().toLowerCase(), login.getPassword()));

		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			User user = userService.getUserByUsername(login.getUsername());
			if (user != null) {
				List<String> messages =  (List<String>) request.getSession().getAttribute("USER_ID");

				LoginResponse loginResponse = new LoginResponse();
				loginResponse.setId(user.getId());
				loginResponse.setSessionToken(request.getSession().getId());

				return ResponseEntity.ok(loginResponse);

			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid user or password");
	}
	
	@PostMapping("/logout")
	public ResponseEntity<?> logout(@RequestBody String sessionTokken, HttpServletRequest request) {
		request.getSession().invalidate();
		return  ResponseEntity.ok("");
	}
}
