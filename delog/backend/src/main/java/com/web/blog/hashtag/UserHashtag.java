package com.web.blog.hashtag;

import java.util.List;

import com.web.blog.model.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserHashtag {
	User user;
	List<String> title;
}
