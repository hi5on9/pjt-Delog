
package com.web.blog.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ReportList {
	@Id
	private int rid;
	
	private String reporter;
	private String reported;
	private int reporterUid;
	private int reportedUid;
	private String reason;
	private Date create_date;
	private int term;
	
}
