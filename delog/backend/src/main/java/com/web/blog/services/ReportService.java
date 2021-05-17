package com.web.blog.services;

import java.sql.SQLException;
import java.util.List;

import com.web.blog.model.Prison;
import com.web.blog.model.Report;
import com.web.blog.model.ReportList;
import com.web.blog.model.ReportUser;

public interface ReportService {

	public List<ReportUser> getReporterList(int uid) throws SQLException;
	
	public int sendReport(Report report) throws SQLException;
	
	public int deleteReport(int rid) throws SQLException;
	
	public List<Report> getReport(Report report) throws SQLException;
	
	public List<ReportList> getAllReportList() throws SQLException;
	
	public int sendPrison(Prison prison) throws SQLException;
	
	public int deletePrison() throws SQLException;
	
	public Prison checkPrison(int uid) throws SQLException;
	
	public Report getOneReport(int rid) throws SQLException;
}
