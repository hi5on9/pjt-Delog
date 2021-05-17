package com.web.blog.model.post;

import com.web.blog.model.user.User;

import lombok.Data;

@Data
public class PostUser {
	private User user;
	private Post post;
	@Override
	public String toString() {
		return "PostUser] post= pid " + post.getPid() + "]";
	}
	
	
	
}
