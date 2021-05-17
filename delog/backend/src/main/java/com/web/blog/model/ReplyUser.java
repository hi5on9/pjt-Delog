package com.web.blog.model;

import com.web.blog.model.user.User;

import lombok.Data;

@Data
public class ReplyUser {
	private Reply reply;
	private User user;
	
	private int type;
	
}