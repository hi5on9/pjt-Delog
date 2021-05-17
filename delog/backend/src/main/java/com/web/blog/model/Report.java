
package com.web.blog.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {
	@Id
	private int rid;
	private int reporterUid;
	private int reportedUid;
	private String reason;
	private Date create_date;
	
	
	public Report(int reporterUid, int reportedUid) {
		this.reporterUid = reporterUid;
		this.reportedUid = reportedUid;
	}
}
