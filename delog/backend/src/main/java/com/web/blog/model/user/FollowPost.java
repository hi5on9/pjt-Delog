package com.web.blog.model.user;

import com.web.blog.model.post.Post;

import lombok.Data;

@Data
public class FollowPost {
	private Follow follow;
	private Post post;
	private User user;
}
