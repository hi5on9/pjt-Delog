
package com.web.blog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyList {
	
	private int rid;
	private int uid;
	private int likes;
	private int hates;
	private int type;
	
	
}
