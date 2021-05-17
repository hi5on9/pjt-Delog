package com.web.blog.model.page;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostPage {
	String value;
	int page;
	int hid;
	String title;
}
