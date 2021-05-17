package com.web.blog.model.user;

import lombok.Data;

@Data
public class FollowUser {
	private User user;
	private Follow follow;
	
}
