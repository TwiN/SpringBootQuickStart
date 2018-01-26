package org.twinnation.quickstart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.twinnation.quickstart.bean.User;
import org.twinnation.quickstart.service.UserService;

import javax.servlet.http.HttpSession;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password, HttpSession session) {
		return userService.login(username, password, session);
	}
	
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "{}";
	}
	
	
	////////////////////////
	// NON-MAPPED METHODS //
	////////////////////////
	
	
	private User getUser(HttpSession session) {
		return (User)session.getAttribute("USER");
	}
	
}
