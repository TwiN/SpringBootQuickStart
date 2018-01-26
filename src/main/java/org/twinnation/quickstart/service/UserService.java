package org.twinnation.quickstart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.twinnation.quickstart.bean.User;
import org.twinnation.quickstart.repository.UserRepository;
import org.twinnation.quickstart.util.HashUtils;
import org.twinnation.quickstart.util.Utils;

import javax.servlet.http.HttpSession;


@Service
public class UserService {
	
	@Autowired private UserRepository userRepository;
	
	
	public UserService() {}
	
	
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	
	public String login(String username, String password, HttpSession session) {
		User user = getUserByUsername(username);
		if (user == null || !HashUtils.validateSha256WithSalt(password, user.getPassword())) {
			return Utils.jsonReply("ERROR", true, "MESSAGE", "Invalid username or password.");
		} else {
			User userWithoutPassword = new User(user.getUsername(), null, user.isAdmin());
			if (session != null) { // There is no reason session could be null unless we're running a test
				session.setAttribute("USER", userWithoutPassword); // Don't store the pwd
			}
			return Utils.jsonReply("ERROR", false, "USER", userWithoutPassword);
		}
	}
	
	
	public String register(String username, String password, boolean isAdmin) {
		if (userRepository.findByUsername(username) != null) {
			return Utils.jsonReply("ERROR", true, "MESSAGE", "Username is already taken");
		}
		if (username.length() < 4 || password.length() < 4) {
			return Utils.jsonReply("ERROR", true, "MESSAGE", "Username/password must be at least 4 characters");
		}
		User user = new User(username, HashUtils.sha256WithRandomSalt(password), isAdmin);
		userRepository.save(user);
		return Utils.jsonReply("ERROR", false, "MESSAGE", "Registered successfully");
	}
	
	
	
	public String register(String username, String password) {
		return register(username, password, false);
	}
	
	
	/**
	 * Checks if the user is connected (by looking through the user's session)
	 */
	public String checkIfUserLoggedIn(HttpSession session) {
		if (session.getAttribute("USER") != null) {
			User user = (User)session.getAttribute("USER");
			user.setPassword(null);
			return Utils.jsonReply("ERROR", false, "USER", user);
		}
		// user isn't connected, but since this method will be called every time a page is refreshed, we don't return
		// any ERROR, because the user might just be a guest looking around on the website.
		return "{ }";
	}
	
	
}
