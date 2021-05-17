package com.web.blog.model.post;

import lombok.Data;

@Data
public class LikeList {
	
	private int rid;
	private int uid;
	private int likes;
	private int hates;
	
	
	public LikeList(int rid, int uid) {
		this.rid = rid;
		this.uid = uid;
	}
}
