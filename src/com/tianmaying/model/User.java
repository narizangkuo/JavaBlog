package com.tianmaying.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.tianmaying.utils.DBHelper;
import com.tianmaying.utils.RowMapper;


public class User {

    private Long id;
    private String email;
    private String username;
    private String password;
    private String description;
    private String avatar;

    public User(String email, String username, String password) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    
    public User(String email, String username, String password, String description) {
        this(email, username, password);
        this.description = description;
    }

    public User() {
		// TODO Auto-generated constructor stub
	}

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static User getByUsername(String username) {
    	return (User)DBHelper.queryObject("select id, username, password, email, description from user where username=" + username, new UserMapper());
    }
    
    @Override
    public boolean equals(Object user) {
        if (!(user instanceof User)) {
            return false;
        }
        return this.getUsername().equals(((User) user).getUsername());
    }

    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}