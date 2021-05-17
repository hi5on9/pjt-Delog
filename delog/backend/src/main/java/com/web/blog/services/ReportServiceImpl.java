package com.web.blog.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.blog.daos.ReportDao;
import com.web.blog.model.Prison;
import com.web.blog.model.Report;
import com.web.blog.model.ReportList;
import com.web.blog.model.ReportUser;

@Service
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	ReportDao reportDao;


	@Override
	public List<ReportUser> getReporterList(int uid) throws SQLException {
		//System.out.println("ReportService - getReporterList] uid : " + uid);
		return reportDao.getReporterList(uid);
	}


	@Override
	public int sendReport(Report report) throws SQLException {
		return reportDao.sendReport(report);
	}

	@Override
	public int deleteReport(int rid) throws SQLException {
		return reportDao.deleteReport(rid);
	}


	@Override
	public List<Report> getReport(Report report) throws SQLException {
		return reportDao.getReport(report);
	}


	@Override
	public List<ReportList> getAllReportList() throws SQLException {
		return reportDao.getAllReportList();
	}


	@Override
	public int sendPrison(Prison prison) throws SQLException {
		return reportDao.sendPrison(prison);
	}


	@Override
	public int deletePrison() throws SQLException {
		return reportDao.deletePrison();
	}


	@Override
	public Prison checkPrison(int uid) throws SQLException {
		return reportDao.checkPrison(uid);
	}


	@Override
	public Report getOneReport(int rid) throws SQLException {
		return reportDao.getOneReport(rid);
	}

	
	
}
