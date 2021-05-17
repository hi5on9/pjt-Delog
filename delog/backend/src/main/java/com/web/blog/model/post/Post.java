
package com.web.blog.model.post;

import java.util.Date;
import java.util.List;

import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

	@Id
	private int pid;
	private String writer;
	private String title;
	private String contents;
	private int hit;
	private boolean is_hidden;
	private Date create_date;
	private String tags;
	private int cnt;
}
