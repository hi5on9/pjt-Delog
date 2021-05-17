package com.web.blog.model;

import com.web.blog.model.user.User;

import lombok.Data;

@Data
public class NoteUser {
	private Note note;
	private User user;
	
	
}