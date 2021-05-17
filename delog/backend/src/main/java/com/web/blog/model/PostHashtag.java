package com.web.blog.model;

import com.web.blog.model.post.Post;
import com.web.blog.model.user.User;

import lombok.Data;

@Data
public class PostHashtag {
	Hashtag hashtag;
	Post post;
	User user;
}
