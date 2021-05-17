package com.web.blog.daos;

import java.sql.SQLException;
import java.util.List;

import com.web.blog.model.Prison;
import com.web.blog.model.PrisonUser;
import com.web.blog.model.Report;
import com.web.blog.model.ReportList;
import com.web.blog.model.ReportUser;

public interface ReportDao {
	
	// 유저 기준
	public List<ReportUser> getReporterList(int uid) throws SQLException;
	
	public int sendReport(Report report) throws SQLException;
	
	public int deleteReport(int rid) throws SQLException;
	
	public List<Report> getReport(Report report) throws SQLException;
	
	// 관리자 기준
	public List<ReportList> getAllReportList() throws SQLException;
	
	public int sendPrison(Prison prison) throws SQLException;
	
	public int deletePrison() throws SQLException;
	
	public Prison checkPrison(int uid) throws SQLException;
	
	public Report getOneReport(int rid) throws SQLException;

}
