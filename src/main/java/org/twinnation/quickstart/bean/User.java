package org.twinnation.quickstart.bean;

import javax.persistence.*;
import java.io.Serializable;


@Entity
public class User implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String username;
	
	private String password;
	private boolean isAdmin;
	
	
	public User(String username, String password, boolean isAdmin) {
		this.username = username;
		this.password = password;
		this.isAdmin = isAdmin;
	}
	
	
	public User(String username, String password) {
		this(username, password, false);
	}
	
	
	public User() {}
	
	
	public Long getId() {
		return id;
	}
	
	
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public String getUsername() {
		return username;
	}
	
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	public String getPassword() {
		return password;
	}
	
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public boolean isAdmin() {
		return isAdmin;
	}
	
	
	public void setAdmin(boolean admin) {
		isAdmin = admin;
	}
	
	
	
	
}
