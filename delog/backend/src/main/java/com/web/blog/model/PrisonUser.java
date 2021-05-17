package com.web.blog.model;

import com.web.blog.model.user.User;

import lombok.Data;

@Data
public class PrisonUser {
	private User user;
	private Prison priosn;
	
}