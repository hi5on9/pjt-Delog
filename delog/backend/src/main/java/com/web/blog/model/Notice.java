
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
public class Notice {

	@Id
	private int nid;
	
	private String title;
	private String contents;
	private Date create_date;
	private int is_hidden;
	private int is_comment;
	private int is_main;
	private int category;
	private int hit;

}
