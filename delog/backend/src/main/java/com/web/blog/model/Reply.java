
package com.web.blog.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Reply {
	@Id
	private int rid;
	
	private int pid;
	private int prid;
	private int writer;
	private String contents;
	private Date create_date;
	private int likes;
	private int hates;
	private int type;
	
	
	public Reply(int pid, int type) {
		this.pid = pid;
		this.type = type;
	}
	
	public Reply(int pid, int prid, int type) {
		this.pid = pid;
		this.prid = prid;
		this.type = type;
	}
	
	public Reply(int pid, int prid, int writer, String contents) {
		this.pid = pid;
		this.prid = prid;
		this.writer = writer;
		this.contents = contents;
	}
}
